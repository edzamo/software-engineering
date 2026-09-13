# Interfaces funcionales — Predicate, Consumer, Supplier, Function y compañía

La revolución de Java 8 no fue solo "ahora hay lambdas" — fue que la biblioteca estándar (`java.util.function`) define un catálogo de interfaces funcionales reusables, para no tener que declarar una interfaz propia cada vez que se necesita pasar comportamiento como parámetro.

## Qué hace que una interfaz sea "funcional"

Una interfaz funcional (`@FunctionalInterface`) declara **exactamente un método abstracto** (*SAM — Single Abstract Method*) — puede tener métodos `default`/`static` además, pero solo uno sin implementar. Eso es lo único que una expresión lambda necesita para saber "qué implementar".

```java
@FunctionalInterface
interface Validador<T> {
    boolean esValido(T valor); // el único método abstracto — esto es lo que la lambda implementa
}

Validador<String> noVacio = s -> !s.isBlank(); // la lambda ES la implementación de esVálido()
```

`@FunctionalInterface` no es obligatoria para que algo funcione — es una anotación de **intención**: si alguien agrega sin querer un segundo método abstracto, el compilador lo rechaza ahí mismo, en vez de romper en silencio a todo el código que ya usaba esa interfaz con una lambda.

## El catálogo de `java.util.function` — la tabla que hay que saber de memoria

| Interfaz | Método abstracto | Qué hace | Ejemplo |
|---|---|---|---|
| **`Predicate<T>`** | `boolean test(T t)` | Evalúa una condición sobre `T`, devuelve `true`/`false`. | `Predicate<Cliente> esMayorDeEdad = c -> c.edad() >= 18;` |
| **`Consumer<T>`** | `void accept(T t)` | Recibe `T`, produce un efecto colateral (loguear, persistir) — no devuelve nada. | `Consumer<Pedido> log = p -> logger.info("Pedido {}", p.id());` |
| **`Function<T, R>`** | `R apply(T t)` | Transforma `T` en `R`. | `Function<Cliente, String> nombre = Cliente::nombre;` |
| **`Supplier<T>`** | `T get()` | No recibe nada, produce un `T` — patrón Factory. | `Supplier<UUID> nuevoId = UUID::randomUUID;` |
| `BiConsumer<T, U>` | `void accept(T t, U u)` | Como `Consumer` pero con dos parámetros. | `BiConsumer<String, Integer> print = (k, v) -> System.out.println(k + "=" + v);` |
| `BiFunction<T, U, R>` | `R apply(T t, U u)` | Como `Function` pero combina dos entradas. | `BiFunction<Integer, Integer, Integer> sumar = Integer::sum;` |
| `UnaryOperator<T>` *(extiende `Function<T,T>`)* | `T apply(T t)` | Transformación donde entrada y salida son del **mismo tipo**. | `UnaryOperator<String> mayus = String::toUpperCase;` |
| `BinaryOperator<T>` *(extiende `BiFunction<T,T,T>`)* | `T apply(T t1, T t2)` | Combina dos valores del mismo tipo en uno — típico de reducciones. | `BinaryOperator<Integer> max = Integer::max;` |

**Cómo recordarlos sin memorizar cada uno**: preguntate cuántos parámetros recibe (`Bi` = dos) y qué devuelve (`Predicate` → `boolean`, `Consumer` → nada, `Function`/`Supplier` → un valor). El resto son variaciones de esa matriz.

## Lambda vs. clase anónima — qué cambia realmente

```java
// Clase anónima — genera un .class físico separado
Comparator<Cliente> porEdad = new Comparator<Cliente>() {
    @Override
    public int compare(Cliente a, Cliente b) {
        return Integer.compare(a.edad(), b.edad());
    }
};

// Lambda — misma semántica, sin archivo .class adicional
Comparator<Cliente> porEdadLambda = (a, b) -> Integer.compare(a.edad(), b.edad());
```

