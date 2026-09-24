# Java 17 – 21+ — Reglas de Stack

Aplica junto a `skills/stacks/java/common-naming.md`. Las reglas de arquitectura siguen siendo las de `skills/architecture/`; aquí solo se decide **cómo** expresarlas en Java moderno.

## 1. Versión y toolchain
- LTS objetivo: **17** (mínimo) y **21** (recomendado). Fijar `--release` en el build.
- Java 21: usar `--enable-preview` **solo** para APIs en preview (p. ej. String Templates, Scoped Values); nunca en producción sin decisión explícita.
- Formato/estático: Spotless/Checkstyle, ErrorProne, SpotBugs.
- Tests de arquitectura: **ArchUnit** (ver §9).

## 2. Records (Java 16+) — Value Objects y DTOs
Usa `record` para Value Objects, comandos, queries, eventos y DTOs inmutables.

```java
public record Email(String value) {
    public Email {                                   // constructor compacto = validación
        Objects.requireNonNull(value, "email");
        value = value.strip().toLowerCase(Locale.ROOT);
        if (!value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new InvalidEmailException(value);
        }
    }
}

public record RegisterAdopterCommand(String fullName, Email email, LocalDate birthDate) {}
```

Reglas:
- **R-01** Validar invariantes en el constructor compacto; el record nunca existe en estado inválido.
- **R-02** Copias defensivas de colecciones: `List.copyOf(items)` en el constructor compacto.
- **R-03** No usar records para entidades con identidad mutable ni para entidades ORM.
- **R-04** Los records no llevan anotaciones de framework en dominio; mapear en el adaptador.
- **R-05** Métodos de comportamiento permitidos (`Money.plus`) → retornan nueva instancia.

## 3. Sealed Interfaces/Classes (Java 17+) — Modelado de variantes
Modela estados, resultados y errores de dominio como jerarquías cerradas.

```java
public sealed interface AdoptionResult
        permits AdoptionResult.Approved, AdoptionResult.Rejected, AdoptionResult.PendingReview {

    record Approved(AdoptionId id, Instant at) implements AdoptionResult {}
    record Rejected(AdoptionId id, RejectionReason reason) implements AdoptionResult {}
    record PendingReview(AdoptionId id, List<String> missingDocs) implements AdoptionResult {
        public PendingReview { missingDocs = List.copyOf(missingDocs); }
    }
}
```

- **S-01** Prefiere `sealed` a `enum` cuando las variantes portan datos distintos.
- **S-02** Prefiere resultados tipados (`sealed`) a excepciones para *flujos de negocio esperados*; reserva excepciones para violaciones de invariantes/errores técnicos.
- **S-03** Declara `permits` explícito cuando las variantes están en archivos distintos; `final`/`record` en las hojas.
- **S-04** Un `switch` sobre sealed **sin `default`**: el compilador garantiza exhaustividad.

## 4. Pattern Matching
### `instanceof` con patrón (16+)
```java
if (event instanceof AdopterRegistered e && e.email().isCorporate()) { notify(e); }
```

### `switch` con patrones y guards (21)
```java
String describe(AdoptionResult r) {
    return switch (r) {
        case AdoptionResult.Approved a        -> "Aprobada " + a.id();
        case AdoptionResult.Rejected(var id, var reason)
                when reason.isDefinitive()    -> "Rechazo definitivo " + id;
        case AdoptionResult.Rejected rej      -> "Rechazada " + rej.id();
        case AdoptionResult.PendingReview p   -> "Pendiente: " + p.missingDocs();
    };
}
```

### Record patterns (21)
```java
if (shape instanceof Rectangle(Point(var x, var y), var w, var h)) { ... }
```

- **P-01** Cero `default` en `switch` sobre sealed; el compilador detecta variantes nuevas.
- **P-02** Ordena de casos específicos a generales (guards antes del caso genérico).
- **P-03** Evita `instanceof` + cast manual y cadenas de `if/else` por tipo.
- **P-04** Usa `case null` explícito si el selector puede ser nulo (o evítalo con `Objects.requireNonNull`).
- **P-05** `var` en patrones de record solo si el tipo es evidente.

## 5. Virtual Threads (Java 21) — Concurrencia
Para cargas **I/O-bound** con estilo bloqueante.

```java
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    var profile = executor.submit(() -> profileClient.fetch(id));
    var orders  = executor.submit(() -> orderClient.fetch(id));
    return new Dashboard(profile.get(), orders.get());
}
```
Hilo suelto: `Thread.ofVirtual().name("worker-", 0).start(task);`

