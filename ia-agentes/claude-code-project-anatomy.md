# Anatomía de un proyecto con Claude Code

Mapa de referencia de **todo lo que puede tener** un proyecto que usa Claude Code para desarrollo de software — qué pieza resuelve qué problema, cuál es opcional, y dónde vive cada una. Esta carpeta (`ia-agentes/`) ya implementa varias de estas piezas de verdad (no son solo teoría); las que faltan quedan marcadas como tales.

## El árbol completo

```
proyecto/
├── .github/workflows/                # CI — corre tests + tests de arquitectura (opcional)
│
├── agents/<nombre>/                  # FUENTE de cada agente
│   ├── agent.yaml                    #   name, description, tools, model
│   └── prompt.md                     #   el system prompt
│
├── skills/                           # FUENTE de cada skill, agrupada por categoría
│   ├── architecture/<estilo>/        #   agnóstico de lenguaje: invariantes de arquitectura
│   │   ├── skill.yaml
│   │   └── rules.md
│   ├── quality/<tema>/               #   agnóstico de lenguaje: TDD, clean code, OWASP, refactor
│   │   ├── skill.yaml
│   │   └── protocol.md | rules.md | checklists.md | catalog.md
│   ├── stacks/<lenguaje>/            #   SÍ depende del lenguaje: versión, herramientas, sintaxis
│   │   ├── skill.yaml
│   │   └── rules.md
│   └── <nombre>/                     #   skills sueltas, sin categoría (procedimientos)
│       ├── skill.yaml
│       └── rules.md
│
├── compiler/compile.py               # traduce agents/ + skills/ al formato que Claude Code
│                                      # auto-descubre — ver "Por qué un compilador" más abajo
├── tests/                            # valida la fuente y que lo compilado esté sincronizado
│
├── pipelines/                        # documentación del orden del pipeline (no ejecutable hoy)
│
├── CLAUDE.md                         # memoria persistente — instrucciones que el hilo principal
│                                      # lee siempre, sin invocarlas explícitamente
├── .mcp.json                         # servidores MCP registrados (herramientas externas reales)
│
└── .claude/                          # SALIDA — lo que Claude Code realmente lee, generado
    ├── settings.json                 #   permisos (allow/deny por tool), hooks — versionado
    ├── settings.local.json           #   overrides personales — NO versionado (.gitignore)
    ├── CLAUDE.md                     #   reglas del proyecto (se edita acá directo, no se compila)
    ├── hooks/<nombre>.py             #   hooks reales (ej. el que hace cumplir TDD)
    ├── scripts/<nombre>.py           #   scripts que los agentes corren (ej. detectar el stack)
    ├── agents/<nombre>.md            #   GENERADO desde agents/<nombre>/
    ├── skills/<nombre>/SKILL.md      #   GENERADO desde skills/[<categoría>/]<nombre>/
    └── commands/<nombre>.md          #   slash commands custom (opcional, no usado acá)
```

## Las piezas, una por una — qué resuelve cada una

