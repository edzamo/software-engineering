"""Valida la estructura de agent-harness/agents/ y que lo compilado en
ia-agentes/.claude/agents/ esté sincronizado con la fuente.

No requiere pytest ni ninguna dependencia externa — usa solo `unittest` de la
librería estándar, para que "cp -r ia-agentes/ proyecto-nuevo/" alcance sin
tener que instalar nada.

Uso:
    python3 -m unittest discover -s agent-harness/tests -v
    (o, parado en agent-harness/tests/: python3 test_agents_structure.py)
"""
from __future__ import annotations

import sys
import unittest
from pathlib import Path

TESTS_DIR = Path(__file__).resolve().parent
HARNESS_DIR = TESTS_DIR.parent
sys.path.insert(0, str(HARNESS_DIR / "runners" / "claude_code"))

import compile as harness_compile  # noqa: E402  (import tardío, después de tocar sys.path)

AGENTS_SRC = HARNESS_DIR / "agents"
COMPILED_OUT = HARNESS_DIR.parent / ".claude" / "agents"


def agent_dirs() -> list[Path]:
    if not AGENTS_SRC.exists():
        return []
    return sorted(p for p in AGENTS_SRC.iterdir() if p.is_dir())


class TestAgentSourceStructure(unittest.TestCase):
    """Cada carpeta bajo agents/ debe tener el mínimo necesario para compilar."""

    def test_hay_al_menos_un_agente(self):
        self.assertTrue(agent_dirs(), "no se encontró ningún agente en agent-harness/agents/")

    def test_cada_agente_tiene_agent_yaml_e_instructions(self):
        for agent_dir in agent_dirs():
            with self.subTest(agent=agent_dir.name):
                self.assertTrue((agent_dir / "agent.yaml").exists(), "falta agent.yaml")
                self.assertTrue((agent_dir / "instructions.md").exists(), "falta instructions.md")

    def test_agent_yaml_tiene_los_campos_obligatorios(self):
        for agent_dir in agent_dirs():
            with self.subTest(agent=agent_dir.name):
                fields = harness_compile.parse_agent_yaml(agent_dir / "agent.yaml")
                for required in ("name", "description", "tools"):
                    self.assertIn(required, fields, f"falta el campo '{required}'")
                self.assertEqual(
                    fields["name"], agent_dir.name,
                    "el campo 'name' de agent.yaml debe coincidir con el nombre de la carpeta",
                )

    def test_instructions_no_esta_vacio(self):
        for agent_dir in agent_dirs():
            with self.subTest(agent=agent_dir.name):
                body = (agent_dir / "instructions.md").read_text(encoding="utf-8").strip()
                self.assertTrue(body, "instructions.md está vacío")


class TestCompiledOutputInSync(unittest.TestCase):
    """Si alguien edita .claude/agents/*.md a mano en vez de recompilar, esto falla."""

    def test_cada_agente_esta_compilado(self):
        for agent_dir in agent_dirs():
            with self.subTest(agent=agent_dir.name):
                compiled_path = COMPILED_OUT / f"{agent_dir.name}.md"
                self.assertTrue(
                    compiled_path.exists(),
                    f"falta compilar — correr: python3 runners/claude_code/compile.py",
                )

    def test_compilado_coincide_con_la_fuente(self):
        for agent_dir in agent_dirs():
            compiled_path = COMPILED_OUT / f"{agent_dir.name}.md"
            if not compiled_path.exists():
                continue  # ya lo reporta test_cada_agente_esta_compilado
            with self.subTest(agent=agent_dir.name):
                expected = harness_compile.render_agent(agent_dir)
                actual = compiled_path.read_text(encoding="utf-8")
                self.assertEqual(
                    expected, actual,
                    f"{compiled_path.name} desactualizado respecto a la fuente — "
                    f"correr: python3 runners/claude_code/compile.py",
                )


if __name__ == "__main__":
    unittest.main()