Reglas:
- **V-01** Un virtual thread por tarea; **nunca** en pools de tamaño fijo ni reutilizados.
- **V-02** No para CPU-bound intensivo (usar `ForkJoinPool`/platform threads).
- **V-03** Evitar *pinning*: no sostener `synchronized` durante I/O bloqueante; usar `ReentrantLock`. Diagnóstico: `-Djdk.tracePinnedThreads=full`.
- **V-04** Limitar concurrencia hacia recursos escasos con `Semaphore` (no con el tamaño del pool).
- **V-05** No almacenar objetos pesados en `ThreadLocal`; preferir `ScopedValue` (preview en 21) o parámetros.
- **V-06** Propagar cancelación/timeouts: `Future.cancel`, `orTimeout`, o `StructuredTaskScope` (preview en 21):

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {   // --enable-preview
    var user  = scope.fork(() -> users.find(id));
    var pets  = scope.fork(() -> pets.byOwner(id));
    scope.join().throwIfFailed();
    return new View(user.get(), pets.get());
}
```
- **V-07** El dominio **no** crea hilos: la concurrencia vive en aplicación/adaptadores, tras puertos.
- **V-08** Con frameworks: habilitar la opción nativa (Spring Boot 3.2+: `spring.threads.virtual.enabled=true`; Quarkus: `@RunOnVirtualThread`).

## 6. Otras características a usar
| Característica | Uso |
|----------------|-----|
| Text Blocks (15) | SQL/JSON de prueba, plantillas; nunca concatenar entrada de usuario |
| `var` (10) | Solo con tipo evidente en el lado derecho |
| Switch expressions (14) | Sustituyen `switch` de sentencia con `break` |
| `Optional` | Solo como **retorno**; nunca en campos, parámetros ni colecciones |
| `Stream.toList()` (16) | Lista inmutable; sustituye `collect(toList())` |
| Sequenced Collections (21) | `getFirst()/getLast()/reversed()` |
| `HexFormat`, `String.formatted` | Utilidades modernas |
| `java.time` | Siempre; inyectar `Clock` |
| Helpful NPE messages (14) | Mantener activados |

## 7. Prohibiciones
- `java.util.Date`, `Calendar`, `SimpleDateFormat`.
- Herencia de implementación en dominio; usar composición/sealed.
- `Optional.get()` sin verificación; usar `orElseThrow(...)`.
- `Thread.stop`, finalizers, `Object.finalize`.
- Pools de virtual threads, `synchronized` con I/O.
- Serialización Java nativa.
- Lombok en el dominio moderno (los `record` lo reemplazan); si se usa, solo en adaptadores.

## 8. Errores y `null`
- Invariantes → excepción no verificada de dominio (`DomainException` jerárquica).
- Ausencia esperada → `Optional`/`sealed Result`.
- `Objects.requireNonNull` con mensaje en constructores; anotaciones `@NonNull` de paquete solo en adaptadores.

## 9. Arquitectura: prueba ejecutable con ArchUnit
```java
@AnalyzeClasses(packages = "com.acme.adoption", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    @ArchTest
    static final ArchRule domain_is_pure = noClasses().that().resideInAPackage("..domain..")
        .should().dependOnClassesThat().resideInAnyPackage(
            "..application..", "..adapter..", "org.springframework..", "jakarta..", "io.quarkus..",
            "reactor..", "com.fasterxml..");

    @ArchTest
    static final ArchRule application_has_no_adapters = noClasses().that().resideInAPackage("..application..")
        .should().dependOnClassesThat().resideInAPackage("..adapter..");

    @ArchTest
    static final ArchRule adapters_are_isolated = slices().matching("..adapter.(*)..")
        .should().notDependOnEachOther();

    @ArchTest
    static final ArchRule no_cycles = slices().matching("com.acme.adoption.(*)..").should().beFreeOfCycles();
}
```

## 9b. Tests
- JUnit 5 + AssertJ; `@ParameterizedTest` para tablas de casos; Testcontainers solo en integración.
- Nombres: `debe_<resultado>_cuando_<condición>` (o `should_..._when_...`).
- Sin `Thread.sleep`; usar Awaitility para asincronía.
- Fakes en memoria para puertos de salida + test de contrato compartido.
- Mutation testing (PIT) en dominio.

## 10. Checklist Java 17–21+
- [ ] VOs/DTOs como `record` con validación compacta y copias defensivas.
- [ ] Variantes de dominio como `sealed` y `switch` exhaustivo sin `default`.
- [ ] `instanceof`/`switch` con pattern matching; sin casts manuales.
- [ ] Virtual threads por tarea, sin pinning, sin pools; límites con `Semaphore`.
- [ ] Sin `Date/Calendar`; `Clock` inyectado.
- [ ] `Optional` solo como retorno.
- [ ] ArchUnit ejecutándose en CI.
- [ ] Sin frameworks importados en `..domain..` ni `..application..`.
