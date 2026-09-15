# SaludTools — Desarrollador(a) Senior

> **Entrevista técnica: HOY, 5:00 PM.** Por eso este documento tiene el contenido completo replicado acá mismo (no solo links) — para tener todo a mano sin saltar de carpeta en carpeta. Los links a las fuentes generales quedan más abajo, para cuando haya que reestructurar esto de vuelta en referencias puras.

Java 21, Spring WebFlux, AWS, microservicios sobre un producto HealthTech.

## Estado del proceso

| Etapa | Con quién | Foco |
|---|---|---|
| 1 · Entrevista inicial | Carlos Polanco — Head of Engineering | Conocerte, presentar la compañía/producto/desafío. No es técnica dura, pero puede tocar arquitectura a alto nivel. |
| 2 · Evaluación técnica en vivo | 2 developers Senior de SaludTools | Preguntas conceptuales + caso práctico (microservicio Java compartiendo pantalla), 1h agendada / 1h30 recomendada. |
| 3 · Entrevista final | Camilo Naranjo — Founder de SaludTools | Última instancia antes de la definición. Visión de producto/negocio y fit. |

Contacto de proceso: Ani (reclutadora). Detalle de fit, logística y entregables esperados en [`fit-y-liderazgo.md`](fit-y-liderazgo.md).

---

## 00 · Plan de estudio — qué hacer con el tiempo que queda (hoy, entrevista a las 5pm)

Ya no queda tiempo para "seguir leyendo teoría nueva" — todo lo de abajo (01-06) ya está documentado y probado. Lo que falta es **priorizar** y **practicar en voz alta**, no acumular más lectura.

### La ansiedad real que hay que resolver primero: "casi todo el código lo hace la IA"

Eso es correcto, y es exactamente lo que SaludTools espera de un Senior en 2026 — no te van a medir por velocidad de tipeo. Lo que un entrevistador Senior evalúa hoy, con o sin IA generando el código, son 3 cosas puntuales:

1. **Criterio de arquitectura** — ¿dónde va esta lógica? (`domain` vs `application` vs `infrastructure`), no "¿sabés la sintaxis de memoria?".
2. **Explicar el *por qué*, no solo mostrar que funciona** — si la IA te sugiere un `flatMap`, ¿sabés decir por qué ahí y no un `map`? Si sugiere `Mono.just(archivo.leer())`, ¿detectás que está mal y por qué?
3. **Revisar y corregir lo que la IA propone, en voz alta** — es literalmente el flujo que ya practicaste con los agentes (`hexagonal-architect` → `tdd-reviewer` → `spring-boot-webflux-dev`, ver sección 07). No es una habilidad nueva que te falte: es la que ya construiste sin llamarla así.

**Guion corto si preguntan directamente "¿cómo usás la IA?":** *"La uso para el primer borrador — el esqueleto, el boilerplate, un test inicial — pero reviso cada sugerencia contra las reglas de la arquitectura antes de aceptarla: si mete lógica de negocio en el controller, si usa `map` donde debería ir `flatMap`, si el `Mono` puede terminar vacío sin manejarlo. La IA acelera el primer paso, el criterio de por qué algo está bien sigue siendo mío."*

### Las 2 formas que puede tomar el ejercicio — un guion para cada una

No sabés cuál te va a tocar, así que preparate para las dos con una apertura ya decidida (no improvisada):

| Si es... | Tu apertura (primeros 2-3 min, en voz alta) |
|---|---|
| **"Implementá esto"** (código real, en tu IDE, con o sin autocomplete permitido) | 1) Repetí el requerimiento con tus palabras para confirmar que entendiste. 2) Anunciá el esqueleto hexagonal antes de tipear una línea (`domain`/`application`/`infrastructure`, ver sección 04). 3) Empezá por el test que falla (TDD, sección 05), no por la implementación. 4) Si usás autocomplete/IA, verbalizá por qué aceptás o corregís cada sugerencia relevante (esto es lo que más pesa, ver arriba). |
| **"Diseñemos un sistema"** (arquitectura, pizarra o con Claude ahí mismo) | 1) Preguntá 2-3 cosas antes de dibujar nada: volumen esperado, qué dato es crítico que nunca se pierda (consistencia fuerte vs eventual), quién más consume esto. 2) Dibujá el hexágono + los límites de microservicio, no el detalle de clases. 3) Nombrá 2-3 alternativas reales para cada decisión (SQL vs NoSQL, sync vs async — ver [`system-design/02-databases-sql-vs-nosql.md`](../../system-design/02-databases-sql-vs-nosql.md) y [`microservices-patterns/`](../../microservices-patterns)) y cerrá con "para este caso, con este volumen/equipo, elijo X porque...". |

### SaludTools es una startup — que se note en cómo respondés

No es un banco ni una corporación con comités de arquitectura — es una startup HealthTech. Priorizá simplicidad sobre ceremonia en cualquier respuesta de diseño: si te dan a elegir, defendé la opción que resuelve el problema **hoy** sin sobre-ingeniería (mismo criterio YAGNI de [`clean-code/`](../../clean-code)), y mencionalo explícitamente — "en una startup, agregaría esto solo cuando el volumen real lo justifique, no antes" es una respuesta de nivel senior, no una excusa.

