# Java 8 – 11 — Reglas de Stack

Para bases de código legadas. Respeta `common-naming.md` y las reglas de arquitectura agnósticas.

## 1. Lo que tienes
| Versión | Características útiles |
|---------|-------------------------|
| 8 | Lambdas, Streams, `Optional`, `java.time`, default methods, `CompletableFuture` |
| 9 | Módulos (JPMS), `List.of/Set.of/Map.of`, `Optional.or/ifPresentOrElse`, private interface methods |
| 10 | `var` local |
| 11 | `String.isBlank/strip/lines/repeat`, `Files.readString`, HTTP Client, `var` en lambdas, `Predicate.not` |

## 2. Reglas
- **J8-01** Value Objects: clase `final`, campos `private final`, constructor con validación, `equals/hashCode/toString`. (Sin records: usa builder o Lombok `@Value` **solo en adaptadores**; en dominio prefiere código explícito.)
- **J8-02** Colecciones inmutables: `List.of` (9+) o `Collections.unmodifiableList(new ArrayList<>(x))` (8); copia defensiva en el constructor.
- **J8-03** `Optional` solo como retorno. Nunca `Optional.get()` sin `isPresent`; usa `orElseThrow`, `map`, `ifPresentOrElse` (9+).
- **J8-04** `java.time` siempre; inyecta `Clock`. Prohibido `Date/Calendar/SimpleDateFormat`.
- **J8-05** Streams: pipelines cortos y sin efectos secundarios; nada de `forEach` que muta estado externo; `.collect(Collectors.toList())` (o `toUnmodifiableList` en 10+).
- **J8-06** Variantes de dominio: `enum` con comportamiento o jerarquía cerrada por convención (constructor package-private + `final`); sin sealed, evita `instanceof` en cadena → patrón **Visitor** o polimorfismo.
- **J8-07** `var` (10+) solo con tipo evidente; nunca con literales `null`/diamond ambiguo.
- **J8-08** Concurrencia: `ExecutorService` con pools acotados, `CompletableFuture` con `Executor` explícito y timeouts (`orTimeout` desde 9). Sin `Thread` manual ni `synchronized` sobre objetos públicos.
- **J8-09** Recursos: `try-with-resources` siempre; nunca `finalize`.
- **J8-10** Interfaces con default methods solo para evolución compatible, no para lógica de dominio.
- **J8-11** `Objects.requireNonNull(x, "x")` en constructores; sin `null` como retorno de colecciones (devuelve vacías).
- **J8-12** Text blocks no disponibles: usa constantes o recursos externos para SQL/JSON largos.
- **J8-13** Si el runtime es 8: cuidado con librerías (Spring Boot ≤2.7); preferir migrar a 17 por soporte de seguridad.

## 3. Ejemplo — Value Object sin records
```java
public final class Email {
    private final String value;

    private Email(String value) { this.value = value; }

    public static Email of(String raw) {
        Objects.requireNonNull(raw, "email");
        String normalized = raw.trim().toLowerCase(Locale.ROOT);
        if (!normalized.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new InvalidEmailException(normalized);
        }
        return new Email(normalized);
    }

    public String value() { return value; }

    @Override public boolean equals(Object o) {
        return this == o || (o instanceof Email && value.equals(((Email) o).value));
    }
    @Override public int hashCode() { return value.hashCode(); }
    @Override public String toString() { return value; }
}
```

## 4. Ejemplo — Resultado con Visitor (sin sealed)
```java
public interface AdoptionResult {
    <R> R accept(Visitor<R> v);
    interface Visitor<R> { R approved(Approved a); R rejected(Rejected r); }
}
```

## 5. Migración recomendada (8/11 → 17+)
1. Actualiza build a 17 con `--release 17` y corrige APIs removidas.
2. Sustituye VOs por `record`.
3. Reemplaza Visitors/enum-switch por `sealed` + pattern matching.
4. Evalúa virtual threads (21) para I/O bloqueante.
5. Cubre cada paso con tests existentes (refactor en verde).

## 6. Checklist
- [ ] Sin `Date/Calendar`; `Clock` inyectado.
- [ ] Colecciones defensivas e inmutables.
- [ ] `Optional` solo en retornos.
- [ ] `try-with-resources` para recursos.
- [ ] ArchUnit ejecutándose en CI (versión compatible con Java 8).
- [ ] Sin frameworks en dominio/aplicación.
