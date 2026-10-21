# Agente · especificación de la implementación

> ⬅️ [Documentación del patrón](../README.md) · [Catálogo](../../README.md)
> ⏳ **Estado:** pendiente. Esta carpeta es la **especificación** (independiente del lenguaje) de lo que se va a construir; se implementa en el taller **W02** ([plan de talleres](../../../talleres/README.md)), primero en Java/Spring AI.

## Caso de uso

**Conciliación bancaria** con herramientas (datos ficticios en un CSV).

## Flujo

Objetivo *"concilia este extracto"* → bucle: 🟨 LLM elige herramienta (`leer_extracto`, `buscar_pago`, `anotar_conciliacion`) → resultado vuelve al LLM → repite → **STOP** cuando decide que terminó.

## Piezas previstas

Orquestador · validaciones · pruebas (los nombres de archivo dependen del stack del taller).

## Cómo se prueba (TDD)

Las **herramientas** se prueban solas (son funciones normales) y el **tope de iteraciones** con un LLM falso que nunca termina.

Regla del catálogo: **las pruebas no llaman al LLM real** (cuestan dinero y no son repetibles). El LLM se reemplaza por una versión falsa.

## Reglas

- Código en el repo de laboratorios `ai-engineering-labs` (carpeta del taller correspondiente; [plan y estructura](../../../talleres/README.md#1b--dos-repositorios-teoría-aquí-práctica-allá)); el stack inicial es **Java con Spring AI**.
- Claves en `.env` (nunca en Git); se versiona solo `.env.example`.
- Datos y dinero **ficticios**.
