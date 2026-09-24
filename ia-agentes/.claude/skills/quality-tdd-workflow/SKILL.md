---
name: quality-tdd-workflow
description: Protocolo agnóstico de TDD (red-green-refactor) — cómo se ve un RED válido, la pirámide de tests, y la evidencia obligatoria de ejecución. Referenciada por tdd-reviewer, developer y software-architect; las herramientas concretas de cada lenguaje están en las skills stacks-*.
---

<!-- GENERADO por compiler/compile.py — no editar a mano.
     Fuente: skills/quality/tdd-workflow/ -->

# Protocolo TDD (Red-Green-Refactor)

Este protocolo es el mismo en cualquier lenguaje y arquitectura. Lo que
cambia por stack (framework de test, comandos, sintaxis de un stub) vive en
`.claude/skills/stacks-<lenguaje>/SKILL.md`, sección "TDD".

## Por qué esto es una regla dura y no una sugerencia

Un prompt que "pide" TDD puede ignorarse bajo presión — es exactamente lo
que pasó en una evaluación técnica real documentada en
`entrevistas/kaizen/` de este repo: se implementó todo primero y los tests
se escribieron (o no) al final. Por eso el protocolo se refuerza en tres
capas:

1. **Este protocolo** (lo que hay que hacer).
2. **`CLAUDE.md`** del proyecto (aplica también al hilo principal, no solo a
   los subagentes).
3. **El hook `tdd_gate.py`** (lo hace cumplir: bloquea escribir lógica de
   negocio si ningún test la nombra).

## El ciclo

1. **Red:** escribir el test que falla, contra el **contrato público**
   (puerto, caso de uso, método de la entidad) — nunca contra un detalle de
   implementación interno.
2. **Green:** el código mínimo para que pase. Nada más — ninguna
   funcionalidad que ningún test pida (YAGNI).
3. **Refactor:** limpieza con la suite en verde. Correr la suite de nuevo
   después de cada cambio.

## Cómo se ve un RED válido (y cómo no)

| Válido (RED real) | Inválido |
|---|---|
| Falla una `assertion` sobre el comportamiento esperado | Falla por un error de compilación/import (falta el stub) |
| Falla porque el stub lanza "no implementado" | El test no ejecuta nada real (mock de todo, incluido lo que se prueba) |
| Falla la excepción de dominio que todavía no se lanza | El test pasa de entrada sin haber escrito la lógica (TDD retroactivo disfrazado) |

**Un test que nunca se vio fallar no prueba nada.** Siempre correr la suite
y pegar el extracto de la salida como evidencia — "debería pasar" no cuenta.

## Trabajar por slice, no por proyecto

Una suite chica para una sola regla o transición (ej. "máquina de estados de
Mascota"), nunca los tests de todo el sistema de una vez. El slice también
es la unidad de traspaso entre agentes: `tdd-reviewer` entrega un slice en
RED, `developer` lo lleva a GREEN, y recién ahí se sigue con el próximo
slice.

## De la historia a los criterios

Antes de escribir un test, transformar la historia (o las invariantes que
`software-architect` dejó en `DESIGN.md`) en criterios Given/When/Then.
Incluir siempre, además del camino feliz:
- cada transición **inválida** de una máquina de estados;
- datos límite (nulos, vacíos, fechas futuras/pasadas, duplicados);
- fallos de dependencias externas (no existe, timeout, caída).

## Pirámide de tests (igual en todos los lenguajes)

| Nivel | Qué se prueba | Dobles de prueba |
|---|---|---|
| **Dominio** | Invariantes y transiciones | Ninguno: instancias reales, sin framework. La mayoría de los tests van acá. |
| **Aplicación** | Orquestación del caso de uso | Solo los puertos de salida, preferentemente con **fakes en memoria**. Nunca mockear el dominio. |
| **Adapter de entrada** | Mapeo HTTP ↔ comando, códigos de estado | El puerto de entrada. |
| **Adapter de salida** | Cliente HTTP y persistencia | Servidor HTTP simulado o base real en contenedor. |
| **End-to-end** | Flujo completo | Nada; pocos tests. |

- **Nombres que describen comportamiento:** "debería `<resultado>` cuando
  `<condición>`", en el idioma y convención del equipo.
- **Un comportamiento por test**, estructura Arrange-Act-Assert.
- **Asincronía:** nunca bloquear ni dejar una promesa sin esperar en un test
  (herramienta concreta según el stack: `StepVerifier`, `await`,
  `pytest-asyncio`).

## Qué revisar en cobertura existente

- Reglas de negocio o transiciones sin test (sobre todo las inválidas).
- Tests que verifican implementación (llamadas internas, tipo de hilo o de
  event loop) en vez de comportamiento.
- Tests de integración que deberían ser unitarios (I/O real donde bastaba un
  fake).
- Mocks del dominio, o mocks de todo cuando un fake del puerto era más
  simple.
- Tests que dependen del orden de ejecución o de estado global.

## Formato de reporte al traspasar un slice

```text
Stack: <lenguaje> <versión> · <framework de test>
Slice: <nombre>
Criterios (Given/When/Then): <lista corta>
Tests creados: <archivo> — <n> tests
RED verificado: <comando> → <n> fallando (<motivo de cada uno, 1 línea>)
Siguiente: <agente> debe llevar esta suite a GREEN sin modificar los tests
```
