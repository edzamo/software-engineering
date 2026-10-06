# Skills (SABER HACER)

Índice. Los agentes leen estos archivos por ruta (`.claude/skills/...`).

| Carpeta | Archivos | Cuándo |
|---------|----------|--------|
| `architecture/hexagonal` | `rules.md`, `invariants.json` | Siempre en Fase 1 (INV-01..18 valen para los tres estilos) |
| `architecture/clean` | `rules.md` | Estilo Clean |
| `architecture/onion` | `rules.md` | Estilo Onion |
| `architecture/clean/by-layer` | `rules.md`, `invariants.json` | Perfil Clean por carpeta de capa en la raíz de `src/` (CA-xx / OE-xx propios del perfil) |
| `quality/clean-code` | `rules.md` | Fase 3 y revisión |
| `quality/refactoring` | `catalog.md` | REFACTOR y revisión |
| `quality/over-engineering` | `forced-patterns.md` | Revisión: patrones forzados / sobreingeniería |
| `quality/web-performance` | `checklist.md`, `images.md` | Web pública: Core Web Vitals, presupuesto de imágenes |
| `quality/web-analytics` | `ga4.md` | Sitios que miden con GA4 |
| `quality/owasp-security` | `checklists.md`, `static-sites.md` | Auditoría de seguridad (y sitios estáticos) |
| `quality/tdd-workflow` | `protocol.md`, `static-sites.md` | Fases 2-3 (y qué se prueba en un sitio estático) |
| `stacks/java` | `index.md` → `common-naming.md`, `java8-11/`, `java17-21-plus/`, `spring-boot/` (`best-practices.md`, `mapstruct.md`), `quarkus/` | Proyectos Java |
| `stacks/dotnet` | `index.md`, `naming.md`, `clean-architecture-dotnet.md` | Proyectos .NET |
| `stacks/node-typescript` | `index.md` → `naming.md`, `naming-by-layer.md`, `nestjs-hexagonal.md`, `nestjs-clean.md`, `testing.md`, `openapi-contract.md`, `prisma.md` | Node.js / TypeScript |
| `stacks/astro` | `index.md` → `best-practices.md` | Sitios Astro estáticos |
| `stacks/react-nextjs` | `index.md` → `clean-frontend.md`, `nextjs-pwa.md` | React / Next.js |
| `stacks/python` | `index.md`, `naming.md`, `hexagonal-python.md` | Proyectos Python |
| `DECISIONS.md` | ADR-lite de decisiones de diseño vigentes | Fase 1 (consultar antes de bloquear) |
| `conventional-commit/`, `pr-description/`, `git-feature-flow/` | `SKILL.md` (skills nativos; `install.sh` los enlaza en `~/.claude/skills/`) | Commits y PRs |

## Dónde viven las reglas de un proyecto
Los skills de negocio de un proyecto **no** van aquí: viven en `<proyecto>/.claude/skills/projects/<proyecto>/` y su `CLAUDE.md` los lista en «Skills del proyecto». Resolución de rutas y reparto kit/proyecto: `../../PROJECT-CONTRACT.md`.
