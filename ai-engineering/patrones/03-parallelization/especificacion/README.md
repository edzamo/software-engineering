# Parallelization · especificación de la implementación

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** (independiente del lenguaje) de lo que se va a construir; se implementa en el taller **W03** ([plan de talleres](../../../talleres/README.md)), primero en Java/Spring AI.

## Caso de uso

**Revisar un cambio de código** (un diff de ejemplo).

## Flujo

🟦 Coordinator reparte el diff → 🟨 revisor de **seguridad** + 🟨 revisor de **estilo** + 🟨 revisor de **pruebas** (a la vez) → 🟦 Aggregator junta y ordena por severidad.

## Piezas previstas

Orquestador · validaciones · pruebas (los nombres de archivo dependen del stack del taller).

## Cómo se prueba (TDD)

El **agregador** se prueba con resultados falsos, incluido **una rama que falla** (el informe debe salir igual y avisar qué faltó).

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Código en `ai-engineering/talleres/NN-nombre/<stack>/` ([plan](../../../talleres/README.md)); el stack inicial es **Java con Spring AI**.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
