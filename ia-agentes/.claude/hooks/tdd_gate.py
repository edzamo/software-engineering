#!/usr/bin/env python3
"""Hook PreToolUse de Claude Code: bloquea escribir lógica de negocio sin un
test previo que la especifique (TDD obligatorio, no sugerido).

Los prompts de los agentes *piden* TDD; este hook lo *hace cumplir*, porque
corre sobre cada Write/Edit — del hilo principal y de cualquier subagente.
Es agnóstico de lenguaje (Java, Kotlin, TypeScript, Python) y de arquitectura
(hexagonal, clean, onion): lo que protege es el núcleo de negocio, se llame
como se llame la carpeta.

Regla: un archivo de producción dentro de una capa de negocio (`domain/`,
`application/`, `usecases/`, `entities/`, `core/`) solo se puede escribir si
algún archivo de test del proyecto ya lo nombra (clase o módulo). Es la
secuencia de TDD: primero el test que lo referencia (RED), después el código
(GREEN).

Se permite sin test (scaffold o contratos, no es lógica):
- crear un archivo NUEVO que sea stub (`UnsupportedOperationException`,
  `NotImplementedError`, `throw new Error('Not implemented')`, `TODO()`);
- contratos y datos: interfaces, enums, records, tipos, `Protocol`/ABC,
  excepciones, `package-info.java`, `__init__.py`;
- los propios archivos de test.

Todo lo demás (adapters, controllers, config) queda fuera de alcance: se
cubre con tests de integración, no con esta regla por archivo.

Escape explícito (usar a conciencia, ej. un spike descartable):
    TDD_GATE=off claude

Entrada: JSON del hook por stdin. Salida: exit 0 = permitir; exit 2 = bloquear
(Claude Code le muestra el stderr al modelo, que debe corregir el rumbo).
"""
from __future__ import annotations

import ast
import json
import os
import re
import sys
from pathlib import Path

SOURCE_EXTENSIONS = {".java", ".kt", ".ts", ".mts", ".py"}
BUSINESS_DIRS = {"domain", "application", "usecases", "use-cases", "use_cases", "entities", "core"}
PROJECT_MARKERS = ("pom.xml", "build.gradle", "build.gradle.kts", "package.json", "pyproject.toml", "setup.py")
IGNORED_DIRS = {".git", "node_modules", ".venv", "venv", "build", "target", "dist", ".gradle", "__pycache__"}
STUB_MARKERS = re.compile(r"UnsupportedOperationException|NotImplementedError|not implemented|\bTODO\(\)", re.I)

STUB_HINT = {
    ".java": "cuerpos que lancen `new UnsupportedOperationException()`",
    ".kt": "cuerpos con `TODO()`",
    ".ts": "cuerpos con `throw new Error('Not implemented')`",
    ".mts": "cuerpos con `throw new Error('Not implemented')`",
    ".py": "cuerpos con `raise NotImplementedError`",
}


# --- Qué archivo es -----------------------------------------------------------

def is_test_file(path: Path) -> bool:
    posix = path.as_posix()
    name = path.name
    return (
        "/src/test/" in posix
        or "/__tests__/" in posix
        or re.search(r"\.(spec|test)\.m?[jt]sx?$", name) is not None
        or re.match(r"(test_.*|.*_test|conftest)\.py$", name) is not None
        or any(part in ("test", "tests") for part in path.parts[:-1])
    )


def project_root(path: Path) -> Path:
    for parent in path.parents:
        if any((parent / marker).exists() for marker in PROJECT_MARKERS):
            return parent
    env_root = os.environ.get("CLAUDE_PROJECT_DIR")
    return Path(env_root) if env_root else path.parent


def in_business_layer(path: Path, root: Path) -> bool:
    try:
        parts = path.relative_to(root).parts[:-1]
    except ValueError:
        parts = path.parts[:-1]
    return any(part.lower() in BUSINESS_DIRS for part in parts)


# --- Qué contiene -------------------------------------------------------------

def resulting_content(tool_name: str, tool_input: dict, path: Path) -> str:
    """Contenido que tendrá el archivo si se permite la operación."""
    if tool_name == "Write":
        return tool_input.get("content", "")
    current = path.read_text(encoding="utf-8") if path.exists() else ""
    edits = tool_input.get("edits") or [tool_input]  # MultiEdit o Edit
    for edit in edits:
        old, new = edit.get("old_string", ""), edit.get("new_string", "")
        current = current.replace(old, new) if edit.get("replace_all") else current.replace(old, new, 1)
    return current


