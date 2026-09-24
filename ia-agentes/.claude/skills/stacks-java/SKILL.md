---
name: stacks-java
description: Conocimiento de stack para proyectos Java/Kotlin (Maven o Gradle) — qué features usar según la versión del proyecto (8, 11, 17, 21, 25), herramientas y comandos de TDD (JUnit 5, AssertJ, Mockito, StepVerifier), layout por arquitectura (hexagonal/clean/onion) y empaquetado Docker. Úsala siempre que detect_stack.py reporte java o kotlin, antes de escribir tests o código.
---

<!-- GENERADO por compiler/compile.py — no editar a mano.
     Fuente: skills/stacks/java/ -->

# Stack Java

Esta skill **no define el proceso** (eso es de los agentes: TDD primero, slices, decisiones consultadas). Define **cómo se hace en Java**.

## 0. Detectar antes de escribir

```bash
python3 .claude/scripts/detect_stack.py
```

Usá `version`, `build_tool`, `frameworks`, `test_one` y `test_all` de la salida. **Nunca uses una feature de una versión mayor que la del proyecto.** Si no hay versión declarada, preguntá; en proyectos nuevos, la recomendación por defecto es la **última LTS (25)**.

## 1. Qué usar según la versión (solo LTS; las no-LTS no se usan en producción)

| Versión | Lo que se habilita (acumulativo) | Regla práctica |
|---|---|---|
| **8** (2014) | Lambdas, Streams, `Optional`, `java.time` | Sin `var`, sin records: POJOs inmutables a mano (campos `final`, sin setters). |
| **11** (2018) | `var` (10), `HttpClient`, `String.isBlank()/strip()/lines()`, `Files.readString`, `List.of()`/`Map.of()` (9) | `var` solo cuando el tipo es obvio por el lado derecho. |
| **17** (2021) | `record` (16), `sealed` (17), text blocks (15), switch expressions (14), pattern matching de `instanceof` (16), NPEs descriptivos | Value Objects y Commands = `record` con validación en el *compact constructor*. Jerarquías cerradas = `sealed interface`. Baseline de Spring Boot 3.x/4.x. |
| **21** (2023) | Virtual Threads (444), record patterns (440), pattern matching en `switch` (441), Sequenced Collections (431). *Preview, no usar en prod:* Structured Concurrency, Scoped Values. | `getFirst()/getLast()/reversed()`. Switch exhaustivo sobre `sealed` sin `default`. |
| **25** (2025) | Scoped Values **final** (506), constructores flexibles: validar antes de `super(...)` (513), `import module` (511), archivos fuente compactos e `void main()` de instancia (512), compact object headers (519). *Todavía preview:* Structured Concurrency (505), primitive patterns (507), Stable Values (502). | `ScopedValue` en vez de `ThreadLocal`. Validar argumentos antes de `super(...)`. |

> Cadencia: una release cada 6 meses (marzo y septiembre) y una LTS cada 2 años (21 → 25 → 29 en 2027). Para una versión no-LTS reciente, verificá las JEPs en openjdk.org antes de afirmar qué trae: no las inventes.

### Reglas de lenguaje (aplican desde la versión que las habilita)

- **Records:** para DTOs, Commands y Value Objects. La validación del *compact constructor* es **estructural** (¿el dato está bien formado?). Una regla que depende del **estado** de una entidad (ej. "solo se adopta si está DISPONIBLE") va en el método de la entidad, no en el record.
- **Interfaz vs record:** interfaz solo si hay (o puede haber) más de una implementación intercambiable, como los puertos. Un paquete de datos es record, no interfaz.
- **Pattern matching:** desestructurar con record patterns en vez de encadenar getters. Guardas con `when`, `case null ->` explícito, y sin `default` sobre tipos `sealed` (esconde el caso olvidado).
- **Colecciones:** `List.of()`, `.toList()` en streams (16+), `Optional` solo como tipo de retorno (nunca en campos ni parámetros).

### Concurrencia: Virtual Threads vs Reactor (21+) — son alternativas, no se suman