### Bloques de estudio para hoy (en orden, con tiempo asignado)

| Bloque | Tiempo | Qué hacer |
|---|---|---|
| 1 · Simulacro completo | 45-60 min | Corré el flujo de la sección 07 de punta a punta con un dominio **nuevo** que no hayas usado antes (ni citas médicas ni café — inventá uno de SaludTools, ej. "seguimiento de signos vitales"). Cronometrado. Es la práctica de mayor impacto de todas. |
| 2 · Operadores reactivos en voz alta | 25-30 min | Repasá [`webflux-operators.md`](../../frameworks/spring-boot/webflux-operators.md) — `map`/`flatMap`, `switchIfEmpty`, manejo de errores. Explicá cada uno en voz alta como si se lo dijeras a alguien no técnico, sin mirar el ejemplo. |
| 3 · Vocabulario DDD sin mirar | 15-20 min | Tapá la sección 03.6 y decí de memoria: Entity vs VO, Aggregate, Repository pattern, Domain Event, Bounded Context — una frase cada uno. |
| 4 · Fit y logística | 15-20 min | Repasá [`fit-y-liderazgo.md`](fit-y-liderazgo.md) — tené listo un ejemplo propio por cada fila de responsabilidades, y la respuesta a "por qué SaludTools/por qué salud". |
| 5 · Sanity check de entorno | 10 min | Confirmá que el IDE, JDK 21 y (si aplica) LocalStack arrancan sin fricción — no aprendas nada nuevo de AWS hoy, solo verificá que lo que ya sabés arranca. |

**No** le metas tiempo nuevo a temas que no dominás de antes (ej. Spring Batch, Event Sourcing a fondo) — la ganancia marginal de estudiarlos hoy es baja comparada con practicar el flujo completo del bloque 1.

---

## 00.5 · Vocabulario para un simulador tipo "Head of Engineering" (ADRs, HL7/FHIR, gobierno técnico)

Si usás un prompt de simulación de entrevista (4 bloques: arquitectura/stack, gobierno técnico con ADRs, deuda técnica/producción, liderazgo/HL7-FHIR), esto es lo que el repo **no** cubría todavía porque nunca lo necesitamos hasta ahora — es la parte específica de SaludTools/HealthTech, no repaso general.

### ADR (Architecture Decision Record) — Bloque 2

Un ADR es un documento corto que registra **una** decisión de arquitectura: el contexto, las opciones consideradas, la elegida y por qué. No es diseño detallado, es trazabilidad de decisiones para que en 6 meses alguien entienda el "por qué" sin preguntarte a vos.

Formato mínimo (memorizable):
1. **Contexto** — qué problema forzó la decisión.
2. **Opciones consideradas** — mínimo 2, con trade-offs reales (no una "correcta" y una de relleno).
3. **Decisión** — la elegida, en una frase.
4. **Consecuencias** — qué se gana y qué se sacrifica (deuda técnica aceptada a propósito).

Ejemplo hablado (adaptalo en la entrevista a lo que te pregunten):
> "Para el envío de eventos de facturación electrónica, documenté un ADR: contexto era necesidad de desacoplar la generación de RIPS del flujo síncrono de la API; opciones eran SQS (simple, manejado, at-least-once) vs. Kafka (retención/replay, pero más ceremonia operativa para el tamaño del equipo); elegí SQS porque el volumen y el equipo no justificaban Kafka todavía — lo documenté como decisión revisable si el volumen crece 10x."

### HL7 / FHIR — Bloque 4 (interoperabilidad en salud)

No hace falta ser experto en un día, pero sí poder hablar con criterio 60 segundos:
- **HL7 v2** — el estándar más viejo y todavía dominante en LATAM para mensajería entre sistemas de salud (admisión, resultados de labs) — mensajes tipo texto delimitado (`ADT`, `ORU`), no JSON.
- **FHIR** (Fast Healthcare Interoperability Resources) — el estándar moderno, basado en recursos REST/JSON (`Patient`, `Encounter`, `Observation`, `Bundle`). Es lo que se usa si SaludTools expone o consume API-first con otros sistemas (labs, IPS, EPS).
- **Por qué importa para el rol**: SaludTools integra con terceros (clínicas, labs, entidades regulatorias — RIPS). La pregunta real no es "¿conocés el estándar al detalle?" sino "¿entendés que la interoperabilidad médica tiene reglas de formato y regulación que no podés inventar libremente, a diferencia de una API interna?". Respuesta segura: *"No he implementado FHIR en profundidad, pero entiendo el modelo de recursos REST y la razón regulatoria detrás — lo abordaría revisando la spec del recurso específico antes de diseñar la integración, no asumiendo un formato propio."*

### Deuda técnica y producción sin frenar el negocio — Bloque 3

- **Refactor sin detener el negocio**: strangler fig (reemplazar módulo por módulo detrás de la misma interfaz), feature flags para activar el código nuevo gradualmente, y siempre con el módulo viejo corriendo en paralelo hasta validar en producción real.
- **Observabilidad mínima que hay que nombrar**: logs estructurados + métricas (los "golden signals": latencia, tráfico, errores, saturación) + tracing distribuido si hay microservicios. Sin esto, "reducir deuda técnica" no se puede medir.
- **Gestión de incidentes**: postmortem sin culpa (blameless) enfocado en la causa raíz del sistema, no en quién lo escribió — es la respuesta que un Head of Engineering espera de alguien que ya lideró equipos.