def _python_has_logic(content: str) -> bool:
    """True si alguna función tiene cuerpo real (no `...`, `pass`, docstring
    ni `raise NotImplementedError`). Sin funciones reales = contrato o dato."""
    try:
        tree = ast.parse(content)
    except SyntaxError:
        return True
    for node in ast.walk(tree):
        if not isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef)):
            continue
        for stmt in node.body:
            if isinstance(stmt, ast.Pass):
                continue
            if isinstance(stmt, ast.Expr) and isinstance(stmt.value, ast.Constant):
                continue  # docstring o `...`
            if isinstance(stmt, ast.Raise) and "NotImplementedError" in ast.unparse(stmt):
                continue
            return True
    return False


def is_contract_or_data(path: Path, content: str) -> bool:
    stem, ext = path.stem, path.suffix
    if ext in (".java", ".kt"):
        if stem == "package-info" or stem.endswith("Exception"):
            return True
        pattern = rf"\b(interface|enum|record|enum class|data class)\s+{re.escape(stem)}\b"
        return re.search(pattern, content) is not None
    if ext in (".ts", ".mts"):
        # Solo tipos: sin clases, funciones ni arrow functions = nada que testear.
        return re.search(r"\bclass\b|\bfunction\b|=>", content) is None
    if ext == ".py":
        return stem == "__init__" or not _python_has_logic(content)
    return False


def test_references(root: Path, path: Path) -> bool:
    """¿Algún test nombra este archivo? Clase Java (`Mascota`), módulo TS
    (`mascota.entity`) o módulo Python (`mascota`) — todos son el stem."""
    word = re.compile(rf"(?<![\w]){re.escape(path.stem)}(?![\w])")
    for dirpath, dirnames, filenames in os.walk(root):
        dirnames[:] = [d for d in dirnames if d not in IGNORED_DIRS]
        for filename in filenames:
            candidate = Path(dirpath) / filename
            if candidate.suffix not in SOURCE_EXTENSIONS or not is_test_file(candidate):
                continue
            try:
                if word.search(candidate.read_text(encoding="utf-8", errors="ignore")):
                    return True
            except OSError:
                continue
    return False


# --- Decisión -----------------------------------------------------------------

def decide(payload: dict) -> tuple[bool, str]:
    """Devuelve (permitido, motivo). Separado de main() para poder testearlo."""
    if os.environ.get("TDD_GATE", "").lower() == "off":
        return True, "TDD_GATE=off"

    tool_name = payload.get("tool_name", "")
    if tool_name not in ("Write", "Edit", "MultiEdit"):
        return True, "no es una escritura"

    tool_input = payload.get("tool_input", {})
    path = Path(tool_input.get("file_path", ""))
    if path.suffix not in SOURCE_EXTENSIONS or path.name.endswith(".d.ts"):
        return True, "no es código fuente"
    if is_test_file(path):
        return True, "es un test"

    root = project_root(path)
    if not in_business_layer(path, root):
        return True, "fuera de las capas de negocio"

    content = resulting_content(tool_name, tool_input, path)
    if is_contract_or_data(path, content):
        return True, "contrato o dato"
    if tool_name == "Write" and not path.exists() and STUB_MARKERS.search(content):
        return True, "stub nuevo del scaffold"
    if test_references(root, path):
        return True, "hay un test que lo especifica"

    return False, (
        f"TDD: bloqueado escribir lógica en {path.name} — ningún test del proyecto "
        f"({root}) nombra a `{path.stem}` todavía.\n"
        "Orden obligatorio: 1) tdd-reviewer escribe el test que lo referencia y lo "
        "EJECUTA mostrando RED; 2) recién ahí se implementa (GREEN) y se vuelve a "
        "correr la suite.\n"
        f"Si es solo scaffold, crealo como archivo nuevo con {STUB_HINT.get(path.suffix, 'stubs')}."
    )


def main() -> None:
    try:
        payload = json.load(sys.stdin)
    except json.JSONDecodeError:
        sys.exit(0)  # entrada inesperada: no bloquear por un fallo del propio hook
    allowed, reason = decide(payload)
    if not allowed:
        print(reason, file=sys.stderr)
        sys.exit(2)
    sys.exit(0)


if __name__ == "__main__":
    main()
