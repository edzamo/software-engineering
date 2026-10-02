# Spring Boot (MVC / WebFlux / Data / Security) — Buenas Prácticas

Spring es un **detalle de infraestructura**: vive en `adapter.*` y en la clase de arranque. `domain` no lo importa; `application` solo usa `@Service`/`@Transactional` (§1).

> Nota: el contrato reactivo (`Mono`/`Flux` por puerto) solo aplica a proyectos **WebFlux**; en MVC bloqueante los puertos son síncronos.

## 1. Ubicación de Spring en la arquitectura
| Capa | ¿Spring permitido? |
|------|--------------------|
| domain | No |
| application (`port`, `service`) | `@Service` en casos de uso y `@Transactional` solo en métodos multi-puerto (sin `@Autowired`, Lombok ni `@Slf4j`) |
| adapter.in (controllers, listeners) | Sí |
| adapter.out (repos, clients) | Sí |
| clase de arranque / `@Configuration` opcional | Sí (no se exige paquete `bootstrap`; el composition root es la clase `@SpringBootApplication` + component-scan) |

Los casos de uso llevan `@Service` (y `@Transactional` solo en el método que escribe en varios puertos) y reciben sus puertos por constructor explícito; Spring los cablea solo:
```java
@Service
class RegisterAdopterService implements RegisterAdopter {
    RegisterAdopterService(AdopterRepository repo, Clock clock) { ... }

    @Transactional // solo si este método escribe en más de un puerto (INV-18)
    public Adopter register(...) { ... }
}
```
Son las **únicas** anotaciones de Spring permitidas en `application` (INV-12). Sin Lombok ni `@Slf4j`. Un `@Configuration` con `@Bean` solo se justifica para beans que no son casos de uso (p. ej. `Clock`) y puede vivir en la clase `@SpringBootApplication`; no crees una clase de configuración para cablear casos de uso. Un decorador o `TransactionTemplate` es una alternativa válida, no obligatoria: en servicios pequeños es sobreingeniería.

## 2. Inyección y configuración
- Inyección **por constructor**; sin `@Autowired` en campos.
- `@ConfigurationProperties` tipadas (records con `@ConstructorBinding` implícito) + `@Validated`; nada de `@Value` disperso.
- Perfiles por entorno; secretos desde variables/gestor, jamás en `application.yml` versionado.
- `spring.main.lazy-initialization=false` en prod para fallar rápido.

## 3. Web MVC
- Controladores delgados: mapear request → comando, llamar al caso de uso, mapear resultado → response DTO.
- DTOs de transporte separados del dominio; validación con Bean Validation (`@Valid`, `@NotBlank`...) **solo en DTOs**.
- Manejo de errores centralizado: `@RestControllerAdvice` + `ProblemDetail` (RFC 7807).
- Códigos HTTP correctos; `Location` en 201; idempotencia con `Idempotency-Key` en POST críticos.
- Paginación limitada (`size` máximo), sin exponer entidades.

## 4. Spring WebFlux (reactivo)
- **Regla de oro**: nunca bloquear el event loop (`block()`, `Thread.sleep`, JDBC, I/O síncrono). Detección: BlockHound en tests.
- Tipos de retorno `Mono<T>`/`Flux<T>` de punta a punta; el puerto puede declarar `Mono/Flux` **solo si el equipo acepta Reactor en aplicación**; alternativa pura: puertos síncronos + adaptadores que envuelven con `Mono.fromCallable(...).subscribeOn(boundedElastic())`.
- Sin `subscribe()` manual en servicios; el framework suscribe.
- Operadores: `flatMap` para asincronía, `concatMap` para orden, `flatMapSequential` con concurrencia acotada; `timeout`, `retryWhen(Retry.backoff(...))`, `onErrorMap` a errores de dominio.
- Backpressure: `limitRate`, `buffer` acotado; jamás `onBackpressureBuffer()` ilimitado.
- Contexto: `Context`/Micrometer Observation en lugar de `ThreadLocal`.
- Tests: `StepVerifier`, `WebTestClient`; virtual time para timeouts.
- BD reactiva: R2DBC / Reactive Mongo; no mezclar JPA con WebFlux sin aislar en `boundedElastic`.
- Cliente HTTP: `WebClient` con timeouts de conexión/lectura/respuesta y pool acotado.
- Si la carga es I/O bloqueante con Java 21, valora **MVC + virtual threads** en lugar de WebFlux.

## 5. Spring Data
- Repositorios Spring Data **solo** en `adapter.out.persistence`, ocultos tras el puerto de dominio.
- Entidades JPA (`@Entity`) ≠ entidades de dominio; mapeo explícito. **Manual por defecto** (mappers estáticos con test de ida y vuelta, INV-11). MapStruct solo si se cumplen los umbrales y reglas de `spring-boot/mapstruct.md` (MS-01..10); con dominio inmutable y pocos agregados el mapeo manual es más simple.
- `open-in-view=false`; cargas explícitas (`@EntityGraph`, proyecciones) para evitar N+1.
- Migraciones con Flyway/Liquibase; `ddl-auto=validate|none` en prod.
- `@Transactional` solo en el método de caso de uso que escribe en varios puertos; una operación de un solo puerto ya es atómica en su adaptador. Sin `@Transactional` de clase ni `readOnly` por defecto.
- Consultas: derivadas simples o `@Query` parametrizadas; jamás concatenar strings.
- Locking optimista (`@Version`) para agregados concurrentes.

