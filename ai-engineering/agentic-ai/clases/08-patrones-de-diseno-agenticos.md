# Clase 08 · Semana 1, día 2 — Los 5 patrones de workflow (y los agentes por contraste)

> ⬅️ [07 · ¿Qué es un agente?](07-que-es-un-agente-workflow-vs-agent.md) · ➡️ Siguiente: _pendiente_
> 🗓️ **Semana 1 · Día 2 — "Agents and Agentic Patterns"** · tipo: 🟠 teoría
> **Qué vas a poder explicar al terminar:** los 5 patrones de diseño de workflows con LLM, en qué se diferencian, cómo elegir entre ellos y dónde empieza un agente.
> ✅ Fuente: **diapositivas + explicación hablada** del instructor. Cada patrón vive en el [catálogo](../../patrones/README.md), con dibujo, caso de uso e implementación.

---

## 🍎 Con manzanas

Cinco **recetas de cocina** para organizar el trabajo de varios empleados (los LLM) en la frutería:

1. **Cadena de montaje:** lavar → revisar → empacar.
2. **Recepcionista:** manda a cada cliente al mostrador correcto.
3. **Equipo en paralelo:** tres empleados hacen tres tareas a la vez.
4. **Jefe que reparte:** decide en el momento qué piezas hacen falta.
5. **Control de calidad:** uno escribe, otro revisa, vuelve si no pasa.

Y una sexta cosa, que **no es receta**: el **empleado libre** (agente), que decide el camino mientras anda.

> En palabras de la calle: **un patrón es una forma probada de conectar varias llamadas a un LLM, como una receta que ya se sabe que funciona.**

---

## ⚠️ Advertencia del instructor: son ideas, no reglas

- Los patrones vienen del artículo **"Building Effective Agents"** de Anthropic: **cinco para workflows y uno para agentes** (lectura recomendada).
- Su objetivo es **hacerte pensar** cómo abordar un problema, **no son inamovibles**.
- En **ingeniería de software** hay patrones consolidados ("uso este patrón para este problema"). **Aquí no funciona así**: el campo es más joven. Verás **elementos de varios patrones mezclados** en sistemas reales.
- **No memorices los nombres** ("evaluador", "optimizador"): lo importante es captar la idea. Los iremos construyendo y el instructor los señalará en las próximas semanas.

> 🍎 Como las recetas: no son leyes; el cocinero las **mezcla y ajusta**. Esto conecta con los [patrones de diseño clásicos](../../../design-pattern) del repo, pero con una diferencia: allí hay catálogo fijo, aquí son **guías de pensamiento**.

## 1 · Los 5 patrones de un vistazo

| # | Patrón | La idea (diapositiva) | Quién decide el camino | 🍎 | Documento |
|---|---|---|---|---|---|
| 1 | **Prompt chaining** | Decompose into fixed sub-tasks | Tú (pasos fijos, con puerta intermedia) | Cadena de montaje | [📄](../../patrones/01-prompt-chaining/README.md) |
| 2 | **Routing** | Direct an input into a specialized sub-task | Un LLM clasifica; **una** ruta | Recepcionista | [📄](../../patrones/02-routing/README.md) |
| 3 | **Parallelization** | Breaking down tasks and running subtasks concurrently | Tú (ramas fijas, **todas** a la vez) | Equipo en paralelo | [📄](../../patrones/03-parallelization/README.md) |
| 4 | **Orchestrator-worker** | Complex tasks broken down dynamically and combined | Un LLM decide **qué** subtareas | Jefe que reparte | [📄](../../patrones/04-orchestrator-worker/README.md) |
| 5 | **Evaluator-optimizer** | LLM output is validated by another | Ciclo generar → evaluar → corregir | Control de calidad | [📄](../../patrones/05-evaluator-optimizer/README.md) |
| — | **Agentes** (por contraste) | Open-ended · feedback loops · no fixed path | El LLM, paso a paso | Empleado libre | [📄](../../patrones/06-agente/README.md) |

## 2 · Dibujos, diferencias y guía de decisión → viven en el catálogo

Para no duplicar, los **dibujos de los cinco patrones**, **cómo se diferencian** y la **guía "¿cuál elijo?"** están en el [**catálogo de patrones**](../../patrones/README.md), junto con la implementación de cada uno.

## 3 · Qué debe dominar cada nivel

| Nivel | Debe poder... |
|---|---|
| 🟢 **Junior** | Nombrar los 5 patrones y explicar cada uno con su analogía de la frutería. |
| 🟡 **Mid** | Dibujar cada patrón, decir qué parte es LLM y cuál es código, y distinguir los pares que se confunden (routing/paralelización, paralelización/orchestrator). |
| 🔴 **Senior** | Elegir y **combinar** patrones por costo, latencia y testeabilidad; saber cuándo basta un prompt; y justificar cuándo cruzar a un agente con sus controles. |

## 4 · Escalera de entrevista

**"Cuéntame qué patrones conoces para orquestar varios LLM."**

- 🟢 *Frase junior:* "Cadena de prompts, enrutamiento, paralelización, orquestador con trabajadores y evaluador-optimizador."
- 🟡 *Capa mid:* "La cadena avanza por pasos fijos con validación; el router elige una ruta; la paralelización corre ramas independientes; el orquestador decide las subtareas dinámicamente; y el evaluador valida y devuelve feedback en un ciclo."
- 🔴 *Cierre senior:* "Empiezo por el más simple que resuelva el problema, porque cada patrón agrega costo y puntos de falla. Los combino según el caso y solo paso a un agente abierto cuando no puedo definir el camino, con topes de iteración, permisos mínimos y trazabilidad."

## 🔗 Relacionado

- Dato extra: el instructor dice que se suele llamar **"LLM como juez"** al patrón 5.
- Cada patrón: [1](../../patrones/01-prompt-chaining/README.md) · [2](../../patrones/02-routing/README.md) · [3](../../patrones/03-parallelization/README.md) · [4](../../patrones/04-orchestrator-worker/README.md) · [5](../../patrones/05-evaluator-optimizer/README.md) · [Agentes](../../patrones/06-agente/README.md)
- [Clase 07 · workflow vs agente](07-que-es-un-agente-workflow-vs-agent.md) · [Clase 06 · primeras llamadas](06-primeras-llamadas-openai-encadenar.md) · [Índice](../README.md)
- [Roadmap AI Engineering · Fases 3 y 4](../../README.md) — ReAct, orquestación y evals.
