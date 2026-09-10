# runners/claude_code/ — el único runner implementado hoy

Este es el adaptador real y funcional: toma la fuente canónica de cada agente (`agent-harness/agents/<nombre>/agent.yaml` + `instructions.md`) y la compila al único formato que Claude Code sabe leer — un archivo `.claude/agents/<nombre>.md` con frontmatter YAML + system prompt en el mismo archivo.

## Uso

```bash
# desde agent-harness/runners/claude_code/
python3 compile.py
```

Sin dependencias — no requiere `pip install` nada, corre con el Python que ya tengas instalado (3.9+). Esto es intencional: la idea de todo `agent-harness/` es que se pueda copiar a un proyecto nuevo con un `cp -r` y usar de inmediato, sin paso de instalación.

## Qué hace exactamente `compile.py`

1. Recorre cada carpeta en `agent-harness/agents/`.
2. Lee `agent.yaml` (parser propio, minimalista — ver docstring del script) y `instructions.md`.
3. Arma el archivo final: frontmatter (`name`, `description`, `tools`, y `model` si no es `default`) + un comentario HTML que marca el archivo como generado + el cuerpo de `instructions.md`.
4. Lo escribe en `ia-agentes/.claude/agents/<nombre>.md` — que es la carpeta que Claude Code auto-descubre al abrir `ia-agentes/` como raíz del proyecto (ver [`ia-agentes/README.md`](../../../README.md#cómo-activarlos)).

## Regla de oro: no edites `.claude/agents/*.md` a mano

Esos archivos ahora son **salida generada**. Si necesitás cambiar el comportamiento de un agente:

1. Editá `agent-harness/agents/<nombre>/instructions.md` (el prompt) o `agent.yaml` (nombre, descripción, tools, modelo).
2. Corré `python3 compile.py` de nuevo.
3. `agent-harness/tests/test_agents_structure.py` falla si el `.claude/agents/*.md` quedó desactualizado respecto a la fuente — es la forma de detectar que alguien editó el archivo compilado directamente en vez de la fuente.

## Por qué esto y no un adaptador más elaborado

No hay watch mode, no hay hook de pre-commit que recompile solo, no hay validación de esquema más allá de los 3 campos obligatorios. Es deliberado: el volumen actual (6 agentes, cambios poco frecuentes) no justifica más infraestructura — correr `compile.py` a mano después de editar un agente es una fricción aceptable. Si el catálogo crece mucho o cambia seguido, ahí sí valdría la pena automatizarlo (ej. un hook de git o un target de Makefile).
