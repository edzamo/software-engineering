# Collections & Streams — cheatsheet de operaciones del día a día

Operaciones sobre `List`, `Map` y `Stream` — la base de cualquier lógica de transformación de datos en Java, tanto en código bloqueante como dentro de un `map`/`flatMap` reactivo (ver [`reactive-programming/`](../reactive-programming)). Es contenido que se oxida rápido si no se usa seguido, por eso vale la pena tenerlo como cheatsheet.

> Este documento asume que ya sabés **cuál** estructura elegir (`ArrayList` vs `LinkedList`, `HashMap` vs `TreeMap`, etc. — ver [`data-structures-decision-guide.md`](data-structures-decision-guide.md)) y se enfoca en **cómo operarla** una vez elegida. Las interfaces funcionales que se usan en `filter`/`map` (`Predicate`, `Function`, etc.) están detalladas en [`functional-interfaces.md`](functional-interfaces.md).

## 1. List — cheatsheet

| Necesito | Cómo |
|---|---|
| Lista inmutable | `List.of(a, b, c)` |
| Lista mutable a partir de otra | `new ArrayList<>(lista)` |
| Filtrar | `lista.stream().filter(x -> ...).toList()` |
| Transformar | `lista.stream().map(x -> ...).toList()` |
| Ordenar por campo | `lista.stream().sorted(Comparator.comparing(Persona::apellido)).toList()` |
| Ordenar descendente | `.sorted(Comparator.comparing(Persona::edad).reversed())` |
| Orden por múltiples campos | `Comparator.comparing(Persona::apellido).thenComparing(Persona::nombre)` |
| Buscar el primero que cumple | `lista.stream().filter(...).findFirst()` → `Optional<T>` |
| ¿Alguno cumple? | `lista.stream().anyMatch(...)` |
| ¿Todos cumplen? | `lista.stream().allMatch(...)` |
| Sumar un campo numérico | `lista.stream().mapToInt(Persona::edad).sum()` |
| Quitar duplicados | `lista.stream().distinct().toList()` |
| Partición en dos grupos (bool) | `Collectors.partitioningBy(p -> p.edad() > 60)` |

```java
List<Cliente> mayores = clientes.stream()
    .filter(c -> c.edad() > 60)
    .sorted(Comparator.comparing(Cliente::apellido))
    .toList();
```

## 2. Map — cheatsheet

| Necesito | Cómo |
|---|---|
| Map inmutable | `Map.of("a", 1, "b", 2)` |
| Agrupar una lista por campo | `Collectors.groupingBy(Cliente::ciudad)` → `Map<String, List<Cliente>>` |
| Agrupar y contar | `Collectors.groupingBy(Cliente::ciudad, Collectors.counting())` → `Map<String, Long>` |
| Agrupar y transformar cada grupo | `Collectors.groupingBy(Cliente::ciudad, Collectors.mapping(Cliente::nombre, Collectors.toList()))` |
| Lista → Map por clave | `lista.stream().collect(Collectors.toMap(Cliente::id, c -> c))` |
| Recorrer entradas | `map.forEach((k, v) -> ...)` o `map.entrySet().stream()...` |
| Valor con default si no existe | `map.getOrDefault(key, valorPorDefecto)` |
| Insertar solo si no existe | `map.putIfAbsent(key, value)` |
| Insertar o **calcular** si no existe | `map.computeIfAbsent(key, k -> new ArrayList<>())` |
| Actualizar un valor existente | `map.computeIfPresent(key, (k, v) -> v + 1)` |
| Combinar valor nuevo con existente | `map.merge(key, 1, Integer::sum)` |
| Filtrar por valor | `map.entrySet().stream().filter(e -> e.getValue() > 10)` |

