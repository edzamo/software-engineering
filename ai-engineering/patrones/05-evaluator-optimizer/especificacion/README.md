# Evaluator-optimizer · especificación de la implementación

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** (independiente del lenguaje) de lo que se va a construir; se implementa en el taller **W07** ([plan de talleres](../../../talleres/README.md)), primero en Java/Spring AI.

## Caso de uso

Redactar el **aviso de un pago rechazado**.

## Flujo

🟨 Generator redacta → 🟨 Evaluator aplica una **rúbrica** (tono cordial, motivo claro, sin prometer lo que la política no permite, máximo 4 líneas) → si rechaza, vuelve con feedback → **máximo 3 intentos** → si no mejora, escalar a una persona.

## Piezas previstas

Orquestador · validaciones · pruebas (los nombres de archivo dependen del stack del taller).

## Cómo se prueba (TDD)

El **bucle** se prueba con un evaluador falso que rechaza N veces: debe parar en el tope y escalar.

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Código en `ai-engineering/talleres/NN-nombre/<stack>/` ([plan](../../../talleres/README.md)); el stack inicial es **Java con Spring AI**.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
