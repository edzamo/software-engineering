#!/usr/bin/env python3
"""Detecta el stack de un proyecto: lenguaje, versión, build tool y cómo
correr los tests. Es el paso 0 de todos los agentes: el rol (arquitecto, TDD,
developer) es agnóstico; lo que cambia por lenguaje se decide con esta salida
y la skill `stacks-<lenguaje>` correspondiente (más las de `extra_skills`).

Uso:
    python3 .claude/scripts/detect_stack.py [raíz]    # raíz por defecto: $CLAUDE_PROJECT_DIR o cwd

Salida: JSON con una entrada por stack encontrado (un monorepo puede tener
varios, ej. backend Java + frontend TypeScript).

Sin dependencias externas: solo la librería estándar (3.9+).
"""
from __future__ import annotations

import json
import os
import re
import sys
from pathlib import Path

IGNORED_DIRS = {".git", "node_modules", ".venv", "venv", "build", "target", "dist", ".gradle", ".idea", "__pycache__"}
MAX_DEPTH = 3  # suficiente para monorepos tipo <raíz>/<servicio>/build.gradle


def read(path: Path) -> str:
    try:
        return path.read_text(encoding="utf-8", errors="ignore")
    except OSError:
        return ""


def first_match(patterns: list[str], text: str) -> str | None:
    for pattern in patterns:
        match = re.search(pattern, text)
        if match:
            return match.group(1)
    return None


def java_stack(module: Path) -> dict:
    gradle = next((module / f for f in ("build.gradle.kts", "build.gradle") if (module / f).exists()), None)
    build_text = read(gradle) if gradle else read(module / "pom.xml")
    version = first_match([
        r"JavaLanguageVersion\.of\((\d+)\)",
        r"JavaVersion\.VERSION_(\d+)",
        r"sourceCompatibility\s*=\s*['\"]?(\d+)",
        r"<maven\.compiler\.release>(\d+)<",
        r"<java\.version>(\d+)<",
        r"<release>(\d+)</release>",
    ], build_text) or first_match([r"java=(\d+)"], read(module / ".sdkmanrc")) \
        or first_match([r"^(\d+)"], read(module / ".java-version").strip())
    kotlin = "kotlin" in build_text.lower()

    if gradle:
        runner = "./gradlew" if (module / "gradlew").exists() else "gradle"
        test_all, test_one = f"{runner} test", f"{runner} test --tests '<Clase>'"
        build_tool = "gradle"
    else:
        runner = "./mvnw" if (module / "mvnw").exists() else "mvn"
        test_all, test_one = f"{runner} -q test", f"{runner} -q test -Dtest=<Clase>"
        build_tool = "maven"

    frameworks = [name for key, name in (("spring-boot-starter-webflux", "spring-webflux"),
                                         ("spring-boot-starter-web", "spring-mvc"),
                                         ("quarkus", "quarkus"), ("micronaut", "micronaut"))
                  if key in build_text]
    extra_skills = []
    if version and version.isdigit():
        extra_skills.append("stacks-java-java8-11" if int(version) <= 11 else "stacks-java-java17-21-plus")
    if any(f.startswith("spring") for f in frameworks):
        extra_skills.append("stacks-java-spring-boot")
    if "quarkus" in frameworks:
        extra_skills.append("stacks-java-quarkus")
    return {"language": "kotlin" if kotlin else "java", "version": version, "build_tool": build_tool,
            "frameworks": frameworks, "test_all": test_all, "test_one": test_one, "skill": "stacks-java",
            "extra_skills": extra_skills}