## 6. Spring Security
- `SecurityFilterChain` bean (sin `WebSecurityConfigurerAdapter`); **deny by default** (`anyRequest().authenticated()`).
- Resource server: `oauth2ResourceServer(jwt)`; validar `iss`, `aud`, `exp`; algoritmo fijado; JWKS con caché.
- Autorización a nivel de método (`@PreAuthorize`) **en adaptadores de entrada**; comprobar propiedad del recurso (anti-IDOR).
- Contraseñas: `DelegatingPasswordEncoder` con Argon2/bcrypt.
- CSRF activo para sesiones/cookies; desactivar solo en APIs stateless con tokens en cabecera.
- CORS con orígenes explícitos; cabeceras de seguridad por defecto activas.
- Sesión `STATELESS` en APIs; `SecurityContext` no se propaga al dominio: pasar `Principal`/`ActorId` como parámetro tipado.
- Actuator: exponer solo `health`/`info`, protegido el resto; sin `env`/`heapdump` públicos.

## 7. Observabilidad y resiliencia
- Micrometer + OpenTelemetry; logs JSON con `traceId`.
- Resilience4j (circuit breaker, retry con jitter, bulkhead, timeouts) en `adapter.out`.
- Health/readiness/liveness diferenciados; apagado ordenado (`server.shutdown=graceful`).

## 8. Tests
| Nivel | Herramienta |
|-------|-------------|
| Dominio/aplicación | JUnit 5 + AssertJ, sin contexto Spring |
| Adaptador web | `@WebMvcTest` / `@WebFluxTest` |
| Persistencia | `@DataJpaTest`/`@DataR2dbcTest` con H2 (offline/CI simple) o Testcontainers (fidelidad con el motor real). Riesgo de H2 vs MySQL/Postgres: diferencias de dialecto, tipos y locking; documéntalo y exige ambos si hay SQL nativo |
| Contrato | Spring Cloud Contract / Pact |
| Arquitectura | ArchUnit |
| Integración | `@SpringBootTest` mínimo, Testcontainers |

## 8b. Patrones de test que funcionaron
- Matriz de transiciones con `@ParameterizedTest` + `@EnumSource(mode = EXCLUDE)`; contrato abstracto compartido fake/JPA; test de rollback multi-puerto; concurrencia con `ExecutorService` + `CountDownLatch`; exhaustividad de enums dominio↔persistencia; PAN ausente de todas las columnas (detalle en `quality/tdd-workflow/protocol.md`).
- Tests de contexto Spring que declaran un bean propio (p. ej. `Clock`) colisionan con el de la clase principal: `@Primary` o nombre distinto. Slice tests con mappers generados: `@Import`.

## 9. Anti-patrones
- `@Entity` o `@JsonProperty` en dominio.
- `@Autowired`, Lombok o `@Slf4j` en aplicación (`@Service` y `@Transactional` sí están permitidas).
- Caso de uso que recibe `ServerRequest`, `HttpServletRequest` o `Authentication`.
- `block()` en WebFlux; `subscribe()` dentro de un servicio.
- `@Autowired` en campo; `@Value` disperso.
- Devolver entidades JPA en controladores.
- Excepciones tragadas en `@ControllerAdvice` que devuelven 200.

## 10. Checklist
- [ ] Spring solo en `adapter`, la clase de arranque y (`@Service`/`@Transactional`) en casos de uso.
- [ ] Casos de uso con `@Service`, constructor explícito y `@Transactional` solo en métodos multi-puerto; sin clases de configuración solo para cablearlos.
- [ ] Sin bloqueo en pipelines reactivos (BlockHound).
- [ ] DTOs, entidades JPA y dominio separados.
- [ ] Security deny-by-default, JWT validado, anti-IDOR.
- [ ] Migraciones versionadas, `ddl-auto` seguro.
- [ ] ArchUnit en CI; Testcontainers (o H2 documentado) para persistencia.

## 11. Lecciones de proyectos reales (coffee-shop hexagonal)
- Los servicios de aplicación **no** llevan `@Slf4j`/`@RequiredArgsConstructor` (constructor explícito); `@Service` sí y `@Transactional` solo en métodos multi-puerto (ver §1). Un `BeanConfig` solo para cablearlos fue sobreingeniería.
- Entidades JPA con sufijo `JpaEntity`; sin clases homónimas al dominio.
- Adaptador de persistencia: `@Component` llamado `<Agregado>PersistenceAdapter`; Spring Data queda privado a su paquete.
- Mapeos estáticos están bien, pero con test de ida y vuelta por estado (INV-11). Nunca colapsar estados de dominio en un solo valor de BD.
- `@Builder` de Lombok en entidades JPA: inicializa colecciones con `@Builder.Default` (`new ArrayList<>()`) para evitar NPE en helpers como `setItems`.
- Controladores: `@RestController`, `PUT` para reemplazo, `@Valid` en DTOs, DTOs en `adapter.in.web.dto` (no en un paquete `mapper`).
- `@RestControllerAdvice` obligatorio: `NotFound → 404`, transición inválida → `409`, validación → `400/422`, con `ProblemDetail`.
- `@Transactional` en el método que escribe en varios puertos (INV-18), con test de rollback. No añadas decoradores transaccionales por defecto.
- No crees clases de configuración ni decoradores que solo repitan lo que una anotación ya resuelve.
- Sin beans con `UnsupportedOperationException`: si un puerto aún no tiene adaptador, el caso de uso no se registra.
- `IllegalStateException` genérica en dominio → excepción tipada con sufijo `Exception` (`OrderStateException`); en desarrollos nuevos se exige el sufijo `Exception` (incluidas las de puerto, p. ej. `OrderNotFoundException`).
- Datos de tarjeta: guardar solo últimos 4 dígitos/token; sobrescribir `toString` del `record`.
