---
description: Flujo ligero TDD-first para bugfixes o ajustes de reglas sobre un diseño ya aprobado
argument-hint: <bug o cambio a realizar>
---

Aplica `.claude/pipelines/tdd-first-pipeline.json` a: $ARGUMENTS

1. Verifica que la suite base esté en verde. Si el cambio toca un puerto, adaptador, módulo o sentido de dependencia nuevo, **detente** y ejecuta `/feature-implementation`.
2. Invoca `tdd-driver-agent`: test de regresión o de comportamiento en RED con evidencia → GREEN mínimo → REFACTOR.
3. Invoca en paralelo `code-reviewer-agent` y `security-agent` sobre el diff.
4. Reporta evidencia RED/GREEN y veredictos.
