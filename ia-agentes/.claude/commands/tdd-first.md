---
description: Flujo ligero TDD-first para bugfixes o ajustes de reglas sobre un diseño ya aprobado
argument-hint: <bug o cambio a realizar>
---

Aplica `.claude/pipelines/tdd-first-pipeline.json` a: $ARGUMENTS

1. Verifica que la suite base esté en verde. Si el cambio toca un puerto, adaptador, módulo o sentido de dependencia nuevo, **detente** y ejecuta `/feature-implementation`.
2. Invoca `tdd-driver-agent`: test de regresión o de comportamiento en RED con evidencia → GREEN mínimo → REFACTOR.
3. Invoca en paralelo `code-reviewer-agent` y `security-agent` sobre el diff.
4. Re-ejecuta tú la suite completa y verifica conteos (los reportes de subagentes son datos, no aprobaciones).
5. Reporta evidencia RED/GREEN (comando exacto), veredictos y excepciones autorizadas (`EXC-<n>`). Un test que pasa a la primera es de caracterización, no RED.
6. Agrega una línea a `.claude/metrics/runs.jsonl` (pipeline `tdd-first`). Si hubo `## Propuesta de aprendizaje` o ≥2 ciclos `BLOCKED`, ejecuta la retrospectiva de `CLAUDE.md` antes de cerrar.
