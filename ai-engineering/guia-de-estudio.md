# Guía de estudio — taller un día, despliegue el siguiente

> **Qué es esto:** el **calendario de estudio** que mezcla los dos caminos: **🛠️ días de taller** (practicar los 12 temas del [Agentic Track](agentic-ai/README.md) en Java, luego Quarkus, TypeScript y Python) y **☁️ días de despliegue** (el [Production Track](llm-production/README.md): nube, trazas, guardrails, evaluación), con **🔁 días de repaso y reorganización** cada pocos días. Es la **opción C** que elegimos.
> ⬅️ [Guía de estudio general](README.md) · [Talleres](talleres/README.md) · [Los 12 temas](agentic-ai/README.md) · [Production Track](llm-production/README.md)

---

## 🍳 Con la cocina

Un restaurante se aprende **alternando oficio y servicio**: un día **practicas una técnica en tu cocina** (el taller) y al siguiente **ves cómo se monta eso para cien comensales** (el despliegue). Cada pocos días paras a **repasar las recetas y reordenar la despensa** (repaso y reorganización), porque lo que no se repasa se olvida y lo que no se ordena no se encuentra.

> En palabras de la calle: **un día haces, el siguiente despliegas, y cada cuarto día repasas y reordenas. Así lo aprendido se queda.**

---

## ⚠️ Antes de empezar: dos aclaraciones honestas

| Tema | Aclaración |
|---|---|
| **Qué es el Production Track** | Asumes que trata de "agentes MCP que despliegan el proyecto en AWS/Azure". **No lo sé todavía 🔎**: por lo que quedó anotado, cubre despliegue (Vercel, AWS/Bedrock, GCP, Azure), observabilidad (Langfuse), guardrails e inyección de prompt, y un caso multiagente financiero. Los **días ☁️ de abajo son provisionales** hasta el **Día 0** |
| **Los tiempos** | Cada día es una sesión de ~2 h 🧩. Si estudias 3–4 días por semana, **los bloques 1 a 3 son ~8–10 semanas**. Es una estimación mía |

## Día 0 · Triaje del Production Track (una sola vez, ~30 min)

1. **Tú:** pegas el **índice de lecciones** del Production Track (títulos y duraciones) en [`llm-production/clases/_pegar-clase-aqui.md`](llm-production/clases/_pegar-clase-aqui.md).
2. **Yo:** lo clasifico en 🟢 ver / 🟡 parte / 🔴 saltar y **reemplazo los días ☁️ provisionales** por los reales.
3. **Resultado:** los días ☁️ quedan con **lecciones concretas** y se descarta lo que no sirve.

☐ Día 0 hecho

---

## 1 · El ritmo

```mermaid
graph LR
    A["🛠️ Día impar<br/>Taller (practicar un tema)"] --> B["☁️ Día par<br/>Despliegue (Production Track)"]
    B --> A
    B -. "cada 4.º par" .-> R["🔁 Repaso y reorganización"]
    R -.-> A
```

| Tipo | Qué haces | Duración 🧩 | Cómo se cierra |
|---|---|---|---|
| **🛠️ Taller** | Un taller del [plan](talleres/README.md) con **TDD sin llamar al modelo real** | ~2 h | Pruebas en verde + la **definición de hecho** del taller |
| **☁️ Despliegue** | Lecciones elegidas del Production Track + 20 min de lectura del tema del Agentic Track | ~2 h | Una **nota de 10 líneas**: qué cambia al llevarlo a la nube y qué riesgo trae |
| **🔁 Repaso** | Releer escaleras de entrevista de los últimos temas **sin mirar**; **reorganizar** las notas | ~2 h | Notas integradas en los temas; lista "qué me falló" |

### Plantilla de cada sesión (siempre igual)

| Minuto | Qué |
|---|---|
| 0–10 | **Repaso del día anterior**: responde su escalera de entrevista en voz alta |
| 10–25 | Concepto del día (la sección del tema, con el diagrama) |
| 25–90 | **Práctica** (taller, o lecciones de despliegue) |
| 90–110 | **"¿Qué rompe esto?"**: un fallo, un riesgo de seguridad y un costo |
| 110–120 | **Nota de cierre** (máx. 10 líneas) y ☐ → ✅ en esta guía |

---

## 2 · El calendario