### Trade-offs escalabilidad vs. costo vs. time-to-market — Bloque 2

Heurística para responder cualquier pregunta de este tipo: *"la decisión correcta depende del volumen y la etapa de la empresa, no de la solución más elegante — en una startup en crecimiento, prefiero la opción más simple que no me bloquee escalar después, y documento en el ADR cuándo revisarla."* Es la misma idea que ya usás en la sección 00 sobre YAGNI en SaludTools — reutilizala acá.

---

## 00.7 · Píldoras relámpago — Java Backend + AWS (repaso rápido-fuego)

12 preguntas típicas de entrevista "Java + AWS senior", condensadas a una línea cada una — para captar rápido, no para leer en detalle (el detalle ya está en las carpetas de siempre, linkeadas donde aplica).

### Java backend

| # | Pregunta | Respuesta relámpago |
|---|---|---|
| 1 | ¿Cómo diseñás un backend Java escalable? | Microservicios + comunicación async (Kafka/SQS) + caching (Redis) + connection pooling (HikariCP) + stateless detrás de un load balancer. Ver [`microservices-patterns/`](../../microservices-patterns). |
| 2 | ¿Java 8 vs Java 17+? | 8: lambdas/streams/`Optional`. 11: HTTP Client, `var`. 17 (LTS): sealed classes, records, pattern matching `instanceof`. Detalle completo en [`java-core/java-version-evolution.md`](../../java-core/java-version-evolution.md). |
| 3 | ¿Cómo asegurás calidad de código y performance? | Ver tabla debajo — es contenido nuevo, no estaba en el repo. |
| 10 | ¿Cómo manejás fallas en sistemas distribuidos? | Retry con backoff + circuit breaker (Resilience4j) + DLQ + idempotency keys + graceful degradation. Ya cubierto en [`microservices-patterns/`](../../microservices-patterns) sección 2. |
| 11 | Contame de un proyecto que llevaste vos solo, de punta a punta. | Tené un ejemplo propio armado (migración monolito→microservicios, CI/CD, IaC, entrega sin supervisión constante) — ver [`fit-y-liderazgo.md`](fit-y-liderazgo.md) para cómo estructurar la respuesta con el framework STAR. |
| 12 | ¿Cómo depurás un problema de performance en producción? | Orden: CloudWatch metrics → X-Ray traces → GC logs/heap dump → recién ahí optimizar la causa raíz. Detalle completo (nuevo) en [`cloud-aws/`](../../cloud-aws) sección "Observabilidad y debugging". |

**Calidad de código y performance en Java (pregunta 3 — contenido nuevo):**

| Práctica | Herramienta típica |
|---|---|
| Análisis estático | SonarQube / Checkstyle — antes de mergear, no como auditoría post-hoc. |
| Tests | JUnit + tests de integración — no solo unitarios. |
| Profiling | JVisualVM o Java Flight Recorder — para encontrar el método/hilo real que consume CPU, no adivinar. |
| Tuning de GC | Parámetros de la JVM ajustados al patrón de la app (throughput vs. baja latencia) — no defaults sin revisar en un servicio con SLA de latencia. |
| CI/CD | Build + tests + análisis estático automatizados en cada PR — la calidad se aplica en la pipeline, no se pide "por favor" en el code review. |

### AWS

| # | Pregunta | Respuesta relámpago |
|---|---|---|
| 4 | ¿Cómo diseñás almacenamiento de archivos con S3? | `S3AsyncClient` + keys con UUID + versioning/lifecycle + IAM/bucket policies + CloudFront. Detalle completo ya en [`cloud-aws/`](../../cloud-aws) sección S3. |
| 5 | ¿Buenas prácticas corriendo Java en EC2? | ASG + ALB/NLB + SSM Parameter Store + CloudWatch/X-Ray + AMI horneada con Packer. Nuevo — en [`cloud-aws/`](../../cloud-aws) sección EC2. |
| 6 | ¿DynamoDB vs RDS? | DynamoDB: NoSQL, latencia constante, escala automático, sin joins — alto volumen de escritura por clave. RDS: relacional, joins/transacciones. Nuevo — en [`cloud-aws/`](../../cloud-aws) sección DynamoDB. |
| 7 | ¿Cómo usás Elasticsearch desde Java? | Cliente REST para indexar/consultar, full-text search, sharding+replication, Kibana para visualizar. Nuevo — en [`cloud-aws/`](../../cloud-aws) sección Elasticsearch. |
| 8 | ¿CDK vs CloudFormation? | CDK es código (Java/TS/Python) que **compila a** CloudFormation — no lo reemplaza, le da abstracciones reales encima. Nuevo — en [`cloud-aws/`](../../cloud-aws) sección IaC. |
| 9 | ¿Cómo diseñarías un sistema de gestión de órdenes escalable en AWS? | API Gateway → Lambda/EC2 (microservicios Spring Boot) → DynamoDB (lookups) + S3 (archivo) → SQS/Kafka (eventos async) → Elasticsearch (búsqueda) → CloudWatch/X-Ray/ELK (observabilidad) → Multi-AZ + retries + DLQs (resiliencia). Es literalmente juntar todas las píldoras de arriba en un solo diagrama — practicalo dibujándolo de memoria. |

