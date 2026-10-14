# Evaluator-optimizer · implementación en Python

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** de lo que se va a construir; el código se agrega aquí al implementarlo.

## Caso de uso

Redactar el **aviso de un pago rechazado**.

## Flujo

🟨 Generator redacta → 🟨 Evaluator aplica una **rúbrica** (tono cordial, motivo claro, sin prometer lo que la política no permite, máximo 4 líneas) → si rechaza, vuelve con feedback → **máximo 3 intentos** → si no mejora, escalar a una persona.

## Archivos previstos

`aviso_rechazo.py` · `bucle.py` · `test_bucle.py`

## Cómo se prueba (TDD)

El **bucle** se prueba con un evaluador falso que rechaza N veces: debe parar en el tope y escalar.

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Entorno compartido del catálogo (uv), definido en la raíz de [`patrones/`](../../README.md) cuando se implemente el primero.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
