# Routing · implementación en Python

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** de lo que se va a construir; el código se agrega aquí al implementarlo.

## Caso de uso

**Fichaje y solicitudes** de empleados.

## Flujo

Mensaje → 🟨 Router devuelve **una palabra**: `marcar`, `permiso`, `consulta` u `otro` → especialista: registrar entrada/salida, pedir un permiso, responder cuántas horas lleva. `otro` → **escalar a una persona**.

## Archivos previstos

`fichaje_routing.py` · `especialistas.py` · `test_despacho.py`

## Cómo se prueba (TDD)

El **despacho** (etiqueta → función) se prueba con etiquetas falsas, incluida una **inesperada** que debe caer en la ruta por defecto.

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Entorno compartido del catálogo (uv), definido en la raíz de [`patrones/`](../../README.md) cuando se implemente el primero.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
