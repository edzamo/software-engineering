---
name: stacks-typescript
description: Conocimiento de stack para proyectos TypeScript/Node (NestJS, Express, Fastify) — qué features usar según la versión de TypeScript y Node, herramientas y comandos de TDD (Vitest, Jest, node:test), layout por arquitectura (hexagonal/clean/onion) y empaquetado Docker. Úsala siempre que detect_stack.py reporte typescript o javascript, antes de escribir tests o código.
---

<!-- GENERADO por compiler/compile.py — no editar a mano.
     Fuente: skills/stacks/typescript/ -->

# Stack TypeScript / Node

Esta skill **no define el proceso** (eso es de los agentes: TDD primero, slices, decisiones consultadas). Define **cómo se hace en TypeScript**.

## 0. Detectar antes de escribir

```bash
python3 .claude/scripts/detect_stack.py
```

Usá `version` (TypeScript), `node`, `test_runner`, `frameworks` y `test_one`. **Respetá la versión del proyecto.** En proyectos nuevos: Node LTS activa (24), TypeScript 5.x y Vitest.

## 1. Configuración base no negociable

- `tsconfig.json` con `"strict": true`, `"noUncheckedIndexedAccess": true` y `"noImplicitOverride": true`.
- Prohibido `any` explícito: `unknown` + narrowing. `as` solo con un comentario que lo justifique.
- ESLint (`typescript-eslint`, reglas `strict-type-checked`) + Prettier.

## 2. Qué usar según la versión

| Versión | Lo que se habilita | Regla práctica |
|---|---|---|
| TS **4.9** | `satisfies` | Validar la forma de un objeto sin perder el tipo literal. |
| TS **5.0** | Decoradores estándar (TC39), `const` type parameters | **NestJS sigue usando decoradores legacy:** mantené `experimentalDecorators` y `emitDecoratorMetadata` en proyectos Nest. |
| TS **5.2** | `using` / `await using` (liberación explícita de recursos) | Conexiones o locks que deben cerrarse siempre. |
| TS **5.4-5.5** | `NoInfer`, predicados de tipo inferidos (`filter` que estrecha el tipo) | Menos type guards escritos a mano. |
| TS **5.8** | `--erasableSyntaxOnly` | Si el código corre con el *type stripping* de Node, prohíbe `enum` y `namespace` (usá uniones de literales y `as const`). |
| Node **20/22/24** (LTS) | `node:test` nativo, `fetch` global, *type stripping* de `.ts` en Node reciente | No agregues dependencias para algo que Node ya trae. Verificá en nodejs.org el estado del *type stripping* para la versión exacta del proyecto. |

## 3. Modelado de dominio en TS

- **Value Objects:** clase con constructor `private` + fábrica estática `create()` que valida, campos `readonly`, igualdad por valor (`equals`).
- **Entidades:** métodos de comando (`iniciarAdopcion()`), nunca setters públicos. Estado como **unión de literales** (`type Estado = 'DISPONIBLE' | 'EN_PROCESO' | 'ADOPTADA'`), con un `switch` exhaustivo y `never` en el `default`.
- **Errores de dominio:** `class TransicionInvalidaError extends Error`, o un tipo `Result<T, E>` si el equipo prefiere no usar excepciones. Elegir uno y ser consistente.
- **Puertos:** `interface` (en Nest, más un *injection token* con `Symbol`, porque las interfaces no existen en runtime).

## 4. TDD en TypeScript

| Nivel | Herramienta | Notas |
|---|---|---|
| Dominio | Vitest (o Jest en Nest) | Sin Nest ni mocks: `Mascota.create(...)` directo. `it.each` para tablas de transiciones. |
| Aplicación | Vitest/Jest + fakes | Preferí **fakes en memoria** que implementen el puerto sobre `vi.fn()`/`jest.fn()`: tests menos frágiles. |
| HTTP (Nest) | `@nestjs/testing` + `supertest` | `Test.createTestingModule` reemplazando los providers de los puertos. |
| Cliente HTTP | `msw` o `nock` | Probar 200, 404, timeout y 5xx. |
| Integración | Testcontainers for Node | Base de datos real. |

- **Correr un solo test:** `npx vitest run src/mascotas/domain/mascota.entity.spec.ts -t 'no permite adoptar'` · `npx jest <archivo> -t '<nombre>'` · `node --test <archivo>`.
- **Cómo se ve un RED válido:** falla de un `expect` o `Error('Not implemented')` del stub. Un error de **compilación de TS no es RED**: primero hace falta el stub.
- **Stub del scaffold:** `throw new Error('Not implemented: <regla>');`.
- **Ubicación:** `<archivo>.spec.ts` al lado del código (convención Nest) o en `test/`. Un `describe` por comportamiento y un `it('should … when …')`.
- **Async:** siempre `await` o `return` de la promesa en el test; un test async sin `await` pasa aunque falle.

## 5. Layout según la arquitectura elegida

**Hexagonal (NestJS):**

```text
src/<contexto>/
├── domain/                 mascota.entity.ts, estado-adopcion.ts, *.error.ts (sin imports de Nest)
├── application/
│   ├── ports/in/           iniciar-adopcion.use-case.ts (interface + command type)
│   ├── ports/out/          mascota.repository.ts (interface + token Symbol)
│   └── services/           iniciar-adopcion.service.ts
├── infrastructure/
│   ├── http/               mascota.controller.ts, dto/ (class-validator), exception filter
│   ├── persistence/        in-memory-mascota.repository.ts, prisma-mascota.repository.ts
│   └── clients/            adoptantes.http-client.ts
└── <contexto>.module.ts    wiring: provide token → useClass adapter
```

**Clean:** `entities/` · `use-cases/` · `interface-adapters/` · `frameworks/`. **Onion:** `domain/` · `application/` · `infrastructure/`. Regla común: **`domain/` no importa nada de Nest, ORMs ni HTTP**. Se puede verificar con `eslint-plugin-boundaries` o `dependency-cruiser`.

## 6. Docker

Multi-stage: `node:<lts>-alpine` para build (`npm ci`, `npm run build`) → runtime con `npm ci --omit=dev` (o `node:<lts>-slim`), `USER node`, `NODE_ENV=production`, y `.dockerignore` con `node_modules` y `dist`.

## 7. Antipatrones a señalar

`any` · promesas sin `await` (usá la regla `no-floating-promises`) · lógica de negocio en controllers o en *entities* del ORM · la entidad de Prisma/TypeORM usada como modelo de dominio · `enum` de TS si el código corre con type stripping · mocks de todo en vez de fakes de los puertos · tests que dependen del orden de ejecución.
