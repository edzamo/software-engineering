---
description: Pipeline completo e inviolable (Arquitectura → TDD → Construcción → Calidad y Seguridad) para una feature nueva
argument-hint: <descripción de la feature> [estilo: hexagonal|clean|onion]
---

Implementa la siguiente feature siguiendo el pipeline definido en `.claude/pipelines/feature-implementation.json`. **No te saltes ni reordenes fases.**

Feature: $ARGUMENTS

## Fase 0 — Contexto y mejores prácticas
Detecta stack, versión y framework (`pom.xml`/`build.gradle`, `*.csproj`, `package.json`, `pyproject.toml`). Lee `.claude/skills/stacks/<lenguaje>/index.md` y los archivos que indique. Resume en 5 líneas las restricciones aplicables (versión de lenguaje, framework, herramienta de test y de arquitectura). Si el estilo no viene en los argumentos, usa `hexagonal` y dilo explícitamente. Si el repo ya tiene código, lístalo por capas antes de diseñar.

## Fase 1 — Arquitectura
Redacta una propuesta de diseño (componentes por capa, puertos in/out, dependencias, composition root) **sin escribir código**. Invoca el subagente `arch-validator-agent` con esa propuesta.
- Si responde `BLOCKED` o `NEEDS_CLARIFICATION`: corrige la propuesta y reenvía (máx. 3 ciclos; luego pregunta al usuario). No avances.

## Fase 1b — Andamiaje mínimo (sin lógica)
Solo build, estructura de paquetes vacía, dependencias de test y el **test de arquitectura** (ArchUnit / NetArchTest / dependency-cruiser / import-linter). Sin comportamiento. Verifica que el build y la suite (vacía) ejecutan.

## Fase 2 — TDD (RED)
Invoca `tdd-driver-agent` con los puertos, casos de uso e invariantes aprobados, en modo RED: solo tests, ejecución de la suite y evidencia de fallo. Exige `RED_CONFIRMED`. **Empieza por el dominio.**

## Fase 3 — Construcción (GREEN + REFACTOR)
Continúa con `tdd-driver-agent`: ciclo GREEN mínimo → suite completa → REFACTOR en micro-pasos, repitiendo por cada comportamiento del plan y respetando el **orden de capas**: (1) dominio, (2) casos de uso, (3) adaptadores de salida, (4) adaptadores de entrada, (5) composition root. No se abre una capa hasta que la anterior esté en verde y refactorizada. Exige un test de arquitectura ejecutándose y en verde, y un test de ida y vuelta por cada mapeo de persistencia.

## Fase 4 — Calidad y Seguridad (en paralelo)
Invoca `code-reviewer-agent` y `security-agent` en paralelo sobre el diff.
- `MUST_FIX`, hallazgos HIGH/CRITICAL o secretos: vuelve a la Fase 2 (cada fix inicia con un test que falle).

## Cierre
Entrega: resumen de fases, veredictos de los 4 agentes, evidencia RED/GREEN, cobertura y lista de archivos cambiados. Si algún veredicto es `BLOCKED`, dilo sin suavizarlo.
