#!/usr/bin/env python3
"""Compila las fuentes de `agents/` y `skills/` al único formato que Claude
Code auto-descubre: `.claude/agents/<nombre>.md` (frontmatter + prompt en un
archivo) y `.claude/skills/<nombre>/SKILL.md` (un directorio plano por
skill, sin subcarpetas de categoría).

Por qué existe esta capa: la fuente está organizada como se organizaría
cualquier repo de ingeniería de software agnóstico —

    agents/<nombre>/{agent.yaml, prompt.md}
    skills/<categoría>/<nombre>/{skill.yaml, <archivo de contenido>}
    skills/<nombre>/{skill.yaml, <archivo de contenido>}   (skills sin categoría, ej. procedimientos)
    skills/<categoría>/<nombre>/<sub>/{skill.yaml, ...}    (skill anidada, ej. stacks/java/java17-21-plus)

— pero Claude Code exige un directorio plano en `.claude/agents/` y
`.claude/skills/`. Este compilador es el único lugar que conoce esa
traducción: `skills/<categoría>/<nombre>/` se aplana a
`.claude/skills/<categoría>-<nombre>/`, y `agents/<nombre>/` a
`.claude/agents/<nombre>.md`. El resto del proyecto (agentes, hooks, docs)
referencia siempre la ruta compilada (`.claude/...`), nunca la fuente.

Sin dependencias externas: los `.yaml` acá son solo pares "clave: valor" de
una sola línea, el mismo formato que ya usa el frontmatter de Claude Code —
no hace falta PyYAML.

Ojo con los links relativos hacia fuera de `ia-agentes/` dentro de un
`prompt.md`/`rules.md`/etc.: este compilador copia el cuerpo *tal cual*, sin
reescribir rutas. `.claude/agents/<nombre>.md` queda a la misma profundidad
que `agents/<nombre>/` (3 niveles hasta la raíz del repo), así que un link
correcto en la fuente también lo es en lo compilado. Pero
`.claude/skills/<flat>/SKILL.md` siempre queda a 3 niveles, aunque la fuente
tenga categoría (`skills/architecture/hexagonal/`, 3 niveles, coincide) o no
(`skills/conventional-commit/`, 2 niveles, NO coincide) — escribí esos links
pensando en la profundidad de `.claude/skills/<flat>/`, que es lo que
Claude Code realmente lee.

Uso:
    python3 compiler/compile.py             # compila agentes y skills
    python3 -m unittest discover tests -v   # valida que la fuente y lo compilado coincidan
"""
from __future__ import annotations

import shutil
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]           # ia-agentes/
AGENTS_SRC = ROOT / "agents"
SKILLS_SRC = ROOT / "skills"
AGENTS_OUT = ROOT / ".claude" / "agents"
SKILLS_OUT = ROOT / ".claude" / "skills"

AGENT_REQUIRED = ("name", "description", "tools")
AGENT_FRONTMATTER_ORDER = ("name", "description", "tools", "model")
SKILL_REQUIRED = ("name", "description")


def parse_yaml(path: Path, required: tuple[str, ...]) -> dict[str, str]:
    """Parser mínimo: solo líneas "clave: valor", sin listas ni bloques."""
    fields: dict[str, str] = {}
    for raw_line in path.read_text(encoding="utf-8").splitlines():
        line = raw_line.rstrip()
        if not line or line.lstrip().startswith("#"):
            continue
        if ":" not in line:
            raise ValueError(f"{path}: línea sin 'clave: valor' -> {line!r}")
        key, _, value = line.partition(":")
        fields[key.strip()] = value.strip()
    missing = [f for f in required if f not in fields]
    if missing:
        raise ValueError(f"{path}: faltan campos obligatorios {missing}")
    return fields


# --- Agentes: agents/<nombre>/{agent.yaml,prompt.md} -> .claude/agents/<nombre>.md ---

