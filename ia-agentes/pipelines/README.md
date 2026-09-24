# Pipelines

Documentación legible de en qué orden se invoca cada agente para un flujo
completo — no un orquestador ejecutable. Nada en este repo lee estos JSON
automáticamente.

## Por qué existe esto si no se ejecuta

Un pipeline documentado ayuda a explicar el flujo (a otra persona, o al
armar el prompt inicial de una sesión), pero **la garantía real** de que el
orden se respeta (sobre todo TDD antes que implementación) no está acá: está
en [`.claude/hooks/tdd_gate.py`](../.claude/hooks/tdd_gate.py) (bloquea
código sin test) y en [`.claude/CLAUDE.md`](../.claude/CLAUDE.md) (las
reglas que el hilo principal lee siempre). Un JSON que nadie ejecuta es una
promesa, no una regla — la lección completa de por qué esto importa está en
la retrospectiva de [`entrevistas/kaizen/`](../../entrevistas/kaizen) del
repo.

Si en algún momento aparece un caso de uso real para orquestar esto
(ej. correr el pipeline completo sin supervisión, o desde un cliente
distinto de Claude Code), este es el lugar donde documentarlo primero y
recién después conectarlo a un ejecutor — no al revés.

## Archivos

| Archivo | Qué documenta |
|---|---|
| [`feature-implementation.json`](feature-implementation.json) | El orden de agentes para una feature nueva, de punta a punta (rama → diseño → RED → GREEN → revisión → empaquetado → PR). |
