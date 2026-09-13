# Software Engineering — Ruta de estudio

Repositorio de estudio y repaso rápido para consolidar lo que un **Software Engineer / Solutions Architect Senior** debe dominar: fundamentos de OOP, SOLID y clean code, patrones de diseño, arquitecturas de software (hexagonal, clean, onion, MVC) y de microservicios, system design a gran escala, programación reactiva, cloud (AWS), UML, TDD, cómo integrar IA (subagentes de Claude Code) al flujo de trabajo, y la disciplina de AI Engineering en sí (LLMs, RAG, agentes autónomos).

> Este repo es **solo documentación** — píldoras en Markdown con diagramas Mermaid, pensadas para repasar rápido y consultar en el momento (una entrevista, una decisión de arquitectura). No contiene proyectos de código para clonar y correr: esa parte vive aparte, fuera de este repo, para que acá no haya nada que compilar ni mantener — solo materia de estudio.

## Mapa del repositorio

```mermaid
graph LR
    root["software-engineering"]

    root --> oop["oop-design/<br/>Fundamentos OOP"]
    root --> javacore["java-core/<br/>POO, estructuras de datos,<br/>evolución 8→21"]
    root --> solid["solid-principles/<br/>Principios SOLID"]
    root --> cc["clean-code/<br/>DRY, KISS, YAGNI, code review"]
    root --> dp["design-pattern/<br/>Patrones de diseño"]
    root --> ddd["ddd/<br/>Domain-Driven Design"]
    root --> uml["uml/<br/>Notación UML"]
    root --> arch["software-architectures/<br/>MVC, Clean, Onion, Hexagonal"]
    root --> sysdes["system-design/<br/>System Design"]
    root --> micro["microservices-patterns/<br/>Microservicios"]
    root --> frameworks["frameworks/<br/>Spring Boot, NestJS"]
    root --> reactive["reactive-programming/<br/>Programación reactiva"]
    root --> aws["cloud-aws/<br/>AWS + LocalStack"]
    root --> tdd["tdd/<br/>TDD"]
    root --> ia["ia-agentes/<br/>IA con Claude Code"]
    root --> aieng["ai-engineering/<br/>AI Engineer roadmap"]
    root --> entrevistas["entrevistas/<br/>Bitácora de procesos"]

    oop --> javacore
    oop --> solid
    solid --> cc
    cc --> dp
    dp --> ddd
    ddd --> arch
    arch --> sysdes
    sysdes --> micro
    arch --> micro
    arch --> frameworks
    frameworks --> reactive
    micro --> reactive
    micro --> aws
    arch --> tdd
    arch --> ia
    ia --> aieng
    reactive --> entrevistas
    aws --> entrevistas
    micro --> entrevistas
    aieng --> entrevistas

    style root fill:#0d7d72,color:#fff
```

La progresión de aprendizaje sugerida sigue las flechas: primero fundamentos de objetos, después principios de diseño y patrones, y desde ahí hacia arquitectura de sistemas completos (hexagonal → microservicios → reactivo/cloud), con TDD y UML como prácticas transversales.

## Índice

