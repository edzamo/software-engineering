# Anatomía de un proyecto con Claude Code

Mapa de referencia de **todo lo que puede tener** un proyecto que usa Claude Code para desarrollo de software — qué pieza resuelve qué problema, cuál es opcional, y dónde vive cada una. Esta carpeta (`ia-agentes/`) ya implementa varias de estas piezas de verdad (no son solo teoría); las que faltan quedan marcadas como tales.

## El árbol completo

```
proyecto/
├── CLAUDE.md                        # memoria persistente — instrucciones que el hilo principal
│                                     # lee siempre, sin invocarlas explícitamente
├── .mcp.json                        # servidores MCP registrados (herramientas externas reales)
│
├── .claude/
│   ├── settings.json                 # permisos (allow/deny por tool), hooks — versionado, del equipo
│   ├── settings.local.json           # overrides personales — NO versionado (va en .gitignore)
│   ├── agents/<nombre>.md            # subagentes: delegables, corren en su propio contexto
│   ├── skills/<nombre>/SKILL.md      # skills: procedimientos que corre el hilo principal
│   └── commands/<nombre>.md          # slash commands custom (opcional, no usado en este repo todavía)
│
├── agent-harness/                    # PATRÓN OPCIONAL — fuente canónica + compilador
│   └── ...                           # ver agent-harness/README.md — se justifica solo si hace
│                                      # falta portabilidad multi-runtime (ver más abajo)
│
└── (tests, scripts, el resto del código del proyecto)
```

## Las piezas, una por una — qué resuelve cada una

