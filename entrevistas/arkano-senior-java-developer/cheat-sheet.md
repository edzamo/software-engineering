# Cheat sheet — Arkano, entrevista técnica

Dos usos: **(1) durante la entrevista**, busca con `Ctrl+F` (o `Cmd+F`) una palabra clave y lee la respuesta de una o dos líneas; **(2) antes**, haz el entrenamiento rápido de la sección 11.

> Guía completa: [`README.md`](README.md) · Todas las respuestas están **de junior a senior**: empieza con la frase 🟢, baja a 🟡 y 🔴 solo si te piden más. Analogía de todo: la **frutería de manzanas** ([`glosario.md`](../../messaging-streaming/glosario.md)).

## Índice rápido

[0 · Tu presentación](#c0) · [1 · Respuestas relámpago A-Z](#c1) · [2 · Kafka: repaso para quien lo usó hace tiempo](#c2) · [3 · Quarkus y SmallRye](#c3) · [4 · Java 17/21](#c4) · [5 · Resiliencia](#c5) · [6 · JPA, Hibernate y PostgreSQL](#c6) · [7 · Hexagonal](#c7) · [8 · Testing](#c8) · [9 · Extras del job description](#c9) · [10 · Tu proyecto de pagos](#c10) · [11 · Entrenamiento rápido](#c11) · [12 · Cuando no sabes](#c12) · [13 · Preguntas para hacerles](#c13)

**Contexto del proceso:** más de 100 personas aplicaron a la vacante, así que lo que te diferencia no es recitar definiciones, sino **explicar con claridad y respaldar con un proyecto real** (tu pasarela de pagos en un banco). Glassdoor no trae preguntas técnicas de Arkano (solo 3 reseñas, sin detalle), así que esta preparación sigue la lista de Lourdes y el job description.

---

<a id="c0"></a>

## 0 · Tu presentación (60 segundos)

Plantilla: **quién eres → qué has hecho → qué te trae aquí**. Completa los 🔎 con tus datos reales.

> "Soy desarrollador backend con 🔎 N años de experiencia, sobre todo en Java y Node.js. Trabajé en banca, en una **pasarela de pagos** para 🔎 Banco Pichincha, con una solución de 🔎 NTT Data: servicios con Spring Boot, **mensajería con Kafka**, procesos programados con **Quartz** y PostgreSQL, con foco en que **un pago nunca se duplique ni se pierda**: idempotencia, reintentos y conciliación. Me interesa Arkano por 🔎 el trabajo con Azure y proyectos regionales."

**Lo que te diferencia:** experiencia en pagos bancarios, mensajería, sistemas que no pueden fallar y criterio de diseño. Úsalo.

---

<a id="c1"></a>

## 1 · Respuestas relámpago (A-Z)

Formato: **término → qué es en una frase** (🟢) · *con manzanas*.

| Término | Respuesta |
|---|---|
| **ACID** | Atomicidad, Consistencia, Aislamiento, Durabilidad: garantías de una transacción. *La venta se anota completa o no se anota* |
| **Adapter (patrón)** | Traduce una interfaz a otra; clave para integrar legacy. *Un enchufe adaptador* |
| **At-least-once** | Nunca se pierde, puede duplicarse; exige idempotencia. *Gritas el pedido hasta que confirmen* |
| **Bulkhead** | Aislar recursos por dependencia para que una lenta no hunda a las demás. *Una caja por tipo de cliente* |
| **CAP** | Ante una partición de red eliges consistencia o disponibilidad |
| **Circuit Breaker** | Corta las llamadas a un servicio que falla; estados cerrado, abierto, semiabierto. *El fusible de la casa* |
| **Clean Code** | Nombres claros, funciones pequeñas, sin duplicación, con tests |
| **CQRS** | Un modelo para escribir y otro para consultar. *Libro de ventas y pizarra de resumen* |
| **Dead Letter Queue** | Destino de lo que siempre falla, para no bloquear. *La bandeja "revisar a mano"* |
| **DIP (SOLID)** | Depender de abstracciones, no de clases concretas. *Enchufas a un puerto, no a un banco* |
| **Dirty checking** | Hibernate detecta cambios en entidades gestionadas y hace el `UPDATE` al flush, sin `save` |
| **DRY / KISS / YAGNI** | No repetir / mantenerlo simple / no construir lo que aún no necesitas |
| **Eventual consistency** | Los datos se alinean con un pequeño retraso |
| **Event Sourcing** | El estado es la suma de eventos. *El stock se calcula del libro* |
| **Hexagonal** | Negocio al centro; puertos (interfaces) y adaptadores (REST, BD, Kafka). *La tienda con enchufes y cables* |
| **Idempotencia** | Repetir da el mismo resultado que hacerlo una vez. *El sello "YA COBRADO"* |
| **Idempotency-Key** | Id único por intento de pago; el mismo id dos veces es la misma operación |
| **ISP (SOLID)** | Interfaces pequeñas y específicas |
| **Jitter** | Azar en el tiempo de reintento para que no reintenten todos a la vez |
| **JPA vs Hibernate** | JPA es la especificación; Hibernate, la implementación |
| **KRaft** | Quórum de controladores de Kafka basado en Raft; reemplazó a ZooKeeper (eliminado en Kafka 4.0) |
| **Lazy loading** | La relación se carga al usarla; `LazyInitializationException` si es fuera de la transacción |
| **LSP (SOLID)** | Una subclase debe poder sustituir a su padre sin romper nada |
| **Mock vs Stub vs Spy vs Fake** | Verifica llamadas / devuelve respuestas / real vigilado / implementación simple |
| **MVCC (PostgreSQL)** | Cada transacción ve su versión de los datos: leer no bloquea escribir |
| **N+1** | 1 consulta de lista + N por cada relación. Arreglo: `JOIN FETCH`, `@EntityGraph`, DTO |
| **OCP (SOLID)** | Abierto a extensión, cerrado a modificación (Strategy en vez de `switch`) |
| **Outbox** | Guardar dato y evento en la misma transacción; otro proceso publica. *Misma hoja: "vendí" y "avisar"* |
| **Panache** | Capa de Quarkus sobre Hibernate (equivale a Spring Data) |
| **Polling Publisher** | Un proceso revisa una tabla cada cierto tiempo y publica lo pendiente |
| **Proxy (patrón)** | Controla el acceso; así funcionan `@Transactional` y el lazy loading |
| **Record (Java 17)** | Clase inmutable de datos, con `equals`/`hashCode` generados |
| **Retry + backoff** | Reintentar espaciando (1 s, 2 s, 4 s) |
| **Saga** | Operación larga en pasos, con compensación si falla uno. *Vender → cobrar → despachar; si falla, devolver* |
| **Sealed class** | Jerarquía cerrada; el `switch` exige cubrir todos los casos |
| **SRP (SOLID)** | Una clase, una razón para cambiar |
| **Strategy (patrón)** | Algoritmos intercambiables en vez de `if/else`: método de pago, descuento |
| **Timeout** | No esperar para siempre |
| **Virtual threads (Java 21)** | Hilos baratos: código bloqueante simple con alta concurrencia |
| **`@Blocking` (Quarkus)** | Ejecutar en un worker thread porque el código bloquea (JDBC) |
| **`@Version`** | Bloqueo optimista: si la versión cambió al guardar, falla |

**Patrones GoF:** son **23** → 5 creacionales (Factory Method, Abstract Factory, Builder, Prototype, Singleton), 7 estructurales (Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy), 11 de comportamiento (Chain of Responsibility, Command, Interpreter, Iterator, Mediator, Memento, Observer, State, Strategy, Template Method, Visitor). Detalle: [sección 04](README.md#s04).

---

<a id="c2"></a>

## 2 · Kafka: repaso de 15 minutos (lo usaste en el banco, hace tiempo)

### Cómo presentarlo con honestidad

> "En el banco usé Kafka en una pasarela de pagos: 🔎 tópicos de entrada y salida, 🔎 consumidores y productores con Spring. Hace un tiempo que no lo uso en el día a día, así que repasé los conceptos: particiones, grupos, commit de offsets, idempotencia y manejo de errores. Con SmallRye Reactive Messaging no he trabajado, pero entiendo que es el equivalente de `@KafkaListener` y `KafkaTemplate` en Quarkus."

Eso es **verdad y suficiente**. No finjas experiencia reciente: con dos preguntas se nota.

### Los 12 conceptos (léelos hasta poder decirlos sin mirar)

| # | Concepto | 🟢 Frase | 🍎 Manzanas |
|---|---|---|---|
| 1 | **Kafka** | Log distribuido de eventos: se escribe al final y se **lee sin borrar** | El libro de ventas con líneas numeradas |
| 2 | **Topic** | Canal con nombre para un tipo de evento | Un cuaderno |
| 3 | **Partición** | División del topic; **el orden solo vale dentro de una** | Secciones del cuaderno |
| 4 | **Key** | Decide la partición; misma key, mismo orden | El nombre del cliente decide la sección |
| 5 | **Offset** | Posición del mensaje en su partición | Número de línea |
| 6 | **Consumer group** | Consumidores que se reparten las particiones; cada partición la lee **uno** del grupo | Equipo de contadores |
| 7 | **Replicación + `acks=all` + `min.insync.replicas=2`** | No pierdes datos si cae un servidor | Fotocopias en 3 oficinas |
| 8 | **Commit de offsets** | Marcar hasta dónde leíste; confirmar **después de procesar** | El marcador del libro |
| 9 | **At-least-once + idempotencia** | Puede llegar dos veces: el consumidor ignora duplicados con el `eventId` | El sello "YA COBRADO" |
| 10 | **Rebalanceo y lag** | Reasignar particiones al entrar o salir un consumidor; lag = lo que falta por leer | Un contador se enferma; líneas sin leer |
| 11 | **DLT y reintentos** | El mensaje que siempre falla va a otro topic, tras reintentos con backoff | Bandeja "revisar a mano" |
| 12 | **Outbox** | Guardar dato y evento en la misma transacción; otro proceso publica | Misma hoja: "vendí" y "avisar" |

### Las preguntas que más caen (respuesta de una línea)

| Pregunta | Respuesta |
|---|---|
| ¿Cómo garantizas el orden? | Con una key (cuenta, pedido): sus eventos van a la misma partición |
| ¿Qué pasa si hay más consumidores que particiones? | Los sobrantes quedan ociosos |
| ¿Cómo no pierdes mensajes? | Productor `acks=all` e idempotente; consumidor con commit manual tras procesar; RF=3 |
| ¿Cómo evitas duplicados? | At-least-once más consumidor idempotente (`eventId` con restricción única) |
| ¿Exactly-once? | Existe dentro de Kafka (idempotencia y transacciones); con sistemas externos necesitas idempotencia propia |
| ¿Qué haces con un mensaje que falla? | Reintento con backoff y luego Dead Letter Topic con alerta |
| ¿Kafka vs RabbitMQ/SQS? | Kafka es un log que se relee, con alto volumen y orden por clave; una cola se consume y desaparece, y es más simple |
| ¿Qué es ZooKeeper/KRaft? | Coordinaba el cluster; hoy lo hace KRaft integrado en Kafka |
| ¿Qué es Schema Registry? | Guarda los esquemas y valida que los cambios sean compatibles |
| ¿Cómo publicas y guardas en BD sin inconsistencias? | Outbox |
| ¿Qué es el consumer lag? | Mensajes pendientes por consumir; la métrica clave |
| ¿Qué es log compaction? | Conserva solo el último valor por key |

### Con SmallRye (Quarkus), en 10 líneas

```java
@Incoming("pedidos")  @Blocking @Transactional
public void procesar(PedidoCreado e) { ... }              // consume; ack al terminar el método

@Inject @Channel("despachos") Emitter<DespachoCreado> emitter;
emitter.send(evento);                                      // produce
```
```properties
mp.messaging.incoming.pedidos.connector=smallrye-kafka
mp.messaging.incoming.pedidos.failure-strategy=dead-letter-queue    # fail (por defecto) | ignore | dead-letter-queue | delayed-retry-topic
mp.messaging.outgoing.despachos.acks=all
```
Ack por defecto al terminar el método; commit `throttled`; pruebas con `InMemoryConnector`; equivalente Spring: `@KafkaListener` + `KafkaTemplate`. Detalle: [`smallrye-reactive-messaging.md`](../../frameworks/quarkus/smallrye-reactive-messaging.md).

Más profundidad si te la piden: [`kafka.md`](../../messaging-streaming/kafka.md) (arquitectura, headers, banca).

---

<a id="c3"></a>

## 3 · Quarkus y SmallRye

| Pregunta | Respuesta |
|---|---|
| ¿Qué es Quarkus? | Framework Java para microservicios y cloud: arranca rápido y gasta poca memoria porque hace la configuración **en el build** |
| ¿Spring vs Quarkus? | Mismos patrones; Spring configura en runtime, Quarkus en build; DI con CDI; tiene dev mode, Dev Services e imagen nativa |
| ¿DI? | CDI: `@Inject`, `@ApplicationScoped` |
| ¿Endpoint? | `@Path` + `@GET` (Jakarta REST) |
| ¿Persistencia? | Hibernate ORM con **Panache** |
| ¿Config? | `application.properties`, `@ConfigProperty`, perfiles `%dev` `%test` `%prod` |
| ¿Reactivo? | `Uni` (0 o 1) y `Multi` (0 a N) de Mutiny; equivalen a `Mono` y `Flux`; el event loop no se bloquea |
| ¿Código bloqueante? | `@Blocking`, o un endpoint imperativo, o virtual threads |
| ¿Qué es SmallRye Reactive Messaging? | La librería de Quarkus para mensajería (`@Incoming`, `@Outgoing`, `Emitter`) con conectores (Kafka, AMQP...) |
| ¿Pruebas? | `@QuarkusTest`, `@InjectMock`, RestAssured, Dev Services |

Tabla de equivalencias completa: [sección 20](README.md#s20) · [`quarkus/README.md`](../../frameworks/quarkus/README.md).

---

<a id="c4"></a>

## 4 · Java 17 / 21

| Tema | Respuesta |
|---|---|
| Java 17 | Records, sealed classes, text blocks, `instanceof` con patrón, `switch` con flechas |
| Java 21 | Virtual threads, `switch` con patrones, record patterns, `SequencedCollection` |
| `equals` / `hashCode` | Si dos objetos son `equals`, mismo `hashCode`; si no, falla `HashMap` |
| `HashMap` | Hash de la clave → bucket; colisiones con lista o árbol; promedio O(1); no es thread-safe (`ConcurrentHashMap`) |
| Streams | Pipeline perezoso; nada corre hasta la operación terminal |
| `Optional` | Para retornos que pueden no tener valor; no para campos ni parámetros |
| Checked vs unchecked | Checked: el compilador obliga a manejar; unchecked (`RuntimeException`): errores de programación |
| `synchronized` / `volatile` | Exclusión mutua / visibilidad (no atomicidad) |
| GC | Libera lo inalcanzable; por generaciones; G1 por defecto; ZGC para pausas bajas |
| Inmutabilidad | Records y colecciones inmutables: menos errores y threads más seguros |

Detalle: [sección 03](README.md#s03) y [20](README.md#s20).

---

<a id="c5"></a>

## 5 · Resiliencia

| Patrón | Frase |
|---|---|
| **Timeout** | Lo primero: no esperar para siempre |
| **Retry** | Solo operaciones idempotentes, con backoff y jitter |
| **Circuit Breaker** | **Cerrado** (mide fallos) → **abierto** (corta y usa fallback) → **semiabierto** (prueba unas llamadas) → cerrado o abierto |
| **Bulkhead** | Aislar recursos por dependencia |
| **Fallback** | Plan B honesto (caché, "pendiente de confirmar") |
| **Rate limiter / load shedding** | Proteger al servicio |

**Quarkus:** `@Timeout`, `@Retry`, `@CircuitBreaker`, `@Bulkhead`, `@Fallback` (SmallRye Fault Tolerance). **Spring:** Resilience4j. **Trampa:** un timeout dispara el reintento y cuenta como fallo del circuit breaker.

**Respuesta modelo (servicio lento satura mis hilos):** *timeout corto → bulkhead → circuit breaker con fallback → reintentos idempotentes con backoff y jitter → monitoreo de p99, tasa de fallos y estado del breaker.* Detalle: [sección 16](README.md#s16) y [24](README.md#s24).

---

<a id="c6"></a>

## 6 · JPA, Hibernate y PostgreSQL

| Pregunta | Respuesta |
|---|---|
| N+1 | Lista + una consulta por elemento. `JOIN FETCH`, `@EntityGraph`, `@BatchSize` o DTO |
| Lazy vs eager | Lazy al usar, eager siempre; poner `LAZY` y traer lo necesario |
| `LazyInitializationException` | Acceso lazy fuera de la transacción; usar `JOIN FETCH` o devolver DTO |
| Estados de entidad | Transient → Managed → Detached → Removed |
| `@Transactional` | Rollback en runtime exceptions; `REQUIRED` por defecto; no funciona en autoinvocación; en la capa de aplicación |
| Concurrencia | `@Version` (optimista, lo habitual) o `FOR UPDATE` (pesimista); `SKIP LOCKED` para colas |
| Aislamiento PostgreSQL | `READ COMMITTED` por defecto; MVCC |
| Índices | B-tree; compuesto respeta el orden; `EXPLAIN (ANALYZE)` |
| Dinero | `BigDecimal`, nunca `double` |
| Enums | `@Enumerated(STRING)` |
| Esquema | Flyway o Liquibase; `generation=none` en producción |
| Insert en lote | `SEQUENCE`, no `IDENTITY` |
| Paginación grande | Por cursor/keyset, no `OFFSET` |
| Upsert | `INSERT ... ON CONFLICT` (clave para idempotencia) |

Detalle: [sección 22](README.md#s22) · [`persistencia-hibernate-postgresql.md`](../../frameworks/quarkus/persistencia-hibernate-postgresql.md).

---

<a id="c7"></a>

## 7 · Hexagonal

| Pregunta | Respuesta |
|---|---|
| ¿Qué es? | Negocio al centro; **puertos** (interfaces que define el núcleo) y **adaptadores** (REST, JPA, Kafka, cliente del banco) que los implementan |
| Regla de oro | La dependencia apunta **hacia adentro**; el dominio no conoce frameworks |
| Puerto IN / OUT | IN: lo que el sistema ofrece (`CrearPagoUseCase`); OUT: lo que necesita (`PagoRepository`, `BancoPort`) |
| Entidad de dominio vs entidad JPA | Distintas, con un mapper; el modelo de negocio no queda atado a la tabla |
| ¿Dónde va `@Transactional`? | En el caso de uso |
| ¿Cómo se prueba? | Dominio sin mocks; casos de uso con mocks de los puertos; adaptadores con integración |
| ¿Cuándo no? | CRUD sin lógica de negocio (sobreingeniería) |

Detalle: [sección 23](README.md#s23) · [`hexagonal-architecture.md`](../../software-architectures/hexagonal-architecture.md).

---

<a id="c8"></a>

## 8 · Testing

| Pregunta | Respuesta |
|---|---|
| AAA | Arrange (preparar), Act (actuar), Assert (comprobar) |
| JUnit 5 | `@Test`, `@BeforeEach`, `@ParameterizedTest`, `@Nested`, `assertThrows`, `assertAll` |
| Mockito | `@Mock`, `@InjectMocks`, `when/thenReturn`, `verify`, `ArgumentCaptor` |
| Mock / Stub / Spy / Fake | Verifica / devuelve / real vigilado / implementación simple |
| ¿Qué mockeas? | Tus **puertos**; no tipos de terceros ni el dominio |
| Pirámide | Muchos unitarios, algunos de integración, pocos end-to-end |
| Quarkus | `@QuarkusTest`, `@InjectMock`, Dev Services, `InMemoryConnector` |
| Spring | `@SpringBootTest`, `@MockBean`, Testcontainers |
| BD en pruebas | PostgreSQL real en contenedor; evitar H2 como sustituto |
| TDD | Rojo, verde, refactor |
| Cobertura | Indicador, no objetivo; importan los caminos de error |

Detalle: [sección 25](README.md#s25) · [`junit5-mockito.md`](../../tdd/junit5-mockito.md).

---

<a id="c9"></a>

## 9 · Extras del job description

El job description menciona más cosas que la lista de Lourdes. Respuestas mínimas por si salen:

| Tema | Respuesta mínima |
|---|---|
| **OWASP Top 10 (2021)** | A01 Broken Access Control · A02 Cryptographic Failures · A03 Injection · A04 Insecure Design · A05 Security Misconfiguration · A06 Vulnerable Components · A07 Auth Failures · A08 Integrity Failures · A09 Logging/Monitoring Failures · A10 SSRF (existe una edición 2025: confírmala en owasp.org) |
| SQL injection | Consultas parametrizadas, nunca concatenar |
| JWT | Validar firma, `exp`, `iss`, `aud`; vida corta; no datos sensibles |
| REST | Verbos, códigos (201 con `Location`, 400, 401, 403, 404, 409, 429), idempotencia con `Idempotency-Key`, OpenAPI |
| **Node.js** | Un hilo con event loop no bloqueante: ideal para I/O, no para CPU; `async/await`; NestJS parecido a Spring |
| **GraphQL** | El cliente pide los campos que necesita; resuelve over/under-fetching; riesgos: N+1 (DataLoader) y consultas abusivas |
| **Azure** | Functions (serverless), Service Bus (colas/topics), Event Hubs (compatible con Kafka), API Management (gateway), AKS (Kubernetes), DevOps (CI/CD), Key Vault, Entra ID |
| **Ágil** | Scrum (sprints, planning, daily, review, retro); ten un ejemplo de retro o de cambio de alcance |
| **Legacy** | Adapter, Anti-Corruption Layer, Strangler Fig |
| **Documentar APIs** | OpenAPI/Swagger |

Detalle: secciones [07](README.md#s07), [08](README.md#s08), [09](README.md#s09), [11](README.md#s11), [12](README.md#s12).

---

<a id="c10"></a>

## 10 · Tu proyecto de pagos en 5 frases (úsalo como ejemplo de casi todo)

1. "Era una **pasarela de pagos**: recibía órdenes, las procesaba contra el banco y devolvía el resultado."
2. "Usábamos **mensajería con tópicos de entrada y salida** para desacoplar y absorber picos."
3. "**Quartz** disparaba procesos a una hora configurada: leía una tabla de pendientes y publicaba al tópico; era un *Polling Publisher*."
4. "Como todo es *at-least-once*, el diseño era **idempotente**: id único por pago, estado condicional y consumidores que ignoran duplicados."
5. "Si el banco no respondía, el pago quedaba **pendiente de confirmación** y se resolvía por consulta o conciliación; nunca se reintentaba a ciegas."

Sirve para: Kafka, idempotencia, resiliencia, transacciones, arquitectura, concurrencia y testing. Detalle y dibujo: [`caso-pasarela-pagos.md`](../../system-design/caso-pasarela-pagos.md) (🔎 contrasta con tu memoria lo que no recuerdes con certeza).

---

<a id="c11"></a>

## 11 · Entrenamiento rápido (30 minutos)

**Cómo usarlo:** lee la pregunta, **contesta en voz alta**, y luego despliega la respuesta. Cuenta cuántas dijiste con la frase 🟢 clara.

<details><summary><b>1.</b> ¿Qué es Kafka y en qué se diferencia de una cola?</summary>

🟢 Un log distribuido de eventos: se escribe al final y se lee sin borrar; varios consumidores leen con su propio offset. 🟡 En una cola el mensaje se consume y desaparece. 🔴 Elijo Kafka por volumen, orden por clave y poder releer; una cola si solo reparto tareas.
</details>

<details><summary><b>2.</b> ¿Cómo garantizas el orden en Kafka?</summary>

🟢 Solo dentro de una partición. 🟡 Uso una key (la cuenta) para que sus eventos vayan a la misma partición. 🔴 No hay orden global; una key muy popular crea una partición caliente; cambiar el número de particiones rompe el reparto.
</details>

<details><summary><b>3.</b> ¿Cómo evitas procesar un mensaje dos veces?</summary>

🟢 Guardo el id del mensaje procesado. 🟡 Tabla de `eventId` con restricción única, en la misma transacción que el efecto. 🔴 Es at-least-once: lo hago idempotente de punta a punta y confirmo el offset solo después de persistir.
</details>

<details><summary><b>4.</b> ¿Qué es SmallRye Reactive Messaging?</summary>

🟢 La librería de Quarkus para mensajería: `@Incoming`, `@Outgoing` y `Emitter`. 🟡 Canales y conectores (`smallrye-kafka`), con estrategias de ack y de fallo. 🔴 Abstrae el broker sobre flujos Mutiny; el equivalente en Spring es `@KafkaListener` más `KafkaTemplate` o Spring Cloud Stream.
</details>

<details><summary><b>5.</b> Un mensaje falla siempre, ¿qué haces?</summary>

🟢 Lo mando a otro topic. 🟡 `failure-strategy=dead-letter-queue`, o `delayed-retry-topic` si es transitorio, con alerta. 🔴 Distingo transitorio de permanente, y mi consumidor es idempotente porque el reproceso es inevitable.
</details>

<details><summary><b>6.</b> ¿Qué es el patrón Outbox?</summary>

🟢 Guardar el dato y el evento en la misma transacción. 🟡 Un relay o CDC publica después a Kafka. 🔴 Evita la inconsistencia entre base de datos y broker sin transacción distribuida; la entrega queda at-least-once.
</details>

<details><summary><b>7.</b> Explícame los estados del Circuit Breaker.</summary>

🟢 Cerrado: pasa todo y mide fallos. Abierto: corta y usa fallback. Semiabierto: prueba unas llamadas. 🟡 Abre al superar el umbral de fallos y vuelve a probar tras un tiempo. 🔴 Lo combino con timeout, bulkhead y reintentos idempotentes; el fallback debe ser honesto.
</details>

<details><summary><b>8.</b> ¿Qué es el problema N+1 y cómo lo resuelves?</summary>

🟢 Una consulta de lista que dispara una extra por cada elemento. 🟡 `JOIN FETCH`, `@EntityGraph` o `@BatchSize`. 🔴 Para lecturas uso proyecciones/DTO y lo detecto contando consultas en tests.
</details>

<details><summary><b>9.</b> ¿Lazy o eager? ¿Qué es `LazyInitializationException`?</summary>

🟢 Lazy carga al usar; eager siempre; la excepción es acceder a una relación lazy fuera de la transacción. 🟡 Pongo `LAZY` y traigo lo necesario por consulta. 🔴 Devuelvo DTOs en lugar de entidades y no uso "open session in view".
</details>

<details><summary><b>10.</b> ¿Cómo evitas que dos usuarios pisen el mismo registro?</summary>

🟢 Con un campo de versión. 🟡 `@Version` (optimista); si falla, reintento. 🔴 Optimista por defecto; pesimista (`FOR UPDATE`) con alta contención, como saldos.
</details>

<details><summary><b>11.</b> ¿Qué es la arquitectura hexagonal?</summary>

🟢 Separar el negocio de los detalles técnicos con puertos y adaptadores. 🟡 El núcleo define interfaces; REST, JPA y Kafka son adaptadores. 🔴 Invierte la dependencia hacia el dominio: facilita probar y cambiar tecnología; el costo son mapeos extra y solo vale con lógica de negocio real.
</details>

<details><summary><b>12.</b> ¿Quarkus o Spring Boot? ¿Por qué?</summary>

🟢 Quarkus arranca más rápido y gasta menos memoria. 🟡 Configura en el build, usa CDI, tiene dev mode e imagen nativa; Spring tiene el ecosistema más grande. 🔴 Elijo según el contexto: arranque y memoria críticos, Quarkus; equipo y ecosistema ya en Spring, Spring. Los patrones son los mismos.
</details>

<details><summary><b>13.</b> ¿Reactivo o imperativo?</summary>

🟢 Reactivo usa `Uni`/`Multi` y no bloquea. 🟡 El event loop no puede bloquearse; con JDBC uso `@Blocking`. 🔴 Reactivo solo si todo el camino es no bloqueante y hay alta concurrencia; si no, imperativo o virtual threads (Java 21).
</details>

<details><summary><b>14.</b> ¿Qué es un mock y qué mockeas?</summary>

🟢 Un objeto falso que reemplaza una dependencia. 🟡 Mockito programa con `when` y verifica con `verify`; distingo mock, stub, spy y fake. 🔴 Mockeo mis puertos, no tipos de terceros; demasiados mocks acoplan la prueba a la implementación.
</details>

<details><summary><b>15.</b> ¿Cómo pruebas el acceso a datos?</summary>

🟢 Con una base de pruebas. 🟡 PostgreSQL real en contenedor (Dev Services o Testcontainers). 🔴 Evito H2 como sustituto de PostgreSQL porque difiere en SQL y tipos.
</details>

<details><summary><b>16.</b> Explícame SOLID con un ejemplo.</summary>

🟢 Cinco principios para que el código sea fácil de cambiar y probar. 🟡 SRP: una razón para cambiar; OCP: extender sin modificar (Strategy en lugar de `switch`); LSP: la subclase sustituye al padre; ISP: interfaces pequeñas; DIP: depender de abstracciones. 🔴 No son leyes: aplicarlos de más es sobreingeniería (YAGNI). Da **un ejemplo tuyo**.
</details>

<details><summary><b>17.</b> ¿Qué patrón de diseño has usado y dónde?</summary>

Elige **tres con un caso real**: Strategy (métodos de pago), Adapter (integrar un sistema externo), Proxy (`@Transactional`), Observer/Command/Template Method. Cierra con cuándo **no** lo usarías (sobreingeniería).
</details>

<details><summary><b>18.</b> ¿Qué haces si el banco no responde a un cobro?</summary>

🟢 No reintento a ciegas: puedo cobrar dos veces. 🟡 Estado `PENDIENTE_CONFIRMACION` y consulta de estado. 🔴 Conciliación, llave de idempotencia hacia el banco, circuit breaker y alerta por antigüedad de pendientes.
</details>

<details><summary><b>19.</b> ¿Por qué Quartz y no `@Scheduled`?</summary>

🟢 Con varias instancias `@Scheduled` corre en todas. 🟡 Quartz persiste en BD, tiene cluster, misfire y programación dinámica. 🔴 Los locks de BD limitan la escala; hoy usaría un scheduler gestionado y un disparo por pago.
</details>

<details><summary><b>20.</b> Cuéntame un proyecto del que estés orgulloso.</summary>

Usa la sección 10 de este documento: problema → arquitectura → decisión clave y por qué → un problema real (duplicados, timeout del banco, picos) → resultado.
</details>

**Resultado:** 16 o más con la frase 🟢 clara = estás listo. Si fallas en un bloque, vuelve a su sección arriba y repítelo.

---

<a id="c12"></a>

## 12 · Cuando no sabes

1. **Di lo que sí sabes cerca:** *"No he usado X en producción, pero conozco Y, que resuelve lo mismo, y funciona así..."*
2. **Razona en voz alta:** el entrevistador evalúa cómo piensas más que el dato exacto.
3. **Nunca inventes un detalle** (un nombre de propiedad, una versión): se nota y te cuesta la credibilidad. Di *"no recuerdo el nombre exacto, la idea es..."*.
4. **Pide un ejemplo concreto** si la pregunta es ambigua.
5. **Ofrece cómo lo averiguarías** (documentación, un spike, una prueba).

**Frases útiles:** "Déjame pensarlo un segundo." · "Con Spring lo hice así; en Quarkus entiendo que es equivalente." · "Depende de X; en mi caso elegiría esto por esta razón." · "Esa decisión tiene un costo: ..."

**Trampas a evitar:** decir "Kafka garantiza exactly-once" sin matices · mockear todo · recomendar microservicios o reactivo "por moda" · confundir CQRS con SQS · afirmar experiencia reciente en algo que no usas hace tiempo.

---

<a id="c13"></a>

## 13 · Preguntas para hacerles (elige 3)

- ¿El proyecto usa **Quarkus o Spring Boot**, y qué parte del stack es nueva o heredada?
- ¿Cómo está organizado el equipo y cómo se hacen las **revisiones de código** y los despliegues?
- ¿Qué **desafío técnico** tiene hoy el producto (rendimiento, mensajería, integración con legacy)?
- ¿Cómo se ve el éxito en los **primeros 90 días**?
- ¿Cómo es la **modalidad** de contratación y de trabajo (remoto o híbrido, país de contrato)?
- ¿Cuáles son los siguientes pasos del proceso y los plazos?
