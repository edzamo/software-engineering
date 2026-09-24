# Reglas del proyecto (aplican al hilo principal y a todos los subagentes)

Los agentes son **roles agnósticos** (arquitecto, TDD, developer, revisores).
El conocimiento reutilizable vive en skills, en tres categorías:
- `architecture-{hexagonal,clean,onion}` — invariantes de arquitectura.
- `quality-{tdd-workflow,clean-code,owasp,refactoring}` — cómo testear,
  revisar y refactorizar.
- `stacks-{java,typescript,python}` — lo único que cambia por lenguaje.

## Paso 0 — Detectar el stack, siempre

```bash
python3 .claude/scripts/detect_stack.py
```

Devuelve el lenguaje, la versión, el framework y los comandos de test de cada
módulo. Después, leé la skill `stacks-<lenguaje>` correspondiente. **Nunca uses
una feature de una versión mayor que la del proyecto.** En un proyecto nuevo
sin stack detectado, preguntá lenguaje, versión y framework antes de generar
nada.

## TDD obligatorio — no negociable, en cualquier lenguaje y arquitectura

Ninguna lógica de negocio se escribe antes que el test que la especifica.
Vale también cuando el pedido es "creá el proyecto completo" o viene con
apuro.

Pipeline por **slice** (una regla o transición a la vez, nunca el proyecto
entero de una vez):

1. `software-architect` → análisis, alternativas con trade-offs, **tu
   decisión**, `DESIGN.md` (diagramas + ADRs) y scaffold con stubs. Sin
   reglas de negocio.
2. `tdd-reviewer` → tests del slice, **ejecutados**, con la salida en RED a la
   vista.
3. `developer` (o un especialista de framework, ej.
   `spring-boot-webflux-dev`) → implementación mínima hasta GREEN, sin tocar
   los tests, y **ejecutando** la suite de nuevo.
4. Refactor → correr la suite otra vez.
5. Al final: `owasp-security-reviewer`, `clean-code-reviewer`,
   `docker-packager`, `gitflow-release-manager`.

Si el hilo principal va a escribir código él mismo en vez de delegar, sigue
exactamente el mismo orden: test → correrlo (RED) → implementación → correrlo
(GREEN).

El hook `.claude/hooks/tdd_gate.py` bloquea escribir archivos de las capas de
negocio (`domain/`, `application/`, `usecases/`, `entities/`, `core/`) en
Java, Kotlin, TypeScript o Python si ningún test los nombra. Si bloquea, **no
se busca rodearlo**: se vuelve al paso 2.

## Decisiones de diseño: las toma el humano

Ante una decisión de arquitectura (qué arquitectura interna, sync vs async,
dueño de un dato, tipo de persistencia, estructura del proyecto), presentá 2
alternativas con trade-offs y una recomendación, y **esperá la decisión**
antes de generar código. No asumas el rol de "arquitecto que decide solo".

## Entrega en pasos chicos

Después de cada slice, mostrá qué se hizo, la salida de los tests y cuál es
el siguiente slice, y esperá el OK antes de seguir.