| Carpeta | Tema | Estado | Contenido destacado |
|---|---|---|---|
| [`oop-design/`](oop-design) | Fundamentos OOP | ✅ Completo | Cohesión/acoplamiento, abstracción, encapsulamiento, descomposición, asociación, generalización — agnóstico de lenguaje. |
| [`java-core/`](java-core) | Java como lenguaje | ✅ Completo | POO implementada en Java (equals/hashCode, abstract vs interface), árbol de decisión de estructuras de datos (List/Set/Map/Queue + Big-O), interfaces funcionales (Java 8), línea de tiempo de versiones (8→21), y cheatsheet de Collections & Streams. |
| [`solid-principles/`](solid-principles) | Principios SOLID | ✅ Completo | Los 5 principios con diagramas "violación vs aplicado" y píldoras de repaso. |
| [`clean-code/`](clean-code) | Clean Code | ✅ Completo | DRY, KISS, YAGNI, Law of Demeter/Tell-Don't-Ask/Hollywood Principle y checklist de code review — los principios "chicos" que complementan SOLID. |
| [`design-pattern/`](design-pattern) | Patrones de diseño (GoF) | ✅ Completo | Creacionales, estructurales y de comportamiento — los 22 patrones GoF documentados con ejemplo y "cuándo usarlo". |
| [`ddd/`](ddd) | Domain-Driven Design | ✅ Completo | Entities vs Value Objects, Aggregates, Repository pattern, Domain Events, Domain Service, Bounded Context, CQRS — con `Order` (coffee shop) y `Appointment` (citas médicas) como casos trabajados. |
| [`uml/`](uml) | Notación UML | ✅ Completo | Cheatsheet de las 5 relaciones (herencia, asociación, agregación, composición, dependencia) con diagramas Mermaid. |
| [`software-architectures/`](software-architectures) | Arquitecturas de software | 🟡 En progreso | MVC, Clean Architecture, Onion, Hexagonal (puertos/adaptadores) — comparadas entre sí. Mobile (MVVM/MVP/MVI) y web frontend (Flux/Redux) pendientes. |
| [`system-design/`](system-design) | System Design | 🟡 En progreso | Escalar de 0 a millones de usuarios, SQL vs NoSQL (ACID/CAP theorem), REST vs GraphQL vs gRPC — basado en ByteByteGo. Se va sumando módulo a módulo. |
| [`microservices-patterns/`](microservices-patterns) | Patrones de microservicios | ✅ Completo | Comunicación sync/async, resiliencia (circuit breaker, retry, bulkhead), consistencia (saga, outbox), API-first, OWASP. |
| [`frameworks/`](frameworks) | Spring Boot, NestJS | 🟡 En progreso | Un framework de backend por carpeta ([`spring-boot/`](frameworks/spring-boot), [`nestjs/`](frameworks/nestjs)): fundamentos, capa web, persistencia, seguridad — con tabla de analogía punto a punto entre ambos. |
| [`reactive-programming/`](reactive-programming) | Programación reactiva | ✅ Completo | Mono/Flux, `map` vs `flatMap`, manejo de errores reactivo, R2DBC vs JPA, testing con StepVerifier. |
| [`cloud-aws/`](cloud-aws) | Cloud (AWS) | ✅ Completo | Servicios AWS clave para un backend Java + cómo practicar con LocalStack (sin tarjeta ni cuenta real). |
| [`tdd/`](tdd) | TDD | ✅ Completo | Ciclo red-green-refactor (diagrama de estados), pirámide de testing, patrón AAA. |
| [`ia-agentes/`](ia-agentes) | IA aplicada al desarrollo | 🟡 En progreso | 7 agentes + 2 skills de Claude Code para desarrollo de software (hexagonal, Java 21, Spring Boot WebFlux, TDD, GitFlow, OWASP, clean code, commits, PRs), con `agent-harness/` como fuente/compilador reusable entre proyectos; analítica/big data pendientes. |
| [`ai-engineering/`](ai-engineering) | AI Engineering / Agentes (roadmap) | 🟡 En progreso | Roadmap formal de 14 semanas/7 fases para pasar de usar IA a construirla: prompting, structured outputs, RAG, function calling, MCP, orquestación multi-agente (LangGraph), IA en producción (FastAPI/Docker/Guardrails/Evals). Distinto de `ia-agentes/`: acá se documenta cómo se construyen los sistemas de IA, no cómo usarlos como herramienta de desarrollo. |
| [`entrevistas/`](entrevistas) | Bitácora de procesos de entrevista | 🟡 En progreso | Un proceso en curso (SaludTools) — logística, checklist y mapa hacia el resto del repo. Se va sumando por proceso. |

## Cómo está organizado

- **Un tema por carpeta raíz.** Cada carpeta es autocontenida: un `README.md` como punto de entrada, con diagramas Mermaid y píldoras — sin código para compilar.
- **Los resúmenes de curso se cargan incrementalmente**, módulo por módulo, para evitar documentos gigantes.
- **Los diagramas se escriben en Mermaid**, no en imágenes ni ASCII art — GitHub los renderiza nativamente en el navegador, sin depender de herramientas externas (PlantUML, draw.io) para poder leerlos.
- **El código de referencia vive fuera de este repo**, en una carpeta local aparte (`codigo-por-reorganizar/`) — proyectos completos en Java/JS que respaldaron esta documentación, pendientes de reorganizar en sus propios repos. No se versiona acá para que este repo se mantenga liviano y 100% enfocado en estudio.
- Antes de crear una carpeta nueva para un tema, revisar si ya existe una carpeta similar y seguir el mismo patrón de organización.

## Cómo estudiar con este repo

1. Andá al índice de arriba y abrí el tema que necesitás repasar — cada `README.md` de carpeta está pensado para leerse solo, sin depender de los demás.
2. Los diagramas Mermaid son el resumen visual — si tenés poco tiempo, mirá esos primero y volvé al texto para el detalle.
3. Los temas marcados 🟡 en el índice son los que más vale la pena reforzar primero.
