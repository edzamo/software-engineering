#!/usr/bin/env bash
# PreToolUse gate (Write|Edit) — refuerzo mecánico de TDD-001.
#
# No sustituye al juicio del orquestador ni de tdd-driver-agent: es defensa en
# profundidad. Bloquea escribir/editar código fuente de producción salvo que
# exista evidencia reciente (marker file) de RED_CONFIRMED o de andamiaje
# autorizado (Fase 1b). Los agentes de solo lectura y los archivos fuera de
# `src`/código fuente (skills, docs, pipelines, config) nunca se bloquean.
#
# Markers (ver .claude/.tdd-state/README.md):
#   .claude/.tdd-state/red_confirmed   — toca tdd-driver-agent tras RED_CONFIRMED
#   .claude/.tdd-state/scaffolding_ok  — toca el orquestador al abrir Fase 1b
# Ambos expiran a los FRESHNESS_MINUTES minutos (por defecto 30).

set -euo pipefail

FRESHNESS_MINUTES=30
STATE_DIR=".claude/.tdd-state"

input=$(cat)
file_path=$(echo "$input" | jq -r '.tool_input.file_path // empty')

if [ -z "$file_path" ]; then
  echo '{"continue": true}'
  exit 0
fi

# Rutas que nunca requieren RED previo: configuración, documentación, skills,
# pipelines, comandos, build files y el propio estado del hook.
case "$file_path" in
  *.md|*.json|*.yml|*.yaml|*.xml|*.properties|*.gitignore|*.editorconfig)
    echo '{"continue": true}'
    exit 0
    ;;
  */.claude/*|*/skills/*|*/pipelines/*|*/commands/*|*/hooks/*)
    echo '{"continue": true}'
    exit 0
    ;;
esac

# Archivos de test: siempre permitidos (son precisamente lo que RED escribe).
case "$file_path" in
  *Test.*|*Tests.*|*Spec.*|*/test/*|*/tests/*|*/__tests__/*|*.test.*|*.spec.*|*test_*.py|*_test.py)
    echo '{"continue": true}'
    exit 0
    ;;
esac

# Solo código fuente reconocible entra al gate.
case "$file_path" in
  *.java|*.kt|*.cs|*.ts|*.tsx|*.js|*.jsx|*.py|*.go|*.rb) ;;
  *)
    echo '{"continue": true}'
    exit 0
    ;;
esac

is_fresh() {
  local marker="$1"
  [ -f "$marker" ] || return 1
  local now mtime age_minutes
  now=$(date +%s)
  mtime=$(stat -f %m "$marker" 2>/dev/null || stat -c %Y "$marker" 2>/dev/null)
  age_minutes=$(( (now - mtime) / 60 ))
  [ "$age_minutes" -le "$FRESHNESS_MINUTES" ]
}

if is_fresh "$STATE_DIR/red_confirmed" || is_fresh "$STATE_DIR/scaffolding_ok"; then
  echo '{"continue": true}'
  exit 0
fi

cat <<EOF
{
  "continue": false,
  "stopReason": "TDD-001: sin evidencia reciente de RED_CONFIRMED ni de andamiaje autorizado para $file_path. Ejecuta tdd-driver-agent (RED) o declara la Fase 1b de andamiaje antes de escribir producción.",
  "hookSpecificOutput": {
    "hookEventName": "PreToolUse",
    "permissionDecision": "deny",
    "permissionDecisionReason": "TDD-001: falta marker .claude/.tdd-state/red_confirmed o scaffolding_ok (vigente ${FRESHNESS_MINUTES} min) para $file_path."
  }
}
EOF
exit 0
