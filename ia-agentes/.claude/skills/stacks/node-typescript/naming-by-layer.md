# Node.js / TypeScript — Convenciones de Nombres y Estilo

Perfil `by-layer`. Base: `naming.md` (mismo directorio), ajustada a las convenciones del equipo (archivos en kebab-case con sufijo; clases en PascalCase). Si una regla de la base choca con esta tabla, gana esta tabla.

## Nombres
| Elemento | Convención | Ejemplo |
|----------|-----------|---------|
| Carpetas de dominio (`use-cases/`) o feature (frontend) | minúsculas en plural | `use-cases/orders/`, `ui/clients/` |
| Carpetas de capa | fijas | backend: `core`, `use-cases`, `controllers`, `services`, `frameworks`; frontend: `core`, `use-cases`, `hooks`, `ui`, `services`, `composition` |
| Archivos (backend): entidades, errores, controladores, DTOs, repositorios, mappers, módulos | **kebab-case con sufijo**, un archivo por clase; la clase conserva su nombre PascalCase | `core/entities/user.entity.ts`, `core/errors/invalid-credentials.error.ts`, `controllers/dtos/login.dto.ts`, `controllers/mappers/user.mapper.ts`, `controllers/auth.controller.ts`, `services/auth.module.ts` |
| Casos de uso | kebab-case con sufijo `.use-case.ts` | `use-cases/create-order.use-case.ts` |
| Puerto / implementación (archivos) | `<rol>.repository.ts` / `<tecnología>-<rol>.repository.ts` | `ports/user.repository.ts`, `repositories/prisma-user.repository.ts` |
| Adaptadores de librería | subcarpeta de `frameworks` (backend) o `services` (frontend) | `frameworks/security/bcrypt-password-hasher.ts` |
| Tests | Mismo nombre + `.spec.ts`; ambas apps en `test/unit/` con la misma ruta que en `src/` | `test/unit/core/entities/order.entity.spec.ts` |
| Clases, interfaces, tipos, enums | `PascalCase`, sin prefijo `I` | `Order`, `OrderRepository` |
| Variables, funciones, propiedades | `camelCase` | `findByEmail` |
| Constantes | `UPPER_SNAKE_CASE` | `MAX_RETRIES` |
| Caso de uso (clase plana, sin interfaz aparte) | `XxxUseCase` | `CreateOrderUseCase` |
| Puerto (`abstract class`/`interface` en `core/ports/`) | rol en lenguaje de negocio | `OrderRepository`, `PdfGenerator` |
| Implementación en `frameworks/data-services/prisma` | prefijo de la tecnología | `PrismaOrderRepository` |
| Errores | `XxxError` que extiende `DomainError` (1_) o `ApplicationError` (2_) | `OrderNotFoundError` |
| Booleanos | `is/has/can/should` | `isPaid` |

## Configuración TypeScript
- `strict: true` obligatorio. **Meta a adoptar** de forma gradual: `noUncheckedIndexedAccess`, `exactOptionalPropertyTypes`, `noImplicitOverride`.
- Prohibido `any` (ESLint como error): usa `unknown` + narrowing. Activa `no-floating-promises` cuando se pueda.
- ESLint (`@typescript-eslint`) en CI; formateo automático sin discusiones de estilo.

## Estilo
- Variantes con **uniones discriminadas** y `never` para exhaustividad. Sin `enum` numéricos: uniones de literales o `as const` (los enums del ORM se convierten a uniones en el dominio vía mapper, ver `prisma.md`).
- Value Objects: clases inmutables (`readonly`), fábrica estática y validación en el constructor. Tipos nominales (branded) para IDs cuando aporten.
- `async/await` siempre; sin promesas sueltas; `AbortSignal` para timeouts en llamadas salientes.
- Inmutabilidad: `readonly`, `ReadonlyArray<T>`, `as const`.
- Errores: clases con `cause`; nunca lanzar strings; los mensajes al cliente no filtran datos sensibles.
- **Tiempo e IDs:** en `core` y `use-cases`, recíbelos como parámetro (o una dependencia simple) en lugar de `new Date()`/`randomUUID()` directo; así los tests son deterministas.
- **Programación declarativa:** transformar con `map`, filtrar con `filter`, buscar con `find/some/every`, reducir con `reduce` cuando expresa mejor la intención que un acumulador mutable. Encadena en lugar de anidar. `forEach` solo para efectos reales. `.sort()` muta: copia antes si el input no es tuyo. Prefiere `const` con expresión derivada a `let` reasignado, y `?.`/`??` a `if (x != null)` cuando solo se elige un valor por defecto. **Si un paso intermedio con nombre o un `for` es más legible, gana la legibilidad.**
- DTOs que cruzan HTTP (`controllers/dtos`): `class` + decoradores (`class-validator`); `type` para uniones/DTOs internos simples; `interface` para contratos que se implementan.

## Idioma
Identificadores, commits de código y tests en inglés; contenido de negocio, documentación y mensajes al usuario final en español.

## Estructura
Por capa en la raíz de `src/`: backend en `nestjs-clean.md`, frontend en `../react-nextjs/clean-frontend.md`.