- Una lambda **no genera una clase física** — se traduce a la instrucción de bytecode `invokedynamic`, resuelta en tiempo de ejecución. Esto reduce el número de archivos `.class` generados y el overhead de carga de clases.
- Semánticamente ambas hacen lo mismo (implementan el único método abstracto), pero una lambda solo puede implementar una interfaz **funcional** — una clase anónima puede implementar cualquier interfaz (con más de un método) o extender una clase.

## Method references — 4 variantes del operador `::`

| Variante | Sintaxis | Equivalente lambda |
|---|---|---|
| Método estático | `ClassName::staticMethod` | `x -> ClassName.staticMethod(x)` |
| Método de instancia de un objeto particular | `objetoConcreto::metodo` | `x -> objetoConcreto.metodo(x)` |
| Método de instancia de un tipo arbitrario (el receptor es el primer parámetro) | `ClassName::metodoDeInstancia` | `(obj, x) -> obj.metodoDeInstancia(x)` |
| Constructor | `ClassName::new` | `x -> new ClassName(x)` |

```java
List<String> nombres = clientes.stream()
    .map(Cliente::nombre)        // método de instancia de un tipo arbitrario — Cliente es el "receptor"
    .map(String::toUpperCase)    // mismo caso — String es el receptor
    .toList();

Supplier<ArrayList<String>> factory = ArrayList::new; // constructor
```

## Composición — `andThen` y `compose`

```java
Function<Integer, Integer> duplicar = x -> x * 2;
Function<Integer, Integer> sumarUno = x -> x + 1;

Function<Integer, Integer> f1 = duplicar.andThen(sumarUno); // (x*2)+1 — primero duplicar, después sumarUno
Function<Integer, Integer> f2 = duplicar.compose(sumarUno); // (x+1)*2 — primero sumarUno, después duplicar
```

`andThen` ejecuta la función actual primero y encadena la siguiente sobre el resultado; `compose` hace lo inverso — ejecuta el argumento primero. `Predicate` tiene su propia composición lógica: `and()`, `or()`, `negate()`.

```java
Predicate<Cliente> esMayorDeEdad = c -> c.edad() >= 18;
Predicate<Cliente> tieneDeuda = c -> c.deuda().compareTo(BigDecimal.ZERO) > 0;

Predicate<Cliente> esMayorSinDeuda = esMayorDeEdad.and(tieneDeuda.negate());
```

## Currying — descomponer una función de N argumentos en una cadena de un argumento

```java
BiFunction<Integer, Integer, Integer> sumar = (a, b) -> a + b;

// versión currificada — una Function que devuelve otra Function
Function<Integer, Function<Integer, Integer>> sumarCurried = a -> b -> a + b;
Function<Integer, Integer> sumarDiez = sumarCurried.apply(10);
int resultado = sumarDiez.apply(5); // 15
```

Es más una herramienta conceptual de programación funcional pura que algo de uso diario en Java empresarial — vale saber explicarla en una entrevista, pero no es algo que aparezca seguido en código de producción Java (a diferencia de lenguajes funcionales puros).

## Drills de repaso

| Tiempo | Pregunta |
|---|---|
| 4 min | "¿Cuál es la diferencia entre `Function<T,R>` y `UnaryOperator<T>`? ¿Por qué existe este último si es un caso particular del primero?" |
| 4 min | "Escribí de memoria las 4 variantes de method reference con un ejemplo de cada una." |
| 5 min | "¿Qué significa que una interfaz sea funcional (SAM)? ¿Por qué `@FunctionalInterface` no es estrictamente obligatoria pero sí recomendable?" |
| 4 min | "Explicá la diferencia entre `f.andThen(g)` y `f.compose(g)` con un ejemplo concreto." |

## Referencias

- Documentación oficial de Oracle — [`java.util.function` package summary](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html).
- Urma, Fusco, Mycroft — *Modern Java in Action* — capítulos sobre lambdas, referencias a métodos y composición funcional.

Relacionado: [`collections-and-streams.md`](collections-and-streams.md) para dónde se usan estas interfaces en la práctica (`Stream.filter(Predicate)`, `Stream.map(Function)`), y [`java-version-evolution.md`](java-version-evolution.md) para el contexto histórico (Java 8) en el que se introdujo todo este catálogo.