def render_agent(agent_dir: Path) -> str:
    fields = parse_yaml(agent_dir / "agent.yaml", AGENT_REQUIRED)
    prompt_path = agent_dir / "prompt.md"
    if not prompt_path.exists():
        raise FileNotFoundError(f"{agent_dir}: falta prompt.md")
    body = prompt_path.read_text(encoding="utf-8").strip()

    frontmatter = ["---"]
    for key in AGENT_FRONTMATTER_ORDER:
        value = fields.get(key)
        if not value or (key == "model" and value == "default"):
            continue
        frontmatter.append(f"{key}: {value}")
    frontmatter.append("---")

    notice = (
        "<!-- GENERADO por compiler/compile.py — no editar a mano.\n"
        f"     Fuente: agents/{agent_dir.name}/agent.yaml + prompt.md -->"
    )
    return "\n".join(frontmatter) + "\n\n" + notice + "\n\n" + body + "\n"


def compile_agents(write: bool = True) -> list[str]:
    if not AGENTS_SRC.exists():
        return []
    names = []
    for agent_dir in sorted(p for p in AGENTS_SRC.iterdir() if p.is_dir()):
        if not (agent_dir / "agent.yaml").exists():
            continue
        content = render_agent(agent_dir)
        if write:
            AGENTS_OUT.mkdir(parents=True, exist_ok=True)
            (AGENTS_OUT / f"{agent_dir.name}.md").write_text(content, encoding="utf-8")
        names.append(agent_dir.name)
    return names


# --- Skills: skills/[<categoría>/]<nombre>/{skill.yaml,<contenido>.md[,extras]} ---
#     -> .claude/skills/[<categoría>-]<nombre>/SKILL.md [+ extras copiados tal cual]

def skill_dirs() -> list[tuple[Path, str]]:
    """(directorio fuente, nombre plano de salida) para cada skill.yaml bajo skills/."""
    if not SKILLS_SRC.exists():
        return []
    found: list[tuple[Path, str]] = []
    for skill_yaml in sorted(SKILLS_SRC.rglob("skill.yaml")):
        skill_dir = skill_yaml.parent
        rel = skill_dir.relative_to(SKILLS_SRC)
        # rel es "<nombre>" (sin categoría) o "<categoría>/<nombre>"
        flat_name = "-".join(rel.parts)
        found.append((skill_dir, flat_name))
    return found


def render_skill(skill_dir: Path, flat_name: str) -> tuple[str, list[Path]]:
    fields = parse_yaml(skill_dir / "skill.yaml", SKILL_REQUIRED)
    content_files = sorted(
        p for p in skill_dir.glob("*.md") if p.name != "skill.yaml"
    )
    if len(content_files) != 1:
        raise ValueError(
            f"{skill_dir}: se esperaba exactamente un .md de contenido, "
            f"encontrados {[p.name for p in content_files]}"
        )
    body = content_files[0].read_text(encoding="utf-8").strip()
    extras = [p for p in skill_dir.iterdir()
              if p.is_file() and p.name not in ("skill.yaml", content_files[0].name)]  # las subcarpetas son skills anidadas, se compilan aparte

    frontmatter = f"---\nname: {fields['name']}\ndescription: {fields['description']}\n---"
    notice = (
        "<!-- GENERADO por compiler/compile.py — no editar a mano.\n"
        f"     Fuente: skills/{skill_dir.relative_to(SKILLS_SRC)}/ -->"
    )
    del flat_name  # el nombre plano ya está en fields['name']; se deja explícito para claridad
    return frontmatter + "\n\n" + notice + "\n\n" + body + "\n", extras


def compile_skills(write: bool = True) -> list[str]:
    names = []
    for skill_dir, flat_name in skill_dirs():
        content, extras = render_skill(skill_dir, flat_name)
        out_dir = SKILLS_OUT / flat_name
        if write:
            out_dir.mkdir(parents=True, exist_ok=True)
            (out_dir / "SKILL.md").write_text(content, encoding="utf-8")
            for extra in extras:
                shutil.copy2(extra, out_dir / extra.name)
        names.append(flat_name)
    return names


def main() -> None:
    agent_names = compile_agents(write=True)
    for name in agent_names:
        print(f"✓ agent  {name} -> .claude/agents/{name}.md")
    skill_names = compile_skills(write=True)
    for name in skill_names:
        print(f"✓ skill  {name} -> .claude/skills/{name}/SKILL.md")
    print(f"\n{len(agent_names)} agente(s) + {len(skill_names)} skill(s) compilados en .claude/")


if __name__ == "__main__":
    main()
