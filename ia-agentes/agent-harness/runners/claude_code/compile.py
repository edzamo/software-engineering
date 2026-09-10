#!/usr/bin/env python3
"""Compila los agentes de agent-harness/agents/ al formato que Claude Code
realmente consume: un único archivo `.claude/agents/<nombre>.md` con
frontmatter YAML + system prompt en el mismo archivo.

Fuente de cada agente: agent-harness/agents/<nombre>/agent.yaml + instructions.md
Salida: ia-agentes/.claude/agents/<nombre>.md (lo que Claude Code auto-descubre)

Sin dependencias externas: agent.yaml se limita a pares "clave: valor" de una
sola línea (mismo formato que ya usa el frontmatter de Claude Code), así que
no hace falta PyYAML ni ninguna librería fuera de la estándar de Python.

Uso:
    python3 compile.py            # compila todos los agentes
    python3 -m unittest discover ../../tests   # valida que la fuente y lo compilado coincidan
"""
from __future__ import annotations

from pathlib import Path

HARNESS_DIR = Path(__file__).resolve().parents[2]          # agent-harness/
AGENTS_SRC = HARNESS_DIR / "agents"
COMPILED_OUT = HARNESS_DIR.parent / ".claude" / "agents"    # ia-agentes/.claude/agents/

REQUIRED_FIELDS = ("name", "description", "tools")
FRONTMATTER_ORDER = ("name", "description", "tools", "model")


def parse_agent_yaml(path: Path) -> dict[str, str]:
    """Parser mínimo para el subconjunto de YAML que usa agent.yaml: solo
    líneas "clave: valor", sin listas anidadas ni bloques multilínea."""
    fields: dict[str, str] = {}
    for raw_line in path.read_text(encoding="utf-8").splitlines():
        line = raw_line.rstrip()
        if not line or line.lstrip().startswith("#"):
            continue
        if ":" not in line:
            raise ValueError(f"{path}: línea sin 'clave: valor' -> {line!r}")
        key, _, value = line.partition(":")
        fields[key.strip()] = value.strip()

    missing = [f for f in REQUIRED_FIELDS if f not in fields]
    if missing:
        raise ValueError(f"{path}: faltan campos obligatorios {missing}")
    return fields


def render_agent(agent_dir: Path) -> str:
    """Devuelve el contenido final del .md compilado, sin escribirlo a disco
    (así tests/test_agents_structure.py puede comparar contra lo ya compilado)."""
    fields = parse_agent_yaml(agent_dir / "agent.yaml")
    instructions_path = agent_dir / "instructions.md"
    if not instructions_path.exists():
        raise FileNotFoundError(f"{agent_dir}: falta instructions.md")
    body = instructions_path.read_text(encoding="utf-8").strip()

    frontmatter = ["---"]
    for key in FRONTMATTER_ORDER:
        value = fields.get(key)
        if not value:
            continue
        if key == "model" and value == "default":
            continue  # "default" = hereda el modelo de la sesión, no se declara
        frontmatter.append(f"{key}: {value}")
    frontmatter.append("---")

    notice = (
        "<!-- GENERADO por agent-harness/runners/claude_code/compile.py — no editar a mano.\n"
        f"     Fuente: agent-harness/agents/{agent_dir.name}/agent.yaml + instructions.md -->"
    )

    return "\n".join(frontmatter) + "\n\n" + notice + "\n\n" + body + "\n"


def compile_all(write: bool = True) -> list[tuple[str, Path]]:
    if not AGENTS_SRC.exists():
        raise SystemExit(f"No existe {AGENTS_SRC}")

    results: list[tuple[str, Path]] = []
    for agent_dir in sorted(p for p in AGENTS_SRC.iterdir() if p.is_dir()):
        if not (agent_dir / "agent.yaml").exists():
            continue  # carpeta sin agent.yaml todavía, se ignora (ej. en construcción)
        content = render_agent(agent_dir)
        out_path = COMPILED_OUT / f"{agent_dir.name}.md"
        if write:
            COMPILED_OUT.mkdir(parents=True, exist_ok=True)
            out_path.write_text(content, encoding="utf-8")
        results.append((agent_dir.name, out_path))
    return results


def main() -> None:
    results = compile_all(write=True)
    for name, out_path in results:
        print(f"✓ {name} -> {out_path.relative_to(HARNESS_DIR.parent)}")
    print(f"\n{len(results)} agente(s) compilado(s) en {COMPILED_OUT.relative_to(HARNESS_DIR.parent)}")


if __name__ == "__main__":
    main()