| Pieza | Qué problema resuelve | Vive en | Estado en este repo |
|---|---|---|---|
| **`CLAUDE.md`** | Contexto que el hilo principal debe conocer *siempre*, sin que nadie lo pida — convenciones del repo, qué evitar, cómo correr los tests. Es memoria, no una tarea puntual. | Raíz del proyecto (o de una subcarpeta, si aplica solo ahí). | No usado en este repo todavía — es documentación pura, sin convenciones de build que memorizar. Candidato natural si el repo empezara a tener código real para compilar. |
| **`.claude/settings.json`** | Permisos: qué tools se auto-aprueban, cuáles requieren confirmación, hooks (comandos que corren en eventos como "antes de una tool call"). Es la config real de seguridad/automatización — no un archivo propio inventado. | `.claude/settings.json` (versionado, del equipo) + `.claude/settings.local.json` (personal, no versionado). | No configurado en `ia-agentes/` — se usa con los permisos por defecto de la sesión. |
| **`.mcp.json`** | Registro de servidores MCP: integraciones reales con sistemas externos (GitHub API, una base de datos, un linter que devuelve JSON) que un agente o skill puede usar como tool. | Raíz del proyecto. | No usado — los 6 agentes de este repo solo necesitan `Read/Write/Edit/Bash/Grep/Glob`, que ya cubren todo lo que hacen (ver [`agent-harness/tool-integrations/README.md`](agent-harness/tool-integrations/README.md) para el análisis de cuándo sí haría falta). |
| **`.claude/agents/*.md`** | Subagentes: delegás una tarea completa (revisar OWASP, armar el esqueleto hexagonal) a un contexto separado, con su propio criterio versionado. Ideal cuando la tarea es un dominio de juicio propio y puede correr independiente del hilo principal. | `.claude/agents/<nombre>.md`. | **Implementado.** 6 agentes — ver [`README.md`](README.md#catálogo-por-dominio). Fuente canónica en [`agent-harness/agents/`](agent-harness/agents). |
| **`.claude/skills/<nombre>/SKILL.md`** | Skills: un procedimiento puntual y repetible que el **hilo principal** ejecuta él mismo (no delega a otro contexto) — más liviano que un agente, para tareas más mecánicas. | `.claude/skills/<nombre>/SKILL.md`. | **Implementado.** `conventional-commit` y `pr-description` — ver tabla más abajo. |
| **`.claude/commands/<nombre>.md`** | Slash commands custom — atajos tipo `/mi-comando` que insertan un prompt predefinido. Útil para flujos que se repiten pero no necesitan la lógica de una Skill (más simple: solo texto, sin instrucciones de "cuándo" invocarlo). | `.claude/commands/<nombre>.md`. | No usado en este repo — las 2 tareas repetitivas que identificamos (commit, PR) ya encajan mejor como Skills (se auto-invocan por contexto, un command hay que tipearlo). |
| **`agent-harness/`** | Patrón organizacional opcional: separa la fuente de cada agente (`agent.yaml` + `instructions.md`) de cómo se compila para un runtime puntual (`.claude/agents/*.md`). Se justifica cuando además de Claude Code hace falta portabilidad (SDK crudo, otro cliente) o reutilización de piezas (ej. un checklist en `rules/` leído por otra herramienta). | `agent-harness/` en la raíz del proyecto (acá vive dentro de `ia-agentes/`). | **Implementado** — ver [`agent-harness/README.md`](agent-harness/README.md) para el detalle completo de por qué existe y qué hace cada subcarpeta. |
| **Automatización / "loops"** | Para tareas que no son "hacé esto una vez", sino "revisá esto cada tanto" o "corré esto en un horario" — dos mecanismos distintos, no lo mismo: **(a)** un loop de sesión con paso dinámico (repetir un prompt cada N minutos mientras la sesión sigue abierta), **(b)** una rutina programada en la nube con cron (corre sola, sin sesión abierta). | No es un archivo del proyecto — se invoca como skill/tool de la sesión (`/loop`, `schedule`), no se versiona en el repo. | No usado — ningún flujo de este repo necesita correr sin supervisión en un horario. Si algún día hiciera falta (ej. "revisar cada mañana si hay una entrevista agendada"), es candidato. |

## Agent vs. Skill — la pregunta que más se confunde

| | Agent | Skill |
|---|---|---|
| ¿Dónde corre? | Contexto/hilo separado (se invoca con el tool `Agent`). | El mismo hilo principal (se invoca con el tool `Skill`). |
| ¿Para qué tipo de tarea? | Un **dominio de juicio** completo — requiere analizar, decidir, a veces iterar (revisar seguridad, diseñar arquitectura). | Un **procedimiento puntual** — pasos más mecánicos y predecibles (armar un mensaje de commit, una descripción de PR). |
| ¿Cuánto contexto necesita? | Bastante — por eso vale la pena aislarlo en su propio contexto. | Poco — por eso no vale la pena el costo de abrir un contexto nuevo. |
| Ejemplo en este repo | `owasp-security-reviewer` (analiza código, prioriza, arma un reporte con criterio). | `conventional-commit` (mira el diff, sigue una receta, produce un mensaje). |

## Dos patrones para organizar los agentes — cuál usar

- **Plano** (`.claude/agents/*.md` escritos directamente a mano, sin `agent-harness/`): la opción correcta si el único objetivo es usar Claude Code — es más simple, cero pasos extra.
- **Harness** (fuente canónica + compilador, como `agent-harness/` acá): se justifica cuando hace falta portabilidad a otro runtime, o reutilizar una pieza (como el checklist OWASP) desde afuera del prompt del agente. Es el punto intermedio elegido en este repo — ver el razonamiento completo en [`agent-harness/README.md`](agent-harness/README.md#por-qué-esta-estructura-y-no-algo-más-simple-o-más-elaborado).

No hay una respuesta "correcta" universal — es una decisión de costo/beneficio según cuántos runtimes reales vas a necesitar, la misma lógica de YAGNI que aplicamos en todo [`clean-code/`](../clean-code).

## Cómo llevarte esto a un proyecto nuevo

```bash
# lo mínimo para tener agentes + skills funcionando
cp -r ia-agentes/.claude ./.claude

# si además querés la fuente editable (agent-harness)
cp -r ia-agentes/agent-harness ./agent-harness
```

Relacionado: [`README.md`](README.md) para el catálogo de agentes y cómo activarlos, [`agent-harness/README.md`](agent-harness/README.md) para el patrón fuente/compilador, y [`.claude/skills/`](.claude/skills) para las 2 skills implementadas.

## Referencias

- [Claude Code — Subagents](https://docs.claude.com/en/docs/claude-code/sub-agents)
- [Claude Code — Settings](https://docs.claude.com/en/docs/claude-code/settings) (permisos, hooks)
- [Model Context Protocol (MCP)](https://modelcontextprotocol.io/) — especificación de los servidores MCP.