## 01 · Programación reactiva

Lo más probable es que el diferenciador de la entrevista pase por acá. Objetivo: no bloquear el event loop, nunca.

### Mono vs Flux — decisión en 2 segundos

- **`Mono<T>`** — 0 o 1 elemento. Un `findById`, el resultado de un `POST`, una respuesta de otro servicio.
- **`Flux<T>`** — 0..N elementos. Un `findAll`, un stream de eventos, una lista paginada.

### map vs flatMap — la pregunta que seguro te hacen

| Operador | Cuándo | Firma mental |
|---|---|---|
| `map` | Transformación **síncrona** pura, sin I/O. Cambiás el tipo o el valor, nada más. | `T -> R` |
| `flatMap` | La transformación **devuelve otro Mono/Flux** — otra llamada reactiva (otro servicio, otra query R2DBC). | `T -> Mono<R>` |
| `flatMapMany` | Desde un `Mono`, expandís a `Flux` (ej: traés un pedido y devolvés sus líneas). | `T -> Flux<R>` |
| `concatMap` | Como flatMap pero preserva el orden — más lento, útil si el orden importa (auditoría, pagos). | `T -> Mono<R>` ordenado |

> ⚠️ **Señal de alarma:** si ves un `Mono` anidado dentro de un `map` (`map(x -> otroServicio.buscar(x))` devolviendo `Mono<Mono<R>>`), era `flatMap`. Es el error más común y el que un entrevistador senior detecta al instante.

```java
// map: transformación pura
Mono<CreditEvaluationResponse> response = evaluation
    .map(e -> new CreditEvaluationResponse(e.id(), e.status()));

// flatMap: encadena otra llamada reactiva
Mono<RiskData> risk = riskPort.getRiskData(cedula)   // ya es Mono<RiskData>
    .flatMap(data -> approvalService.evaluate(data)); // evaluate() devuelve Mono<Status>
```

### Manejo de errores — el cheat sheet

| Operador | Uso |
|---|---|
| `onErrorResume` | Recuperar con un valor/flujo alternativo (ej: fallback a caché si el servicio externo falla). |
| `onErrorMap` | Traducir una excepción de infraestructura (timeout, IOException) a una excepción de dominio (`RiskServiceUnavailableException`). |
| `onErrorReturn` | Devolver un valor fijo ante error. Usar con cuidado: nunca sin loggear antes, o tragás el error. |
| `retryWhen(Retry.backoff(...))` | Reintentar llamadas externas inestables con backoff exponencial. Ideal para el WebClient hacia otro microservicio. |
| `doOnError` | Side-effect (logging) sin alterar el flujo — no "consume" el error. |

```java
riskPort.getRiskData(cedula)
    .retryWhen(Retry.backoff(3, Duration.ofMillis(200)))
    .onErrorMap(WebClientResponseException.class,
        ex -> new RiskServiceUnavailableException("Riesgo no disponible", ex))
    .doOnError(ex -> log.error("Fallo consultando riesgo: {}", cedula, ex));
```

### Otros puntos que suelen preguntar

- **Cold vs hot:** un `Flux` cold empieza a emitir recién cuando alguien se suscribe (cada suscriptor recibe su propia secuencia); un hot flux emite independientemente de si hay suscriptores (ej: eventos de un sensor).
- **R2DBC vs JPA:** JPA/JDBC es bloqueante — usarlo dentro de un pipeline reactivo bloquea el event loop de Netty y anula la ventaja de WebFlux. R2DBC es el driver no bloqueante para SQL.
- **`WebClient` vs `RestTemplate`:** `RestTemplate` está deprecado y es bloqueante. `WebClient` es el cliente reactivo estándar.
- **`Schedulers.boundedElastic()`:** único caso válido para envolver una llamada legada bloqueante que no podés evitar — nunca como solución por defecto.
- **Testing:** `StepVerifier.create(mono).expectNext(x).verifyComplete()` — nunca `.block()` en producción ni en tests de flujos reales.

---

## 02 · Java 21

Lo que cambió desde la última vez que programaste seguido — records, pattern matching y streams se usan como default, no como curiosidad.

- **Record** — DTO/Value Object inmutable en una línea: `record RiskData(int score, List<Debt> debts) {}`. Genera constructor, getters, `equals`/`hashCode`/`toString`.
- **Pattern matching switch** — `switch` puede retornar valor y hacer binding de tipo: útil para mapear excepciones a respuestas HTTP sin cadenas de `if/instanceof`.
- **Virtual threads** — hilos livianos (Project Loom). No reemplazan a Reactor para I/O reactivo puro, pero simplifican código bloqueante concurrente si te preguntan la diferencia.
- **`toList()`** — reemplaza `.collect(Collectors.toList())`. Devuelve lista inmutable.

### Streams — equivalencia mental con Reactor

Si te piden resolver algo con `Stream` (filtros, mapeos), es el mismo razonamiento que `map`/`flatMap` en Reactor, pero síncrono y "de una sola vez" (no reactivo, no backpressure):

