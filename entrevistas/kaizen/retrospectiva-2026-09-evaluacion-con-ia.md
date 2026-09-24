# Retrospectiva — Evaluación técnica en vivo con IA (2026-09)

Rol Senior backend (Java 21, WebFlux, microservicios) en una startup HealthTech. El proceso se cerró en la etapa técnica. El material de preparación quedó en [`archivo/`](../archivo/saludtools-desarrollador-senior).

Este documento registra **qué pasó, por qué pasó y qué cambia**. La versión ideal del mismo ejercicio está en [`caso-adoptantes-mascotas.md`](caso-adoptantes-mascotas.md), y el método general en [`README.md`](README.md).

---

## 1. El feedback

> "...continuar con perfiles que, en esta etapa, muestran un poquito de mayor autonomía para **analizar el problema, definir una solución y explicar las decisiones de diseño e implementación**. El equipo valoró que tenés buenas bases en programación reactiva.
> Como recomendación, te puede servir practicar ejercicios de arquitectura de microservicios **partiendo del análisis del problema y sus posibles alternativas**. Si usás IA como apoyo, es útil que puedas **revisar críticamente lo que propones** y explicar por qué elegís una solución, qué cambiarías y cómo la implementarías."

**Traducción:** "autonomía" no se refiere a trabajar solo o en remoto. Se refiere a **conducir el ejercicio con criterio propio**. No vieron que yo pensara la solución: vieron que la pensaba la IA.

## 2. El ejercicio

Dos microservicios, **Adoptantes** y **Mascotas**, con CRUD cada uno. La mascota tiene fecha de rescate obligatoria y tres estados (disponible, en proceso, adoptada). Además había que relacionar adoptantes con mascotas **demostrando la comunicación entre servicios**. Stack: Spring WebFlux + Gradle. Enunciado completo en [`caso-adoptantes-mascotas.md` §0](caso-adoptantes-mascotas.md#0-el-enunciado).

## 3. Qué pasó

1. Pegué mis agentes de Claude Code y le pasé a la IA este prompt, con el enunciado entero:
   > *"Actúa como arquitecto de soluciones y crea un proyecto Spring WebFlux Gradle"*
2. Dediqué buena parte del tiempo a explicar los agentes y conceptos (TDD, SOLID, Claude Code).
3. Dije que *"en un mundo ideal"* primero entendería la historia de usuario y el dominio, pero **no lo hice** con el enunciado que tenía delante.
4. La IA implementó sin tests. Lo noté y dije "lo ideal sería TDD", pero **no la frené ni la corregí**.
5. Respondí bien las preguntas conceptuales: CQRS, resiliencia, logs, reactivo, system design.
6. Recorrimos el código capa por capa y expliqué qué hacía cada una.
7. A **"¿qué cambiarías?"** respondí lo que la IA había hecho **bien** (OpenAPI, verbos, validaciones, nombres).
8. No entendí dos preguntas, una sobre renombrar o cambiar la clase del repositorio en memoria, y **supuse** en vez de pedir que me las aclararan.

**Lo que salió bien:** base técnica sólida (el feedback lo reconoce), buena fluidez al hablar y la solución quedó funcionando.

## 4. Diagnóstico

| # | Qué hice | Qué se esperaba | Paso del protocolo |
|---|---|---|---|
| 1 | Pegué el enunciado entero en la IA | Analizarlo en voz alta: dominio, reglas, preguntas | 1 · Entender |
| 2 | "En un mundo ideal entendería el dominio" | Entenderlo ahí mismo | 1 · Entender |
| 3 | No dibujé nada | Contexto + dominio + estados + secuencia | 2 · Modelar |
| 4 | No planteé alternativas | Sync vs async, dueño de la relación, estructura | 3 · Decidir |
| 5 | Le pedí a la IA que fuera "el arquitecto" | La IA ejecuta **mi** diseño, slice por slice | 5 · Ejecutar |
| 6 | Expliqué los agentes durante mucho tiempo | Explicar el problema; la herramienta, en 1 min | Comunicación |
| 7 | Vi que no hubo TDD y no lo corregí | Frenar, borrar y pedir el test primero | 5 · Ejecutar |
| 8 | "¿Qué cambiarías?" → defendí la salida de la IA | 3-5 críticas concretas para producción | 7 · Autocrítica |
| 9 | Adiviné el sentido de preguntas ambiguas | "¿Te referís a X o a Y?" | Comunicación |

Puntaje con la rúbrica del caso: **4 / 22**. El conocimiento conceptual sacó el máximo; el proceso visible, casi cero.

## 5. Causa raíz (5 porqués)

1. **¿Por qué no avancé?** Porque no demostré autonomía para analizar, decidir y criticar.
2. **¿Por qué no la demostré?** Porque el análisis y el diseño los hizo la IA, no yo en voz alta.
3. **¿Por qué se los dejé a la IA?** Porque entendí "prueba de habilidades con IA" como "mostrar mi flujo de agentes", no como "mostrar mi criterio usando IA".
4. **¿Por qué esa interpretación?** Porque mi preparación se centró en **automatizar** el flujo (agentes que hacen hexagonal, TDD, WebFlux) y no en **ensayar la conducción** del flujo en voz alta.
5. **¿Por qué no lo ensayé?** Porque nunca hice un simulacro cronometrado de principio a fin. El plan del día lo tenía como bloque 1 y no se ejecutó.

**Causa raíz:** tenía la herramienta y el conocimiento, pero **no tenía un protocolo ensayado** para hacer visible mi criterio. Bajo presión, el flujo automatizado reemplazó al pensamiento visible.

## 6. Acciones (Kaizen)

| Acción | Dónde está | Criterio de "hecho" |
|---|---|---|
| Adoptar el protocolo de 7 pasos | [`README.md` §2](README.md#2-el-protocolo-en-7-pasos) | Lo aplico en cada simulacro |
| Primeros 25 min sin IA, siempre | Checklist de [`README.md` §6](README.md#6-checklist-de-30-segundos-leer-justo-antes-de-entrar) | En cada simulacro grabado, la IA aparece recién después del `DESIGN.md` |
| Dibujar siempre en Mermaid | [`README.md` §2, paso 2](README.md#paso-2--modelar-y-dibujar) | 4 diagramas en menos de 12 min |
| Nunca delegar el rol evaluado en la IA | Plantilla de prompt en [`README.md` §2, paso 5](README.md#paso-5--ejecutar-conduciendo-a-la-ia) | Prompts por slice, con tests primero |
| Rehacer este mismo ejercicio siguiendo el caso ideal | [`caso-adoptantes-mascotas.md`](caso-adoptantes-mascotas.md) | Rúbrica ≥ 18/22 |
| Simulacros semanales con enunciados nuevos | [`README.md` §5](README.md#5-plan-de-práctica) | Registrados en la tabla PDCA de [`README.md` §7](README.md#7-registro-kaizen-pdca) |

## 7. Lo que me llevo

> Me evaluaron por el criterio que mostré, no por el código que generé. El conocimiento ya lo tengo; ahora me toca hacerlo visible.
