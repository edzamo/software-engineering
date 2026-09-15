# Spring WebFlux — guía de estudio (Parte 3: filtros, HTTP/2, streaming, performance, testing de integración)

Continuación de [`webflux.md`](webflux.md) (Parte 1: el framework) y [`webflux-operators.md`](webflux-operators.md) (Parte 2: catálogo de operadores). Estos 5 temas son los que separan "sé usar `Mono`/`Flux`" de "sé llevar WebFlux a producción" — cada uno en formato cheatsheet, mismo criterio que el resto de la guía.

## 1 · `WebFilter` — interceptar toda request/response sin tocar cada controller

Un `WebFilter` corre para **todas** las requests, antes de llegar al controller — el lugar correcto para logging, correlation-id, o CORS custom que no cubre la configuración estándar. No confundir con `SecurityWebFilterChain` (ver [`webflux.md`](webflux.md) sección 10): la seguridad usa su propia cadena, separada de los `WebFilter` de aplicación.

```java
@Component
public class CorrelationIdWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String correlationId = exchange.getRequest().getHeaders()
            .getFirst("X-Correlation-Id");
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }
        exchange.getResponse().getHeaders().add("X-Correlation-Id", correlationId);

        return chain.filter(exchange)
            .contextWrite(ctx -> ctx.put("correlationId", correlationId)); // disponible más abajo vía deferContextual
    }
}
```

| | `WebFilter` | `@ExceptionHandler` / `@RestControllerAdvice` | `SecurityWebFilterChain` |
|---|---|---|---|
| Cuándo corre | Toda request, antes del controller. | Solo cuando algo lanza una excepción. | Toda request, específicamente para auth/authz. |
| Uso típico | Logging, correlation-id, headers custom, CORS a medida. | Traducir excepciones a `ProblemDetail` (ver [`webflux.md`](webflux.md) sección 8). | JWT, roles, rutas públicas vs protegidas. |
| Orden | Se controla con `@Order` — varios filtros pueden coexistir en cadena. | N/A — se dispara por tipo de excepción. | Corre antes que los `WebFilter` de aplicación. |

**Frase para entrevista:** "un `WebFilter` es el equivalente reactivo de un `Filter` de Servlet — corre en la cadena antes de que la request llegue al controller, y como devuelve `Mono<Void>`, tiene que encadenarse con `chain.filter(exchange)`, nunca bloquear ahí."

## 2 · HTTP/2 — cuándo importa y qué cambia

HTTP/2 multiplexa muchos requests sobre **una sola conexión TCP** (en vez de una conexión por request de HTTP/1.1), y comprime headers. Encaja naturalmente con la filosofía de WebFlux (pocas conexiones, mucho tráfico concurrente), pero **no es automático**:

```yaml
server:
  http2:
    enabled: true
  ssl:
    enabled: true          # HTTP/2 sobre texto plano (h2c) casi no lo soporta ningún cliente/browser real
    key-store: classpath:keystore.p12
```

| | HTTP/1.1 | HTTP/2 |
|---|---|---|
| Conexiones por cliente | Varias (o keep-alive reusado, pero secuencial por conexión). | Una, multiplexada — varios streams en paralelo. |
| Requisito práctico | Ninguno especial. | TLS en la práctica (los navegadores no negocian h2 sin HTTPS). |
| Dónde se nota más | — | Muchos requests pequeños concurrentes del mismo cliente (típico de un frontend SPA pidiendo varios recursos). |

**Trampa de entrevista**: activar HTTP/2 no vuelve "mágicamente más rápido" un backend con pocos clientes concurrentes o con requests pesados — el beneficio es la multiplexación de **muchas** conexiones cortas del mismo origen, no el throughput bruto de un único request grande.

## 3 · Streaming — Server-Sent Events y respuestas infinitas

Un `Flux` no tiene que traer todos los datos antes de responder — puede **transmitirlos a medida que se generan**. El caso más pedido en entrevista es Server-Sent Events (SSE):

```java
@GetMapping(value = "/appointments/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<ServerSentEvent<AppointmentEvent>> streamUpdates() {
    return appointmentEventPort.subscribeToUpdates()      // Flux<AppointmentEvent> que emite cuando algo cambia
        .map(event -> ServerSentEvent.<AppointmentEvent>builder()
            .id(event.id().toString())
            .event("appointment-updated")
            .data(event)
            .build());
}
```

```java
// Ejemplo didáctico de un stream infinito — nunca completa, cada suscriptor recibe su propia cadencia
Flux<Long> heartbeat = Flux.interval(Duration.ofSeconds(5));
```