```java
List<String> nombresDeudas = riskData.debts().stream()
    .filter(d -> d.monthlyPayment().compareTo(BigDecimal.ZERO) > 0)
    .map(RiskData.Debt::debtName)
    .toList();

// pattern matching switch (Java 21)
String categoria = switch (evaluationStatus) {
    case APROBADO -> "cliente aprobado";
    case RECHAZADO -> "requiere revisión manual";
};

// instanceof con binding
if (exception instanceof CedulaInvalidaException ex) {
    return build(400, "Cédula Inválida", ex.getMessage());
}
```

Más drills de Map/List/Stream (no específicos de esta entrevista) en [`java-core/`](../../java-core).

---

## 03 · AWS — conceptos clave + práctica sin tarjeta

Minikube emula Kubernetes, no AWS. Para practicar S3/SQS/RDS sin poner una tarjeta real, la herramienta correcta es **LocalStack**.

| Servicio | Para qué lo usás desde un backend Java |
|---|---|
| `S3` | Almacenar archivos (ej: documentos clínicos, resultados). SDK: `S3AsyncClient` para no bloquear. |
| `SQS` | Cola de mensajes para desacoplar microservicios — patrón event-driven, procesamiento asíncrono. |
| `SNS` | Pub/sub — un evento notifica a varios consumidores (ej: SQS + Lambda a la vez). |
| `RDS` | Postgres/MySQL gestionado — equivalente cloud del Postgres que usarías en Docker. |
| `Lambda` | Función serverless — útil para tareas puntuales disparadas por un evento S3/SQS. |
| `ECS/Fargate` | Cómo se despliega en producción un contenedor Spring Boot — sin gestionar servidores. |
| `Secrets Manager` | Credenciales de BD y API keys — nunca hardcodeadas ni en `application.properties`. |
| `IAM` | Roles y permisos — un servicio nunca usa credenciales de usuario, usa un role con permisos mínimos. |

### LocalStack — arrancar en 3 comandos

```bash
docker compose up -d                                   # levanta Postgres + LocalStack
curl http://localhost:4566/_localstack/health          # chequeo
aws --endpoint-url=http://localhost:4566 s3 mb s3://practica-bucket
```

> En Spring, apuntás el `S3AsyncClient`/`SqsAsyncClient` al endpoint de LocalStack (`http://localhost:4566`) con credenciales dummy (`test`/`test`) — el código de producción no cambia, solo el endpoint por config. Si en la entrevista te piden usar AWS, contá esto en voz alta: mostrás que sabés separar código de infraestructura.

Docker-compose y script completo de práctica (fuera de este repo, ver sección de enlaces) en la carpeta local de referencia — comandos equivalentes documentados en [`cloud-aws/`](../../cloud-aws).

---

## 03.5 · Spring / Spring Boot / JPA — fundamentos que dan por sentado que sabés

Preguntas de base que un entrevistador Senior espera contestadas sin dudar, incluso si el stack del día a día ya es 100% reactivo.

- **¿Qué es un framework?** Código de terceros que controla el flujo (te llama a vos, no al revés — *Inversion of Control*). Vos completás los huecos con tu lógica.
- **Spring vs Spring Framework vs Spring Boot:**
  - *Spring* = el ecosistema entero (Framework + Boot + Data + Security + Cloud...).
  - *Spring Framework* = el núcleo (2003): contenedor **IoC** + **Inyección de Dependencias** (`ApplicationContext`, `@Component`, AOP).
  - *Spring Boot* = una capa encima que agrega **auto-configuración** + **starters** + servidor embebido — "Spring, pero sin cablear todo a mano".
- **Constructor injection, no `@Autowired` en campos** — recomendación oficial de Spring (inmutabilidad, dependencias garantizadas no-null, testeable sin el contenedor). Con Lombok: `@RequiredArgsConstructor` sobre campos `private final`, en vez de escribir el constructor.
- **JPA vs Hibernate:** JPA es la **especificación** (`@Entity`, `EntityManager`); Hibernate es la **implementación** más usada de esa especificación. Spring Data JPA agrega repositorios (`JpaRepository<T,ID>`) encima de ambos.
- **JPA/Hibernate es bloqueante** (JDBC por debajo) — nunca dentro de un pipeline WebFlux. Para reactivo: **Spring Data R2DBC** (API distinta, sin lazy-loading ni caché de 1er nivel).
- **Entidad JPA ≠ Entidad DDD:** una `@Entity` de JPA mapea una tabla; una Entidad de DDD es identidad+ciclo de vida en el dominio. En hexagonal son **clases distintas** (`OrderJpaEntity` vs `Order`), unidas por un `Mapper` en el adaptador — el dominio nunca importa `jakarta.persistence`.
- **El problema N+1** — acceder a una relación `@ManyToOne(fetch = LAZY)` dentro de un loop dispara una query por fila. Se resuelve con `JOIN FETCH`, `@EntityGraph`, o una proyección DTO directa — nunca cambiando el fetch type a `EAGER` por defecto (trae de más en los casos que no lo necesitan). Detalle en [`spring-boot/spring-data.md`](../../frameworks/spring-boot/spring-data.md).
- **`@Transactional` no funciona en WebFlux/R2DBC** — depende de `ThreadLocal`, incompatible con un pipeline que salta de hilo en el event loop. Se usa `TransactionalOperator` en su lugar.
- **`WebClient` sin timeout configurado** es la trampa más común en código reactivo real — una llamada colgada consume un canal de Netty indefinidamente. Detalle de `WebClient`, `WebTestClient` y seguridad reactiva (`ServerHttpSecurity`) en [`spring-boot/webflux.md`](../../frameworks/spring-boot/webflux.md).

