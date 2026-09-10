# SaludTools — Desarrollador(a) Senior

Java 21, Spring WebFlux, AWS, microservicios sobre un producto HealthTech. Este archivo es el punto de entrada del proceso: estado, checklist del día, preguntas para hacerles, y el mapa hacia dónde está cada tema técnico de repaso.

## Estado del proceso

| Etapa | Con quién | Foco |
|---|---|---|
| 1 · Entrevista inicial | Carlos Polanco — Head of Engineering | Conocerte, presentar la compañía/producto/desafío. No es técnica dura, pero puede tocar arquitectura a alto nivel. |
| 2 · Evaluación técnica en vivo | 2 developers Senior de SaludTools | Preguntas conceptuales + caso práctico (microservicio Java compartiendo pantalla), 1h agendada / 1h30 recomendada. |
| 3 · Entrevista final | Camilo Naranjo — Founder de SaludTools | Última instancia antes de la definición. Visión de producto/negocio y fit. |

Contacto de proceso: Ani (reclutadora). Detalle de fit, logística y entregables esperados en [`fit-y-liderazgo.md`](fit-y-liderazgo.md).

## Dónde está cada tema técnico

| Tema | Repasar en |
|---|---|
| Mono/Flux, map vs flatMap, manejo de errores reactivo | [`reactive-programming/`](../../reactive-programming) |
| Microservicios: comunicación, resiliencia, saga/outbox, OWASP | [`microservices-patterns/`](../../microservices-patterns) |
| AWS + práctica con LocalStack (sin tarjeta) | [`cloud-aws/`](../../cloud-aws) |
| Map/List/Stream en Java | [`java-core/`](../../java-core) |
| Arquitectura hexagonal (puertos y adaptadores) | [`hexagonal-architecture/`](../../hexagonal-architecture) |
| Escalar de 0 a millones de usuarios | [`system-design/`](../../system-design) |
| TDD (red-green-refactor, AAA) | [`tdd/`](../../tdd) |
| Practicar el ejercicio en vivo con IA | [`ia-agentes/`](../../ia-agentes) |

## Checklist del día de la prueba técnica

Google Meet, pantalla compartida, sin plataforma especial — el entorno lo llevás vos.

- [ ] IDE abierto y probado (IntelliJ), con un proyecto Spring Boot WebFlux ya inicializado de antemano como punto de partida.
- [ ] JDK 21 activo, `./mvnw -v` corrido al menos una vez hoy.
- [ ] Entorno con Postgres + LocalStack levantado (ver [`cloud-aws/`](../../cloud-aws)) antes de entrar a la llamada, si el caso lo amerita.
- [ ] Verbalizar el diseño antes de tipear: entidades, endpoints, capas — 2-3 minutos de "pienso en voz alta".
- [ ] Nombrar en voz alta que estás usando IA (Claude Code / agentes de [`ia-agentes/`](../../ia-agentes)) y cómo revisás lo que genera — está valorado, no lo escondas.
- [ ] Si el tiempo alcanza: agregar manejo de errores y al menos un test antes de "terminar".

## Preguntas para hacerles (mostrar seniority)

- ¿Qué tan reactivo es el stack actual hoy, o es una migración en curso?
- ¿Cómo es el proceso de code review y quién define estándares hoy?
- ¿Qué tan seguido pasan cosas en las guardias — volumen real de incidentes?
- ¿Cómo se toman las decisiones de arquitectura (ADRs, comités, o más orgánico)?