> **Leyenda:** 🛠️ taller · ☁️ despliegue (**provisional hasta el Día 0**) · 🔁 repaso · el estado se marca ☐ / ✅.
> Los temas **01–12** son los de [`agentic-ai/`](agentic-ai/README.md); las W/Q/N/P, los de [talleres](talleres/README.md).

### Bloque 1 · Java con Spring AI: el núcleo (días 1–12)

| Día | Tipo | Qué se hace | Tema(s) | Listo cuando… | |
|:-:|:-:|---|---|---|:-:|
| 1 | 🛠️ | **W00** Proyecto Spring Boot + Spring AI 1.1.x + **modelo falso** + clave de Gemini | [02](agentic-ai/02-modelos-y-proveedores.md) | `mvn test` verde **sin red** | ☐ |
| 2 | ☁️ | **D1** ¿Qué cambia al desplegar un agente? Contenedores, secretos, entornos, costo | [09 §5](agentic-ai/09-seguridad-guardrails-y-humano-en-el-bucle.md) · [02](agentic-ai/02-modelos-y-proveedores.md) | Nota: 5 diferencias entre "mi portátil" y "producción" | ☐ |
| 3 | 🛠️ | **W01** Primer agente: `ChatClient`, prompt y **salida estructurada** | [01](agentic-ai/01-agentes-y-workflows.md) · [03](agentic-ai/03-prompts-contexto-y-salidas-estructuradas.md) | Test del esquema con modelo falso | ☐ |
| 4 | ☁️ | **D2** Una plataforma de despliegue sencilla (tipo Vercel 🔎) | por confirmar | Una app de ejemplo desplegada o descrita paso a paso | ☐ |
| 5 | 🛠️ | **W02** Herramientas y bucle: `@Tool`, **lista blanca**, tope de iteraciones | [04](agentic-ai/04-herramientas-y-bucle-del-agente.md) | Un modelo falso que nunca termina corta en el tope | ☐ |
| 6 | ☁️ | **D3** Trazas en producción (tipo Langfuse 🔎) | [10](agentic-ai/10-observabilidad-evaluacion-y-costos.md) | Sabes qué dato **no** debe salir en una traza | ☐ |
| 7 | 🛠️ | **W03** Workflows: chaining con puerta (pago), routing y paralelización | [07](agentic-ai/07-orquestacion-y-multiagente.md) | Cada rama probada, fallo parcial manejado | ☐ |
| 8 | ☁️ | **D4** Guardrails e inyección de prompt en producción | [09](agentic-ai/09-seguridad-guardrails-y-humano-en-el-bucle.md) | Lista de 5 ataques y su control | ☐ |
| 9 | 🛠️ | **W04 · parte 1** Gerente: plan **validado** (formato y tope de subtareas) | [07](agentic-ai/07-orquestacion-y-multiagente.md) | Planes falsos (vacío, largo, JSON roto) rechazados | ☐ |
| 10 | 🛠️ | **W04 · parte 2** Agentes-herramienta y sintetizador (**tu PoC3**) | [07](agentic-ai/07-orquestacion-y-multiagente.md) · [12](agentic-ai/12-proyectos-del-curso.md) | Reunión → funcionalidades → tareas, de punta a punta | ☐ |
| 11 | ☁️ | **D5** Caso multiagente financiero (🔎 del Production Track) | [07](agentic-ai/07-orquestacion-y-multiagente.md) · [12](agentic-ai/12-proyectos-del-curso.md) | Comparas ese caso con el tuyo | ☐ |
| 12 | 🔁 | **R1** Repaso de 01–04 y 07; **reorganizar** lo aprendido en D1–D5 | todos | Notas integradas en 09 y 10; decidir si hace falta el **tema 13** | ☐ |

### Bloque 2 · Seguridad, MCP y evaluación (días 13–24)

