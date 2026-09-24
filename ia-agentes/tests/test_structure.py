"""Valida la estructura de `agents/` y `skills/`, y que lo compilado en
`.claude/agents/` y `.claude/skills/` esté sincronizado con la fuente.

No requiere pytest ni ninguna dependencia externa — usa solo `unittest` de la
librería estándar.

Uso:
    python3 -m unittest discover -s tests -v
"""
from __future__ import annotations

import sys
import unittest
from pathlib import Path

TESTS_DIR = Path(__file__).resolve().parent
ROOT = TESTS_DIR.parent
sys.path.insert(0, str(ROOT / "compiler"))

import compile as c  # noqa: E402  (import tardío, después de tocar sys.path)

AGENTS_OUT = ROOT / ".claude" / "agents"
SKILLS_OUT = ROOT / ".claude" / "skills"


def agent_dirs() -> list[Path]:
    if not c.AGENTS_SRC.exists():
        return []
    return sorted(p for p in c.AGENTS_SRC.iterdir() if p.is_dir())


class TestAgentSourceStructure(unittest.TestCase):
    """Cada carpeta bajo agents/ debe tener el mínimo necesario para compilar."""

    def test_hay_al_menos_un_agente(self):
        self.assertTrue(agent_dirs(), "no se encontró ningún agente en agents/")

    def test_cada_agente_tiene_agent_yaml_y_prompt(self):
        for agent_dir in agent_dirs():
            with self.subTest(agent=agent_dir.name):
                self.assertTrue((agent_dir / "agent.yaml").exists(), "falta agent.yaml")
                self.assertTrue((agent_dir / "prompt.md").exists(), "falta prompt.md")

    def test_agent_yaml_tiene_los_campos_obligatorios(self):
        for agent_dir in agent_dirs():
            with self.subTest(agent=agent_dir.name):
                fields = c.parse_yaml(agent_dir / "agent.yaml", c.AGENT_REQUIRED)
                self.assertEqual(
                    fields["name"], agent_dir.name,
                    "el campo 'name' de agent.yaml debe coincidir con el nombre de la carpeta",
                )

    def test_prompt_no_esta_vacio(self):
        for agent_dir in agent_dirs():
            with self.subTest(agent=agent_dir.name):
                body = (agent_dir / "prompt.md").read_text(encoding="utf-8").strip()
                self.assertTrue(body, "prompt.md está vacío")


class TestSkillSourceStructure(unittest.TestCase):
    """Cada skill.yaml bajo skills/ debe tener exactamente un archivo de
    contenido y los campos obligatorios."""

    def test_hay_al_menos_una_skill(self):
        self.assertTrue(c.skill_dirs(), "no se encontró ninguna skill en skills/")

    def test_categorias_esperadas_existen(self):
        categorias = {p.relative_to(c.SKILLS_SRC).parts[0] for p, _ in c.skill_dirs()
                      if len(p.relative_to(c.SKILLS_SRC).parts) > 1}
        for esperada in ("architecture", "quality", "stacks"):
            with self.subTest(categoria=esperada):
                self.assertIn(esperada, categorias)

    def test_cada_skill_tiene_yaml_y_un_solo_md_de_contenido(self):
        for skill_dir, flat_name in c.skill_dirs():
            with self.subTest(skill=flat_name):
                fields = c.parse_yaml(skill_dir / "skill.yaml", c.SKILL_REQUIRED)
                self.assertEqual(fields["name"], flat_name,
                                  "el 'name' de skill.yaml debe ser <categoría>-<carpeta> (o <carpeta> sin categoría)")
                content_files = [p for p in skill_dir.glob("*.md") if p.name != "skill.yaml"]
                self.assertEqual(len(content_files), 1,
                                  f"se esperaba 1 archivo de contenido, hay {[p.name for p in content_files]}")
                self.assertTrue(content_files[0].read_text(encoding="utf-8").strip(),
                                 f"{content_files[0].name} está vacío")


class TestCompiledOutputInSync(unittest.TestCase):
    """Si alguien edita `.claude/agents/` o `.claude/skills/` a mano en vez de
    recompilar, o si queda un compilado huérfano sin fuente, esto falla."""

    def test_cada_agente_esta_compilado_y_coincide(self):
        for agent_dir in agent_dirs():
            with self.subTest(agent=agent_dir.name):
                compiled_path = AGENTS_OUT / f"{agent_dir.name}.md"
                self.assertTrue(compiled_path.exists(),
                                 "falta compilar — correr: python3 compiler/compile.py")
                expected = c.render_agent(agent_dir)
                actual = compiled_path.read_text(encoding="utf-8")
                self.assertEqual(expected, actual,
                                  f"{compiled_path.name} desactualizado — correr: python3 compiler/compile.py")

    def test_cada_skill_esta_compilada_y_coincide(self):
        for skill_dir, flat_name in c.skill_dirs():
            with self.subTest(skill=flat_name):
                compiled_path = SKILLS_OUT / flat_name / "SKILL.md"
                self.assertTrue(compiled_path.exists(),
                                 "falta compilar — correr: python3 compiler/compile.py")
                expected, _ = c.render_skill(skill_dir, flat_name)
                actual = compiled_path.read_text(encoding="utf-8")
                self.assertEqual(expected, actual,
                                  f"{compiled_path.name} desactualizada — correr: python3 compiler/compile.py")

    def test_no_hay_compilados_sin_fuente(self):
        fuentes_agentes = {d.name for d in agent_dirs()}
        huerfanos_agentes = sorted(p.stem for p in AGENTS_OUT.glob("*.md") if p.stem not in fuentes_agentes)
        self.assertEqual(huerfanos_agentes, [], "agentes compilados sin fuente en agents/")

        fuentes_skills = {flat for _, flat in c.skill_dirs()}
        huerfanas_skills = sorted(p.name for p in SKILLS_OUT.iterdir()
                                   if p.is_dir() and p.name not in fuentes_skills)
        self.assertEqual(huerfanas_skills, [], "skills compiladas sin fuente en skills/")


if __name__ == "__main__":
    unittest.main()