Detalle completo con tabla comparativa, anotaciones clave y diagrama del ecosistema en [`spring-boot/`](../../frameworks/spring-boot) (incluye también Spring Batch — Job/Step, chunk processing — por si sale como tema de procesamiento masivo).

---

## 03.6 · DDD (Domain-Driven Design) — "definir estándares" no es solo SOLID

La descripción del rol pide explícitamente promover DDD como estándar (ver [`fit-y-liderazgo.md`](fit-y-liderazgo.md), sección 3) — vale la pena tener el vocabulario firme, no solo "haber leído el libro de Evans".

- **Entity vs Value Object** — `class` con igualdad por `id` y ciclo de vida (`Order`, `Appointment`) vs `record` inmutable con igualdad por valor (`LineItem`, `TimeSlot`). Un `record` de Java modela un Value Object, nunca una Entity — la igualdad estructural automática es justo lo que **no** querés en una Entity.
- **Aggregate / Aggregate Root** — el clúster que se guarda/actualiza como una sola transacción. Un Aggregate solo referencia a otro **por ID** (`PatientId`, no `Patient` completo) — nunca lo compone. Regla de oro: una transacción de base de datos = un Aggregate.
- **Repository pattern** — un Repository por Aggregate Root, nunca por tabla (`LineItem` no tiene su propio repositorio). En hexagonal, es exactamente `port/out` + `PersistenceAdapter` — DDD le pone nombre al mismo patrón.
- **Domain Events** — hechos ya ocurridos, nombrados en pasado (`AppointmentCompleted`, no `CompleteAppointment` — eso es un Command). Se publican en la misma transacción que el cambio de estado (Outbox pattern) para no perderlos ni duplicarlos si algo falla después.
- **Domain Service** — una regla que cruza dos Aggregates y no tiene un dueño natural entre las Entities existentes (ej. `transferMoney(from, to, amount)`). No confundir con el Application Service: el Domain Service decide reglas de negocio sin I/O; el Application Service orquesta (Repository, transacción) sin decidir reglas.
- **Bounded Context** — la misma palabra (`Patient`) puede significar algo distinto en dos contextos (Citas Médicas vs Facturación) — es la frontera donde el modelo y el vocabulario son consistentes, no una capa técnica.
- **CQRS** — separar el modelo de escritura (Aggregate rico, con invariantes) del modelo de lectura (proyección plana, un `record` sin comportamiento). Empezar siempre por el nivel más simple (separar puertos/queries en el código) antes de pensar en bases de datos de lectura separadas.
- **Event Sourcing** — no confundir con "publicar Domain Events": acá el evento **es** la fuente de verdad (se persiste la secuencia completa, el estado se reconstruye con replay), no un mensaje adicional después de guardar el estado. Casi siempre viaja junto a CQRS nivel 3. Se justifica solo cuando el historial de cambios es en sí un requisito de negocio (auditoría, sistemas clínicos/financieros) — no por defecto.

> **Frase para repetir en la entrevista:** "en el día a día, el patrón hexagonal me da la ubicación en el código (`domain`/`application`/`infrastructure`); DDD me da el criterio de **qué va dentro de `domain`** — cuándo algo es una Entity, cuándo un Value Object, y dónde trazar el límite de un Aggregate."

Detalle completo, ejemplos trabajados con `Order`/`Appointment`, y drills cronometrados en [`ddd/`](../../ddd).

---

## 04 · Arquitectura hexagonal

```
com.saludtools.<servicio>/
├── domain/              núcleo puro — sin Spring, sin I/O
│   └── <agregado>/       entidad (identidad + ciclo de vida — clase, no record),
│                          value objects (records), excepciones de dominio propias
├── application/         frontera del hexágono
│   ├── port/in/          interfaces de casos de uso (ej. EvaluateXUseCase + su Command)
│   ├── port/out/         interfaces que el caso de uso necesita (ej. XPort)
│   └── service/          XService — implementa el UseCase, orquesta domain + ports out
│                          (constructor injection, Lombok @RequiredArgsConstructor)
└── infrastructure/      adaptadores — implementan los ports
    ├── adapter/in/web/     controller reactivo (Mono/Flux) + DTOs (@Valid) +
    │                       @RestControllerAdvice devolviendo ProblemDetail (RFC 7807)
    ├── adapter/out/persistence/  R2DBC repository (implementa port/out), sufijo "Adapter"
    │   ├── entity/          clase de persistencia (@Table), distinta de la entidad de dominio
    │   └── mapper/          traduce Entity(persistencia)↔Entidad(dominio)
    ├── adapter/out/client/  WebClient hacia servicios externos (implementa port/out)
    └── config/              @Configuration (OpenAPI, @ConfigurationProperties, etc.)
```

### Reglas que ya aplicás (mantenelas en el live coding)

