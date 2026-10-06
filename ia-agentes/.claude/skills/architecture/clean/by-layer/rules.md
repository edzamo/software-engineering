# Clean Architecture por capa (perfil `by-layer`, Fullstack TS): reglas

> **Perfil del kit.** Variante opinada de `../rules.md` (Clean Architecture genérica) con layout por carpeta de capa en la raíz de `src/`. Los ids `CA-xx`/`OE-xx` de este archivo y de `invariants.json` pertenecen a **este perfil** (no son los `CA-01..10` de `../rules.md`). Los ejemplos (`consents`, `clients`, `apps/api`) vienen del primer proyecto que lo usó (Naru Consent) y son ilustrativos. Se elige declarando `Estilo: clean/by-layer` en el `CLAUDE.md` del proyecto.

Estándar del dueño para **backend (NestJS) y frontend (Next.js)**, basado en Robert C. Martin. Es un POC: buenas prácticas sí, sobreingeniería no. Es Clean Architecture; **no se usa "hexagonal"** como etiqueta. Las reglas verificables están en `invariants.json`.

**Layout por capa en la raíz de `src/` (v6), en backend y frontend.** El backend (`core`, `use-cases`, `controllers`, `services`, `frameworks`) sigue [royib/clean-architecture-nestJS](https://github.com/royib/clean-architecture-nestJS); el frontend (`core`, `use-cases`, `hooks`, `ui`, `services`, `composition`) sigue [bespoyasov/frontend-clean-architecture](https://github.com/bespoyasov/frontend-clean-architecture) con los mismos nombres de capa que el backend. Los tests viven fuera de `src/`. Las secciones marcadas *(backend)* / *(frontend)* indican a cuál aplican.

## 1. Regla de dependencia (inviolable)
Las capas internas no saben nada de las externas. Las dependencias van de afuera hacia adentro.

### Backend
`frameworks` / `controllers` -> `use-cases` -> `core`

| Carpeta (`src/`) | Capa | Qué es | Depende de |
|---|---|---|---|
| `core` | 1 | `entities/`, `rules/` (reglas de negocio puras), `errors/` (`DomainError`, `ApplicationError` y concretos), `ports/` (contratos de lo externo). TypeScript puro | Nada |
| `use-cases` | 2 | Casos de uso agrupados por dominio (`use-cases/consents/`...). Reciben los puertos por constructor | `core` |
| `controllers` | 3 | Controladores Nest, `dtos/` (class-validator, Swagger) y `mappers/` DTO <-> `core` | `use-cases`, `core` |
| `frameworks` | 4 | Implementaciones de los puertos: `data-services/prisma` (repositorios + mappers de fila), `mail`, `pdf`, `drive`, `security`, `scheduler`, `http` (filtro global) | `core` (y `use-cases` solo para disparar uno, p. ej. el scheduler) |
| `services` + raíz | Composición | `@Module` de Nest por dominio (cableado `useFactory`), `app.module.ts`, `main.ts`, `configuration/` | Todo |

`controllers` y `frameworks` no se importan entre sí. Solo la composición (`services/`, `app.module.ts`, `main.ts`) ve todas las capas.

### Frontend
`ui` / `services` -> `hooks` -> `use-cases` -> `core`

| Carpeta (`src/`) | Capa | Qué es | Depende de |
|---|---|---|---|
| `core` | 1 | `models/`, `rules/` (validaciones y reglas puras del cliente), `errors/` (`ApplicationError`...), `ports/` (contratos de lo externo). TypeScript puro | Nada |
| `use-cases` | 2 | Casos de uso por feature (`use-cases/consent-wizard/`...). Reciben los puertos por constructor | `core` |
| `hooks` | 3 | Hooks de React que ejecutan los casos de uso y exponen estado a la vista (adaptador vista <-> caso de uso) | `use-cases`, `core` |
| `ui` | 4 | Componentes y pantallas por feature (`ui/consent-wizard/`), `presenters/` (formateo de vista), `format/`, `shared/` (ThemeProvider...) | `hooks`, `core`, `use-cases` (solo tipos), `composition` |
| `services` | 4 | Implementaciones de los puertos: `repositories/` (HTTP), `mappers/` (JSON <-> modelo), `http/` (cliente fetch), `storage/` (IndexedDB) | `core` |
| `composition` + `app` | Composición | `composition/<feature>.composition.ts` cablea repositorios y casos de uso; `app/` (router de Next) solo compone `ui` y `composition` | Todo |

`ui` y `services` no se importan entre sí (la UI obtiene los casos de uso ya cableados desde `composition`). `hooks` no importa `ui`, `services` ni `composition`.

## 2. Estructura
Backend: por capa en la raíz de `src/`. Frontend: por feature y dentro las 4 capas. Dominios actuales del backend: `consents`, `clients`, `auth`, `services`.

### Backend (`apps/api/src`)
```
src/
├── main.ts
├── app.module.ts
├── configuration/                 # @nestjs/config, validación de variables de entorno
├── core/                          # capa 1: TS puro
│   ├── entities/  value-objects/
│   ├── rules/                     # semáforo, signing-readiness, sanitize-delivery-error...
│   ├── errors/                    # domain.error.ts, application.error.ts y los concretos
│   └── ports/                     # consent.repository.ts, mail-sender.ts, pdf-generator.ts...
├── use-cases/                     # capa 2, por dominio
│   ├── auth/  clients/  services/
│   └── consents/                  # subcarpetas por FLUJO si crece (signing/, deliveries/, access-links/)
├── controllers/                   # capa 3
│   ├── *.controller.ts
│   ├── dtos/
│   └── mappers/
├── services/                      # composición: *.module.ts por dominio (useFactory)
└── frameworks/                    # capa 4
    ├── data-services/prisma/      # PrismaService/Module, *.repository.ts (prisma-*.repository.ts) y sus mappers
    ├── mail/  pdf/  drive/  security/  scheduler/
    └── http/                      # domain-exception.filter.ts
```
El dominio de negocio (`auth`, `clients`, `services`, `consents`) ya no es una carpeta de primer nivel: es el nombre de archivo o la subcarpeta dentro de `use-cases/`. No existe `shared/`: los errores base viven en `core/errors` y Prisma en `frameworks`.

### Frontend (`apps/web/src`)
```
src/
├── app/                     # router de Next: solo compone (importa ui/ y composition/)
├── core/                    # capa 1: TS puro
│   ├── models/  rules/  errors/  ports/
├── use-cases/               # capa 2, por feature
│   └── auth/  clients/  consent-wizard/
├── hooks/                   # capa 3: use-*.ts
├── ui/                      # capa 4 (vista)
│   ├── consent-wizard/ (steps/)  clients/  auth/
│   ├── presenters/  format/  shared/
├── services/                # capa 4 (infraestructura)
│   ├── repositories/  mappers/  http/  storage/
└── composition/             # <feature>.composition.ts (cableado manual, sin contenedor de DI)
```
Composición del frontend: `composition/<feature>.composition.ts` cablea a mano `new HttpXxxRepository(fetchClient)` y `new XxxUseCase(repo)`; el hook recibe los casos de uso por parámetro y la UI/`app` importan la composición. `lint:arch` del frontend: `apps/web/.dependency-cruiser.cjs`. Los tests van en `apps/web/test/{unit,e2e,support}` (unit espeja `src/`).

Reglas de forma:
- Subcarpetas **por tipo de pieza** (`entities/`, `ports/`, `mappers/`...): sí, son parte del estándar.
- Backend, `core/` y `controllers/`: **planos por tipo**, sin subcarpeta por entidad. `use-cases/` se agrupa **por dominio** (`use-cases/consents/`) y, si un dominio supera ~15 casos de uso, por **flujo** (`signing/`, `deliveries/`), nunca por entidad.
- Frontend: `core/`, `hooks/`, `services/` planos por tipo (nada por entidad); `use-cases/` y `ui/` se agrupan por **feature** (`consent-wizard`, `clients`, `auth`), nunca por entidad.
- Una capa o subcarpeta que no se necesita se omite; el orden y los nombres no cambian.
- Imports con alias de tsconfig. Backend: `@core/*`, `@use-cases/*`, `@controllers/*`, `@frameworks/*`, `@services/*` (cada uno -> `src/<carpeta>/*`). Frontend: `@/core/...`, `@/use-cases/...`, `@/hooks/...`, `@/ui/...`, `@/services/...`, `@/composition/...` (`@/*` -> `src/*`) y `@support/*` -> `test/support/*`.
- Backend: nombres de archivo en kebab-case con sufijo (`login.use-case.ts`, `user.repository.ts` puerto, `prisma-user.repository.ts` implementación, `invalid-credentials.error.ts`, `login.dto.ts`, `user.mapper.ts`, `auth.controller.ts`, `auth.module.ts`, `user.entity.ts`); las clases conservan su nombre PascalCase.
- Backend: los adaptadores de una librería (Bcrypt, Jwt, Playwright, nodemailer, Prisma) van en una subcarpeta propia de `frameworks/` (`security/`, `pdf/`, `mail/`, `data-services/prisma/`). Frontend: en `services/{http,storage}` (fetch, IndexedDB).
- Backend: **todos los tests fuera de `src/`**, en `apps/api/test/{unit,integration,e2e,support}`; `unit/` espeja la ruta de `src/`; los fakes de puertos viven en `test/support`. Frontend: igual, en `apps/web/test/{unit,e2e,support}` con `unit/` espejando `src/`.

## 3. Mapeo Nest/Next
### Backend (Nest)
| Pieza | Capa | Dónde |
|---|---|---|
| Entidad, Value Object | 1 | `core/entities` |
| Regla de negocio pura (semáforo, elegibilidad) | 1 | `core/rules` |
| Error de negocio (`DomainError`), de flujo (`ApplicationError`) y concretos | 1 | `core/errors` |
| Puerto (`abstract class`, sirve de token en Nest) | 1 | `core/ports` |
| Caso de uso (clase plana, sin `@Injectable`) | 2 | `use-cases/<dominio>/xxx.use-case.ts` |
| Controlador Nest | 3 | `controllers/xxx.controller.ts` |
| DTO (class-validator, Swagger) | 3 | `controllers/dtos` |
| Mapper DTO <-> `core` | 3 | `controllers/mappers` |
| Repositorio Prisma (implementa un puerto) | 4 | `frameworks/data-services/prisma/prisma-xxx.repository.ts` |
| Mapper fila <-> entidad | 4 | `frameworks/data-services/prisma/mappers` |
| Adaptador de librería (Bcrypt, Jwt, Playwright, nodemailer, Drive) | 4 | `frameworks/{security,pdf,mail,drive}` |
| Tarea programada (scheduler) | 4 | `frameworks/scheduler` |
| Filtro global de errores | 4 | `frameworks/http/domain-exception.filter.ts` (mapea por `instanceof DomainError/ApplicationError`) |
| `@Module` y cableado `useFactory` | Composición | `services/xxx.module.ts` |
| Bootstrap, configuración | Composición | `main.ts`, `app.module.ts`, `configuration/` |
| Rutas | 3 | Decoradores del controlador |

Los repositorios Prisma viven en `frameworks` y pueden usar `PrismaService` y `@prisma/client`.

### Frontend (Next)
| Pieza | Capa | Dónde |
|---|---|---|
| Modelo | 1 | `core/models` |
| Validación o regla pura (cédula, semáforo reflejado) | 1 | `core/rules` |
| Error tipado (`ApplicationError`, `NotFoundError`...) | 1 | `core/errors` |
| Puerto (repositorio, cola de firmas) | 1 | `core/ports` |
| Caso de uso (clase plana) | 2 | `use-cases/<feature>/xxx.use-case.ts` |
| Hook de React (estado + ejecuta casos de uso) | 3 | `hooks/use-xxx.ts` |
| Componente o pantalla | 4 | `ui/<feature>/Xxx.tsx` |
| Presenter (formateo real de fechas, textos) | 4 | `ui/presenters` |
| Repositorio HTTP (implementa un puerto) | 4 | `services/repositories/http-xxx.repository.ts` |
| Mapper JSON <-> modelo | 4 | `services/mappers` |
| Cliente HTTP base | 4 | `services/http` |
| IndexedDB | 4 | `services/storage` |
| Cableado de un feature | Composición | `composition/<feature>.composition.ts` |
| Router Next (`src/app/`) | Composición | Solo importa `ui` y `composition` |

## 4. Flujo de una petición
Backend:
```
Controller -> UseCase -> Port -> Repository -> ORM (Prisma) -> BD
```
Ejemplo `consents`: `controllers/consents.controller.ts` (3) -> `use-cases/consents/accept-consent.use-case.ts` (2) -> `core/ports/consent.repository.ts` (puerto, 1) -> `frameworks/data-services/prisma/prisma-consent.repository.ts` (4) -> Prisma -> Postgres. El controlador nunca llama a Prisma; el caso de uso nunca conoce HTTP.

Frontend:
```
Vista -> hook -> UseCase -> Port -> Repository -> HTTP
```
Ejemplo wizard: `ui/consent-wizard/ConsentWizard.tsx` (4) -> `hooks/use-consent-draft.ts` (3) -> `use-cases/consent-wizard/submit-answers.use-case.ts` (2) -> `core/ports/consent.repository.ts` (1) -> `services/repositories/http-consent.repository.ts` (4) -> `services/http/fetch-client.ts`. El cableado vive en `composition/consent-wizard.composition.ts`.

## 5. Errores
- Toda falla es una clase que hereda de `Error`: `DomainError` (negocio) y `ApplicationError` (flujo), ambos en `core/errors`.
- El dominio lanza excepciones de negocio puras.
- La infraestructura captura errores nativos (Prisma, Axios) y los transforma a `ApplicationError` antes de propagarlos (p. ej. Prisma P2025, P2002).
- Un único filtro global (backend: `frameworks/http`; frontend: `services/http` traduce errores de red a errores tipados de `core/errors`) traduce los errores a HTTP; nunca 500 por omisión.

## 6. Mappers
Prohibido pasar JSON/DTO crudos de API o BD al caso de uso o al dominio. Todo dato externo cruza un mapper: backend en `controllers/mappers` (DTO) o `frameworks/data-services/prisma/mappers` (fila); frontend en `services/mappers`.

## 7. Dependencias entre dominios
- **Backend:** los casos de uso de un dominio (`use-cases/consents/`) no importan los de otro (`use-cases/clients/`); comparten vía `core` (entidades, reglas, puertos). Sin ciclos. El `@Module` de un dominio puede importar el `@Module` de otro (`services/`).
- **Frontend:** un feature de `use-cases/` no importa otro; uno de `ui/` tampoco (comparten `ui/shared`, `ui/presenters`, `ui/format`, `core` y `hooks`). Sin ciclos.

## 8. Principio del frontend (NC-FE)
El frontend refleja las reglas de negocio que decide el servidor (semáforo); no las duplica. Su `core` solo tiene lo que debe existir en el cliente (validaciones de formulario, estados de vista).

## 9. Reglas (ids estables)
- **CA-01** Dependencias de afuera hacia adentro (`frameworks`/`controllers` -> `use-cases` -> `core`; frontend `ui`/`services` -> `hooks` -> `use-cases` -> `core`); nunca al revés. Backend: `controllers` y `frameworks` no se importan entre sí. Frontend: `ui` y `services` no se importan entre sí.
- **CA-02** `core` es TypeScript puro: sin frameworks, ORM ni SDKs; no importa otras capas.
- **CA-03** `use-cases` sin frameworks ni ORM; solo importa `core`; los puertos entran por constructor.
- **CA-04** Todo dato externo cruza un mapper (backend: `controllers/mappers` o `frameworks/.../mappers`; frontend: `services/mappers`); no se filtra JSON/DTO/fila cruda a casos de uso o dominio.
- **CA-05** Errores base (`core/errors`): `DomainError` y `ApplicationError` heredan de `Error`; la infraestructura traduce errores nativos a `ApplicationError`.
- **CA-06** Un único filtro/traductor global de errores en la capa 4 (`frameworks/http`); nunca 500 por omisión.
- **CA-07** Backend: un dominio de `use-cases/` no importa otro; comparten vía `core`; sin ciclos (el `@Module` puede importar otro `@Module`). Frontend: un feature de `use-cases/` o de `ui/` no importa otro.
- **CA-08** Datos sensibles nunca en claro en logs ni `toString`.
- **CA-09** Los casos de uso se prueban con fakes en memoria de los puertos. TDD se mantiene.
- **CA-10** Los tests viven **fuera de `src/`**, en `apps/<app>/test/{unit,integration,e2e,support}` (backend y frontend): `unit/` espeja la ruta de `src/`, los fakes de puertos van en `test/support`, y no hay `*.spec.ts(x)` ni carpetas `testing/` dentro de `src/`. Lo verifica `lint:arch` (regla `tests-outside-src`). Las referencias (royib, bespoyasov) no definen esta convención: es estándar propio del dueño.

## 10. Sobreingeniería (se reporta)
- **OE-01** Una interfaz por cada caso de uso (los puertos son por recurso externo: repositorio, PDF, storage, hasher...).
- **OE-02** Subcarpetas por entidad dentro de una capa (`controllers/clients/`, `core/entities/consent/`). En `use-cases/` agrupar por dominio o flujo sí; por entidad no.
- **OE-03** Layout hexagonal (`in/`, `out/`, `adapter/in`) o la etiqueta "hexagonal".
- **OE-04** Patrones sin un problema real (factories, eventos, CQRS, capas vacías).
- **OE-05** Mappers, presenters o entidades duplicadas cuando no hay diferencia ni formateo real.

## 11. Checklist
- [ ] Backend: pieza en la carpeta raíz correcta (`core`, `use-cases`, `controllers`, `frameworks`, `services`). Frontend: pieza en `core`, `use-cases`, `hooks`, `ui`, `services` o `composition`.
- [ ] Dependencias solo hacia adentro; nada de frameworks en `core` y `use-cases`.
- [ ] Mappers en la frontera; errores heredan de `DomainError`/`ApplicationError`.
- [ ] Sin imports entre dominios de `use-cases/` (backend) ni entre features de `use-cases/` o `ui/` (frontend); sin ciclos.
- [ ] Cada caso de uso tiene test con fakes en memoria, en `test/unit/` (CA-10).
- [ ] `lint:arch` en verde.

## Referencias
- [royib/clean-architecture-nestJS](https://github.com/royib/clean-architecture-nestJS): layout del backend por capa en la raíz (`core`, `use-cases`, `controllers`, `services`, `frameworks`).
- [bespoyasov/frontend-clean-architecture](https://github.com/bespoyasov/frontend-clean-architecture): layout del frontend por capa en la raíz (`domain`, `application`, `services`, `ui`); hooks como adaptadores; se adaptaron los nombres a los del backend.
- Prompt de referencia del dueño: "Arquitecto de Soluciones Clean Architecture (Fullstack TS)", basado en Robert C. Martin. Es el estándar de este archivo.
- [Riverpod folder structure + Clean Architecture (dbestech)](https://www.dbestech.com/tutorials/riverpod-folder-structure-clean-architecture): agrupar por feature con sus capas y el flujo vista -> caso de uso -> repositorio abstracto -> implementación. No se tomó Riverpod ni get_it.
- [spring-boot-clean-architecture (repo de ejemplo del dueño)](https://github.com/edzamo/clean-hexagonal-onion-playground/tree/feature/salud-hexagonal/03-clean-architecture/spring-boot-clean-architecture): la regla de dependencia de Uncle Bob y la separación por capas.
