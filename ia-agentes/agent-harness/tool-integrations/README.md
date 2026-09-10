# tool-integrations/ — integraciones de herramientas compartidas (pendiente de implementación real)

> Nota de naming: esta carpeta se llamaba `skills/`, pero ese nombre choca con el concepto real de **Skills de Claude Code** (`.claude/skills/<nombre>/SKILL.md`, procedimientos que el hilo principal invoca con el tool `Skill`) — algo completamente distinto a lo que describe esta carpeta. Se renombró para evitar la confusión. Las Skills reales del proyecto viven en [`../../.claude/skills/`](../../.claude/skills), documentadas en [`ia-agentes/claude-code-project-anatomy.md`](../../claude-code-project-anatomy.md).

Acá irían las integraciones de código que varios agentes necesitan (Git/GitHub, un linter estático, un parser de AST) — hoy **no hay nada implementado**, y es deliberado: no tiene sentido escribir `git_tools.py`/`linter_tools.py` como stubs que no hacen nada real solo para "llenar la carpeta". Eso sería código muerto, exactamente lo que señalamos como antipatrón en [`clean-code-reviewer`](../agents/clean-code-reviewer/instructions.md).

## Por qué esto no es una carpeta de scripts `.py` sueltos

Claude Code no ejecuta código Python arbitrario como "tool" de un agente. Una herramienta real se expone de dos formas:

1. **Tools built-in** (`Read`, `Write`, `Edit`, `Bash`, `Grep`, `Glob`) — ya cubren git (vía `Bash`), lectura/escritura de archivos, y búsqueda. Los 6 agentes actuales solo usan estas, y alcanza para todo lo que hacen hoy.
2. **Servidores MCP** — para una integración más rica (ej. la API de GitHub sin pasar por `gh` CLI, un linter que devuelva JSON estructurado, un parser de AST real como `tree-sitter`), se registra un servidor MCP (`claude mcp add ...` o en `.mcp.json`) y el agente lo referencia en su `tools:` como `mcp__<servidor>__<tool>`.

## Cuándo sí vale la pena sumar algo acá

Si en algún momento hace falta una integración que **no** se resuelve con `Bash` + un CLI existente (por ejemplo, `git`, `gh`, `mvn`, ESLint/Checkstyle vía línea de comandos ya alcanzan para casi todo), el candidato natural es envolverla como servidor MCP, no como script Python suelto:

| Candidato futuro | Qué resolvería | Por qué no está hoy |
|---|---|---|
| MCP wrapper de Git/GitHub | Operaciones de `gitflow-release-manager` sin depender de que `git`/`gh` estén en el PATH del entorno. | `Bash` + `git`/`gh` CLI ya alcanza; no hay problema real que resolver todavía. |
| MCP de análisis estático (linter) | Que `clean-code-reviewer` reciba hallazgos estructurados (JSON) en vez de tener que interpretar output de texto de un linter corrido por `Bash`. | El checklist actual funciona bien leyendo código directo; un linter real se sumaría cuando el volumen de código lo justifique. |
| MCP de parsing de AST (`tree-sitter`) | Búsquedas más precisas que `Grep` basado en regex (ej. "todas las clases que implementan X interfaz"). | `Grep`/`Glob` cubren el caso de uso actual sin la complejidad de mantener un parser. |

Regla general: agregar algo acá recién cuando un agente concreto lo necesite de verdad, no antes — es la misma regla de YAGNI que documentamos en [`clean-code/dry-kiss-yagni.md`](../../../clean-code/dry-kiss-yagni.md).
