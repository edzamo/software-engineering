# Parallelization · implementación en Python

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** de lo que se va a construir; el código se agrega aquí al implementarlo.

## Caso de uso

**Revisar un cambio de código** (un diff de ejemplo).

## Flujo

🟦 Coordinator reparte el diff → 🟨 revisor de **seguridad** + 🟨 revisor de **estilo** + 🟨 revisor de **pruebas** (a la vez) → 🟦 Aggregator junta y ordena por severidad.

## Archivos previstos

`revision_paralela.py` (asyncio) · `agregador.py` · `test_agregador.py`

## Cómo se prueba (TDD)

El **agregador** se prueba con resultados falsos, incluido **una rama que falla** (el informe debe salir igual y avisar qué faltó).

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Entorno compartido del catálogo (uv), definido en la raíz de [`patrones/`](../../README.md) cuando se implemente el primero.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
