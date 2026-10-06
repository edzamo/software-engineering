#!/usr/bin/env bash
# Conecta el kit con Claude Code a nivel de usuario (idempotente, solo crea symlinks).
#   ~/.claude/kit                    -> raíz de este repo
#   ~/.claude/agents/<x>.md          -> kit/.claude/agents/<x>.md
#   ~/.claude/commands/<x>.md        -> kit/.claude/commands/<x>.md
#   ~/.claude/skills/<skill>/        -> kit/.claude/skills/<skill>/   (solo los que tienen SKILL.md)
# Nunca pisa un archivo/directorio real: si existe y no es symlink, lo reporta y sigue.
# Uso: scripts/install.sh [--dry-run]
set -euo pipefail

DRY=0; [ "${1:-}" = "--dry-run" ] && DRY=1
KIT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
HOME_CLAUDE="$HOME/.claude"

link() { # link <destino-real> <ruta-del-symlink>
  local target="$1" dest="$2"
  if [ -L "$dest" ]; then
    [ "$(readlink "$dest")" = "$target" ] && { echo "ok      $dest"; return; }
    echo "retarg  $dest -> $target"; [ $DRY -eq 0 ] && ln -sfn "$target" "$dest"; return 0
  fi
  if [ -e "$dest" ]; then echo "OMITIDO $dest existe y no es symlink (resuélvelo a mano)"; return; fi
  echo "crea    $dest -> $target"; [ $DRY -eq 0 ] && ln -s "$target" "$dest"
  return 0
}

[ $DRY -eq 0 ] && mkdir -p "$HOME_CLAUDE/agents" "$HOME_CLAUDE/commands" "$HOME_CLAUDE/skills"
link "$KIT" "$HOME_CLAUDE/kit"
for f in "$KIT"/.claude/agents/*.md;   do link "$HOME_CLAUDE/kit/.claude/agents/$(basename "$f")"   "$HOME_CLAUDE/agents/$(basename "$f")";   done
for f in "$KIT"/.claude/commands/*.md; do link "$HOME_CLAUDE/kit/.claude/commands/$(basename "$f")" "$HOME_CLAUDE/commands/$(basename "$f")"; done
for d in "$KIT"/.claude/skills/*/; do
  [ -f "$d/SKILL.md" ] && link "$HOME_CLAUDE/kit/.claude/skills/$(basename "$d")" "$HOME_CLAUDE/skills/$(basename "$d")"
done
echo "Listo$([ $DRY -eq 1 ] && echo ' (dry-run: no se cambió nada)'). Reinicia Claude Code para que descubra los agentes."
