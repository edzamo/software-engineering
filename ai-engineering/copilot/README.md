# Copilot como agente · apuntes del curso

Resúmenes **propios** (no son transcripciones) del curso [GitHub Copilot Masterclass: Java, Spring, IntelliJ, Quarkus, Jakarta EE](https://www.udemy.com/course/github-copilot-masterclass-java-spring-intellij-quarkus-jakartaee/) en Udemy. Se escriben de junior a senior, con la analogía de la cocina. El código de práctica vive en el [repo de ejercicios](https://github.com/edzamo/ai-engineering-labs).

## 🍳 Con la cocina
Copilot es un **ayudante de cocina** al que le das la receta del restaurante (instrucciones del repo), los utensilios (herramientas y MCP) y una comanda clara (el prompt). En modo *pregunta* te contesta; en modo *agente* **cocina solo**, prueba, corrige y vuelve a probar, mientras tú, como jefe de cocina, revisas el plato antes de que salga.

## 1 · Cómo trabajamos (flujo por clase)
1. Pegas las transcripciones del día en [`_pegar-aqui.md`](_pegar-aqui.md) (ignorado por git: no se sube).
2. Al final del día dices «procesa» y se genera `clases/NN-titulo.md` con la plantilla de abajo.
3. Se limpia el archivo de pegado y las transcripciones quedan archivadas en `transcripciones/` (también ignorada).
4. Cuando hay varias clases del mismo tema, se **agrupan** en un concepto (tabla §3).
5. Al terminar una sección, se hace el consolidado: un resumen más corto del bloque.

### Plantilla de cada clase (máximo ~1 pantalla)
```
# NN · Título de la clase
🍳 Con la cocina: una frase.
## Qué se vio  (3–6 viñetas, con tus palabras)
## Junior · Mid · Senior  (qué debe saber cada nivel)
## Hazlo tú  (un mini ejercicio; su código va al repo de ejercicios)
## Pregunta de entrevista  (frase junior → capa mid → cierre senior)
Concepto: [nombre del grupo en §3]
```

## 2 · Índice de clases
| # | Clase | Concepto | Estado |
|---|---|---|---|
| — | _(se llena al pasar cada transcripción)_ | — | ⏳ |

## 3 · Conceptos principales (se agrupan al avanzar)
| Concepto | Clases | Resumen |
|---|---|---|
| _(pendiente)_ | | |

## 4 · No tenemos licencia de Copilot: simulamos con Claude Code
Los mecanismos son casi los mismos; lo que se aprende (instrucciones, contexto, agentes, MCP, revisión) se transfiere. Equivalencias 🔎 (se confirman con lo que muestre cada clase):

| En Copilot | En Claude Code (lo que usamos) | Idea |
|---|---|---|
| Modo agente | Sesión normal con herramientas | El modelo edita, ejecuta y corrige en bucle |
| Modo Plan / Ask | Modo plan / preguntar sin editar | Pensar antes de tocar |
| Instrucciones del repo (`.github/copilot-instructions.md`) | `CLAUDE.md` | La receta del restaurante |
| Agentes personalizados | Subagentes (`.claude/agents/`) | Cocineros con un rol |
| Archivos de prompt / habilidades | Skills y comandos | Recetas reutilizables |
| MCP | MCP | Mismo estándar |
| CLI de Copilot | Claude Code en terminal | Agente fuera del IDE |

> **Límite honesto:** lo que dependa de la interfaz de Copilot en IntelliJ (atajos, menús, selección de modelo, cuotas) **no se puede practicar sin la licencia**. En esas clases solo se resume y se marca 🔎 «no probado». Comprobar si la consultora da licencia en el proyecto.

Relacionado: [guía de estudio](../guia-de-estudio.md) · [MCP](../conceptos/05-mcp.md) · [prompts y contexto](../conceptos/03-prompts-contexto-y-salidas-estructuradas.md) · [talleres](../talleres/README.md).
