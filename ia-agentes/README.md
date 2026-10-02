# Agentes y Skills agnósticos para Claude Code

Kit **copy-paste**: copia `CLAUDE.md` + `AGENTS.md` + la carpeta `.claude/` a la raíz de cualquier repositorio (Java hoy, TypeScript o Python mañana — es agnóstico de stack). Claude Code solo auto-carga `CLAUDE.md`/`CLAUDE.local.md` **al nivel raíz del proyecto** (nunca si está anidado dentro de `.claude/`); `agents/` y `commands/` sí se descubren dentro de `.claude/`. Los skills y pipelines (en carpetas jerárquicas) los leen los agentes y el orquestador por ruta explícita.

`AGENTS.md` es la fuente única de verdad (reglas del flujo); `CLAUDE.md` solo la importa (`@AGENTS.md`) para que Claude Code la cargue. Otras herramientas (Codex, Cursor, Copilot) leen `AGENTS.md` de forma nativa sin necesitar `CLAUDE.md`.

```
<raíz del repo>/
├── CLAUDE.md                              # `@AGENTS.md` — puntero para que Claude Code la auto-cargue
├── AGENTS.md                              # reglas inviolables del flujo (fuente única de verdad)
└── .claude/
    ├── settings.json                      # hooks (gate TDD), permisos de equipo — se versiona
    ├── settings.local.json                # overrides personales — gitignored
    ├── hooks/
    │   └── tdd-gate.sh                    # PreToolUse: bloquea Write/Edit en producción sin RED_CONFIRMED/andamiaje
    ├── .tdd-state/                        # marker files efímeros del hook — gitignored (ver .tdd-state/README.md)
    ├── metrics/
    │   └── runs.jsonl                     # historial append-only de ejecuciones (kaizen, ver metrics/README.md)
    ├── agents/                            # subagentes (frontmatter nativo)
    │   ├── arch-validator-agent.md        # Fase 1 — solo lectura, bloquea el flujo
    │   ├── tdd-driver-agent.md            # Fases 2-3 — RED → GREEN → REFACTOR
    │   ├── security-agent.md              # Fase 4 — OWASP / SAST
    │   └── code-reviewer-agent.md         # Fase 4 — Clean Code / SOLID
    ├── context/
    │   └── PROJECT.md                     # lo crea la Fase 0 de /feature-implementation si el alcance es > 1 historia
    ├── skills/                            # conocimiento reutilizable (ver skills/README.md)
    │   ├── DECISIONS.md                   # ADR-lite: decisiones vigentes y su porqué (crece con cada proyecto)
    │   ├── architecture/{hexagonal,clean,onion,frontend-component}/
    │   ├── quality/{tdd-workflow,clean-code,owasp-security,refactoring}/
    │   └── stacks/{java,dotnet,node-typescript,python,frontend}/   # únicos con sintaxis/framework
    ├── commands/
    │   ├── feature-implementation.md      # /feature-implementation <descripción> — único punto de entrada
    │   └── tdd-first.md                   # /tdd-first <bug>
    ├── pipelines/                         # especificación máquina-legible + reglas
    │   ├── feature-implementation.json
    │   ├── tdd-first-pipeline.json
    │   └── rules/*.rules.json
    └── .github/workflows/validate.yml     # copiar a <repo>/.github/workflows/ para activarlo
```

## Al copiar este kit a un proyecto nuevo
1. Copia `CLAUDE.md`, `AGENTS.md` **y** `.claude/` a la raíz del repo destino (no solo `.claude/`).
2. Verifica que ambos quedaron como hermanos de `.claude/`, no dentro — si no, Claude Code no los carga.
3. `.claude/skills/DECISIONS.md` empieza vacío o con las decisiones de este kit base; es específico de cada proyecto, no lo arrastres de otro repo.
4. `.claude/metrics/runs.jsonl` también empieza vacío por proyecto — es la base de datos de kaizen de *ese* repo.
5. `.claude/.tdd-state/` se crea solo (gitignored); no hace falta copiarlo.

## Uso
Un solo punto de entrada, agnóstico de si es un proyecto nuevo completo, un sistema solo-backend, solo-frontend, o una feature suelta para un cliente — **siempre el mismo comando**, el orquestador ajusta cuánto contexto captura en la Fase 0:
- `/feature-implementation Marketplace de adopciones: backend Java/Spring + frontend React, alcance inicial solo catálogo y solicitud` → al ser una descripción de proyecto completo, la Fase 0 crea `.claude/context/PROJECT.md` (objetivo, alcance, backlog) y lo confirma contigo antes de pasar a Arquitectura.
- `/feature-implementation Registrar adoptante con validación de email` → una sola historia; la Fase 0 va directo a Arquitectura (sin crear `PROJECT.md`).
- `/tdd-first Corregir cálculo de costo con tamaño LARGE` → flujo ligero con test de regresión, para bugfixes/ajustes acotados.
- También puedes invocar un agente directamente: «usa arch-validator-agent para revisar este diseño».
- **Full-stack**: nunca se implementa front+back en una sola invocación — son dos historias independientes (cada una con su estilo: `hexagonal/clean/onion` para backend, `frontend-component` para UI), cada una pasa por el mismo `/feature-implementation`. Si no dependen una de la otra, pueden correr en paralelo.

## Fases inviolables — el orden nunca cambia, da igual el alcance
| Fase | Agente | Puerta de salida |
|------|--------|------------------|
| 0. Contexto y dominio (DDD-lite) | orquestador | Stack detectado, bounded context/agregado de la feature enmarcado; `PROJECT.md` solo si el alcance es > 1 historia |
| 1. Arquitectura | `arch-validator-agent` | Sin violaciones ARCH-xxx (backend) o FE-0x (frontend); `BLOCKED` detiene el flujo |
| 2. TDD (RED) | `tdd-driver-agent` | Tests fallidos con evidencia antes de cualquier producción |
| 3. Construcción | `tdd-driver-agent` + `.claude/skills/stacks/` | GREEN mínimo + REFACTOR, test de arquitectura y de ida y vuelta de mapeos |
| 4. Calidad y Seguridad | `code-reviewer-agent` + `security-agent` (en paralelo) | Sin `MUST_FIX` ni hallazgos HIGH/CRITICAL |

## Agnosticismo
`.claude/skills/architecture/` y `.claude/skills/quality/` no atan reglas a frameworks. Todo lo específico está en `.claude/skills/stacks/` (Java 8–21+, Spring Boot/WebFlux, Quarkus, .NET 8+, NestJS/Express, Python/FastAPI, Frontend React/Vue/Angular); cada stack tiene un `index.md` que indica qué archivo cargar según versión y framework. `frontend-component` es un estilo de arquitectura aparte de hexagonal/clean/onion: pensado para UI (componentes/estado), no fuerza puertos y adaptadores a una vista.

## Lecciones incorporadas de proyectos reales
Invariantes INV-11 a INV-18 (mapeo biyectivo dominio↔persistencia, sin Lombok/DI en núcleo, errores traducidos en un solo punto, unidad de trabajo, datos sensibles) nacieron de revisar `coffee-shop-hexagonal`.

## Añadir un stack
1. Crea `.claude/skills/stacks/<lenguaje>/index.md` + archivos de naming y arquitectura, con un test de arquitectura ejecutable.
2. Regístralo en `stack_resolution` de `.claude/pipelines/feature-implementation.json`.
3. No modifiques `.claude/skills/architecture/` ni `.claude/skills/quality/`.
