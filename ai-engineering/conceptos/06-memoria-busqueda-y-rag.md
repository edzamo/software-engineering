# 06 · Memoria, búsqueda y RAG agéntico

> **Tema 6 de 10.** Fuentes: semana 2 (sesiones), semana 4 (checkpointer) y semana 6 (memoria, Tavily, Qdrant), **reforzado** con los conceptos de recuperación de la [hoja de ruta de 17](../README.md#mapa-de-17-conceptos) (embeddings, similitud, chunking, RAG clásico, ajuste fino). **Aviso honesto:** el RAG del curso es **superficial**; para rigor, el curso *Core Track* (que descartaste) o el *Production Track*.
> ⬅️ [05 · MCP](05-mcp.md) · ➡️ [07 · Orquestación y multiagente](07-orquestacion-y-multiagente.md) · [Índice](README.md)

---

## 🍳 Con la cocina

Hay **tres cuadernos** en la cocina: la **comanda de hoy** (el historial de la conversación, **memoria corta**), el **cuaderno de recetas y clientes** que se conserva de un día para otro (**memoria larga**) y la **biblioteca del barrio** donde el cocinero va a buscar lo que no sabe (**búsqueda y RAG**). El cocinero **no recuerda nada por sí mismo**: lo que parece memoria es **lo que tú le pones otra vez sobre la mesa**.

> En palabras de la calle: **la memoria no existe en el modelo; es una lista de mensajes que tú guardas y reenvías, o una base de datos que el agente consulta con herramientas.**

---

## 1 · Memoria corta: una lista guardada

```mermaid
graph LR
    A["Turno 1: mensaje"] --> L["LLM"]
    L --> B["respuesta"]
    B --> G[("💾 Guardar el historial<br/>por id de hilo")]
    G --> C["Turno 2: historial + mensaje nuevo"] --> L
```

- **Cada llamada empieza de cero.** Para recordar reenvías **toda la lista** (y pagas esos tokens).
- **Cómo se guarda en cada marco:**

| Marco | Memoria corta |
|---|---|
| Agents SDK | `SQLiteSession(id)` o `to_input_list()` + mensaje nuevo |
| LangGraph / `create_agent` | `checkpointer=MemorySaver()` + `thread_id` en cada llamada; **los nodos no cambian** |
| CrewAI | `memory=True` (oculta; difícil de diagnosticar) |
| Spring AI 🧩 | `ChatMemory` con repositorio (en memoria o JDBC) 🔎 |

- **Límite:** la ventana de contexto → se **recorta** o se **resume** (middleware de resumen).
- **`thread_id` = una conversación o un proceso.** En un backend: uno por cliente o por proceso de negocio. Cambiar de `thread_id` = empezar sin memoria.

### Memoria persistente con checkpointer (LangGraph / `create_agent`)

| Capacidad | Cómo |
|---|---|
| Cada `invoke` empieza de cero | — |
| **Memoria** | `compile(checkpointer=MemorySaver())` + `config={"configurable": {"thread_id": "…"}}`; los nodos no cambian |
| **Persistente** | `SqliteSaver.from_conn_string("memory.db")`; en producción Postgres 🔎 |
| Ver estado / historial | `graph.get_state(config)` / `graph.get_state_history(config)` (un checkpoint por superpaso) |
| **Rebobinar y reanudar** | misma `thread_id` + `checkpoint_id` anterior → `invoke` |

- **`thread_id` = una conversación o proceso.** En un backend: uno por cliente o por proceso de negocio.
- **Frente a "truncar mensajes":** en un flujo con muchos pasos puedes reanudar **desde cualquier punto**. En banca: **auditoría, reintentos tras fallo y aprobación humana a mitad del proceso**.
- 🧩 **Modelar un flujo de negocio (p. ej. el cobro con control) como grafo con estado persistido** se parece a las **sagas y estados de pago** que ya conoces.

---

## 2 · Memoria larga, búsqueda web y RAG agéntico (con servidores MCP)

Las siete piezas ya están en [ingeniería de contexto](../conceptos/03-prompts-contexto-y-salidas-estructuradas.md); aquí se ven **con servidores reales**:

| Pieza | Servidor MCP | Qué hace |
|---|---|---|
| **Memoria a largo plazo** | `server-memory` (Anthropic) | **Grafo de conocimiento** en JSON: *entidades*, *relaciones*, *observaciones*; herramientas `create_entities`, `create_relations`, `search_nodes`… En el proyecto, **una base por investigador** (`./memory/<nombre>.db`) |
| **Búsqueda web** | **Tavily** (clave gratuita sin tarjeta) | Reemplaza la búsqueda de pago de OpenAI (semana 2). Herramientas: `search`, `extract`, `crawl`, `map`, `research` |
| **RAG agéntico** | **Qdrant** (local, sin clave) | Dos herramientas: `qdrant-store` y `qdrant-find`; usa un *embedding* local (all-MiniLM-L6-v2). El agente **decide** qué guardar y qué buscar |
| **Datos de mercado** | **Massive** (antes Polygon.io) o simulador propio | Cotizaciones; si no hay clave, `market_server.py` simula precios |
| **Contexto de API al día** | **Context7** (remoto) | Documentación actualizada de librerías |

**Filtro estático de herramientas:** `create_static_tool_filter(allowed_tool_names=["tavily-search"])` — de las 5 herramientas de Tavily solo se expone **una**. 🧩 Principio del mínimo privilegio aplicado a herramientas.

**Divulgación progresiva de herramientas ⭐ (la idea nueva):** Massive pasó de **10–20 herramientas** (una por endpoint) a **meta-herramientas** para **explorar su propia API** (buscar qué hay, consultar, llamar), inspirándose en las *skills* de la [08 · Frameworks y grafos](08-frameworks-y-grafos.md): **no llenar el contexto de lo que no se usa** y dar al agente **un único camino coherente**. Resultado en el proyecto: **17 herramientas en 6 servidores** (el año anterior ~40).

**RAG en una frase del instructor:** montar un RAG a mano es **difícil y largo** (le dedicó una semana en otro curso); con un servidor MCP de Qdrant **solo hay que conocer los parámetros**. 🧩 Matiz: es **cómodo para empezar**; para un caso real hay que decidir *chunking*, embeddings, evaluación y datos sensibles.

⚠️ **MCP alojado (cuarta forma):** OpenAI puede **ejecutar el cliente y el servidor MCP en su lado**; **solo funciona con modelos de OpenAI** → te ata al proveedor. El instructor sugiere **ignorarlo** y quedarse con `stdio`/HTTP.

---

## 3 · RAG clásico: cómo funciona por dentro

El RAG agéntico de arriba **esconde** el trabajo; aquí está el mecanismo. Tiene **dos tiempos**:

```mermaid
graph LR
    subgraph IDX["① Indexar (una vez, o al cambiar los documentos)"]
        D["Documentos"] --> C["Partir en trozos<br/>(chunking)"] --> E1["Embedding de<br/>cada trozo"] --> V[("Base vectorial")]
    end
    subgraph QRY["② Consultar (en cada pregunta)"]
        Q["Pregunta"] --> E2["Embedding de<br/>la pregunta"] --> B["Buscar los k trozos<br/>más parecidos"] --> P["Prompt = pregunta<br/>+ trozos"] --> L["LLM"] --> R["Respuesta<br/>con fuentes"]
    end
    V --> B
```

| Pieza | Qué es | Detalle que importa |
|---|---|---|
| **Embedding** | Una **lista de números** que representa el **significado** de un texto | Textos de significado parecido quedan **cerca**, aunque no compartan palabras (*reembolso* ≈ *devolución de dinero*) |
| **Similitud** | Qué tan cerca están dos vectores. La más común es la **similitud del coseno** (el ángulo entre ellos; 1 = misma dirección) | Se pide los **k** más cercanos (*top-k*) y a veces un **umbral** mínimo |
| **Base vectorial** | Guarda los vectores y los **indexa** para buscar rápido | pgvector = una columna `vector` en Postgres, con índices tipo HNSW o IVFFlat; Qdrant es un servidor propio |
| **Chunking** | Partir los documentos en trozos | Trozos de **cientos de tokens** con un **solape del 10 al 20 %** 🧩: así una idea no se corta por la mitad. Muy grandes **desenfocan** la búsqueda; muy pequeños **pierden contexto** |

```mermaid
quadrantChart
    title Mapa de significado (dos dimensiones inventadas, solo para ver la idea)
    x-axis Poco financiero --> Muy financiero
    y-axis Caso puntual --> Política general
    quadrant-1 Dinero y reglas
    quadrant-2 Otros temas generales
    quadrant-3 Otros temas puntuales
    quadrant-4 Dinero y casos
    "reembolso": [0.85, 0.30]
    "devolución de dinero": [0.80, 0.25]
    "contracargo": [0.75, 0.35]
    "política de comisiones": [0.80, 0.85]
    "receta de pastel": [0.10, 0.20]
```

> *Reembolso* y *devolución de dinero* caen **juntos**: la búsqueda por significado los encuentra aunque la palabra sea distinta. Una búsqueda por palabras clave no.

**Lo que más se rompe (en orden de frecuencia 🧩):**

| Fallo | Síntoma | Remedio |
|---|---|---|
| Chunking malo | Recupera trozos cortados o sin contexto | Ajustar tamaño y solape, y **mirar qué trozos devuelve** |
| ⭐ Modelo de embeddings **distinto** al indexar y al consultar | Resultados sin sentido | Usar **el mismo** en los dos tiempos y guardarlo en la configuración |
| Recupera pero **no cita** | No se puede verificar la respuesta | Devolver la fuente de cada trozo |
| Documentos desactualizados | Respuestas viejas | Reindexar al cambiar |
| ⚠️ Un documento recuperado trae **instrucciones** | Inyección indirecta | Tratar los trozos como **datos** ([09](09-seguridad-guardrails-y-humano-en-el-bucle.md)) |
| ⚠️ Datos sensibles dentro de los vectores | Fuga de datos o imposible borrar | Permisos por documento y **borrado** al reindexar (clave en banca 🧩) |

**Cómo se evalúa un RAG** (el curso **no** lo enseña): dos preguntas por separado. ① ¿**Recuperó** el trozo correcto? (con casos fijos: pregunta → trozo esperado). ② ¿La **respuesta** se apoya en él y lo cita? Si mezclas las dos, no sabes cuál falló. → [10 · Evaluación](10-observabilidad-evaluacion-y-costos.md).

🧩 **Java / Spring AI:** `EmbeddingModel` y `TokenTextSplitter` existen en 2.0.1 (verificado). El almacén en memoria (`SimpleVectorStore`) y los *advisors* de RAG viven en módulos aparte que **aún no están en el proyecto** 🔎: se verifican al empezar **W11a**.

## 4 · ¿RAG o ajuste fino?

| Necesitas… | Usa |
|---|---|
| Datos que **cambian**, documentos **privados**, respuestas con **fuente** | **RAG** |
| Un **estilo o formato fijo** en una tarea estrecha y repetitiva | **Ajuste fino** (*fine-tuning*): reentrena los pesos |
| Las dos cosas | Primero RAG; el ajuste fino, solo si de verdad hace falta |

**Orden recomendado:** **prompt primero → RAG después → ajuste fino al final.** El ajuste fino pide un conjunto de datos, evaluación y **repetirlo cuando cambie el modelo base** ⚠️. En cambio, el RAG se actualiza **reindexando**. 🧩 Para datos de clientes, el RAG permite **permisos por documento y borrado**; lo que entra a los pesos de un modelo ya no se puede sacar.

## 5 · Los cuatro oficios y sus herramientas

| Oficio | Para qué | Opción del curso | 🧩 Cuidado |
|---|---|---|---|
| **Memoria corta** | Seguir la conversación | Sesión / checkpointer | Resumir si es larga |
| **Memoria larga** | Recordar entre conversaciones | Servidor de memoria (grafo en JSON); una base **por agente** | Datos personales: ver [09](09-seguridad-guardrails-y-humano-en-el-bucle.md) |
| **Búsqueda web** | Datos actuales | Serper, Tavily (gratis) o la alojada de OpenAI (de pago, atada) | Fecha actual en el prompt |
| **RAG** | Datos propios por significado | Qdrant (local) como herramienta `store` / `find` | Chunking, embeddings y evaluación **no** se enseñan → ver §3 |

```mermaid
graph TD
    A["Agente"] -- "qdrant-store(texto, metadatos)" --> Q[("Qdrant · embedding local")]
    A -- "qdrant-find(consulta)" --> Q
    A -- "search / extract" --> T["Tavily (web)"]
    A -- "create_entities / search_nodes" --> M[("Memoria: grafo JSON")]
```

> 🧩 **Idea:** el **RAG agéntico** es solo **dar al agente las herramientas de guardar y buscar** y dejar que decida; no sustituye el diseño de un RAG para un caso real.

## 6 · Qué debe dominar cada nivel

| Nivel | Debe poder... |
|---|---|
| 🟢 **Junior** | Explicar que el modelo no recuerda y que se **reenvía el historial**; usar una sesión o un `thread_id`. |
| 🟡 **Mid** | Persistir el estado (SQLite/JDBC), resumir contexto largo, usar un servidor de memoria y de búsqueda; distinguir memoria corta, larga y RAG. |
| 🔴 **Senior** | Decidir **qué recordar y por cuánto tiempo** (privacidad); diseñar un RAG con chunking y **evaluación**; controlar costo del historial; separar memoria por agente/cliente. |

## 7 · Escalera de entrevista

**"¿Cómo das memoria a un agente?"**
- 🟢 "Guardo el historial y lo reenvío."
- 🟡 "Persisto el estado por `conversationId` y resumo cuando se alarga; para datos de otras sesiones uso una memoria larga o un RAG."
- 🔴 "Separo memoria corta y larga, decido qué se guarda y por cuánto (datos personales), la aíslo por cliente y evalúo si recuperar mejora el resultado."

**"¿Cómo funciona un RAG?"**
- 🟢 "Busca en mis documentos y se los da al modelo para que responda con ellos."
- 🟡 "Parto los documentos en trozos, los convierto en embeddings, los guardo; en cada pregunta busco los k más cercanos y los pongo en el prompt."
- 🔴 "Cuido el chunking y que el embedding sea el mismo al indexar y al consultar, devuelvo fuentes, evalúo recuperación y respuesta por separado y trato los trozos como datos no confiables."

**"¿RAG o ajuste fino?"**
- 🟢 "RAG si hay documentos; ajuste fino para cambiar el estilo."
- 🟡 "RAG para datos que cambian y con fuente; ajuste fino para formato fijo en tareas estrechas."
- 🔴 "Prompt, luego RAG, ajuste fino al final: el reentrenamiento es caro, hay que repetirlo y no permite borrar datos."

## 🛠️ Ejercicios (taller)

| Taller | Qué haces |
|---|---|
| **W05** | **Memoria y estado** en Spring AI: historial por `conversationId`, repositorio JDBC y **reanudar** tras reiniciar la app |
| **W11a** 🧩 | **Embeddings y similitud** (~1 h): un `EmbeddingModel` **falso** con vectores escritos a mano; buscar los k más cercanos y comprobar que *reembolso* queda junto a *devolución* 🔎 |
| **W11b** 🧩 | **Chunking y RAG clásico** (~1 h): partir un texto con `TokenTextSplitter`, indexar, consultar y **evaluar** si recuperó el trozo esperado 🔎 |
| **W11** | **RAG agéntico**: pgvector o Qdrant como herramienta; el agente guarda y recupera datos propios |
| **Reflexión escrita** | ¿Qué recordaría y qué **no** recordaría un agente de tu banco? |

## 🔗 Relacionado

[03 · Contexto](03-prompts-contexto-y-salidas-estructuradas.md) · [05 · MCP](05-mcp.md) · [08 · LangGraph y checkpoints](08-frameworks-y-grafos.md) · [03 · Prompts y contexto](../conceptos/03-prompts-contexto-y-salidas-estructuradas.md) · [Plan de talleres](../talleres/README.md)