- **Virtual Threads + código bloqueante** (JDBC, librerías sin driver reactivo) cuando reescribir a `Mono`/`Flux` no se justifica. Es la opción pragmática. En Spring Boot: `spring.threads.virtual.enabled=true`.
- **Reactor/WebFlux** cuando ya hay drivers reactivos (R2DBC, WebClient) y hace falta composición de streams o backpressure. Detalle en el agente `spring-boot-webflux-dev`.
- **Nunca mezclarlos sin razón:** nada de `.block()` "para unir" ambos mundos. Bloquear dentro de un flujo reactivo sigue siendo un bug aunque el hilo sea virtual.
- **Reglas de Virtual Threads:** no se poolean (`Executors.newVirtualThreadPerTaskExecutor()`). Nada de `synchronized` con I/O adentro (causa *pinning*; se mitigó mucho en 24+, pero en 21 usá `ReentrantLock`). Para diagnosticar: `-Djdk.tracePinnedThreads=full` (21).

## 2. TDD en Java

| Nivel | Herramienta | Notas |
|---|---|---|
| Dominio | JUnit 5 + AssertJ | Sin Spring y sin mocks: `new Mascota(...)` directo. `@ParameterizedTest` para tablas de transiciones. |
| Aplicación | JUnit 5 + Mockito | Mockear **solo los puertos de salida**, nunca el dominio. |
| Reactivo | `StepVerifier` (reactor-test) | **Nunca** `.block()` en un test. |
| Web | `@WebFluxTest` / `@WebMvcTest` + `WebTestClient` / `MockMvc` | Mockear los puertos de entrada. |
| Cliente HTTP | `MockWebServer` (okhttp) o WireMock | Probar 200, 404, timeout y 5xx. |
| Integración | `@SpringBootTest` + Testcontainers | Base de datos real en Docker. |

- **Correr un solo test:** `./gradlew test --tests 'MascotaTest'` · `./mvnw -q test -Dtest=MascotaTest`.
- **Cómo se ve un RED válido:** `AssertionFailedError`, `UnsupportedOperationException` del stub, o la excepción de dominio esperada que todavía no se lanza. Un error de **compilación no es RED**: primero hace falta el stub (scaffold).
- **Stub del scaffold:** `throw new UnsupportedOperationException("TODO: <regla>");`.
- **Nombres:** `should<Resultado>When<Condición>()`, `@DisplayName` y `@Nested` por escenario.
- **Ubicación:** `src/test/java/<mismo paquete>/<Clase>Test.java`.
- **Opcional:** ArchUnit para verificar en el build que `domain` no importa `infrastructure` ni frameworks.

## 3. Layout según la arquitectura elegida

**Hexagonal** (convención `buckpal`, Tom Hombergs):

```text
src/main/java/com/<empresa>/<servicio>/
├── domain/                      entidades, VOs, excepciones de negocio (sin frameworks)
├── application/
│   ├── port/in/                 XxxUseCase (interfaz) + XxxCommand (record)
│   ├── port/out/                LoadXxxPort, SaveXxxPort
│   └── service/                 XxxService implements XxxUseCase
└── infrastructure/
    ├── adapter/in/web/          XxxController, DTOs con @Valid, @RestControllerAdvice → ProblemDetail
    ├── adapter/out/persistence/ XxxPersistenceAdapter (+ entity/ y mapper/ con driver real)
    ├── adapter/out/client/      XxxClientAdapter (WebClient/RestClient)
    └── config/                  @Configuration, OpenAPI (springdoc), @ConfigurationProperties
```

**Clean:** `entities/` · `usecases/` (interactors + boundaries) · `interfaceadapters/` (controllers, presenters, gateways) · `frameworks/`. **Onion:** `domain/model` · `domain/services` · `application/` · `infrastructure/`. En las tres, **las dependencias apuntan hacia el dominio**.

Convenciones Spring: inyección por constructor (`@RequiredArgsConstructor` sobre campos `private final`, sin `@Autowired` en campos), `application.yml`, `@ConfigurationProperties` en vez de `@Value` disperso, `ProblemDetail` (RFC 7807) para errores.

## 4. Docker

Multi-stage: `eclipse-temurin:<versión>-jdk` para build → `eclipse-temurin:<versión>-jre` para runtime, usuario no root y el jar copiado desde el stage de build. Con Spring Boot se pueden usar *layered jars* (`java -Djarmode=tools`) para aprovechar la cache de capas.

## 5. Antipatrones a señalar

`.block()` en código reactivo · JPA/JDBC dentro de WebFlux · `@Autowired` en campos · setters públicos que saltean invariantes (`setEstado`) · `Optional` en campos · `catch (Exception e)` genérico que traga errores · features de una versión mayor a la del proyecto · `IllegalStateException` genérica en lugar de una excepción de dominio con nombre.
