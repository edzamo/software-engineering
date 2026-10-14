# Orchestrator-worker · implementación en Python

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** de lo que se va a construir; el código se agrega aquí al implementarlo.

## Caso de uso

**Planificar una feature** repartida en subtareas.

## Flujo

Descripción de la feature → 🟨 Orchestrator devuelve un **plan en JSON** (lista de subtareas, máximo N) → 🟨 workers hacen cada subtarea → 🟨 Synthesizer arma un plan coherente.

## Archivos previstos

`planificador.py` · `validar_plan.py` · `test_validar_plan.py`

## Cómo se prueba (TDD)

La **validación del plan** (formato, tope de subtareas) se prueba con planes falsos: vacío, demasiado largo, JSON roto.

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Entorno compartido del catálogo (uv), definido en la raíz de [`patrones/`](../../README.md) cuando se implemente el primero.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
