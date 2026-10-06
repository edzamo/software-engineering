# NestJS con Clean Architecture: layout y reglas

Fuente única de la estructura del backend. Reglas de dependencia, errores y mappers: `../../architecture/clean/by-layer/rules.md` (tablas "Mapeo Nest" incluidas). Es Clean Architecture, no hexagonal. Layout **por capa en la raíz de `src/`**, inspirado en `royib/clean-architecture-nestJS`.

## 1. Layout
```
src/
├── main.ts
├── app.module.ts
├── configuration/                  # @nestjs/config validado al arrancar
├── core/                           # capa 1: TS puro
│   ├── entities/  value-objects/  rules/           # rules: semáforo, signing-readiness, sanitize-delivery-error...
│   ├── errors/                     # DomainError, ApplicationError y concretos
│   └── ports/                      # abstract class por recurso externo (ConsentRepository, MailSender, PdfGenerator...)
├── use-cases/                      # capa 2: por dominio (auth, clients, services, consents)
│   └── consents/                   # si supera ~15 casos de uso: signing/, deliveries/, access-links/ (por flujo, no por entidad)
├── controllers/                    # capa 3: *.controller.ts, dtos/, mappers/
├── services/                       # composición: <dominio>.module.ts con useFactory
└── (fuera de src) test/            # unit/ (espejo de src), integration/, e2e/, support/ (fakes)
└── frameworks/                     # capa 4
    ├── data-services/prisma/       # PrismaService/Module, prisma-*.repository.ts, mappers/
    ├── mail/  pdf/  drive/  security/  scheduler/
    └── http/                       # domain-exception.filter.ts
```
No hay `modules/` ni `shared/`. Los tests **no viven en `src/`**: van en `apps/api/test/{unit,integration,e2e,support}` (unit espeja `src/`; fakes en `test/support`). Ver `testing.md`. Alias de `tsconfig.json`/`jest.config.js`/`tsc-alias`: `@core/*`, `@use-cases/*`, `@controllers/*`, `@frameworks/*`, `@services/*` (a `src/`) y `@support/*` (a `test/support/`). Archivos en kebab-case con sufijo (`login.use-case.ts`, `user.repository.ts` puerto, `prisma-user.repository.ts`, `invalid-credentials.error.ts`, `login.dto.ts`, `user.mapper.ts`, `auth.controller.ts`, `auth.module.ts`); las clases conservan su nombre PascalCase.

Flujo: Controller -> UseCase -> Port (core) -> Repository (frameworks) -> Prisma -> BD.

## 2. Reglas
- Dependencias hacia adentro: `frameworks`/`controllers` -> `use-cases` -> `core`. `controllers` y `frameworks` no se importan entre sí. `frameworks` solo importa un caso de uso para dispararlo (scheduler). Solo `services/`, `app.module.ts` y `main.ts` ven todas las capas.
- `core` y `use-cases` no importan `@nestjs/*`, Prisma, `class-validator`, Playwright, nodemailer ni SDKs.
- Caso de uso = clase plana `XxxUseCase` (sin `@Injectable`) con `execute()`. Sin interfaz por caso de uso. Un dominio de `use-cases/` no importa otro; comparten vía `core`.
- Puertos = `abstract class` en `core/ports/`, uno por recurso externo; sirve de token de inyección.
- `controllers`: controladores, DTOs (class-validator, Swagger) y mappers DTO <-> `core`.
- `frameworks`: repositorios Prisma (usan `PrismaService`/`@prisma/client`) con sus mappers de fila, adaptadores de librería cada uno en su subcarpeta, scheduler y filtro global.
- Cableado con `useFactory` en `services/<dominio>.module.ts`:
  ```ts
  { provide: AcceptConsentUseCase,
    useFactory: (repo: ConsentRepository) => new AcceptConsentUseCase(repo),
    inject: [ConsentRepository] }
  ```
- `ValidationPipe({ whitelist: true, forbidNonWhitelisted: true, transform: true })` global; las reglas de negocio se validan en el dominio.
- Errores: negocio puro (`DomainError`) y de flujo (`ApplicationError`) en `core/errors`. Un único filtro global (`frameworks/http`) los traduce a HTTP. Los repositorios traducen errores de Prisma (P2025, P2002) a `ApplicationError`. Nunca `HttpException` desde `core` o `use-cases`.
- Mappers: la fila de Prisma y los DTO nunca llegan al caso de uso ni a `core` sin pasar por un mapper.
- Trabajo en segundo plano: se persiste el pendiente en la misma transacción y se reintenta; no se "lanza y olvida".
- Secretos solo en variables de entorno.
- `.dependency-cruiser.cjs` y `npm run lint:arch` verifican estas reglas por carpeta raíz.

## 3. Agregar una funcionalidad
1. Dominio correcto (`consents`, `clients`, `auth`, `services` o uno nuevo justificado) y los RF-xx.
2. `core`: entidades, reglas, errores y puertos nuevos. Puro, testeable con `new`.
3. `use-cases/<dominio>/`: `xxx.use-case.ts` contra los puertos de `core/ports`.
4. `controllers`: controlador + DTOs + mapper. Contrato antes del controlador (`openapi-contract.md`).
5. `frameworks`: repositorio Prisma y adaptadores que implementen los puertos.
6. `services/<dominio>.module.ts`: cableado `useFactory`.
7. `lint:arch` sin violaciones.

## 4. Anti-patrones
- `@Injectable()`/Prisma en `use-cases` o `core`; decoradores de ORM en las entidades.
- Interfaz por caso de uso; subcarpetas por entidad dentro de una capa.
- Filas Prisma o DTO crudos entrando al caso de uso.
- Servicios que lanzan `HttpException` o reciben `Request/Response`.
- Un `controller` que importa `frameworks` (o al revés); un dominio de `use-cases` que importa otro; `forwardRef` para esconder ciclos; `any` en fronteras.

Seguridad: `../../quality/owasp-security/checklists.md`. Pruebas: `testing.md`.