| Día | Tipo | Qué se hace | Tema(s) | Listo cuando… | |
|:-:|:-:|---|---|---|:-:|
| 13 | 🛠️ | **W06** Aprobación humana + *advisors* (PII, registro, tope de llamadas) | [09](agentic-ai/09-seguridad-guardrails-y-humano-en-el-bucle.md) | Un pago **no se ejecuta** sin `approve` | ☐ |
| 14 | ☁️ | **D6** Evaluaciones en producción | [10](agentic-ai/10-observabilidad-evaluacion-y-costos.md) | Defines una métrica de negocio para tu caso | ☐ |
| 15 | 🛠️ | **W07** Evaluador (LLM juez) + primer conjunto de pruebas | [10](agentic-ai/10-observabilidad-evaluacion-y-costos.md) | Rechaza N veces y escala a humano | ☐ |
| 16 | ☁️ | **D7** Nube I: contenedores y servicios gestionados (tipo AWS 🔎) | por confirmar | Diagrama de despliegue de tu PoC | ☐ |
| 17 | 🛠️ | **W08** Cliente MCP con **filtro de herramientas** | [05](agentic-ai/05-mcp.md) | Solo las herramientas permitidas llegan al modelo | ☐ |
| 18 | ☁️ | **D8** Nube II: otra nube para comparar (GCP o Azure 🔎) | por confirmar | Tabla comparativa de las dos nubes | ☐ |
| 19 | 🛠️ | **W09** Servidor MCP propio (cuentas/pagos ficticios) | [05](agentic-ai/05-mcp.md) | Otro cliente lo consume | ☐ |
| 20 | ☁️ | **D9** MCP remoto: autenticación y exposición segura 🔎 | [05](agentic-ai/05-mcp.md) · [09](agentic-ai/09-seguridad-guardrails-y-humano-en-el-bucle.md) | Lista de verificación de un servidor MCP remoto | ☐ |
| 21 | 🛠️ | **W05** Memoria y estado: historial por `conversationId`, reanudar | [06](agentic-ai/06-memoria-busqueda-y-rag.md) · [08](agentic-ai/08-langchain-langgraph-y-deep-agents.md) | Cerrar la app y continuar la conversación | ☐ |
| 22 | ☁️ | **D10** Escala, costos y resiliencia | [10](agentic-ai/10-observabilidad-evaluacion-y-costos.md) | Presupuesto y topes definidos para tu PoC | ☐ |
| 23 | 🛠️ | **W10** Observabilidad local (Micrometer/OpenTelemetry) | [10](agentic-ai/10-observabilidad-evaluacion-y-costos.md) | Reconstruyes qué herramienta se llamó y con qué argumentos | ☐ |
| 24 | 🔁 | **R2** Repaso de 05, 06, 09, 10; **reorganizar** | todos | Borrador del **tema 13 · Despliegue y operación** | ☐ |

### Bloque 3 · Despliegue real y cierre en Java (días 25–34)

| Día | Tipo | Qué se hace | Tema(s) | Listo cuando… | |
|:-:|:-:|---|---|---|:-:|
| 25 | 🛠️ | **W12** Sandbox seguro (Docker, sin red, límites, no root) | [09](agentic-ai/09-seguridad-guardrails-y-humano-en-el-bucle.md) | Un intento de salir del directorio **falla** | ☐ |
| 26 | ☁️ | **D11** Desplegar el PoC en **una** nube | por confirmar | El PoC responde desde la nube con costos acotados | ☐ |
| 27 | 🛠️ | **W11** RAG agéntico (pgvector o Qdrant) | [06](agentic-ai/06-memoria-busqueda-y-rag.md) | El agente guarda y recupera datos propios | ☐ |
| 28 | ☁️ | **D12** CI/CD, secretos y entornos del PoC | por confirmar | Pipeline que despliega con secretos fuera del repo | ☐ |
| 29 | 🛠️ | **W13 · parte 1** Multiagente con tablero compartido | [07](agentic-ai/07-orquestacion-y-multiagente.md) | Dos trabajadores sin pisarse | ☐ |
| 30 | 🛠️ | **W13 · parte 2** Orquestador y agente de pruebas | [07](agentic-ai/07-orquestacion-y-multiagente.md) · [12](agentic-ai/12-proyectos-del-curso.md) | Flujo completo en paralelo | ☐ |
| 31 | 🛠️ | **W14** API REST de agentes y preparación para despliegue | [12](agentic-ai/12-proyectos-del-curso.md) | Contrato OpenAPI probado | ☐ |
| 32 | ☁️ | **D13** Despliegue completo del capstone | por confirmar | El capstone vive en la nube, con trazas y límites | ☐ |
| 33 | 🛠️ | **W15** Evaluación sistemática (casos fijos, regresión, costo por ejecución) | [10](agentic-ai/10-observabilidad-evaluacion-y-costos.md) | Un cambio de prompt se acepta o rechaza **por una métrica** | ☐ |
| 34 | 🔁 | **R3** Repaso general; **reorganizar los temas principales** | todos | Production Track integrado (temas 09, 10 y **13**); guía de estudio actualizada | ☐ |

### Bloque 4 · Los mismos oficios en otros stacks (días 35 en adelante)

