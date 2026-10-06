#!/usr/bin/env python3
"""Verifica que las referencias de los CLAUDE.md de un proyecto resuelvan en el proyecto o en el kit.

Uso: scripts/check-project.py <raíz-del-repo>
Regla (PROJECT-CONTRACT.md §2): `.claude/<x>` → <proyecto>/.claude/<x>, luego <kit>/.claude/<x>.
También entiende `~/.claude/kit/<x>` y rutas cortas del kit (`stacks/…`, `architecture/…`, `quality/…`).
Sale con código 1 si alguna referencia no resuelve.
"""
import os, re, sys

KIT = os.path.realpath(os.path.join(os.path.dirname(os.path.abspath(__file__)), ".."))
SHORT = ("stacks/", "architecture/", "quality/")
TICKS = re.compile(r"`([^`\n]+)`")
LINKS = re.compile(r"\]\(([^)\s]+)\)")


def candidates(text):
    for m in TICKS.finditer(text):
        yield m.group(1).strip()
    for m in LINKS.finditer(text):
        yield m.group(1).strip()


def resolve(ref, root, here):
    """Devuelve la ruta que existe, o None."""
    ref = ref.split("#")[0].rstrip("/")
    if ref.startswith("~/.claude/kit/"):
        paths = [os.path.join(KIT, ref[len("~/.claude/kit/"):])]
    elif ref.startswith("../") or ref.startswith("./"):
        paths = [os.path.normpath(os.path.join(here, ref))]
    elif ref.startswith(".claude/"):
        paths = [os.path.join(root, ref), os.path.join(KIT, ref)]
    elif ref.startswith(SHORT):
        sub = os.path.join(".claude", "skills", ref)
        paths = [os.path.join(root, sub), os.path.join(KIT, sub)]
    else:
        return "skip"
    for p in paths:
        if os.path.exists(p):
            return p
    return None


def is_path(ref):
    if any(c in ref for c in "<>*${}|") or " " in ref or "…" in ref:
        return False
    if ref.startswith(("http", "mailto")):
        return False
    return ref.startswith((".claude/", "~/.claude/kit/", "../", "./") + SHORT)


def main():
    root = os.path.realpath(sys.argv[1] if len(sys.argv) > 1 else ".")
    files = []
    for d, dirs, fs in os.walk(root):
        dirs[:] = [x for x in dirs if x not in ("node_modules", ".git", "dist", ".astro", ".next")]
        if "CLAUDE.md" in fs:
            files.append(os.path.join(d, "CLAUDE.md"))
    missing, checked = [], 0
    for f in sorted(files):
        here = os.path.dirname(f)
        text = open(f, encoding="utf-8").read()
        for ref in sorted(set(candidates(text))):
            if not is_path(ref):
                continue
            r = resolve(ref, root, here)
            if r == "skip":
                continue
            checked += 1
            if r is None:
                missing.append((os.path.relpath(f, root), ref))
    for f, ref in missing:
        print(f"FALTA  {f}: {ref}")
    print(f"{len(files)} CLAUDE.md, {checked} referencias, {len(missing)} sin resolver")
    sys.exit(1 if missing else 0)


main()
