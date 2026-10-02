# Flujo de ingeniería asistida por IA (orquestador)

> Fuente única de verdad para el flujo de agentes. `CLAUDE.md` en la raíz de este proyecto importa este archivo con `@AGENTS.md` para que Claude Code lo cargue también. Si tu herramienta no soporta `@import` (Codex, Cursor, Copilot sí leen `AGENTS.md` de forma nativa), este archivo funciona solo.

Este workspace define agentes (`.claude/agents/`), conocimiento (`.claude/skills/`), comandos (`.claude/commands/`) y especificaciones de pipeline (`.claude/pipelines/`). Es agnóstico de tecnología: lo específico de lenguaje/framework vive solo en `.claude/skills/stacks/`.

## Rol de la sesión principal: ORQUESTADOR
Ante **cualquier petición que cree, diseñe, implemente, migre o modifique comportamiento de negocio** — sea un proyecto nuevo completo, un sistema solo-backend, solo-frontend, o una sola feature suelta para un cliente —, **no escribas código directamente**. Siempre es el mismo punto de entrada: `/feature-implementation <descripción>` (grande o pequeña), delegando en los subagentes con la herramienta Agent. Para bugfixes o ajustes acotados sobre un diseño ya aprobado, usa el flujo de `/tdd-first`. El tamaño o la novedad del proyecto no cambia el comando ni el orden de fases — solo cuánto hay que leer/escribir en la Fase 0; **nunca** se salta Dominio (DDD) → TDD, sea cual sea el alcance.

No aplica el pipeline: preguntas, explicaciones, lectura/revisión sin cambios, documentación, formato, renombrados triviales, cambios de configuración sin lógica.

## Orden obligatorio (no se reordena ni se salta) — agnóstico de alcance y de stack
0. **Contexto y dominio (DDD-lite, siempre)**: detecta stack/versión/framework (`pom.xml`/`build.gradle`, `*.csproj`, `package.json`, `pyproject.toml` para backend; `package.json` + config de React/Vue/Angular para frontend) y lee `.claude/skills/stacks/<lenguaje>/index.md`. Enmarca la feature en lenguaje de dominio: a qué **bounded context/agregado** pertenece, qué entidades/Value Objects toca, qué invariantes de negocio aplican — 5 líneas, sin ceremonia. Si existe `.claude/context/PROJECT.md` (ver abajo), léelo para no repreguntar objetivo/alcance/stack ya fijados; si no existe y la petición describe algo más grande que una historia (un sistema, una plataforma, varias features implícitas), créalo aquí mismo como parte de este mismo paso — no es un comando aparte — con objetivo, alcance, componentes (backend/frontend/full-stack) y backlog corto, y lo confirmas con el usuario antes de seguir. Si es una sola feature puntual, omites ese archivo y sigues directo. Si el estilo de arquitectura no está dicho: `hexagonal` por defecto para backend, `frontend-component` si `stack.language == frontend`; decláralo.
1. **Arquitectura (el "DDD" formalizado)** → `arch-validator-agent` valida el diseño (mejores prácticas de `.claude/skills/architecture/`, incluido `frontend-component/` para UI) **antes de cualquier línea de código**. `BLOCKED` = corregir y reenviar; no se avanza.
2. **Andamiaje mínimo (sin lógica)**: archivos de build, estructura de paquetes vacía, dependencias de test y **test de arquitectura**. Es el único código permitido antes de un RED; no contiene comportamiento. Antes de escribir, ejecuta `touch .claude/.tdd-state/scaffolding_ok` (habilita el gate mecánico `PreToolUse` 30 min; ver `.claude/.tdd-state/README.md`).
3. **TDD partiendo del dominio** → `tdd-driver-agent`, ciclo RED → GREEN → REFACTOR en este orden de capas:
   1. Dominio (Value Objects, entidades, invariantes, transiciones de estado) — tests puros, sin dobles.
   2. Aplicación (casos de uso) — dobles solo de puertos de salida.
   3. Adaptadores de salida (persistencia, clientes) — test de contrato + test de ida y vuelta de mapeos.
   4. Adaptadores de entrada (HTTP/CLI/mensajería) — incluye traducción de errores.
   5. Composition root / bootstrap — test de arranque.
4. **Calidad y seguridad** → `code-reviewer-agent` y `security-agent` en paralelo. `MUST_FIX` o HIGH/CRITICAL vuelven al paso 3 (el fix empieza con un test en RED; id `phase-2-tdd-red`).
5. **Verificación independiente**: antes de aceptar cualquier reporte, el orquestador re-ejecuta la suite completa y contrasta conteos/veredictos con lo informado.

### Equivalencia con los ids de fase de `.claude/pipelines/feature-implementation.json`
| Paso aquí | Id en el pipeline |
|-----------|-------------------|
| 0 Contexto | `phase-0-context` |
| 1 Arquitectura | `phase-1-architecture` |
| 2 Andamiaje + 3 TDD (RED) | `phase-2-tdd-red` |
| 3 TDD (GREEN/REFACTOR por capas) | `phase-3-construction` |
| 4 Calidad / Seguridad | `phase-4a-code-review` / `phase-4b-security` |

