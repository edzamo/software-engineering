# Agentes y Skills agnósticos para Claude Code

Kit **copy-paste**: copia la carpeta `.claude/` a la raíz de cualquier repositorio. Claude Code descubre solo `agents/` y `commands/`; los skills (en carpetas jerárquicas) los leen los agentes por ruta.

```
.claude/
├── CLAUDE.md                              # reglas inviolables del flujo
├── agents/                                # subagentes (frontmatter nativo)
│   ├── arch-validator-agent.md            # Fase 1 — solo lectura, bloquea el flujo
│   ├── tdd-driver-agent.md                # Fases 2-3 — RED → GREEN → REFACTOR
│   ├── security-agent.md                  # Fase 4 — OWASP / SAST
│   └── code-reviewer-agent.md             # Fase 4 — Clean Code / SOLID
├── skills/                                # conocimiento reutilizable (ver skills/README.md)
│   ├── architecture/{hexagonal,clean,onion}/
│   ├── quality/{tdd-workflow,clean-code,owasp-security,refactoring}/
│   └── stacks/{java,dotnet,node-typescript,python}/   # únicos con sintaxis/framework
├── commands/
│   ├── feature-implementation.md          # /feature-implementation <feature>
│   └── tdd-first.md                       # /tdd-first <bug>
├── pipelines/                             # especificación máquina-legible + reglas
│   ├── feature-implementation.json
│   ├── tdd-first-pipeline.json
│   └── rules/*.rules.json
└── .github/workflows/validate.yml         # copiar a <repo>/.github/workflows/ para activarlo
```

## Uso
- `/feature-implementation Registrar adoptante con validación de email` → ejecuta las 4 fases con sus gates.
- `/tdd-first Corregir cálculo de costo con tamaño LARGE` → flujo ligero con test de regresión.
- También puedes invocar un agente directamente: «usa arch-validator-agent para revisar este diseño».

## Fases inviolables
| Fase | Agente | Puerta de salida |
|------|--------|------------------|
| 1. Arquitectura | `arch-validator-agent` | Sin violaciones ARCH-xxx; `BLOCKED` detiene el flujo |
| 2. TDD (RED) | `tdd-driver-agent` | Tests fallidos con evidencia antes de cualquier producción |
| 3. Construcción | `tdd-driver-agent` + `skills/stacks/` | GREEN mínimo + REFACTOR, test de arquitectura y de ida y vuelta de mapeos |
| 4. Calidad y Seguridad | `code-reviewer-agent` + `security-agent` | Sin `MUST_FIX` ni hallazgos HIGH/CRITICAL |

## Agnosticismo
`skills/architecture/` y `skills/quality/` no atan reglas a frameworks. Todo lo específico está en `skills/stacks/` (Java 8–21+, Spring Boot/WebFlux, Quarkus, .NET 8+, NestJS/Express, Python/FastAPI); cada stack tiene un `index.md` que indica qué archivo cargar según versión y framework.

## Lecciones incorporadas de proyectos reales
Invariantes INV-11 a INV-18 (mapeo biyectivo dominio↔persistencia, sin Lombok/DI en núcleo, errores traducidos en un solo punto, unidad de trabajo, datos sensibles) nacieron de revisar `coffee-shop-hexagonal`.

## Añadir un stack
1. Crea `skills/stacks/<lenguaje>/index.md` + archivos de naming y arquitectura, con un test de arquitectura ejecutable.
2. Regístralo en `stack_resolution` de `pipelines/feature-implementation.json`.
3. No modifiques `skills/architecture/` ni `skills/quality/`.
