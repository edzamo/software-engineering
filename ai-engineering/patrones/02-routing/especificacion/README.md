# Routing · especificación de la implementación

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** (independiente del lenguaje) de lo que se va a construir; se implementa en el taller **W03** ([plan de talleres](../../../talleres/README.md)), primero en Java/Spring AI.

## Caso de uso

**Fichaje y solicitudes** de empleados.

## Flujo

Mensaje → 🟨 Router devuelve **una palabra**: `marcar`, `permiso`, `consulta` u `otro` → especialista: registrar entrada/salida, pedir un permiso, responder cuántas horas lleva. `otro` → **escalar a una persona**.

## Piezas previstas

Orquestador · validaciones · pruebas (los nombres de archivo dependen del stack del taller).

## Cómo se prueba (TDD)

El **despacho** (etiqueta → función) se prueba con etiquetas falsas, incluida una **inesperada** que debe caer en la ruta por defecto.

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Código en `ai-engineering/talleres/NN-nombre/<stack>/` ([plan](../../../talleres/README.md)); el stack inicial es **Java con Spring AI**.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
