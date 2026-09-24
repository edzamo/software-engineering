"""Valida `.claude/scripts/detect_stack.py` con proyectos mínimos de cada stack.

Uso:
    python3 -m unittest discover -s agent-harness/tests -v
"""
from __future__ import annotations

import json
import sys
import tempfile
import unittest
from pathlib import Path

TESTS_DIR = Path(__file__).resolve().parent
SCRIPTS_DIR = TESTS_DIR.parent / ".claude" / "scripts"
sys.path.insert(0, str(SCRIPTS_DIR))

import detect_stack  # noqa: E402  (import tardío, después de tocar sys.path)


class TestDetectStack(unittest.TestCase):

    def setUp(self):
        self._tmp = tempfile.TemporaryDirectory()
        self.root = Path(self._tmp.name)

    def tearDown(self):
        self._tmp.cleanup()

    def only_stack(self) -> dict:
        stacks = detect_stack.detect(self.root)
        self.assertEqual(len(stacks), 1, stacks)
        return stacks[0]

    def test_java_gradle_con_toolchain(self):
        (self.root / "build.gradle").write_text(
            "plugins { id 'org.springframework.boot' }\n"
            "java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }\n"
            "dependencies { implementation 'org.springframework.boot:spring-boot-starter-webflux' }")
        (self.root / "gradlew").write_text("")
        stack = self.only_stack()
        self.assertEqual((stack["language"], stack["version"], stack["build_tool"]), ("java", "21", "gradle"))
        self.assertIn("spring-webflux", stack["frameworks"])
        self.assertEqual(stack["test_one"], "./gradlew test --tests '<Clase>'")

    def test_java_maven_version_desde_pom(self):
        (self.root / "pom.xml").write_text("<properties><java.version>17</java.version></properties>")
        stack = self.only_stack()
        self.assertEqual((stack["version"], stack["build_tool"]), ("17", "maven"))

    def test_typescript_nest_con_jest(self):
        (self.root / "package.json").write_text(json.dumps({
            "dependencies": {"@nestjs/core": "^11.0.0"},
            "devDependencies": {"typescript": "^5.7.2", "jest": "^29.0.0"},
            "engines": {"node": ">=22"}}))
        stack = self.only_stack()
        self.assertEqual((stack["language"], stack["version"], stack["node"]), ("typescript", "5.7", "22"))
        self.assertEqual((stack["test_runner"], stack["frameworks"]), ("jest", ["nestjs"]))

    def test_python_uv_fastapi(self):
        (self.root / "pyproject.toml").write_text(
            '[project]\nrequires-python = ">=3.12"\ndependencies = ["fastapi"]\n')
        (self.root / "uv.lock").write_text("")
        stack = self.only_stack()
        self.assertEqual((stack["version"], stack["build_tool"]), ("3.12", "uv"))
        self.assertEqual(stack["test_all"], "uv run pytest -q")
        self.assertIn("fastapi", stack["frameworks"])

    def test_monorepo_con_dos_servicios_de_distinto_lenguaje(self):
        (self.root / "api").mkdir()
        (self.root / "api" / "pom.xml").write_text("<java.version>25</java.version>")
        (self.root / "web").mkdir()
        (self.root / "web" / "package.json").write_text('{"devDependencies": {"typescript": "5.8.0"}}')
        (self.root / "web" / "node_modules" / "x").mkdir(parents=True)
        (self.root / "web" / "node_modules" / "x" / "package.json").write_text("{}")
        stacks = {s["module"]: s["language"] for s in detect_stack.detect(self.root)}
        self.assertEqual(stacks, {"api": "java", "web": "typescript"})

    def test_java_21_spring_devuelve_skills_de_version_y_framework(self):
        (self.root / "pom.xml").write_text(
            "<java.version>21</java.version><artifactId>spring-boot-starter-webflux</artifactId>")
        stack = self.only_stack()
        self.assertEqual(stack["extra_skills"], ["stacks-java-java17-21-plus", "stacks-java-spring-boot"])

    def test_java_11_usa_la_skill_legacy(self):
        (self.root / "pom.xml").write_text("<java.version>11</java.version>")
        self.assertEqual(self.only_stack()["extra_skills"], ["stacks-java-java8-11"])

    def test_java_quarkus(self):
        (self.root / "pom.xml").write_text("<java.version>21</java.version><artifactId>quarkus-rest</artifactId>")
        self.assertIn("stacks-java-quarkus", self.only_stack()["extra_skills"])

    def test_nestjs_devuelve_skill_de_framework(self):
        (self.root / "package.json").write_text(
            '{"dependencies": {"@nestjs/core": "^11.0.0"}, "devDependencies": {"typescript": "5.8.0", "jest": "30"}}')
        self.assertEqual(self.only_stack()["extra_skills"], ["stacks-typescript-nestjs"])

    def test_dotnet_csproj(self):
        (self.root / "Api.csproj").write_text(
            '<Project Sdk="Microsoft.NET.Sdk.Web"><PropertyGroup><TargetFramework>net8.0</TargetFramework></PropertyGroup></Project>')
        stack = self.only_stack()
        self.assertEqual((stack["language"], stack["version"], stack["skill"]), ("csharp", "8.0", "stacks-dotnet"))
        self.assertIn("aspnetcore", stack["frameworks"])
        self.assertEqual(stack["test_all"], "dotnet test")

    def test_proyecto_vacio_no_detecta_nada(self):
        self.assertEqual(detect_stack.detect(self.root), [])


if __name__ == "__main__":
    unittest.main()