| Días | Ruta | Talleres | Notas |
|---|---|---|---|
| 35–38 | 🟧 **Quarkus** | Q1 · Q2 · Q3 · Q4 | Al terminar: matriz Spring AI vs. Quarkus; despliegue opcional (☁️ D14) |
| 39–42 | 🟩 **TypeScript (NestJS)** | N1 · N2 · N3 · N4 | El servidor MCP de N3 debe interoperar con el de Java |
| 43–46 | 🟨 **Python (FastAPI)** | P1 · P2 · P3 · P4 | El mismo servidor MCP desde 3 lenguajes |
| 47+ | 🏁 **Capstone** | Sala de conciliación | En el stack elegido, con seguridad, evaluación y despliegue |

☐ Bloque 4 iniciado

---

## 3 · Los puntos de reorganización (🔁)

| Punto | Después de | Qué se reorganiza |
|---|---|---|
| **R1** | Día 12 | Lo aprendido de despliegue y trazas **entra en los temas 09 y 10**; se decide si hace falta un tema nuevo |
| **R2** | Día 24 | Se redacta el **tema 13 · Despliegue y operación** (nube, secretos, CI/CD, costos) |
| **R3** | Día 34 | Se **reordenan los temas principales**, se actualiza el README y esta guía, y se cierra el Production Track |

**Regla:** nada se queda "suelto" en una nota aparte; **todo lo aprendido se integra en un tema** (el mismo criterio que usamos con las semanas del Agentic Track).

## 4 · Reglas del juego

| Regla | Detalle |
|---|---|
| **Una sesión = un objetivo** | Si no cabe en 2 h, se parte en dos días (como W04 y W13) |
| **Pruebas primero** | Los talleres usan TDD con modelo falso; el modelo real solo para comprobar |
| **Nada de claves en git** | `.env` fuera del repo; `.env.example` dentro. Credenciales de la nube, **jamás** |
| **Costos acotados** | Presupuesto fijo en cada clave y **apagar** lo desplegado al terminar el día |
| **Notas cortas** | Máximo 10 líneas por sesión; el resto va a los temas |
| **Si te atrasas** | Se **salta un ☁️**, no un 🛠️; los talleres son lo que más se nota en una entrevista |
| **Datos ficticios** | Siempre; el dominio es pagos y conciliación |

## 5 · Qué debe dominar cada nivel al terminar

| Nivel | Debe poder... |
|---|---|
| 🟢 **Junior** | Construir un agente con herramientas y salida estructurada en Java y explicarlo con un diagrama. |
| 🟡 **Mid** | Añadir aprobación humana, un evaluador y un servidor MCP; desplegar el PoC en una nube con secretos y topes de costo. |
| 🔴 **Senior** | Defender una arquitectura **con seguridad, evaluación y trazas por diseño**, compararla en dos o tres stacks y explicar **qué se desplegó, cuánto cuesta y cómo se vigila**. |

## 6 · Escaleras de entrevista

**"¿Cómo llevarías un agente a producción?"**
- 🟢 "Lo meto en un contenedor y lo subo a un servidor."
- 🟡 "Secretos fuera del código, trazas, topes de costo y aprobación humana en las acciones con efectos."
- 🔴 "Despliegue reproducible (CI/CD), aislamiento del código generado, evaluación con casos fijos en el pipeline, observabilidad sin datos sensibles y plan de costo y de reversa."

**"¿Cómo estudiaste IA aplicada?"**
- 🟢 "Hice un curso."
- 🟡 "Hice un curso y lo practiqué en Java con pruebas."
- 🔴 "Reorganicé un curso por oficios, practiqué los mismos patrones en cuatro stacks y desplegué uno con evaluación y trazas; sé qué parte del curso era débil (seguridad y evaluación) y la completé."

## 7 · Qué se descartó y por qué

| Descartado | Motivo |
|---|---|
| Ver el Production Track completo antes de practicar | Se olvida sin práctica; mucho es específico de cada nube |
| Fechas de calendario fijas | No sé tu disponibilidad; las sesiones son relativas |
| Replicar **todos** los talleres en los cuatro stacks | Demasiado; se replican los esenciales |

## 🔗 Relacionado

[Guía de estudio general](README.md) · [Plan de talleres](talleres/README.md) · [Los 12 temas](agentic-ai/README.md) · [Production Track](llm-production/README.md) · [Diagramas UML](agentic-ai/diagramas-uml.md)