- **`produces = MediaType.TEXT_EVENT_STREAM_VALUE`** es lo que le dice a WebFlux que no junte todo en un solo body — cada elemento del `Flux` se envía como llega.
- **Backpressure real**: si el cliente lee más lento de lo que el `Flux` produce, Reactor no descarta datos por descuido — el productor se ajusta al ritmo del consumidor (a diferencia de simplemente acumular todo en memoria, que es lo que pasaría con un `List` tradicional).
- **Cuándo usarlo de verdad**: notificaciones en vivo, progreso de un proceso largo, dashboards que se actualizan solos — no para un CRUD normal donde el cliente solo necesita "la respuesta completa, una vez".

## 4 · Optimización de rendimiento — lo que un senior debe poder nombrar

| Área | Qué revisar | Por qué importa |
|---|---|---|
| **Connection pools** (R2DBC, `WebClient`) | Tamaño del pool (`spring.r2dbc.pool.max-size`, `maxConnections` de `ConnectionProvider` en `WebClient`) — no dejarlo en default sin pensar el volumen real. | Un pool muy chico serializa requests que deberían ser paralelas; uno muy grande agota conexiones del downstream. |
| **Ningún `.block()` en el pipeline** | Buscarlo en code review — un solo `.block()` en un flujo reactivo anula la ventaja de no bloquear el event loop. | El event loop de Netty tiene pocos hilos — bloquear uno afecta a **todas** las requests que comparten ese hilo, no solo a la que bloqueó. |
| **`Schedulers` correcto** | `boundedElastic()` solo para I/O bloqueante que no se puede evitar; `parallel()` para CPU-bound; nunca el `Schedulers` default para todo. | Usar el scheduler equivocado convierte una ganancia de paralelismo en contención de hilos. |
| **No acumular streams gigantes en memoria** | `collectList()` sobre un `Flux` de millones de filas cancela el propósito del streaming — se vuelve a cargar todo en memoria. | El punto de `Flux` es procesar item por item; forzarlo a lista completa solo tiene sentido para volúmenes chicos. |
| **`BlockHound`** | Librería que detecta llamadas bloqueantes dentro de hilos reactivos **en tests/desarrollo**, lanzando una excepción en vez de dejarlo pasar silenciosamente. | Es la forma de verificar en CI que nadie metió un `.block()` o una llamada JDBC sin querer. |

## 5 · Testing de integración — más allá de `StepVerifier`/`WebTestClient`

[`webflux.md`](webflux.md) sección 9 ya cubre `StepVerifier` (pipeline reactivo) y `WebTestClient` (endpoint HTTP mockeado). Un test de **integración** agrega una pieza más: una base de datos real, no mockeada, típicamente con **Testcontainers**.

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AppointmentIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
        .withDatabaseName("appointments_test");

    @DynamicPropertySource
    static void r2dbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://" + postgres.getHost() + ":" + postgres.getFirstMappedPort() + "/appointments_test");
    }

    @Autowired
    WebTestClient webTestClient;

    @Test
    void shouldPersistAndReturnCreatedAppointment() {
        webTestClient.post().uri("/appointments")
            .bodyValue(new CreateAppointmentRequest(/* ... */))
            .exchange()
            .expectStatus().isCreated();
        // acá la verificación real toca la base de datos del contenedor, no un mock
    }
}
```

**Por qué importa frente a un mock**: un repositorio mockeado no detecta un query R2DBC mal escrito, una constraint de base de datos violada, o una migración (Flyway/Liquibase) rota — Testcontainers levanta una base real, descartable, por ejecución de test.

## Drills de repaso

| Tiempo | Ejercicio |
|---|---|
| 5 min | ¿Qué diferencia hay entre un `WebFilter` y un `@RestControllerAdvice`? Dar un ejemplo de cuándo usar cada uno. |
| 4 min | ¿HTTP/2 hace automáticamente más rápido un backend con pocos clientes concurrentes? Justificar. |
| 6 min | Diseñar (en palabras) un endpoint que transmita el progreso de un proceso largo al cliente sin que este haga polling. ¿Qué operador/anotación usarías? |
| 5 min | Nombrar 3 cosas que un code reviewer senior busca específicamente para performance en un pipeline reactivo. |
| 5 min | ¿Por qué un test de integración con Testcontainers detecta bugs que un `WebTestClient` con repositorio mockeado no detecta? |

## Referencias

- [Spring Framework — WebFlux Reference, sección "WebFilter"](https://docs.spring.io/spring-framework/reference/web/webflux/webfilter.html)
- [Spring Framework — HTTP/2 support](https://docs.spring.io/spring-boot/reference/web/servers.html)
- [Project Reactor — Server-Sent Events](https://docs.spring.io/spring-framework/reference/web/webflux/reactive-spring.html)
- [Testcontainers — Spring Boot integration](https://testcontainers.com/guides/testing-spring-boot-rest-api-integration-tests/)
- [BlockHound — reactor/BlockHound](https://github.com/reactor/BlockHound)

Relacionado: [`webflux.md`](webflux.md) para el framework y los fundamentos de Reactor, [`webflux-operators.md`](webflux-operators.md) para el catálogo de operadores, y [`spring-data.md`](spring-data.md) para R2DBC en profundidad.