def typescript_stack(module: Path) -> dict:
    try:
        pkg = json.loads(read(module / "package.json") or "{}")
    except json.JSONDecodeError:
        pkg = {}
    deps = {**pkg.get("dependencies", {}), **pkg.get("devDependencies", {})}
    ts_version = first_match([r"(\d+\.\d+)"], deps.get("typescript", ""))
    node_version = first_match([r"(\d+)"], pkg.get("engines", {}).get("node", "")) \
        or first_match([r"v?(\d+)"], read(module / ".nvmrc").strip())

    if (module / "pnpm-lock.yaml").exists():
        pm = "pnpm"
    elif (module / "yarn.lock").exists():
        pm = "yarn"
    else:
        pm = "npm"

    if "vitest" in deps:
        runner, test_one = "vitest", "npx vitest run <archivo> -t '<nombre>'"
    elif "jest" in deps:
        runner, test_one = "jest", "npx jest <archivo> -t '<nombre>'"
    else:
        runner, test_one = "node:test", "node --test <archivo>"

    frameworks = [name for key, name in (("@nestjs/core", "nestjs"), ("express", "express"),
                                         ("fastify", "fastify"), ("next", "nextjs"))
                  if key in deps]
    has_ts = ts_version or (module / "tsconfig.json").exists()
    return {"language": "typescript" if has_ts else "javascript", "version": ts_version, "node": node_version,
            "build_tool": pm, "test_runner": runner, "frameworks": frameworks,
            "test_all": f"{pm} test", "test_one": test_one, "skill": "stacks-typescript",
            "extra_skills": ["stacks-typescript-nestjs"] if "nestjs" in frameworks else []}


def python_stack(module: Path) -> dict:
    pyproject = read(module / "pyproject.toml")
    version = first_match([r"requires-python\s*=\s*['\"][^0-9]*(\d+\.\d+)"], pyproject) \
        or first_match([r"^(\d+\.\d+)"], read(module / ".python-version").strip())
    if (module / "uv.lock").exists():
        tool, prefix = "uv", "uv run "
    elif (module / "poetry.lock").exists():
        tool, prefix = "poetry", "poetry run "
    else:
        tool, prefix = "pip", ""
    all_text = pyproject + read(module / "requirements.txt")
    frameworks = [name for key, name in (("fastapi", "fastapi"), ("django", "django"), ("flask", "flask"))
                  if key in all_text.lower()]
    return {"language": "python", "version": version, "build_tool": tool, "frameworks": frameworks,
            "test_all": f"{prefix}pytest -q", "test_one": f"{prefix}pytest -q <archivo>::<test>",
            "skill": "stacks-python"}


def dotnet_stack(module: Path) -> dict:
    project = next(iter(sorted(module.glob("*.csproj"))), None)
    text = read(project) if project else ""
    version = first_match([r"<TargetFramework>net(\d+(?:\.\d+)?)<"], text)
    frameworks = [name for key, name in (("Microsoft.NET.Sdk.Web", "aspnetcore"),
                                         ("Microsoft.EntityFrameworkCore", "efcore"))
                  if key in text]
    return {"language": "csharp", "version": version, "build_tool": "dotnet", "frameworks": frameworks,
            "test_all": "dotnet test", "test_one": "dotnet test --filter \"FullyQualifiedName~<Clase>\"",
            "skill": "stacks-dotnet", "extra_skills": []}


DETECTORS = (
    (("pom.xml", "build.gradle", "build.gradle.kts"), java_stack),
    (("package.json",), typescript_stack),
    (("*.csproj",), dotnet_stack),
    (("pyproject.toml", "setup.py", "requirements.txt"), python_stack),
)


def walk(root: Path, depth: int = 0):
    yield root
    if depth >= MAX_DEPTH:
        return
    try:
        children = sorted(p for p in root.iterdir() if p.is_dir() and p.name not in IGNORED_DIRS)
    except OSError:
        return
    for child in children:
        yield from walk(child, depth + 1)


def detect(root: Path) -> list[dict]:
    stacks: list[dict] = []
    for module in walk(root):
        for markers, detector in DETECTORS:
            if any(any(module.glob(marker)) for marker in markers):
                info = detector(module)
                info["module"] = str(module.relative_to(root)) or "."
                stacks.append(info)
    return stacks


def main() -> None:
    root = Path(sys.argv[1] if len(sys.argv) > 1 else os.environ.get("CLAUDE_PROJECT_DIR", ".")).resolve()
    stacks = detect(root)
    print(json.dumps({"root": str(root), "stacks": stacks}, indent=2, ensure_ascii=False))
    if not stacks:
        print("Sin stack detectado: proyecto nuevo — preguntá lenguaje y versión antes de generar nada.",
              file=sys.stderr)


if __name__ == "__main__":
    main()
