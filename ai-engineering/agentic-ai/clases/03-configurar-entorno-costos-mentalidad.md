# Clase 03 · Semana 1, día 1 — Configurar el entorno: panorama, costos y mentalidad

> ⬅️ [02 · Mapa del curso](02-mapa-del-curso-seis-semanas.md) · ➡️ Siguiente: _pendiente_ (instrucciones por sistema operativo: Windows, Mac y Linux)
> 🗓️ **Semana 1 · Día 1 — "Make an Agentic Workflow"** · tipo: 🟠 preparación · Repo: <https://github.com/ed-donner/agents>
> **Qué vas a poder explicar al terminar:** los 5 pasos para dejar el entorno listo, cuánto cuesta realmente el curso, cómo usar alternativas gratuitas, y por qué un AI Engineer tiene que pensar también como científico de datos.

---

## 🍎 Con manzanas

Antes de abrir la **frutería** hay que **montar el local**: poner el mostrador, conectar el teléfono con los proveedores y guardar la llave de la caja.

- **El IDE (Cursor o similar)** es el mostrador donde trabajas.
- **Clonar el repo** es traer el cuaderno de recetas del curso a tu local.
- **uv** es el repartidor rápido que trae todos los ingredientes (paquetes de Python) sin que se pierda ninguno.
- **La clave API** es la **cuenta con el proveedor**: cada vez que llamas al modelo en la nube, te cobran una pequeña cantidad.
- **El archivo `.env`** es el **cajón con llave** donde guardas esa clave. No se deja a la vista en el mostrador.

> En palabras de la calle: **configurar el entorno es dejar el local listo y con las llaves en el cajón correcto antes de vender la primera manzana.**

---

## 1 · Los 5 pasos (iguales en Windows, Mac y Linux) [~13:00]

| Paso | Qué haces | 🍎 Con manzanas |
|---|---|---|
| 1 | Instalar **Cursor** (o un IDE similar). | Montar el mostrador |
| 2 | **Clonar** el repositorio de Git en tu máquina. | Traer el cuaderno de recetas |
| 3 | Usar **uv** para instalar todas las dependencias. | El repartidor trae los ingredientes |
| 4 | Configurar el **proveedor de modelos** (OpenAI, o Gemini, OpenRouter, etc.). | Abrir la cuenta con el proveedor |
| 5 | Crear el archivo **`.env`** con las claves y arrancar. | Guardar la llave en el cajón |

Las instrucciones paso a paso vienen en las **dos clases siguientes** (Windows y Mac). Linux: se siguen las del repo, o las de Mac, que son muy parecidas. Están enlazadas en el `README` del repo.

## 2 · Qué es **uv**

Un **gestor de paquetes de Python** muy rápido y confiable. Facilita que el entorno quede igual en todas las máquinas, sin pelearse con versiones.

> 🔎 La transcripción dice "lámpara UV": es un error de dictado, se refiere a la herramienta **uv**.

## 3 · Costos de las APIs: cuánto cuesta de verdad

Las APIs llaman a modelos que corren **en la nube**, y eso cuesta dinero. Pero el curso se puede hacer casi gratis.

| Opción | Costo | Comentario |
|---|---|---|
| **OpenAI** (la que usa el instructor) | **Menos de 5 USD** en total siguiendo el curso | Recomendada para tener resultados consistentes con los de las clases |
| **Anthropic (Claude)** | De pago | También funciona |
| **Gemini** | Tiene plan gratuito | |
| **OpenRouter** | Muchos modelos, gratuitos y baratos | |
| **DeepSeek** | Barato | |
| **Modelos locales con Ollama** | Gratis (corren en tu máquina) | |

- **Todo esto está en la Guía 9**, carpeta `guides/` del repo.
- **Se puede completar sin gastar nada.** Con modelos gratuitos, a veces los resultados son peores y toca experimentar más, dar pasos más pequeños y repetir.
- **El gasto grande es opcional:** en el proyecto final (*Equity Traders*) se pueden usar datos de mercado en tiempo real, que pueden costar hasta unos 200 USD. No es necesario.

> 🔎 La transcripción dice "DeepSeq" y "Olama": se refieren a **DeepSeek** y **Ollama**.

## 4 · Tres cosas a tener presentes

1. **Los recursos son tuyos:** página de recursos del curso (enlaces y videos), las **preguntas frecuentes**, y el repo de GitHub con las guías y los ejercicios. Los ejercicios se actualizan, así que **hay que traer siempre la última versión** y leer las instrucciones dentro de cada uno.
2. **Habrá frustración, y es parte del aprendizaje.** Resolver problemas enseña más que seguir el tutorial.
   - Causa muy común: **escribir mal el nombre de la variable en el `.env`**. Por ejemplo, la clave de OpenAI debe llamarse exactamente `OPENAI_API_KEY`. Un nombre mal escrito "no funciona".
   - Si te atoras: mensaje en Udemy, correo o la sección de preguntas frecuentes.
