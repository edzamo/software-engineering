# Agente · implementación en Python

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** de lo que se va a construir; el código se agrega aquí al implementarlo.

## Caso de uso

**Conciliación bancaria** con herramientas (datos ficticios en un CSV).

## Flujo

Objetivo *"concilia este extracto"* → bucle: 🟨 LLM elige herramienta (`leer_extracto`, `buscar_pago`, `anotar_conciliacion`) → resultado vuelve al LLM → repite → **STOP** cuando decide que terminó.

## Archivos previstos

`conciliador_agente.py` · `herramientas.py` · `test_herramientas.py`

## Cómo se prueba (TDD)

Las **herramientas** se prueban solas (son funciones normales) y el **tope de iteraciones** con un LLM falso que nunca termina.

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Entorno compartido del catálogo (uv), definido en la raíz de [`patrones/`](../../README.md) cuando se implemente el primero.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
