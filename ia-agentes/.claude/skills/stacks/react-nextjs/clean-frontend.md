# Next.js con Clean Architecture: layout y reglas

Fuente única de la estructura del frontend. Reglas generales y tabla "Mapeo Nest/Next": `../../architecture/clean/by-layer/rules.md`. Es Clean Architecture, no hexagonal. Layout **por capa en la raíz de `src/`**, inspirado en `bespoyasov/frontend-clean-architecture` y con los mismos nombres de capa que el backend.

## 1. Layout
```
src/
├── app/                        # router de Next: solo compone (importa ui/ y composition/)
├── core/                       # capa 1: TS puro
│   ├── models/  rules/         # rules: screening, national-id, access-link, signature-view (solo lo que debe existir en el cliente)
│   ├── errors/                 # ApplicationError, NotFoundError, ConflictError, NetworkError...
│   └── ports/                  # un contrato por recurso externo (ConsentRepository, SignatureQueue...)
├── use-cases/                  # capa 2: por feature (auth, clients, consent-wizard)
├── hooks/                      # capa 3: use-*.ts (estado de React + casos de uso)
├── ui/                         # capa 4 (vista)
│   ├── consent-wizard/ (steps/)  clients/  auth/
│   └── presenters/  format/  shared/   # formateo real, fechas, ThemeProvider/ServiceWorkerRegistration
├── services/                   # capa 4 (infraestructura)
│   ├── repositories/           # http-*.repository.ts
│   ├── mappers/  http/  storage/   # http: fetch-client, translate-http-error; storage: IndexedDB
└── composition/                # <feature>.composition.ts
test/                           # fuera de src: unit/ (espejo de src), e2e/, support/
```
Una carpeta que no se necesita se omite. No hay `features/` ni `shared/` de primer nivel.

Flujo: Vista -> hook -> UseCase -> Port (core) -> Repository (services) -> HTTP.

Composición: `composition/<feature>.composition.ts` cablea a mano `new HttpXxxRepository(fetchClient)` y `new XxxUseCase(repo)` (sin contenedor de DI). El hook recibe los casos de uso por parámetro (`useConsentDraft(branchId, consentWizardUseCases)`); la UI y `src/app/` importan la composición.

## 2. Reglas
- Dependencias hacia adentro: `ui`/`services` -> `hooks` -> `use-cases` -> `core`. `core` y `use-cases` no importan React, Next, IndexedDB, Zod, axios ni fetch. `ui` y `services` no se importan entre sí; `hooks` no importa `ui`, `services` ni `composition`; `services` solo importa `core`.
- `core`: modelos, validaciones de formulario y estados de vista. **No duplica las reglas de negocio del servidor** (semáforo): las refleja (NC-FE).
- `use-cases`: `xxx.use-case.ts` plano; los puertos (`core/ports`) son por recurso externo (API, storage), no por caso de uso. Un feature no importa otro.
- `hooks`: exponen estado y acciones a la vista llamando a los casos de uso recibidos por parámetro.
- `ui`: componentes React (`'use client'` solo donde hay estado o eventos), por feature; `presenters/` solo si hay formateo real; `shared/` para lo común (ThemeProvider, ServiceWorkerRegistration). Un feature de `ui/` no importa otro. La ruta `/pdf/consent/[id]` reutiliza los mismos componentes.
- `services`: repositorios HTTP que implementan los puertos, mappers JSON <-> modelo (ningún JSON crudo llega al caso de uso; solo donde la forma cambia, sin mappers de identidad), cliente HTTP base (`http/fetch-client.ts`, recibido por constructor) y IndexedDB (`storage/`, implementa el puerto `SignatureQueue`).
- Errores: heredan de `Error` (`core/errors`); el repositorio traduce el error de red (`HttpError`) a un error tipado con `translateHttpError` (404 -> `NotFoundError`); un caso de uso decide por tipo (p. ej. `ResolveClientUseCase` solo registra ante `NotFoundError`).
- Validación de formulario: el esquema Zod vive en `ui/`; si una regla debe compartirse (cédula/RUC) es una función pura en `core/rules` que el esquema reutiliza.
- `lint:arch`: `npm run lint:arch --workspace=apps/web` (`.dependency-cruiser.cjs` por carpeta raíz).
- Pruebas: en `apps/web/test/{unit,e2e,support}`, con `unit/` espejando `src/`. Casos de uso con fakes en memoria de los puertos; repositorios con un `HttpClient` falso; hooks y UI con Testing Library (`../node-typescript/testing.md`). Alias `@support/*` -> `test/support/*`.

## 3. Agregar una funcionalidad
1. Feature correcto y qué lógica debe vivir de verdad en el cliente.
2. `core`: modelos, reglas, errores y puertos nuevos.
3. `use-cases/<feature>/`: caso de uso contra los puertos.
4. `services`: repositorio HTTP y mapper si la forma cambia.
5. `hooks`: hook que ejecuta los casos de uso.
6. `ui/<feature>/`: componentes; `composition/<feature>.composition.ts`: cableado; la página en `src/app/` solo compone.
7. `lint:arch` sin violaciones.

## 4. Anti-patrones
- Reimplementar reglas de negocio del servidor en el cliente.
- JSON de la API usado directo en componentes, hooks o casos de uso.
- Componentes que importan `services` directo; hooks que importan `ui`.
- Capas vacías "por cumplir"; presenters sin formateo real.
- Lógica de negocio dentro de `src/app/` o de componentes.

Más convenciones de React/PWA: `nextjs-pwa.md`.
