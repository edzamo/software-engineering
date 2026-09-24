# Node.js / TypeScript — Convenciones de Nombres y Estilo

## Nombres
| Elemento | Convención | Ejemplo |
|----------|-----------|---------|
| Archivos | `kebab-case` + sufijo de rol | `register-adopter.service.ts`, `adopter.repository.ts` |
| Clases, interfaces, tipos, enums | `PascalCase` (sin prefijo `I`) | `Adopter`, `AdopterRepository` |
| Variables, funciones, propiedades | `camelCase` | `findByEmail` |
| Constantes | `UPPER_SNAKE_CASE` | `MAX_RETRIES` |
| Puertos (tokens DI) | `Symbol`/clase abstracta | `ADOPTER_REPOSITORY` |
| Booleanos | `is/has/can` | `isActive` |
| Genéricos | `T`, `TEntity` | `Result<T, E>` |
| Tests | `*.spec.ts` (unit), `*.e2e-spec.ts` | `register-adopter.service.spec.ts` |

## Configuración TypeScript
- `"strict": true`, `"noUncheckedIndexedAccess": true`, `"exactOptionalPropertyTypes": true`, `"noImplicitOverride": true`.
- Prohibido `any` (usar `unknown` + narrowing); `no-floating-promises` activado.
- `import type` para tipos; alias de paths controlados.
- ESLint (`@typescript-eslint`) + Prettier en CI.

## Estilo
- Modelado de variantes con **uniones discriminadas** y `never` para exhaustividad:
```ts
type AdoptionResult =
  | { kind: 'approved'; id: string }
  | { kind: 'rejected'; id: string; reason: string };

const assertNever = (x: never): never => { throw new Error(`Unexpected: ${JSON.stringify(x)}`); };
```
- Value Objects: clases inmutables con `readonly`, fábrica estática y validación.
- Tipos nominales (branded) para IDs: `type AdopterId = string & { readonly __brand: 'AdopterId' }`.
- `async/await` siempre; nada de callbacks; manejar rechazos; `AbortSignal` para cancelación/timeouts.
- Inmutabilidad: `readonly`, `ReadonlyArray<T>`, `as const`.
- Errores: clases que extienden `Error` con `cause`; nunca lanzar strings.
- Tiempo e IDs inyectados (`Clock`, `IdGenerator`).
- Sin `enum` numéricos; usar uniones de literales o `as const`.

## Estructura (por feature, no por tipo técnico)
```
src/
└── adoption/
    ├── domain/
    ├── application/{ports,services}/
    ├── infrastructure/{http,persistence,messaging}/
    └── adoption.module.ts
```
