# SaludTools — Desarrollador(a) Senior

> **Entrevista técnica: martes que viene.** Por eso este documento tiene el contenido completo replicado acá mismo (no solo links) — para tener todo a mano sin saltar de carpeta en carpeta. Los links a las fuentes generales quedan más abajo, para cuando haya que reestructurar esto de vuelta en referencias puras.

Java 21, Spring WebFlux, AWS, microservicios sobre un producto HealthTech.

## Estado del proceso

| Etapa | Con quién | Foco |
|---|---|---|
| 1 · Entrevista inicial | Carlos Polanco — Head of Engineering | Conocerte, presentar la compañía/producto/desafío. No es técnica dura, pero puede tocar arquitectura a alto nivel. |
| 2 · Evaluación técnica en vivo | 2 developers Senior de SaludTools | Preguntas conceptuales + caso práctico (microservicio Java compartiendo pantalla), 1h agendada / 1h30 recomendada. |
| 3 · Entrevista final | Camilo Naranjo — Founder de SaludTools | Última instancia antes de la definición. Visión de producto/negocio y fit. |

Contacto de proceso: Ani (reclutadora). Detalle de fit, logística y entregables esperados en [`fit-y-liderazgo.md`](fit-y-liderazgo.md).

---

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

## 04 · Arquitectura hexagonal

```
com.saludtools.<servicio>/
├── domain/              núcleo puro — sin Spring, sin I/O
│   ├── model/            entidades y value objects (records)
│   └── service/          reglas de negocio puras
├── application/         frontera del hexágono
│   ├── port/in/          interfaces de casos de uso (ej. EvaluateXUseCase)
│   ├── port/out/         interfaces que el caso de uso necesita (ej. XPort)
│   ├── usecase/          XUseCaseImpl — orquesta domain + ports out
│   └── exception/        excepciones de negocio
└── infrastructure/      adaptadores — implementan los ports
    ├── rest/              controller reactivo (Mono/Flux) + DTOs + @ControllerAdvice
    ├── persistence/       R2DBC repository (implementa port/out)
    └── client/            WebClient hacia servicios externos (implementa port/out)
```

### Reglas que ya aplicás (mantenelas en el live coding)

- `domain` no importa nada de `infrastructure` ni de Spring — es lo primero que un evaluador revisa.
- Naming consistente: `EvaluateCreditUseCase` (port in) → `EvaluateCreditUseCaseImpl`. `RiskPort` (port out) → `RiskAdapter` (infrastructure).
- DTOs de `rest/dto` y `client/dto` nunca cruzan a `domain` — se mapean en el adaptador.
- Excepciones de negocio en `application/exception`, traducidas a HTTP en un manejador centralizado (`@ControllerAdvice`).

> **Nota:** si tu referencia previa usa JPA bloqueante + `@Transactional` (Quarkus/Spring MVC), en Spring WebFlux el adaptador de persistencia usa `R2dbcRepository` y devuelve `Mono`/`Flux` — no hay transacciones bloqueantes tradicionales, se usa `TransactionalOperator` si hace falta.

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

---

## 07 · Agentes de Claude Code

Los 3 agentes de práctica (hexagonal, WebFlux, TDD) están en [`ia-agentes/`](../../ia-agentes) del repo — usalos para practicar el flujo completo y para mostrar en la entrevista cómo integrás IA con criterio.

- **`hexagonal-architect`** — arma el esqueleto de paquetes domain/application/infrastructure para un dominio nuevo, y valida que no se violen los límites del hexágono.
- **`java-reactive-dev`** — implementa o revisa controllers/services/repositories con WebFlux, priorizando la elección correcta entre map/flatMap y el manejo de errores.
- **`tdd-reviewer`** — traduce historias de usuario a criterios Gherkin y guía el ciclo red-green-refactor con JUnit5/Mockito/StepVerifier.

> Flujo sugerido: `hexagonal-architect` arma el esqueleto → `tdd-reviewer` da el primer test que falla → `java-reactive-dev` implementa hasta que pase. Cronometrado a 45–60 min simula la presión real del ejercicio en vivo.

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

---

## Dónde está cada tema en el repo general (para reestructurar después de la entrevista)

Una vez pasada la entrevista, este archivo puede volver a achicarse a solo lo específico de SaludTools + estos links, porque todo el contenido de arriba ya vive de forma genérica acá:

| Tema | Carpeta |
|---|---|
| Mono/Flux, map vs flatMap, manejo de errores reactivo | [`reactive-programming/`](../../reactive-programming) |
| Microservicios: comunicación, resiliencia, saga/outbox, OWASP | [`microservices-patterns/`](../../microservices-patterns) |
| AWS + práctica con LocalStack | [`cloud-aws/`](../../cloud-aws) |
| Map/List/Stream en Java | [`java-core/`](../../java-core) |
| Arquitectura hexagonal | [`hexagonal-architecture.md`](../../software-architectures/hexagonal-architecture.md) |
| Escalar de 0 a millones de usuarios | [`system-design/`](../../system-design) |
| TDD | [`tdd/`](../../tdd) |
| Agentes de Claude Code | [`ia-agentes/`](../../ia-agentes) |