3. **Un AI Engineer es ingeniero de software y también científico de datos.** [~08:00]

> 🔎 La transcripción dice ".emv" y "clave de API abierta": se refieren a **`.env`** y **`OPENAI_API_KEY`**.

## 5 · La idea que más importa: pensar como científico de datos

El instructor lo dice sin rodeos: viniendo de ingeniería de software, **cuesta más** adoptar esa forma de pensar.

| | Ingeniería de software clásica | Trabajar con LLMs |
|---|---|---|
| Resultado | Predecible: mismo input, mismo output | **Estadístico**: puede dar respuestas distintas cada vez |
| Cuando falla | Hay un bug que se busca y se arregla | Puede ser un resultado "poco fiable" sin un bug claro |
| Cómo se avanza | Diseñar, implementar, probar | **Experimentar, refinar, repetir** |
| Sensación al inicio | Orden | "Chapucero", experimental |

> 🍎 Es como un empleado brillante pero **de humor variable**: algunos días responde perfecto y otros se despista. No lo arreglas con un `if`: pruebas, ajustas las instrucciones, vuelves a probar.

Esa variabilidad **es parte de su poder** (puede razonar casos nuevos) y **parte del riesgo**. Conecta con lo que verá el [roadmap en la Fase 5](../../README.md): evaluaciones (evals) y guardrails.

## 6 · Carpeta `guides/` del repo (leerla si eres nuevo)

| Guía | Para qué |
|---|---|
| Línea de comandos | Moverte entre carpetas, ver el directorio actual |
| Git vs GitHub | Conceptos básicos de ambos |
| API, endpoint | Qué es una API y un punto final |
| Variables de entorno | Qué son y por qué `.env` |
| Cuadernos (notebooks) | Cómo usarlos (se usarán mucho) |
| Depuración | Cómo resolver problemas |
| **Guía 9** | **Proveedores alternativos, gratuitos o baratos** (Gemini, OpenRouter…) |

El `README` del repo es lo primero que hay que leer: trae las instrucciones por sistema operativo y la sección de costos.

## 7 · Qué debe dominar cada nivel

| Nivel | Debe poder... |
|---|---|
| 🟢 **Junior** | Decir para qué sirve una clave API y por qué va en un `.env` y no en el código. Nombrar los 5 pasos de la configuración. |
| 🟡 **Mid** | Explicar qué hace uv, cómo cambiar de proveedor (OpenAI a Gemini u Ollama) y por qué una variable mal nombrada rompe todo. Estimar el costo de un proyecto con LLMs. |
| 🔴 **Senior** | Defender cuándo un modelo gratuito/local basta y cuándo no, controlar costos por proyecto, y explicar por qué la naturaleza **no determinista** de un LLM cambia cómo se prueba y se pone en producción (evals, repetibilidad). |

## 8 · Escalera de respuesta para entrevista

**"¿Cómo manejas las API keys y los costos cuando trabajas con LLMs?"**

- 🟢 *Frase junior:* "Las guardo en un archivo `.env` que no se sube a Git, y las leo como variables de entorno."
- 🟡 *Capa mid:* "Uso un gestor de paquetes (uv) para que el entorno sea reproducible y puedo cambiar de proveedor (OpenAI, Gemini, Ollama) con la misma estructura. Para aprender uso modelos baratos o locales."
- 🔴 *Cierre senior:* "Pongo límites de gasto y monitoreo por proyecto; uso modelos pequeños donde alcanza y reservo los grandes para lo difícil. Y como la salida es estadística, valido con evaluaciones repetibles, no con una sola prueba que 'se vio bien'."

**"¿Qué cambia al trabajar con LLMs frente al software tradicional?"**

- 🟢 "Las respuestas no siempre son iguales."
- 🟡 "No hay un resultado único; se experimenta y se itera sobre las instrucciones."
- 🔴 "Hay que diseñar para la variabilidad: validación de salidas, reintentos, evals y trazabilidad, porque no puedo asegurar el mismo output dos veces."

## 🔗 Relacionado

- [Índice del curso](../README.md) · [Clase 01 · Primer agente](01-primer-agente-n8n.md) · [Clase 02 · Mapa](02-mapa-del-curso-seis-semanas.md)
- [Roadmap AI Engineering · Fase 0](../../README.md) — Python, `.env`, Git, APIs REST (esta clase es esa fase en práctica).
- [Production Track](../../llm-production/README.md) — controlar costos y evaluar a escala.