- `domain` no importa nada de `infrastructure` ni de Spring — es lo primero que un evaluador revisa.
- Naming: `EvaluateCreditUseCase` (port in, interfaz) → `EvaluateCreditService` (application/service, **no** `...UseCaseImpl`). `RiskPort` (port out) → `RiskPersistenceAdapter`/`RiskClientAdapter` (infrastructure, sufijo "Adapter" solo en el lado de salida — el controller web no lo lleva).
- Un `Command` (input de un UseCase) es un **`record`**, no una interfaz — solo los puertos (contratos con implementación intercambiable) son interfaces; el Command es dato inmutable, con validación estructural en su compact constructor (*self-validating value object*).
- Cada `UseCase` debe mapear 1:1 a un método que **ya existe** en la entidad de dominio (ej. `Account.withdraw()`) — si el dominio no tiene el comportamiento todavía, se agrega primero ahí, no se inventa el caso de uso antes.
- Una excepción que representa un **hecho de negocio** (ej. "no existe", "transición inválida") vive en `domain`, no en `application/exception` — no es un detalle de cableado. Se traduce a HTTP en un `@RestControllerAdvice` centralizado devolviendo `ProblemDetail`.
- DTOs de `rest`/`client` nunca cruzan a `domain` — se mapean en el adaptador. Nunca serializar la entidad de dominio directo en la respuesta HTTP.

> **Nota JPA vs R2DBC:** si tu referencia previa usa JPA bloqueante + `@Transactional` (Quarkus/Spring MVC), en Spring WebFlux el adaptador de persistencia usa `R2dbcRepository` y devuelve `Mono`/`Flux` — no hay transacciones bloqueantes tradicionales, se usa `TransactionalOperator` si hace falta. Diferencia JPA/Hibernate/Entidad explicada en [`spring-boot/`](../../frameworks/spring-boot).

> **Validado en la práctica (2026-09-13):** este patrón exacto se implementó y probó end-to-end con `curl` real en dos dominios distintos (citas médicas y pedidos de café) en un playground de arquitectura hexagonal — incluyendo el hallazgo real de que un `Mono<T>` vacío en WebFlux devuelve `200` con body vacío por defecto, no `404`; hay que traducirlo explícitamente con `.switchIfEmpty(Mono.error(...))` en el adapter web.

Más detalle y comparación de proyectos de referencia en [`hexagonal-architecture.md`](../../software-architectures/hexagonal-architecture.md).

---

## 05 · TDD y pruebas

Historia de usuario → criterios Gherkin → red-green-refactor.

### Ciclo

1. **Red** — Escribís el test contra la interfaz del caso de uso. Falla porque no existe implementación.
2. **Green** — Código mínimo para pasar — sin optimizar de más.
3. **Refactor** — Limpiás aplicando SOLID, el test sigue en verde.

### Pirámide práctica

| Capa | Herramienta | Nota |
|---|---|---|
| domain/service | JUnit 5, sin mocks | Lógica pura — `new Service()` directo. |
| application/usecase | JUnit 5 + Mockito | Mockeás los ports out. |
| Flujos reactivos | `StepVerifier` (reactor-test) | Nunca `.block()` en el test de un flujo real. |
| Integración | `@SpringBootTest` + Testcontainers / `WebTestClient` | Postgres real en Docker, perfil de test separado. |

```java
StepVerifier.create(useCase.evaluate(cedula, monto, años, salario))
    .expectNextMatches(r -> r.status() == EvaluationStatus.APROBADO)
    .verifyComplete();
```

### Historia de usuario → Gherkin (formato para el vivo)

```gherkin
Como analista de crédito
Quiero que el sistema evalúe automáticamente una solicitud
Para aprobar o rechazar sin intervención manual

Given un cliente con score 85 y sin deudas previas
When solicita un crédito cuya cuota es menor al 40% del salario
Then la evaluación resulta APROBADO
```

Ciclo completo y kata de ejemplo en [`tdd/`](../../tdd).

---

## 06 · Ejercicios rápidos (drills de 5–10 min)

Para hacer con el timer puesto, en tu IDE, antes del día de la prueba.

| Tiempo | Ejercicio |
|---|---|
| 8 min | Dado un `Flux<Paciente>`, filtrá los mayores de 60 años, mapealos a un DTO con `record`, y devolvé un `Flux<PacienteDto>` ordenado por apellido. |
| 10 min | Implementá un endpoint `POST /v1/turnos` reactivo que valide el input, llame a un `WebClient` mockeado hacia "disponibilidad", y si falla devuelva 503 vía `onErrorMap` + `@ControllerAdvice`. |
| 7 min | Escribí de memoria la diferencia entre `map` y `flatMap` con un ejemplo de cada uno, en voz alta, como si se lo explicaras a un perfil de Producto. |
| 10 min | Armá el esqueleto hexagonal completo (domain/application/infrastructure) para un caso nuevo ("gestión de turnos médicos") sin mirar el repo de referencia. |
| 8 min | Test con `StepVerifier` que verifique que un `Mono` propaga `RiskServiceUnavailableException` cuando el WebClient simulado falla. |
| 6 min | Levantá LocalStack, creá un bucket S3 y subí un archivo de prueba con el AWS CLI, sin mirar este documento. |
| 5 min | Explicá en voz alta, con el caso de `Appointment`/`PatientId`, por qué un Aggregate solo referencia a otro por ID y nunca lo compone completo. |
| 5 min | Te preguntan "¿cómo evitás un N+1 en este listado?" con una relación `@ManyToOne` de por medio — respondé nombrando 2 soluciones distintas y su trade-off. |

