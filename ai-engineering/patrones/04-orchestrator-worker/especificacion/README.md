# Orchestrator-worker · especificación de la implementación

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** (independiente del lenguaje) de lo que se va a construir; se implementa en el taller **W04** ([plan de talleres](../../../talleres/README.md)), primero en Java/Spring AI.

## Caso de uso

**Planificar una feature** repartida en subtareas.

## Flujo

Descripción de la feature → 🟨 Orchestrator devuelve un **plan en JSON** (lista de subtareas, máximo N) → 🟨 workers hacen cada subtarea → 🟨 Synthesizer arma un plan coherente.

## Piezas previstas

Orquestador · validaciones · pruebas (los nombres de archivo dependen del stack del taller).

## Cómo se prueba (TDD)

La **validación del plan** (formato, tope de subtareas) se prueba con planes falsos: vacío, demasiado largo, JSON roto.

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Código en `ai-engineering/talleres/NN-nombre/<stack>/` ([plan](../../../talleres/README.md)); el stack inicial es **Java con Spring AI**.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
