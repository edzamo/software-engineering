# Pruebas (Jest, NestJS, ORM, Next.js)

Complementa `../../quality/tdd-workflow/protocol.md` (ciclo y anti-patrones) con lo específico del stack. Los casos de prueba de negocio de un proyecto viven en `../../projects/<proyecto>/` y los comandos concretos, en el `CLAUDE.md` del proyecto (por convención: scripts `test`, `test:cov`, `lint:arch`, `build`).

## Herramientas por nivel
| Nivel | Herramienta |
|-------|-------------|
| `core` y `use-cases` | Jest (`ts-jest`) sin `Test.createTestingModule`; `new XxxUseCase(fake…)`; fakes en memoria de los puertos (`ports/`) |
| HTTP | `@nestjs/testing` + `supertest`, con salidas externas simuladas |
| Persistencia (`frameworks/data-services/prisma`) | ORM contra una base de pruebas (docker-compose o Testcontainers) + test de ida y vuelta por estado/enum solo si hay mapeo entre fila y dominio |
| Frontend (hooks en `hooks/`, UI en `ui/`) | Testing Library + Jest orientados a comportamiento (`getByRole`, `getByLabelText`). Casos de uso y repositorios HTTP sin red: fakes en memoria de los puertos / cliente HTTP falso (`msw` solo si hay integración real, en `test/`) |
| Arquitectura | script `lint:arch` (dependency-cruiser) en CI |

Convención de nombres de los tests: `describe('<unidad>')` + `it('returns <resultado> when <condición>')`. Backend: en `apps/api/test/unit/`, espejo de `src/` (`*.spec.ts`). Los binarios pesados (p. ej. un navegador headless para PDF) no se ejecutan en unitarios: se prueba con un fake del puerto `PdfGenerator` y la ejecución real queda para una prueba de integración/humo en `test/`.

## Política de tests
- **Backend (`apps/api`)**: **todos los tests van en `apps/api/test/`, fuera de `src/`**, con esta estructura:
  ```
  apps/api/test/
  ├── unit/          # espejo de src/: unit/core/rules/..., unit/use-cases/consents/..., unit/controllers/..., unit/frameworks/...
  ├── integration/   # base de datos real (Prisma) u otra infraestructura real
  ├── e2e/           # HTTP real (supertest) con su jest-e2e.json
  └── support/       # fakes en memoria de los puertos (antes `use-cases/testing/fakes.ts`), builders, fixtures
  ```
  El test de `src/use-cases/consents/sign-consent.use-case.ts` es `test/unit/use-cases/consents/sign-consent.use-case.spec.ts`. Importan con los alias (`@core/...`, `@use-cases/...`, `@support/*` -> `test/support/*`), nunca con rutas relativas a `src/`. `src/` queda solo con código de producción, sin `*.spec.ts` ni carpetas `testing/`; el build no incluye `test/`.
- **Frontend (`apps/web`)**: política de ubicación igual, pero **cobertura mínima** (ver la regla del proyecto, p. ej. NC-FE-09: solo lo crítico); no se exige TDD completo ni cobertura por capa. Ubicación: `apps/web/test/{unit,e2e,support}`, con `unit/` espejando `src/` (`test/unit/use-cases/consent-wizard/sign-consent.use-case.spec.ts`), `support/` para fakes y `@support/*` como alias. Sin `*.spec.ts(x)` dentro de `src/`.


## De historia o requisito a criterios de aceptación
Antes de codificar, redacta Given/When/Then. Ejemplo:
```
Dado un formulario con 9 campos obligatorios y 8 completados
Cuando el usuario intenta continuar
Entonces el sistema rechaza el envío y no ejecuta la evaluación
```
Incluye siempre: camino feliz, caso borde (omitidos, vacíos, repetidos), error de dominio y, si aplica, autorización (otro usuario no puede leerlo) y reintento.

## Plan de pruebas (Paso 0 del ciclo)
Ordena de lo simple a lo complejo y asigna cada caso a un nivel. Toda regla de dominio del proyecto genera al menos un caso; cada repositorio Prisma con mapeo, su ida y vuelta por estado/enum; las escrituras multi-tabla, un test de transacción; los trabajos en segundo plano, un test de reintento idempotente.

## Al auditar cobertura
- Señala reglas de negocio sin test y casos borde ausentes.
- Señala integraciones que deberían ser unitarias (I/O real innecesaria) y viceversa.
- Señala promesas sin `await` en un test (pasan en falso), `it.skip`, aserciones triviales y tests acoplados a la implementación.
- Mide cobertura del código **nuevo o modificado** contra los umbrales del protocolo (línea 85 %, ramas 75 %, dominio 95 %); la base histórica no se usa para bloquear.
