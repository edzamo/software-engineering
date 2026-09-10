# tests/ — validación de estructura y sincronización

No son evals de calidad de las respuestas del agente (eso requeriría correr el modelo de verdad, con costo y no determinismo) — son tests de **estructura e higiene**, deterministas y gratis, que corren en milisegundos:

```bash
cd agent-harness
python3 -m unittest discover -s tests -v
```

## Qué valida `test_agents_structure.py`

1. **`TestAgentSourceStructure`** — cada carpeta en `agents/` tiene `agent.yaml` con los campos obligatorios (`name`, `description`, `tools`) e `instructions.md` no vacío, y el `name` declarado coincide con el nombre de la carpeta.
2. **`TestCompiledOutputInSync`** — cada agente tiene su `.claude/agents/<nombre>.md` compilado, y ese archivo coincide byte a byte con lo que generaría `compile.py` ahora mismo. Si alguien edita el `.md` compilado a mano en vez de la fuente, o edita la fuente y se olvida de recompilar, este test lo detecta.

## Qué falta acá (pendiente, no fingido como si ya existiera)

Evals reales de calidad — por ejemplo, correr `owasp-security-reviewer` contra un snippet de código con una inyección SQL conocida y verificar que el hallazgo aparece — requieren invocar el modelo (vía Claude Code o el SDK) y comparar la respuesta contra un criterio esperado. Eso es valioso pero es otro tipo de test (más lento, no determinista al 100%, con costo de tokens), y hoy no está armado. Si se suma, va en un archivo nuevo (`test_evals.py` o similar) separado de este, que es puramente estructural.
