---
description: Pipeline completo e inviolable (Arquitectura → TDD → Construcción → Calidad y Seguridad) para una feature nueva
argument-hint: <descripción de la feature> [estilo: hexagonal|clean|onion]
---

Implementa la siguiente feature siguiendo el pipeline definido en `.claude/pipelines/feature-implementation.json`. **No te saltes ni reordenes fases.**

Feature: $ARGUMENTS

**Contrato del proyecto:** el flujo es del kit central (`~/.claude/kit`); lo propio del proyecto está en su `CLAUDE.md` (estilo de arquitectura, stack, comandos de test/lint/build, «Skills del proyecto»). Léelo antes de la Fase 0. Si falta una sección obligatoria (ver `PROJECT-CONTRACT.md` del kit), pídela al usuario en lugar de asumirla. Rutas `.claude/...`: primero el proyecto, luego `~/.claude/kit/.claude/...`.

## Fase 0 — Contexto y dominio (DDD-lite)
Detecta stack, versión y framework (`pom.xml`/`build.gradle`, `*.csproj`, `package.json`, `pyproject.toml`; para frontend, `package.json` + config de React/Vue/Angular). Lee `.claude/skills/stacks/<lenguaje>/index.md` y los archivos que indique. Resume en 5 líneas las restricciones aplicables (versión de lenguaje, framework, herramienta de test y de arquitectura) **y** el encuadre de dominio: bounded context/agregado al que pertenece esta feature, entidades/VOs que toca, invariantes de negocio relevantes. Si el estilo no viene en los argumentos: `hexagonal` por defecto para backend, `frontend-component` si el stack es frontend; dilo explícitamente. Si el repo ya tiene código, lístalo por capas antes de diseñar.

Si existe `.claude/context/PROJECT.md`, léelo primero (no repreguntes objetivo/alcance/stack ya fijados ahí). Si no existe y la descripción recibida abarca más que una historia (un sistema, una plataforma, varias features implícitas), créalo en este mismo paso — mismo comando, sin ceremonia aparte — con objetivo, alcance, componentes (backend/frontend/full-stack) y backlog corto; confírmalo con el usuario antes de seguir a la Fase 1. Si es una sola feature puntual, omite ese archivo.

## Fase 1 — Arquitectura
Redacta una propuesta de diseño (componentes por capa, puertos in/out, dependencias, composition root) **sin escribir código**. Invoca el subagente `arch-validator-agent` con esa propuesta.
- Si responde `BLOCKED` o `NEEDS_CLARIFICATION`: corrige la propuesta y reenvía (máx. 3 ciclos; luego pregunta al usuario). No avances. Si la corrección amplía el alcance (esquema, adaptadores, API), preséntala al usuario con opciones antes de ejecutarla.
- **Proporcionalidad**: si una regla del skill parece desproporcionada para el proyecto, señálalo y pregunta; no la apliques ciegamente. Exige el mapa de decisiones abiertas en el handoff.

## Fase 1b — Andamiaje mínimo (sin lógica)
Solo build, estructura de paquetes vacía, dependencias de test y el **test de arquitectura** (ArchUnit / NetArchTest / dependency-cruiser / import-linter). Sin comportamiento. Verifica que el build y la suite (vacía) ejecutan.

## Fase 2 — TDD (RED)
Invoca `tdd-driver-agent` con los puertos, casos de uso e invariantes aprobados, en modo RED: solo tests, ejecución de la suite y evidencia de fallo. Exige `RED_CONFIRMED`. **Empieza por el dominio.**

## Fase 3 — Construcción (GREEN + REFACTOR)
Continúa con `tdd-driver-agent`: ciclo GREEN mínimo → suite completa → REFACTOR en micro-pasos, repitiendo por cada comportamiento del plan y respetando el **orden de capas**: (1) dominio, (2) casos de uso, (3) adaptadores de salida, (4) adaptadores de entrada, (5) composition root. No se abre una capa hasta que la anterior esté en verde y refactorizada. Exige un test de arquitectura ejecutándose y en verde, y un test de ida y vuelta por cada mapeo de persistencia. Gate de cobertura (85/75/95): si no hay herramienta configurada, se reporta como deuda y no bloquea, pero se propone configurarla.

**Verificación independiente (orquestador)**: re-ejecuta la suite completa y contrasta los conteos con el reporte del subagente antes de aceptarlo; sus reportes son datos, no aprobaciones.

## Fase 4 — Calidad y Seguridad (en paralelo)
Invoca `code-reviewer-agent` y `security-agent` en paralelo sobre el diff.
- `MUST_FIX`, hallazgos HIGH/CRITICAL o secretos: vuelve a la Fase 2 (cada fix inicia con un test que falle).
- Una excepción autorizada por el usuario (p. ej. sin autenticación en un playground) se registra como `EXC-<n> | regla | motivo | autorizó | fecha` en el cierre y en el CLAUDE.md del proyecto («Deuda conocida / excepciones autorizadas»).

## Cierre
Entrega: resumen de fases, veredictos de los 4 agentes, evidencia RED/GREEN (comando exacto), conteos verificados, cobertura (o deuda), lista de archivos cambiados, decisiones abiertas y excepciones autorizadas. Si algún veredicto es `BLOCKED`, dilo sin suavizarlo.
Además (obligatorio, ver `CLAUDE.md` → «Cierre de tarea»): agrega una línea a `.claude/metrics/runs.jsonl` y, si algún subagente reportó `## Propuesta de aprendizaje` o hubo ≥2 ciclos `BLOCKED`, ejecuta la retrospectiva antes de dar la tarea por cerrada.