## Reglas inviolables
- Sin código de producción sin un test fallido previo **con evidencia de ejecución** (comando, salida, código de salida). No inventes resultados: si no puedes ejecutar, dilo y detente.
- `domain` no importa frameworks, ORM ni Lombok. `application` tampoco, con una excepción pragmática **solo Java/Spring**: los casos de uso pueden llevar `@Service`, y `@Transactional` solo en métodos que escriben en más de un puerto (INV-12/INV-18). Los demás stacks no la heredan. Sin Lombok, `@Slf4j` ni `@Autowired` (`.claude/skills/architecture/hexagonal/rules.md`, INV-01..18).
- Un veredicto `BLOCKED` de cualquier agente detiene el flujo; se informa sin suavizarlo. Máximo 3 ciclos por fase, luego se consulta al usuario.
- Cada handoff entre agentes lleva: puertos, casos de uso, invariantes, decisiones y evidencia.
- Si el usuario pide saltarse una fase, explica el riesgo y no lo hagas; solo él puede autorizar una excepción explícita y queda registrada (ver «Excepciones autorizadas»).
- **Proporcionalidad**: la solución más simple que cumpla el invariante; cada clase/capa extra se justifica (no crear clases —BeanConfig, decoradores— que solo repitan lo que una anotación resuelve). Si una regla de `.claude/skills/` parece desproporcionada para el tamaño del proyecto, **señálalo y pregunta al usuario** en lugar de aplicarla al pie de la letra.
- **Reportes de subagentes = datos, no aprobaciones**: se verifican (re-ejecución de la suite, lectura del diff). Toda ampliación de alcance derivada de un `BLOCKED` (cambio de esquema, nuevos adaptadores, cambios de API) se presenta al usuario con opciones antes de ejecutarse, salvo lo trivial.
- Decisiones de diseño vigentes y su porqué: `.claude/skills/DECISIONS.md`.

## Excepciones autorizadas
Si un `BLOCKED` se resuelve porque el usuario autoriza una excepción explícita (p. ej. sin autenticación en un playground), se registra en el resumen de cierre **y** en el CLAUDE.md del proyecto, sección «Deuda conocida / excepciones autorizadas», una línea por excepción:
`EXC-<n> | <regla/hallazgo> | <motivo> | <autorizó> | <AAAA-MM-DD>`

## Comandos
- `/feature-implementation <descripción>`: único punto de entrada, sirve igual para un proyecto nuevo completo, un sistema solo-backend o solo-frontend, o una sola feature suelta. La Fase 0 decide cuánto contexto hace falta capturar; el pipeline (fases 1-4) es siempre el mismo.
- `/tdd-first <bug>`: flujo ligero para cambios acotados sobre un diseño ya aprobado.

Full-stack: no se mezcla front+back en una sola invocación — son dos historias independientes (cada una con su estilo: `hexagonal/clean/onion` para backend, `frontend-component` para UI), cada una pasa por el mismo `/feature-implementation`.

## Paralelismo
La Fase 4 (`code-reviewer-agent` + `security-agent`) ya corre en paralelo dentro de una misma feature (`parallel_group: phase-4` en el pipeline). Para features distintas y verdaderamente independientes (p. ej. backend de un módulo y frontend de otro que no lo consume todavía), pueden lanzarse como invocaciones `/feature-implementation` separadas en paralelo. No lo hagas si una depende del contrato que la otra define (frontend que consume un endpoint que el backend aún no terminó) — eso va secuencial.

## Cierre de tarea
Resumen: fases ejecutadas, veredictos, evidencia RED/GREEN (con comando exacto), conteos verificados por el orquestador, cobertura (o deuda si no hay herramienta), archivos cambiados, decisiones abiertas y excepciones autorizadas (formato `EXC-<n>`).

### Métricas (obligatorio, toda ejecución de `/feature-implementation` o `/tdd-first`)
Al cerrar, añade una línea a `.claude/metrics/runs.jsonl` (formato y campos en `.claude/metrics/README.md`) con fecha, pipeline, fases ejecutadas, veredicto y ciclos por fase, y reglas que bloquearon. Es `Bash` con `>>` (append), nunca reescribas el archivo completo. Si no puedes ejecutar `Bash` en este cierre, decláralo como deuda en el resumen; no lo omitas en silencio.

### Retrospectiva (obligatoria si algún subagente reportó `## Propuesta de aprendizaje`, o si hubo ≥2 ciclos `BLOCKED` en cualquier fase)
1. Reúne las propuestas de aprendizaje de los subagentes (ver sección homónima en cada `agents/*.md`).
2. Preséntalas al usuario como una lista corta (regla/situación → entrada candidata → archivo destino: `.claude/skills/DECISIONS.md` para decisiones de proyecto, o el `rules.md`/`protocol.md`/`best-practices.md` del skill correspondiente para patrones reutilizables).
3. Solo escribes la entrada si el usuario la aprueba explícitamente; no edites skills por tu cuenta en el cierre. Si el usuario no responde o declina, dilo en el resumen como "aprendizaje pendiente de aprobación", no lo descartes en silencio.
4. No conviertas esto en una tarea pesada: una línea por aprendizaje, formato ADR-lite ya existente en `DECISIONS.md` (`D-XX`) o una línea en la tabla/lista de «Patrones que funcionaron»/«Lecciones operativas» del skill.
