# Prompt chaining · implementación en Python

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** de lo que se va a construir; el código se agrega aquí al implementarlo.

## Caso de uso

Procesar un **pago** (con dinero ficticio, nunca se ejecuta un pago real).

## Flujo

Texto libre *"Paga 150 USD a Frutas del Valle, ref F-102"* → 🟨 LLM1 extrae `{beneficiario, monto, moneda, referencia}` → 🟦 **Gate**: monto > 0, moneda permitida, beneficiario en la lista aprobada, monto bajo el límite → 🟨 LLM2 redacta la confirmación.

## Archivos previstos

`pago_chaining.py` (orquestador) · `gate.py` (validaciones) · `test_gate.py`

## Cómo se prueba (TDD)

El **Gate** se prueba **sin llamar al LLM** (datos falsos): rechaza monto negativo, moneda rara, beneficiario desconocido.

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Entorno compartido del catálogo (uv), definido en la raíz de [`patrones/`](../../README.md) cuando se implemente el primero.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
