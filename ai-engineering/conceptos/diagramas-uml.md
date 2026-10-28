# Diagramas UML de AI Engineering

> Una sola página con **los diagramas que vale la pena tener** del curso: clases, secuencia, estados, componentes y despliegue. Están hechos con Mermaid (se ven en GitHub y en el IDE).
> ⬅️ [Índice del curso](README.md) · Temas: [01 Agentes](01-agentes-y-workflows.md) · [04 Herramientas](04-herramientas-y-bucle-del-agente.md) · [05 MCP](05-mcp.md) · [07 Orquestación](07-orquestacion-y-multiagente.md) · [08 LangGraph](08-frameworks-y-grafos.md) · [09 Seguridad](09-seguridad-guardrails-y-humano-en-el-bucle.md) · [12 Proyectos](../talleres/README.md#11b--lecciones-de-los-proyectos-del-curso-qué-copiar-y-qué-dejar)

## 🍳 Con la cocina

Los diagramas son **el plano de la cocina**: no cocinan, pero te dicen **dónde está cada estación, quién habla con quién y en qué orden**. Si tu cocina cambia de marca (otro framework), el plano casi no cambia.

> En palabras de la calle: **el UML dibuja las piezas que se repiten en todos los marcos; los nombres cambian, el plano no.**

## Qué diagrama usar para qué

| Diagrama | Responde a | Dónde |
|---|---|---|
| **Clases** | ¿Qué piezas hay y cómo se relacionan? | §1, §3, §6 |
| **Secuencia** | ¿En qué orden se hablan? | §2, §4, §7 |
| **Estados** | ¿En qué estados puede estar un agente o un hilo? | §5 |
| **Componentes** | ¿Qué procesos y servicios hay? | §3, §8 |
| **Despliegue** | ¿Dónde corre cada cosa y cómo se conecta? | §8 |

Las etiquetas 🧩 son mías; lo demás sale de los labs del curso y del código de mi fork.

---

## 1 · El agente (válido para los 9 marcos)

```mermaid
classDiagram
    class Agente {
        +modelo
        +instrucciones
        +herramientas[]
        +memoria
        +guardrails[]
        +salidaEstructurada
    }
    class Ejecutor {
        +ejecutar(agente, entrada) Resultado
        +maxIteraciones
    }
    class Herramienta {
        +nombre
        +descripcion
        +esquemaJSON
        +ejecutar(args) resultado
    }
    class Modelo {
        +generar(mensajes, herramientas) Respuesta
    }
    class Memoria {
        +leer(hiloId) mensajes[]
        +guardar(hiloId, mensajes)
    }
    class Guardrail {
        +validar(texto) bool
    }
    class Resultado {
        +textoFinal
        +objetoEstructurado
        +traza
    }
    Agente "1" o-- "0..*" Herramienta
    Agente "1" --> "1" Modelo
    Agente "1" o-- "0..1" Memoria
    Agente "1" o-- "0..*" Guardrail
    Ejecutor ..> Agente : usa
    Ejecutor ..> Resultado : devuelve
    Modelo ..> Herramienta : pide ejecutar
```

| Pieza | Agents SDK | CrewAI | LangChain | ADK | Pydantic AI |
|---|---|---|---|---|---|
| Agente | `Agent` | `Agent` (+ `Task`) | `create_agent` | `LlmAgent` | `Agent` |
| Ejecutor | `Runner.run` | `Crew.kickoff` | `agent.invoke` | `InMemoryRunner` | `agent.run` |
| Memoria | sesiones | `memory=True` | `checkpointer` | sesión | historial |
| Guardrail | `guardrails` | — | *middleware* | callbacks | validadores |

## 2 · El bucle del agente (secuencia)

```mermaid
sequenceDiagram
    participant U as Usuario
    participant E as Ejecutor (tu código)
    participant M as Modelo (LLM)
    participant H as Herramienta
    U->>E: objetivo
    loop hasta que el modelo no pida herramientas (o tope de iteraciones)
        E->>M: mensajes + descripción de herramientas
        M-->>E: texto  o  tool_calls[nombre, args]
        alt pide herramienta
            E->>E: validar nombre y argumentos (allowlist)
            E->>H: ejecutar(args)
            H-->>E: resultado
            Note over E: añade el resultado a los mensajes
        end
    end
    E-->>U: respuesta final
```

> 🧩 **Un LLM solo genera tokens; el agente es tu código que interpreta esos tokens.** Allowlist, validación de argumentos y tope de iteraciones viven en `E`, no en el modelo.

---

## 3 · MCP (clases y componentes)

```mermaid
classDiagram
    class HostMCP {
        +agente
        +crearCliente(parametros)
    }
    class ClienteMCP {
        +transporte
        +listarHerramientas()
        +llamarHerramienta(nombre, args)
    }
    class ServidorMCP {
        +herramientas[]
        +recursos[]
        +prompts[]
        +tools_list()
        +tools_call()
    }
    class Transporte {
        <<interface>>
    }
    class Stdio {
        +comando: uvx / npx / docker
        +proceso hijo
    }
    class HttpStreamable {
        +url
    }
    HostMCP "1" *-- "1..*" ClienteMCP : un cliente por servidor
    ClienteMCP "1" --> "1" ServidorMCP : se conecta
    ClienteMCP --> Transporte
    Transporte <|.. Stdio
    Transporte <|.. HttpStreamable
    ServidorMCP ..> APIExterna : suele envolver
    class APIExterna {
        <<externo>>
    }
```

```mermaid
graph LR
    subgraph Host["Host MCP"]
        A["Agente + LLM"] --> C1["Cliente 1"]
        A --> C2["Cliente 2"]
    end
    C1 -- "stdio · uvx" --> S1["fetch (Python)"]
    C2 -- "stdio · npx" --> S2["playwright (Node)"]
    A -. "HTTP streamable" .-> S3["Context7 (remoto)"]
```

## 4 · Aprobación humana (secuencia, semana 4)

```mermaid
sequenceDiagram
    participant U as Usuario
    participant A as Agente (create_agent)
    participant HITL as HumanInTheLoopMiddleware
    participant CP as Checkpointer (thread_id)
    participant T as Herramienta sensible
    U->>A: tarea
    A->>HITL: intenta llamar a la herramienta
    HITL->>CP: guarda el estado
    HITL-->>U: INTERRUMPIDO: ¿apruebas?
    alt approve
        U->>A: Command(resume=approve)
        A->>CP: restaura el estado
        A->>T: ejecuta
    else edit
        U->>A: Command(resume=edit, args nuevos)
        A->>T: ejecuta con otros argumentos
    else reject
        U->>A: Command(resume=reject, motivo)
        A-->>A: vuelve al modelo con el motivo
    end
    A-->>U: resultado
```

🧩 **Para un pago:** el agente **propone**, una persona **autoriza** y todo queda en el estado persistido.

## 5 · Estados de una ejecución (LangGraph / agente con pausa)

```mermaid
stateDiagram-v2
    [*] --> Creada
    Creada --> Ejecutando : invoke
    Ejecutando --> Esperando_herramienta : tool_calls
    Esperando_herramienta --> Ejecutando : resultado
    Ejecutando --> Interrumpida : herramienta sensible
    Interrumpida --> Ejecutando : approve / edit
    Interrumpida --> Ejecutando : reject (con motivo)
    Ejecutando --> Evaluando : respuesta candidata
    Evaluando --> Ejecutando : no cumple (reintento ≤ 3)
    Evaluando --> Terminada : cumple criterios
    Ejecutando --> Fallida : tope de llamadas o error
    Interrumpida --> Fallida : sin respuesta
    Terminada --> [*]
    Fallida --> [*]
```

Cada transición puede guardarse como **checkpoint** (un `thread_id` + `checkpoint_id`): se puede **rebobinar** y reanudar desde cualquier punto.

---

## 6 · Sidekick (semana 4) y sala de operaciones (semana 6)

### Sidekick: trabajador + evaluador

```mermaid
classDiagram
    class Sidekick {
        +worker: Agente
        +evaluator: LLM
        +setup()
        +run_turn(mensaje, criterios)
        +resume()
    }
    class Trabajador {
        +herramientas[41]
        +middleware[]
    }
    class Middleware {
        <<interface>>
    }
    class TolerateToolErrors
    class TodoListMiddleware
    class PIIMiddleware
    class ModelCallLimitMiddleware
    class HumanInTheLoopMiddleware
    class Evaluador {
        +evaluar(peticion, criterios, respuesta) EvaluatorOutput
    }
    class EvaluatorOutput {
        +criteriosCumplidos
        +necesitaMasInformacion
        +comentarios
    }
    Sidekick *-- Trabajador
    Sidekick *-- Evaluador
    Trabajador o-- Middleware
    Middleware <|.. TolerateToolErrors
    Middleware <|.. TodoListMiddleware
    Middleware <|.. PIIMiddleware
    Middleware <|.. ModelCallLimitMiddleware
    Middleware <|.. HumanInTheLoopMiddleware
    Evaluador ..> EvaluatorOutput
```

### Sala de operaciones

```mermaid
classDiagram
    class TradingFloor {
        +RUN_EVERY_N_MINUTES
        +RUN_EVEN_WHEN_MARKET_IS_CLOSED
        +USE_MANY_MODELS
        +run_every_n_minutes()
    }
    class Trader {
        +name
        +model_name
        +create_agent()
        +run()
    }
    class Researcher {
        +como_herramienta()
    }
    class ServidorMCP {
        <<MCP>>
    }
    class AccountsServer
    class PushServer
    class MarketServer
    class MemoryServer
    class Tavily
    class Fetch
    class Account {
        +balance
        +holdings
        +strategy
        +operar()
    }
    class LogTracer {
        +on_trace_start() on_trace_end()
        +on_span_start() on_span_end()
    }
    TradingFloor "1" *-- "4" Trader : asyncio.gather
    Trader "1" *-- "1" Researcher : as_tool
    Trader ..> AccountsServer
    Trader ..> PushServer
    Trader ..> MarketServer
    Researcher ..> MemoryServer
    Researcher ..> Tavily
    Researcher ..> Fetch
    ServidorMCP <|-- AccountsServer
    ServidorMCP <|-- PushServer
    ServidorMCP <|-- MarketServer
    ServidorMCP <|-- MemoryServer
    ServidorMCP <|-- Tavily
    ServidorMCP <|-- Fetch
    AccountsServer ..> Account
    TradingFloor ..> LogTracer : add_trace_processor
```

> Nombres tomados de `backend/` de mi fork (`traders.py`, `trading_floor.py`, `mcp_servers.py`, `tracers.py`).

## 7 · Orquestación: código vs. LLM (secuencia)

```mermaid
sequenceDiagram
    participant C as Código (asyncio.gather)
    participant T as Trader (LLM)
    participant R as Investigador (agente como herramienta)
    participant S as Servidores MCP
    C->>T: ejecutar ronda (orden fijo, por código)
    T->>T: decide qué operar
    opt el trader decide investigar (autonomía del LLM)
        T->>R: "investiga X"
        R->>S: Tavily · Fetch · Memoria
        S-->>R: datos
        R-->>T: resumen
    end
    T->>S: cuentas · mercado · push
    S-->>T: confirmación
    T-->>C: ronda terminada
```

**Regla del instructor:** lo que tiene **orden fijo** va en **código**; lo **incierto** va en el **LLM**.

---

## 8 · Despliegue y componentes (agente con MCP)

```mermaid
graph TD
    subgraph Equipo["💻 Máquina del desarrollador"]
        APP["Aplicación del agente<br/>(host MCP)"]
        subgraph Procesos["Procesos hijos (stdio)"]
            S1["uvx mcp-server-fetch"]
            S2["npx @playwright/mcp"]
            S3["uv run accounts_server.py"]
        end
        DB[("SQLite: tablero / memoria / trazas")]
        TRC["Panel local: adk web · Mastra Studio"]
    end
    subgraph Nube["☁️ Internet"]
        LLM["API del LLM (OpenAI · Gemini · …)"]
        API["APIs de terceros (Tavily, Massive, Serper)"]
        R["Servidores MCP remotos (Context7)"]
        LS["Plataformas de trazas (LangSmith · OpenAI · CrewAI)"]
    end
    APP --> S1 & S2 & S3
    APP --> DB
    APP -- HTTPS --> LLM
    S1 & S2 -- HTTPS --> API
    APP -- "HTTP streamable" --> R
    APP -. "trazas" .-> LS
    APP -. "trazas locales" .-> TRC
```

⚠️ **Lo que sale de tu red:** prompts y respuestas al **LLM**; datos a las **APIs de terceros**; y las **trazas** si usas una plataforma remota. Los paneles locales (`adk web`, Studio) **no suben nada**.

---

## 9 · Para tus PoC en Java (🧩 propuesta, no del curso)

Mismo plano con las piezas de Spring AI o Quarkus LangChain4j 🔎 (nombres a verificar al elegir framework):

```mermaid
classDiagram
    class OrquestadorServicio {
        +ejecutar(solicitud) Resultado
    }
    class AgenteGerente {
        +herramientas: AgenteComoHerramienta[]
    }
    class AgenteComoHerramienta {
        +descripcion
        +invocar(args)
    }
    class ClienteMCP
    class Guardrail
    class Evaluador
    class RepositorioEstado {
        <<interface>>
        +guardar(hiloId, estado)
        +cargar(hiloId)
    }
    OrquestadorServicio --> AgenteGerente
    AgenteGerente o-- AgenteComoHerramienta
    AgenteGerente ..> ClienteMCP
    OrquestadorServicio ..> Guardrail
    OrquestadorServicio ..> Evaluador
    OrquestadorServicio ..> RepositorioEstado
```

| PoC | Diagrama que lo describe |
|---|---|
| **PoC1** cadena con control (pago) | §2 con un estado persistido y aprobación humana (§4) |
| **PoC2** paralelización con selector (revisión de código) | §6 (sala de operaciones): coordinador por código + varios agentes |
| **PoC3** gerente con agentes-herramienta (reunión → funcionalidades → tareas) | §7: código orquesta lo fijo, el LLM decide lo incierto |

## Qué debe dominar cada nivel

| Nivel | Debe poder... |
|---|---|
| 🟢 **Junior** | Leer el diagrama del bucle y nombrar cada participante. |
| 🟡 **Mid** | Dibujar el diagrama de secuencia de un agente con herramienta y aprobación humana. |
| 🔴 **Senior** | Dibujar el **mapa de amenazas** y el **diagrama de estados** de un agente propio y ubicar dónde van los controles. |

## Escalera de entrevista

**"Dibuja la arquitectura de un agente con herramientas."**
- 🟢 "Usuario, modelo y herramienta en un bucle."
- 🟡 "Ejecutor, modelo y herramientas con validación; memoria por hilo y trazas."
- 🔴 "Además: lista blanca, tope de iteraciones, aprobación humana con estado persistido, aislamiento de código y evaluador."

## 🛠️ Ejercicios

Redibuja en papel (sin mirar) los diagramas §2, §4 y §5 para **un flujo de tu trabajo** y compáralos con los de esta página.

## 🔗 Relacionado

[Catálogo de patrones](../patrones/README.md) · [03 · Prompts y contexto](../conceptos/03-prompts-contexto-y-salidas-estructuradas.md) · [05 · MCP](05-mcp.md) · [08 · Frameworks y grafos](08-frameworks-y-grafos.md)
