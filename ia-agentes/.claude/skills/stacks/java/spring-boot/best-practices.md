# Spring Boot (MVC / WebFlux / Data / Security) — Buenas Prácticas

Spring es un **detalle de infraestructura**: vive solo en `adapter.*` y `bootstrap`. Dominio y aplicación no lo importan.

## 1. Ubicación de Spring en la arquitectura
| Capa | ¿Spring permitido? |
|------|--------------------|
| domain | No |
| application (`port`, `service`) | No (sin `@Service`, `@Transactional`, `@Autowired`) |
| adapter.in (controllers, listeners) | Sí |
| adapter.out (repos, clients) | Sí |
| bootstrap (`@Configuration`) | Sí |

Los casos de uso se registran en `bootstrap` con `@Bean`:
```java
@Configuration
class UseCaseConfig {
    @Bean RegisterAdopter registerAdopter(AdopterRepository repo, Clock clock) {
        return new RegisterAdopterService(repo, clock);
    }
}
```
La transaccionalidad se aplica con un decorador/adaptador (`TransactionalRegisterAdopter`) o vía `TransactionTemplate` en el borde, no anotando la aplicación.

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
- Entidades JPA (`@Entity`) ≠ entidades de dominio; mapeo explícito (MapStruct/manual).
- `open-in-view=false`; cargas explícitas (`@EntityGraph`, proyecciones) para evitar N+1.
- Migraciones con Flyway/Liquibase; `ddl-auto=validate|none` en prod.
- `@Transactional` en el adaptador/decorador; lecturas con `readOnly = true`.
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
| Persistencia | `@DataJpaTest`/`@DataR2dbcTest` + Testcontainers |
| Contrato | Spring Cloud Contract / Pact |
| Arquitectura | ArchUnit |
| Integración | `@SpringBootTest` mínimo, Testcontainers |

## 9. Anti-patrones
- `@Entity` o `@JsonProperty` en dominio.
- `@Service` + `@Transactional` en aplicación.
- Caso de uso que recibe `ServerRequest`, `HttpServletRequest` o `Authentication`.
- `block()` en WebFlux; `subscribe()` dentro de un servicio.
- `@Autowired` en campo; `@Value` disperso.
- Devolver entidades JPA en controladores.
- Excepciones tragadas en `@ControllerAdvice` que devuelven 200.

## 10. Checklist
- [ ] Spring solo en `adapter` y `bootstrap`.
- [ ] Casos de uso cableados en `@Configuration`.
- [ ] Sin bloqueo en pipelines reactivos (BlockHound).
- [ ] DTOs, entidades JPA y dominio separados.
- [ ] Security deny-by-default, JWT validado, anti-IDOR.
- [ ] Migraciones versionadas, `ddl-auto` seguro.
- [ ] ArchUnit + Testcontainers en CI.

## 11. Lecciones de proyectos reales (coffee-shop hexagonal)
- Los servicios de aplicación **no** llevan `@Service`/`@Slf4j`/`@RequiredArgsConstructor`: se cablean en `@Configuration` (ver §1).
- Entidades JPA con sufijo `JpaEntity`; sin clases homónimas al dominio.
- Adaptador de persistencia: `@Component` llamado `<Agregado>PersistenceAdapter`; Spring Data queda privado a su paquete.
- Mapeos estáticos están bien, pero con test de ida y vuelta por estado (INV-11). Nunca colapsar estados de dominio en un solo valor de BD.
- `@Builder` de Lombok en entidades JPA: inicializa colecciones con `@Builder.Default` (`new ArrayList<>()`) para evitar NPE en helpers como `setItems`.
- Controladores: `@RestController`, `PUT` para reemplazo, `@Valid` en DTOs, DTOs en `adapter.in.web.dto` (no en un paquete `mapper`).
- `@RestControllerAdvice` obligatorio: `NotFound → 404`, transición inválida → `409`, validación → `400/422`, con `ProblemDetail`.
- Transacción por caso de uso mediante decorador en `bootstrap` cuando se escriben varios puertos (INV-18).
- Sin beans con `UnsupportedOperationException`: si un puerto aún no tiene adaptador, el caso de uso no se registra.
- `IllegalStateException` genérica en dominio → excepción tipada (`InvalidOrderTransition`).
- Datos de tarjeta: guardar solo últimos 4 dígitos/token; sobrescribir `toString` del `record`.