---

## 07 · Agentes de Claude Code

Los agentes de práctica relevantes (hexagonal, Java 21, Spring Boot WebFlux, TDD) están en [`ia-agentes/`](../../ia-agentes) del repo — usalos para practicar el flujo completo y para mostrar en la entrevista cómo integrás IA con criterio.

- **`hexagonal-architect`** — arma el esqueleto de paquetes domain/application/infrastructure para un dominio nuevo, y valida que no se violen los límites del hexágono.
- **`java-21-dev`** — Java 21 puro, agnóstico de framework (Virtual Threads vs Reactor, Structured Concurrency, Record Patterns).
- **`spring-boot-webflux-dev`** — implementa o revisa controllers/services/repositories con Spring Boot WebFlux, priorizando la elección correcta entre map/flatMap, manejo de errores, inyección de dependencias y `ProblemDetail`.
- **`tdd-reviewer`** — traduce historias de usuario a criterios Gherkin y guía el ciclo red-green-refactor con JUnit5/Mockito/StepVerifier.

> Flujo sugerido: `hexagonal-architect` arma el esqueleto → `tdd-reviewer` da el primer test que falla → `spring-boot-webflux-dev` implementa hasta que pase. Cronometrado a 45–60 min simula la presión real del ejercicio en vivo.

---

## 08 · Checklist del día de la prueba

Google Meet, pantalla compartida, sin plataforma especial — el entorno lo llevás vos.

- [ ] IDE abierto y probado (IntelliJ), con un proyecto Spring Boot WebFlux ya inicializado de antemano como punto de partida.
- [ ] JDK 21 activo, `./mvnw -v` corrido al menos una vez hoy.
- [ ] Entorno con Postgres + LocalStack levantado antes de entrar a la llamada, si el caso lo amerita.
- [ ] Verbalizar el diseño antes de tipear: entidades, endpoints, capas — 2-3 minutos de "pienso en voz alta".
- [ ] Nombrar en voz alta que estás usando IA (Claude Code / agentes) y cómo revisás lo que genera — está valorado, no lo escondas.
- [ ] Si el tiempo alcanza: agregar manejo de errores y al menos un test antes de "terminar".

## Preguntas para hacerles (mostrar seniority)

- ¿Qué tan reactivo es el stack actual hoy, o es una migración en curso?
- ¿Cómo es el proceso de code review y quién define estándares hoy?
- ¿Qué tan seguido pasan cosas en las guardias — volumen real de incidentes?
- ¿Cómo se toman las decisiones de arquitectura (ADRs, comités, o más orgánico)?
- Siendo una startup: ¿cómo balancean deuda técnica vs. velocidad de entrega cuando hay presión de negocio? (muestra que entendés el contexto, no solo arquitectura en abstracto)
- ¿Qué tan integrado está el uso de IA (Claude Code, Copilot, etc.) en el flujo del equipo hoy?

---

## Dónde está cada tema en el repo general (para reestructurar después de la entrevista)

Una vez pasada la entrevista, este archivo puede volver a achicarse a solo lo específico de SaludTools + estos links, porque todo el contenido de arriba ya vive de forma genérica acá:

| Tema | Carpeta |
|---|---|
| Spring / Spring Boot / JPA / Hibernate (fundamentos) | [`spring-boot/fundamentals.md`](../../frameworks/spring-boot/fundamentals.md) |
| WebFlux — Parte 1: controlador, Mono/Flux, map vs flatMap, WebClient, testing, seguridad, vs. Virtual Threads | [`spring-boot/webflux.md`](../../frameworks/spring-boot/webflux.md) |
| WebFlux — Parte 2: catálogo de operadores (`zip`, `switchIfEmpty`, `deferContextual`, `then`, `doOnNext`) con frecuencia real de uso | [`spring-boot/webflux-operators.md`](../../frameworks/spring-boot/webflux-operators.md) |
| Spring Data (repositorios, N+1, `@Transactional`, R2DBC) | [`spring-boot/spring-data.md`](../../frameworks/spring-boot/spring-data.md) |
| Spring Batch (Job/Step, chunk processing) | [`spring-boot/spring-batch.md`](../../frameworks/spring-boot/spring-batch.md) |
| DDD (Entities/VO, Aggregates, Repository, Domain Events/Service, Bounded Context, CQRS) | [`ddd/`](../../ddd) |
| Microservicios: comunicación, resiliencia, saga/outbox, OWASP | [`microservices-patterns/`](../../microservices-patterns) |
| AWS + práctica con LocalStack | [`cloud-aws/`](../../cloud-aws) |
| Java como lenguaje: POO, estructuras de datos, interfaces funcionales, evolución 8→21 | [`java-core/`](../../java-core) |
| Arquitectura hexagonal | [`hexagonal-architecture.md`](../../software-architectures/hexagonal-architecture.md) |
| Escalar de 0 a millones de usuarios | [`system-design/`](../../system-design) |
| TDD | [`tdd/`](../../tdd) |
| Agentes de Claude Code | [`ia-agentes/`](../../ia-agentes) |