| Pieza | Qué problema resuelve | Vive en | Estado en este repo |
|---|---|---|---|
| **`CLAUDE.md`** | Contexto que el hilo principal debe conocer *siempre*, sin que nadie lo pida — convenciones del repo, qué evitar, cómo correr los tests. Es memoria, no una tarea puntual. | Raíz del proyecto, o `.claude/CLAUDE.md`. | **Implementado** — ver [`.claude/CLAUDE.md`](.claude/CLAUDE.md): TDD obligatorio, detectar el stack antes de generar código, decisiones de diseño consultadas. |
| **`.claude/settings.json`** | Permisos: qué tools se auto-aprueban, cuáles requieren confirmación, hooks (comandos que corren en eventos como "antes de una tool call"). | `.claude/settings.json` (versionado) + `.claude/settings.local.json` (personal, no versionado). | **Implementado** — registra el hook `tdd_gate.py` en `PreToolUse`. |
| **`.mcp.json`** | Registro de servidores MCP: integraciones reales con sistemas externos que un agente o skill puede usar como tool. | Raíz del proyecto. | No usado — los agentes de este repo solo necesitan `Read/Write/Edit/Bash/Grep/Glob`. |
| **`.claude/agents/*.md`** | Subagentes: delegás una tarea completa (revisar OWASP, proponer una arquitectura) a un contexto separado, con su propio criterio versionado. | `.claude/agents/<nombre>.md`, **generado** desde `agents/<nombre>/`. | **Implementado.** 8 agentes — ver [`README.md`](README.md#catálogo-por-dominio). |
| **`.claude/skills/<nombre>/SKILL.md`** | Skills: un procedimiento o un cuerpo de conocimiento reutilizable que el **hilo principal** (o un agente) consulta sin abrir un contexto nuevo. | `.claude/skills/<nombre>/SKILL.md`, **generado** desde `skills/[<categoría>/]<nombre>/`. | **Implementado.** 12 skills en 3 categorías agnósticas de lenguaje (`architecture-*`, `quality-*`) + 1 que sí depende del lenguaje (`stacks-*`) + 2 de procedimiento — ver tabla en [`README.md`](README.md#skills). |
| **`.claude/commands/<nombre>.md`** | Slash commands custom — atajos tipo `/mi-comando` que insertan un prompt predefinido. | `.claude/commands/<nombre>.md`. | No usado — las tareas repetitivas identificadas (commit, PR) ya encajan mejor como Skills. |
| **`agents/` + `skills/` + `compiler/`** | Separan la **fuente** editable (organizada por rol y por categoría de conocimiento, como cualquier repo de ingeniería) de **cómo se empaqueta** para el único formato que Claude Code auto-descubre (`.claude/agents/*.md` y `.claude/skills/<nombre>/SKILL.md`, ambos planos). | Raíz de `ia-agentes/`. | **Implementado** — ver "Por qué un compilador" más abajo. |
| **`.claude/hooks/*.py` + `.claude/scripts/*.py`** | Automatización que corre sin que el agente decida invocarla: un hook puede **bloquear** una tool call (ej. escribir lógica sin test); un script es algo que un agente corre explícitamente (ej. detectar el stack del proyecto). | `.claude/hooks/`, `.claude/scripts/` (código de la sesión, no se compila — se copian tal cual). | **Implementado** — `tdd_gate.py` (hook) y `detect_stack.py` (script), ambos con tests propios. |
| **Automatización / "loops"** | Para tareas que no son "hacé esto una vez", sino "revisá esto cada tanto" o "corré esto en un horario". | No es un archivo del proyecto — se invoca como skill/tool de la sesión (`/loop`, `schedule`). | No usado en este repo. |

## Agent vs. Skill — la pregunta que más se confunde

| | Agent | Skill |
|---|---|---|
| ¿Dónde corre? | Contexto/hilo separado (se invoca con el tool `Agent`). | El mismo hilo principal, o consultada por un agente sin abrir contexto nuevo. |
| ¿Para qué tipo de tarea? | Un **dominio de juicio** completo — analiza, decide, a veces itera (revisar seguridad, proponer una arquitectura). | Un **cuerpo de conocimiento o un procedimiento puntual** — invariantes, un checklist, una receta (armar un commit, las reglas de una arquitectura). |
| ¿Cuánto contexto necesita? | Bastante — por eso vale la pena aislarlo. | Poco a moderado — se consulta, no se delega. |
| Ejemplo en este repo | `owasp-security-reviewer` (analiza código, prioriza, arma un reporte con criterio). | `quality-owasp-security` (la tabla de los 10 riesgos que ese agente consulta) — o `conventional-commit` (mira el diff, sigue una receta). |

## Por qué un compilador, y no `.claude/` a mano

Dos formas de organizar agentes y skills:

- **Plano** (`.claude/agents/*.md` y `.claude/skills/*/SKILL.md` escritos directamente a mano): la opción más simple, correcta si el catálogo es chico y nadie necesita reutilizar una pieza de conocimiento desde otro lugar.
- **Fuente + compilador** (`agents/` + `skills/` + `compiler/`, el patrón de este repo): la fuente se organiza como se organizaría cualquier repo de ingeniería de software agnóstico — un rol por agente, un cuerpo de conocimiento por skill, agrupado en categorías (`architecture/`, `quality/`, `stacks/`). El compilador traduce eso al único formato que Claude Code exige: un directorio **plano**, sin subcarpetas de categoría. Sin este paso, `skills/architecture/hexagonal/` y `skills/quality/clean-code/` colisionarían en un mismo nivel (`.claude/skills/hexagonal`, `.claude/skills/clean-code`) o habría que aplanar los nombres a mano y arriesgarse a que la fuente y lo que Claude Code lee se desincronicen.

No hay una respuesta "correcta" universal — es una decisión de costo/beneficio, la misma lógica de YAGNI que aplicamos en todo [`clean-code/`](../clean-code). Acá se justifica porque el catálogo ya tiene 8 agentes y 12 skills, y porque separar "qué es agnóstico" de "qué depende del lenguaje" (`architecture/`+`quality/` vs. `stacks/`) es justo la lección que motivó esta reorganización.

## Cómo llevarte esto a un proyecto nuevo

```bash
# lo mínimo para tener agentes + skills funcionando (no editable ahí)
cp -r ia-agentes/.claude ./.claude

# si además querés la fuente editable (agents/, skills/, compiler/, tests/)
cp -r ia-agentes/agents ./agents
cp -r ia-agentes/skills ./skills
cp -r ia-agentes/compiler ./compiler
cp -r ia-agentes/tests ./tests
```

Con la fuente editable: agregar o cambiar un agente es tocar `agents/<nombre>/prompt.md` (o `skills/<categoría>/<nombre>/rules.md`) y correr `python3 compiler/compile.py`; `python3 -m unittest discover -s tests` valida que quedó todo sincronizado.

Relacionado: [`README.md`](README.md) para el catálogo completo de agentes y skills, y [`.claude/CLAUDE.md`](.claude/CLAUDE.md) para las reglas que aplican siempre.

## Referencias

- [Claude Code — Subagents](https://docs.claude.com/en/docs/claude-code/sub-agents)
- [Claude Code — Settings](https://docs.claude.com/en/docs/claude-code/settings) (permisos, hooks)
- [Model Context Protocol (MCP)](https://modelcontextprotocol.io/) — especificación de los servidores MCP.