```java
// contar pedidos por ciudad
Map<String, Long> pedidosPorCiudad = pedidos.stream()
    .collect(Collectors.groupingBy(Pedido::ciudad, Collectors.counting()));

// acumulador manual con merge — patrón muy usado para conteos/sumas
Map<String, Integer> visitasPorPagina = new HashMap<>();
for (Evento e : eventos) {
    visitasPorPagina.merge(e.pagina(), 1, Integer::sum);
}

// agrupar y sumar un monto (equivalente a un GROUP BY + SUM en SQL)
Map<String, BigDecimal> totalPorCliente = facturas.stream()
    .collect(Collectors.groupingBy(Factura::clienteId,
        Collectors.reducing(BigDecimal.ZERO, Factura::monto, BigDecimal::add)));
```

> 🧠 **Píldora — `merge` vs `computeIfAbsent`:** `merge` es para acumular/combinar un valor (contadores, sumas). `computeIfAbsent` es para inicializar lazy una estructura (ej: `Map<String, List<X>>` donde cada clave necesita su lista antes de poder agregarle algo).

## 3. Streams — puntos que suelen generar dudas

- Un `Stream` se consume **una sola vez** — reusarlo tira `IllegalStateException`.
- Las operaciones intermedias (`map`, `filter`, `sorted`) son **lazy**: no se ejecutan hasta que hay una operación terminal (`toList`, `collect`, `forEach`, `reduce`).
- `reduce` acumula un único resultado sin necesidad de un `Collector`: `lista.stream().reduce(0, Integer::sum)`.
- `flatMap` en Streams (no confundir con el de Reactor, ver [`reactive-programming/`](../reactive-programming)) aplana listas anidadas: `List<List<X>>` → `Stream<X>` con `.flatMap(List::stream)`.
- `.parallelStream()` solo conviene con colecciones grandes y operaciones sin estado compartido — no es el default razonable.

```java
// flatMap en streams: aplanar los ítems de varios pedidos en una sola lista
List<Item> todosLosItems = pedidos.stream()
    .flatMap(p -> p.items().stream())
    .toList();
```

## Drills de repaso (cronometrados)

| Tiempo | Ejercicio |
|---|---|
| 6 min | Dada `List<Turno>`, agrupá por `empleadoId` y contá cuántos turnos tiene cada uno (`Map<String, Long>`). |
| 6 min | Dada `List<Deuda>` con `clienteId` y `monto`, calculá el total de deuda por cliente usando `merge` (sin `Collectors.groupingBy`). |
| 5 min | Dada `List<Cliente>`, separá en dos listas (mayores/menores de 18) con `Collectors.partitioningBy`. |
| 7 min | Dada `List<List<Tag>>` (una lista de tags por producto), devolvé el `Set<Tag>` único de todos los productos usando `flatMap` + `distinct`. |
| 6 min | Implementá un contador de intentos de login fallidos por usuario (`Map<String, Integer>`) usando `computeIfAbsent`/`merge`, e incrementalo en un loop simulando eventos. |
| 8 min | Convertí `List<Cliente>` a `Map<String, ClienteDto>` (clave = id), y explicá qué pasa si hay ids duplicados con `Collectors.toMap` (spoiler: `IllegalStateException`, hay que pasar una función de merge). |

## Referencias

- Documentación oficial de Oracle — [Java Collections Framework](https://docs.oracle.com/javase/8/docs/technotes/guides/collections/overview.html) y [`java.util.stream`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html).
- Baeldung — [Guía de `Stream` y `Collectors` en Java](https://www.baeldung.com/java-8-streams) — fuente de los ejemplos de `groupingBy`/`merge`/`flatMap` usados en este cheatsheet.

Relacionado: [`data-structures-decision-guide.md`](data-structures-decision-guide.md) para cuál estructura elegir antes de operarla, [`functional-interfaces.md`](functional-interfaces.md) para el catálogo completo de `Predicate`/`Function`/etc. que alimenta estos streams, y [`reactive-programming/`](../reactive-programming) para el `flatMap` equivalente en Project Reactor (no confundir ambos).
