# Stack Node/TypeScript — índice (router)

Aplica a un backend NestJS + Prisma (y a otro backend TypeScript). Detecta con `package.json` (`@nestjs/*`, `@prisma/client`, `express`).

| Archivo | Cuándo |
|---------|--------|
| `naming.md` | Siempre: convenciones base y `tsconfig` estricto |
| `naming-by-layer.md` | Si el proyecto declara el perfil `clean/by-layer`: nombres y carpetas por capa; **gana sobre `naming.md`** si chocan |
| `nestjs-clean.md` | Perfil `clean/by-layer` en el backend: layout por capa en la raíz de `src/` (`core`, `use-cases`, `controllers`, `services`, `frameworks`), cableado, cómo agregar una funcionalidad |
| `nestjs-hexagonal.md` | Perfil hexagonal: estructura, DI con tokens, seguridad, dependency-cruiser |
| `testing.md` | Al escribir o auditar pruebas: niveles, herramientas, criterios de aceptación, cobertura |
| `openapi-contract.md` | Si la funcionalidad expone HTTP: contrato antes del controlador y drift check |
| `prisma.md` | Al tocar persistencia, migraciones o el seed |

Frontend: `../react-nextjs/index.md`. Reglas de negocio, seguridad y despliegue **del proyecto**: `skills/projects/<proyecto>/` en el `.claude/` del proyecto (su `CLAUDE.md` las lista en "Skills del proyecto"); léelas siempre antes de diseñar o implementar comportamiento. Los comandos concretos (test, lint, build) viven en el `CLAUDE.md` del proyecto.
