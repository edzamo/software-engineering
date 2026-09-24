# NestJS (y Express) con Arquitectura Hexagonal

NestJS es infraestructura: decoradores y módulos viven en `infrastructure/` y en el `*.module.ts`. Dominio y aplicación son TypeScript puro.

## 1. Estructura por bounded context
```
src/adoption/
├── domain/                     # entidades, VOs, errores (TS puro)
├── application/
│   ├── ports/
│   │   ├── in/register-adopter.use-case.ts
│   │   └── out/adopter.repository.ts
│   └── services/register-adopter.service.ts
├── infrastructure/
│   ├── http/adopter.controller.ts, dto/*.ts
│   ├── persistence/typeorm-adopter.repository.ts, adopter.orm-entity.ts
│   └── messaging/...
└── adoption.module.ts          # composition root del contexto
```

## 2. Reglas
- **NH-01** `domain/` y `application/` **no importan** `@nestjs/*`, `typeorm`, `express`, `class-validator`, `class-transformer`, `rxjs` (salvo decisión explícita).
- **NH-02** Los puertos son **interfaces/clases abstractas + token** definidos en `application/ports`.
- **NH-03** Los servicios de aplicación son clases planas con dependencias por constructor; se registran con `useFactory` en el módulo (sin `@Injectable` en el núcleo):

```ts
// application/ports/out/adopter.repository.ts
export interface AdopterRepository {
  save(adopter: Adopter): Promise<void>;
  findByEmail(email: Email): Promise<Adopter | null>;
}
export const ADOPTER_REPOSITORY = Symbol('ADOPTER_REPOSITORY');

// application/services/register-adopter.service.ts (sin decoradores)
export class RegisterAdopterService implements RegisterAdopterUseCase {
  constructor(private readonly adopters: AdopterRepository, private readonly clock: Clock) {}
  async execute(cmd: RegisterAdopterCommand): Promise<AdopterId> { /* ... */ }
}

// adoption.module.ts
@Module({
  controllers: [AdopterController],
  providers: [
    { provide: ADOPTER_REPOSITORY, useClass: TypeOrmAdopterRepository },
    { provide: CLOCK, useValue: systemClock },
    { provide: REGISTER_ADOPTER, useFactory: (r: AdopterRepository, c: Clock) =>
        new RegisterAdopterService(r, c), inject: [ADOPTER_REPOSITORY, CLOCK] },
  ],
})
export class AdoptionModule {}
```
- **NH-04** Controladores delgados: DTO (`class-validator`) → comando → caso de uso → respuesta DTO. Nunca devuelven entidades de dominio ni ORM.
- **NH-05** Validación de entrada con `ValidationPipe({ whitelist: true, forbidNonWhitelisted: true, transform: true })`; invariantes de negocio en el dominio.
- **NH-06** Mapeo explícito ORM ↔ dominio en el repositorio (`toDomain`, `toPersistence`).
- **NH-07** Errores: `DomainError` → filtro global `ExceptionFilter` que mapea a HTTP (ProblemDetails); sin lanzar `HttpException` desde el dominio/aplicación.
- **NH-08** Cero singletons con estado mutable; scope por defecto `DEFAULT`, evitar `REQUEST` salvo necesidad.
- **NH-09** Módulos por feature; sin dependencias circulares (`forwardRef` = olor arquitectónico).
- **NH-10** Configuración con `@nestjs/config` + validación de esquema (Joi/zod) en el arranque; secretos fuera del repo.

## 3. Seguridad
- Autenticación: `@nestjs/passport` + JWT; validar `iss`, `aud`, `exp`, algoritmo fijado; JWKS con caché.
- Autorización: `Guards` globales *deny-by-default* (`APP_GUARD`), `@Public()` explícito para rutas abiertas; verificación de propiedad del recurso en el caso de uso o guard de recurso (anti-IDOR).
- `helmet`, CORS con lista blanca, `@nestjs/throttler` (rate limiting), límites de tamaño de body.
- Consultas parametrizadas (ORM/QueryBuilder con parámetros); nada de interpolar strings.
- Logs con `nestjs-pino` sin PII/secretos.

## 4. Express (sin Nest)
- Mismo esquema: `routes/controllers` → casos de uso → puertos; composición en `main.ts`/`container.ts`.
- Middleware de errores central; validación con `zod`; sin lógica de negocio en handlers.
- `helmet`, `cors`, `express-rate-limit`.

## 5. Tests
| Nivel | Herramienta |
|-------|-------------|
| Dominio/aplicación | Jest o Vitest, sin Nest, dobles de puertos |
| Adaptadores HTTP | `@nestjs/testing` + `supertest` |
| Persistencia | Testcontainers + BD real |
| Contrato | Pact |
| Arquitectura | `dependency-cruiser` / `eslint-plugin-boundaries` |

Regla ejecutable (`dependency-cruiser`):
```js
module.exports = { forbidden: [
  { name: 'domain-pure', severity: 'error',
    from: { path: '^src/[^/]+/domain' },
    to:   { path: '^src/[^/]+/(application|infrastructure)|node_modules/(@nestjs|typeorm|express|class-validator)' } },
  { name: 'application-no-infra', severity: 'error',
    from: { path: '^src/[^/]+/application' },
    to:   { path: '^src/[^/]+/infrastructure|node_modules/(@nestjs|typeorm|express)' } },
  { name: 'no-circular', severity: 'error', from: {}, to: { circular: true } }
] };
```

## 6. Anti-patrones
- `@Entity`/`@Column` en clases de dominio.
- `@Injectable()` y `@InjectRepository` en la capa de aplicación.
- Servicios que importan `Request/Response` o lanzan `HttpException`.
- `any` en fronteras; `forwardRef` para ocultar ciclos.
- Devolver entidades ORM en controladores.

## 7. Checklist
- [ ] `domain/` y `application/` sin imports de framework (verificado por dependency-cruiser en CI).
- [ ] Puertos con token y casos de uso registrados con `useFactory`.
- [ ] DTO ↔ comando ↔ dominio ↔ ORM mapeados explícitamente.
- [ ] Guards globales deny-by-default; JWT validado; anti-IDOR.
- [ ] `strict` TypeScript y ESLint sin `any`.
