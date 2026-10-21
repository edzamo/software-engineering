# Prompt chaining · especificación de la implementación

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** (independiente del lenguaje) de lo que se va a construir; se implementa en el taller **W03** ([plan de talleres](../../../talleres/README.md)), primero en Java/Spring AI.

## Caso de uso

Procesar un **pago** (con dinero ficticio, nunca se ejecuta un pago real).

## Flujo

Texto libre *"Paga 150 USD a Frutas del Valle, ref F-102"* → 🟨 LLM1 extrae `{beneficiario, monto, moneda, referencia}` → 🟦 **Gate**: monto > 0, moneda permitida, beneficiario en la lista aprobada, monto bajo el límite → 🟨 LLM2 redacta la confirmación.

## Piezas previstas

Orquestador · validaciones · pruebas (los nombres de archivo dependen del stack del taller).

## Cómo se prueba (TDD)

El **Gate** se prueba **sin llamar al LLM** (datos falsos): rechaza monto negativo, moneda rara, beneficiario desconocido.

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Código en el repo de laboratorios `ai-engineering-labs` (carpeta del taller correspondiente; [plan y estructura](../../../talleres/README.md#1b--dos-repositorios-teoría-aquí-práctica-allá)); el stack inicial es **Java con Spring AI**.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
