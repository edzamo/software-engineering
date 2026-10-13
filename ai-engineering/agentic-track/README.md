# AI Engineer — Agentic Track (curso)

Apuntes del curso de Udemy **AI Engineer Agentic Track: The Complete Agent & MCP Course** (curso 5 de 6 del currículo "The Complete AI Curriculum"). **Es el primer curso a tomar.** Cada clase se convierte en un documento de esta carpeta, escrito **de junior a senior** y vinculado al resto del repo.

> **Repo del curso:** <https://github.com/ed-donner/agents> · **Instructor:** Ed Donner.
> **Dónde encaja:** profundiza las Fases 3 y 4 del roadmap [`ai-engineering/`](../README.md) (herramientas, MCP, agentes autónomos, multi-agente).

## 🧪 Dónde se practica (y dónde se documenta)

| | Este repo (`software-engineering`) | Repo de práctica (`agents`, fork propio) |
|---|---|---|
| **Qué contiene** | Solo **documentación**: las clases explicadas "con manzanas", de junior a senior | Los **ejercicios y el código** de cada semana |
| **Ruta local** | `ai-engineering/agentic-track/` | `~/Documents/Personal/github/agents` |
| **Remoto** | Este repo | [`edzamo/agents`](https://github.com/edzamo/agents) (fork de [`ed-donner/agents`](https://github.com/ed-donner/agents)) |
| **Cuándo se sube** | Al procesar cada clase | Al final de cada día de práctica |

> 🔐 En el fork, el `.env` con la clave API está en el `.gitignore`: **nunca se sube**. Antes de cada push conviene revisar con `git status` que no aparezca. Detalle en la [clase 05](clases/05-openai-api-key-env-notebook.md#3--el-archivo-env-donde-más-gente-se-atora).

## 🗺️ El currículo completo (y dónde estamos)

| # | Curso | Temas | Decisión |
|---|---|---|---|
| 1 | AI Builder | Agentes, voice agents, n8n, ElevenLabs | ⏭️ Ya dominado |
| 2 | AI Coder | Claude Code, Copilot, Codex, Antigravity | ⏭️ Cubierto en [`claude/`](../claude/README.md) |
| 3 | AI Leader | AI delivery, transformación, negocio con IA | ⏭️ No es prioridad |
| 4 | AI Engineer · Core Track | LLMs, open-source, RAG, LangChain, fine-tuning | ❌ Muy básico para el nivel actual; se pide reembolso |
| **5** | **AI Engineer · Agentic Track** | Agent loops, OpenAI Agents SDK, CrewAI, LangGraph, MCP | ✅ **Este curso (1º)** |
| 6 | AI Engineer · Production Track | Vercel, AWS/Bedrock, GCP, Azure, LangFuse | 🔜 2º curso → [`production-track/`](../production-track/README.md) |

> 🔎 Si el Core Track se retoma, RAG y fine-tuning siguen cubiertos en la Fase 2 del [roadmap](../README.md#4--roadmap--7-fases-14-semanas).

## 🍎 Con manzanas (la idea completa)

> _Se completa con la primera clase: aquí va la analogía de la frutería que resume el curso en 4 líneas._

## 📚 Índice de clases (por semana, como lo organiza el instructor)

> **6 semanas · 5 días por semana · 8 proyectos.** 🟠 teoría · 🔵 framework · 🟡 proyecto. Las clases se numeran seguidas (01, 02…) y cada una indica su semana y día. Detalle completo en la [clase 02](clases/02-mapa-del-curso-seis-semanas.md).

| Semana | Tema | Los 5 días | Clases | Estado |
|---|---|---|---|---|
| 1 | 🟠 Fundamentos | Agentic Workflow · Agents and Agentic Patterns · Orchestrating LLMs · Autonomy and Tools · 🟡 **P1** Personal career agent | [01 · Primer agente (n8n)](clases/01-primer-agente-n8n.md) · [02 · Mapa del curso](clases/02-mapa-del-curso-seis-semanas.md) · [03 · Configurar el entorno](clases/03-configurar-entorno-costos-mentalidad.md) · [04 · Setup Windows/Mac (guía rápida)](clases/04-setup-windows-mac-guia-rapida.md) · [05 · Clave OpenAI, `.env` y notebook](clases/05-openai-api-key-env-notebook.md) · [06 · Primeras llamadas a OpenAI](clases/06-primeras-llamadas-openai-encadenar.md) · [07 · ¿Qué es un agente? (día 2)](clases/07-que-es-un-agente-workflow-vs-agent.md) | 🟡 En curso (día 2) |
| 2 | 🔵 OpenAI Agents SDK | Conceptos · 🟡 **P2** SDR · Tools vs Agents y Guardrails · 🟡 **P3** Deep Research (2 días) | _pendiente_ | ⏳ |
| 3 | 🔵 CrewAI | Conceptos · Build a Crew agent · 🟡 **P4** Stock Picker · 🟡 **P5** Developer Agent y Engineering Team | | ⏳ |
| 4 | 🔵 LangGraph | Conceptos · Build a LangGraph agent · Tools, memory, web searches · 🟡 **P6** Sidekick (2 días) | | ⏳ |
| 5 | 🔵 Otros frameworks | Google ADK + A2A · AWS Strands · Otros Python · JS/TS · 🟡 **P7** Agent Loop | | ⏳ |
| 6 | 🟠 MCP | Arquitectura y MCP · Server y Client · Servers locales y remotos · 🟡 **P8** AI Equity Traders (2 días) | | ⏳ |

> Tabla completa día por día en la [clase 02](clases/02-mapa-del-curso-seis-semanas.md#21--día-por-día-temario-oficial-de-las-capturas-del-curso).

> ➕ **Para agregar una clase:** pega la transcripción en [`clases/_pegar-clase-aqui.md`](clases/_pegar-clase-aqui.md), indica semana y número, y pide "procesa la clase".

## 🧭 Vista por concepto (independiente de la semana)

| Concepto | Clases donde aparece |
|---|---|
| Agentes y agent loop | [01](clases/01-primer-agente-n8n.md) · [07](clases/07-que-es-un-agente-workflow-vs-agent.md) |
| Workflow vs agente | [07](clases/07-que-es-un-agente-workflow-vs-agent.md#4--workflow-vs-agente-según-anthropic) |
| Llamar a un LLM y encadenar llamadas | [06](clases/06-primeras-llamadas-openai-encadenar.md) |
| Entorno, uv, API keys y `.env` | [03](clases/03-configurar-entorno-costos-mentalidad.md) · [04](clases/04-setup-windows-mac-guia-rapida.md) · [05](clases/05-openai-api-key-env-notebook.md) |
| Manejo de secretos y claves | [05](clases/05-openai-api-key-env-notebook.md#3--el-archivo-env-donde-más-gente-se-atora) |
| Notebooks, celdas y kernel | [05](clases/05-openai-api-key-env-notebook.md#4--primer-notebook-kernel-celdas-y-orden) |
| Costos de APIs y modelos gratuitos/locales | [03](clases/03-configurar-entorno-costos-mentalidad.md#3--costos-de-las-apis-cuánto-cuesta-de-verdad) |
| Naturaleza no determinista de los LLM | [03](clases/03-configurar-entorno-costos-mentalidad.md#5--la-idea-que-más-importa-pensar-como-científico-de-datos) |
| Herramientas / function calling | [01](clases/01-primer-agente-n8n.md) (versión low-code) |
| MCP | [02](clases/02-mapa-del-curso-seis-semanas.md) (plan; se introduce poco a poco) |
| Multi-agente y orquestación | [02](clases/02-mapa-del-curso-seis-semanas.md) (plan: CrewAI, LangGraph) |

## 📖 Glosario rápido

| Término | En una frase | 🍎 Con manzanas | Clase |
|---|---|---|---|
| **Agente de IA** | LLM en un bucle con herramientas para lograr un objetivo. | Empleado con objetivo, teléfono, cuaderno y lápiz | [01](clases/01-primer-agente-n8n.md#1--la-definición-que-usa-todo-el-curso) |
| **Herramienta (tool)** | Acción que el LLM pide y el sistema ejecuta. | El teléfono / el lápiz | [01](clases/01-primer-agente-n8n.md#4--cómo-se-le-da-una-herramienta-a-un-llm-sin-magia-todavía) |
| **Memoria** | Recordar lo dicho antes en la conversación. | Cuaderno de la charla | [01](clases/01-primer-agente-n8n.md#2--las-tres-piezas-que-pide-un-agente-en-n8n) |
| **Autonomía** | Que el agente decida qué herramientas usar y en qué orden. | Equilibra el inventario sin que le dictes los pasos | [01](clases/01-primer-agente-n8n.md#3--la-demo-en-tres-escalones) |
| **uv** | Gestor de paquetes de Python, rápido y confiable. | El repartidor que trae los ingredientes | [03](clases/03-configurar-entorno-costos-mentalidad.md#2--qué-es-uv) |
| **API key / `.env`** | Clave de acceso al proveedor; se guarda en un archivo fuera del código. | La llave de la cuenta, en el cajón con llave | [03](clases/03-configurar-entorno-costos-mentalidad.md#4--tres-cosas-a-tener-presentes) |
| **Mensaje (`messages`)** | Lista de diccionarios con `role` y `content` que se envía al LLM. | La libreta de pedidos | [06](clases/06-primeras-llamadas-openai-encadenar.md#1--la-forma-de-un-mensaje) |
| **Encadenar llamadas** | La salida de una llamada es la entrada de la siguiente. | Cadena de montaje de frutas | [06](clases/06-primeras-llamadas-openai-encadenar.md#3--encadenar-tres-llamadas) |
| **Workflow** | Llamadas a LLM por rutas predefinidas y probables de antemano. | Cadena de montaje con estaciones fijas | [07](clases/07-que-es-un-agente-workflow-vs-agent.md#4--workflow-vs-agente-según-anthropic) |
| **Agente (definición Willison)** | LLM que usa herramientas en bucle para lograr un objetivo; el LLM decide los pasos. | Empleado libre que resuelve el pedido | [07](clases/07-que-es-un-agente-workflow-vs-agent.md#1--las-tres-definiciones-de-la-más-vieja-a-la-más-usada) |
| **LLM = generador de tokens** | El LLM solo predice texto; el agente es el código que lo interpreta. | El empleado escribe notas, tu código las ejecuta | [07](clases/07-que-es-un-agente-workflow-vs-agent.md#2--la-idea-que-más-pesa-el-llm-solo-genera-tokens) |
| **API de OpenAI vs ChatGPT** | La API es para programar y se cobra por uso; ChatGPT es el producto final. | Cuenta de proveedor mayorista vs. comprar en el mostrador | [05](clases/05-openai-api-key-env-notebook.md#1--api-de-openai--chatgpt) |
| **Kernel** | Proceso de Python que ejecuta las celdas de un notebook. | El cocinero que sigue la receta | [05](clases/05-openai-api-key-env-notebook.md#4--primer-notebook-kernel-celdas-y-orden) |
| **Entorno virtual (`.venv`)** | Carpeta aislada con las dependencias de un proyecto. | Caja cerrada de ingredientes por proyecto | [04](clases/04-setup-windows-mac-guia-rapida.md#3--uv-sync-el-comando-importante) |
| **No determinismo** | El LLM puede responder distinto cada vez. | Empleado brillante de humor variable | [03](clases/03-configurar-entorno-costos-mentalidad.md#5--la-idea-que-más-importa-pensar-como-científico-de-datos) |
| **Low-code / no-code** | Armar flujos arrastrando y soltando (n8n). | Armar el local con piezas prefabricadas | [01](clases/01-primer-agente-n8n.md#7--low-code-vs-código) |

## 🔗 Relacionado

- [Roadmap AI Engineering](../README.md) — plan de 14 semanas / 7 fases.
- [Curso Claude](../claude/README.md) — LLM vs agente, harness, MCP.
- [`ia-agentes/`](../../ia-agentes) — subagentes aplicados a este repo.
- [Glosario de la frutería](../../messaging-streaming/glosario.md) — la analogía base del repo.
