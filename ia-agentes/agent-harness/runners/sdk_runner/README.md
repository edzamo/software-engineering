# runners/sdk_runner/ — reservado, no implementado (a propósito)

Esta carpeta existe en la estructura para dejar explícito **dónde iría** un segundo runner si algún día hace falta correr estos mismos agentes fuera de Claude Code (por ejemplo, un pipeline de CI que invoque el Anthropic SDK directo, sin la CLI de Claude Code disponible). Hoy no hay nada acá porque no hay un caso de uso real que lo necesite — YAGNI aplicado a la propia infraestructura de agentes (ver [`clean-code/dry-kiss-yagni.md`](../../../../clean-code/dry-kiss-yagni.md)).

## Por qué no un `loop.py` propio

Un runner por SDK **no necesita** un bucle de ejecución (`loop.py`) escrito desde cero. El Agent SDK / API de Anthropic ya trae:

- El **Tool Runner** (`client.beta.messages.tool_runner`) — un loop agéntico ya resuelto: manda el mensaje, ejecuta las tool calls, vuelve a mandar el resultado, repite hasta que termina.
- Soporte nativo para definir tools (esquema JSON) y ejecutarlas — el mismo concepto que `tools:` en `agent.yaml`, pero en formato que el SDK espera directamente.

Escribir un `loop.py` propio reinventaría esto — exactamente el tipo de sobre-ingeniería que señalamos en la propia carpeta `tool-integrations/`.

## Si en algún momento hace falta implementarlo

La forma de hacerlo sin duplicar la fuente de los agentes:

1. Un script chico (`main.py`) que recorra `agent-harness/agents/`, lea `agent.yaml` + `instructions.md` con las mismas funciones de `runners/claude_code/compile.py` (`parse_agent_yaml`, y leer `instructions.md` como texto), y arme el `system` prompt para el mensaje al SDK.
2. Invocar `client.beta.messages.tool_runner(...)` pasándole ese `system` prompt y las tools definidas en `agent.yaml` mapeadas al formato de tool del SDK.
3. **No** reimplementar el parser de `agent.yaml` — importarlo desde `runners/claude_code/compile.py` (mismo patrón que ya usa `agent-harness/tests/test_agents_structure.py`).

Hasta que ese caso de uso exista de verdad, esta carpeta queda documentada pero vacía.
