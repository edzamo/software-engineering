# Quarkus (Reactive, Panache, Native Image) — Buenas Prácticas

Quarkus es infraestructura: vive en `adapter.*` y `bootstrap`. Dominio y aplicación permanecen libres de `io.quarkus`, `jakarta.*` (CDI/JPA) y Mutiny.

## 1. Cableado (CDI sin contaminar el núcleo)
- Los casos de uso no llevan `@ApplicationScoped`; se producen en `bootstrap`:
```java
@ApplicationScoped
class UseCaseProducers {
    @Produces @ApplicationScoped
    RegisterAdopter registerAdopter(AdopterRepository repo, Clock clock) {
        return new RegisterAdopterService(repo, clock);
    }
}
```
- Inyección por constructor (`@Inject` en el único constructor); sin `@Inject` en campos privados (Quarkus los soporta, pero rompe testabilidad).
- Configuración con `@ConfigMapping` (interfaces tipadas) + validación; perfiles `%dev`, `%test`, `%prod`.

## 2. REST
- **Quarkus REST** (RESTEasy Reactive) para endpoints; `@Path`, `@GET`... solo en `adapter.in`.
- Validación con Hibernate Validator en DTOs; `ExceptionMapper` centralizado devolviendo `application/problem+json`.
- DTOs separados del dominio; mapeo con MapStruct (`componentModel = "cdi"`).

## 3. Modelo reactivo (Mutiny)
- `Uni<T>` (0..1) y `Multi<T>` (0..N); no bloquear **I/O threads**. Un endpoint que devuelve `Uni/Multi` corre en el event loop.
- Código bloqueante: `@Blocking` en el endpoint, o `Uni.createFrom().item(...).runSubscriptionOn(Infrastructure.getDefaultWorkerPool())`.
- **Java 21**: para código imperativo bloqueante usar `@RunOnVirtualThread` en lugar de reactivo cuando la simplicidad prime.
- Reglas: no llamar `.await().indefinitely()` en el event loop; usar `onFailure().transform(...)` para mapear a errores de dominio; `ifNoItem().after(...).fail()` para timeouts.
- Puertos: mantener el núcleo síncrono y tipos de dominio; adaptadores de salida convierten a `Uni`. Si el equipo adopta Mutiny en aplicación, aislar en una única interfaz de puerto reactivo y documentarlo.
- Tests: `UniAssertSubscriber`, `@QuarkusTest` con `@RunOnVertxContext` cuando aplique.
- Mensajería: SmallRye Reactive Messaging (`@Incoming/@Outgoing`) solo en adaptadores; *acks* explícitos y política de fallos (dead-letter).

## 4. Persistencia con Panache
- Panache (Hibernate ORM o **Hibernate Reactive**) **solo** en `adapter.out.persistence`.
- Preferir el patrón **Repository** (`PanacheRepository<E>`) sobre Active Record para respetar el aislamiento del dominio.
- Las entidades Panache son modelos de persistencia; mapear a dominio en el adaptador.
- Reactivo: `PanacheRepositoryBase` con `Uni`; transacciones con `@WithTransaction`/`Panache.withTransaction`.
- Sin N+1: `JOIN FETCH`, proyecciones (`.project(Dto.class)`).
- Migraciones: Flyway/Liquibase; `quarkus.hibernate-orm.database.generation=none` en prod.
- Consultas parametrizadas (`find("email = ?1", email)`); nunca concatenar.

## 5. Native Image (GraalVM/Mandrel)
- Diseño *build-time first*: preferir extensiones Quarkus a librerías con reflexión dinámica.
- Reflexión: `@RegisterForReflection` en DTOs serializados dinámicamente (solo adaptadores).
- Evitar `Class.forName`, proxies dinámicos, recursos cargados por classpath scanning; declarar recursos con `quarkus.native.resources.includes`.
- Inicializar en build time lo estático; en runtime lo que dependa de entorno (`--initialize-at-run-time`).
- Compilar: `./mvnw package -Dnative` (o `-Dquarkus.native.container-build=true`); tests con `@QuarkusIntegrationTest` **contra el binario nativo** en CI.
- Métricas objetivo: arranque < 100 ms, RSS reducido; medirlas y guardarlas como baseline.
- Sin `ThreadLocal` pesado ni `synchronized` con I/O; compatible con virtual threads en JVM mode.

## 6. Seguridad
- `quarkus-oidc` (bearer/JWT) o `smallrye-jwt`; validar `iss`, `aud`, `exp`; algoritmo fijo.
- Autorización con `@RolesAllowed`/`@Authenticated` **en adaptadores de entrada**; comprobar propiedad del recurso.
- `quarkus.http.cors` con orígenes explícitos; cabeceras de seguridad activadas.
- Secretos vía Vault/Kubernetes Secrets/`quarkus-credentials`; nunca en `application.properties`.
- Dev Services solo en dev/test.

## 7. Observabilidad y resiliencia
- `quarkus-opentelemetry`, Micrometer, `quarkus-smallrye-health` (liveness/readiness).
- SmallRye Fault Tolerance (`@Retry`, `@Timeout`, `@CircuitBreaker`, `@Bulkhead`) en adaptadores de salida.
- Logs JSON (`quarkus-logging-json`) con correlación.

## 8. Tests
| Nivel | Herramienta |
|-------|-------------|
| Dominio/aplicación | JUnit 5 + AssertJ, sin Quarkus |
| Adaptadores | `@QuarkusTest` + REST Assured |
| Persistencia | `@QuarkusTest` + Dev Services/Testcontainers |
| Nativo | `@QuarkusIntegrationTest` |
| Arquitectura | ArchUnit |

## 9. Anti-patrones
- `PanacheEntity` (Active Record) usado como entidad de dominio.
- `Uni.await().indefinitely()` dentro de un endpoint reactivo.
- CDI/JPA/Mutiny importado en `..domain..`.
- Reflexión dinámica sin registro para native.
- `@Inject` en campos privados en código propio; scopes innecesarios (`@Singleton` global con estado mutable).

## 10. Checklist
- [ ] Quarkus solo en `adapter` y `bootstrap`; casos de uso via `@Produces`.
- [ ] Sin bloqueo en event loop (`@Blocking`/`@RunOnVirtualThread` cuando proceda).
- [ ] Panache como Repository detrás del puerto, con mapeo a dominio.
- [ ] Build nativo verificado con tests de integración sobre el binario.
- [ ] OIDC/JWT validado; autorización por recurso.
- [ ] ArchUnit y Testcontainers en CI.
