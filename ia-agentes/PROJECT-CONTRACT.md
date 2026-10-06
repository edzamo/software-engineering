# Contrato kit ↔ proyecto

El **kit** (este repositorio) es el estándar: agentes, comandos, hook TDD, pipelines y skills genéricos (`architecture/`, `quality/`, `stacks/`). El **proyecto** solo aporta su *corazón*: lo que no sirve en ningún otro lado. Los agentes del kit leen este contrato para saber dónde mirar.

## 1. Regla de reparto
| Va al **kit** (`ia-agentes/.claude/`) | Va al **proyecto** (`<repo>/CLAUDE.md` + `<repo>/.claude/`) |
|---|---|
| Agentes del flujo, comandos, hook, pipelines | `CLAUDE.md`: stack, estilo, comandos, «Skills del proyecto», convenciones, `EXC-n` |
| `skills/architecture/`, `skills/quality/`, `skills/stacks/` | `skills/projects/<proyecto>/` (reglas de negocio, seguridad, marca, despliegue) |
| Skills operativos genéricos (`conventional-commit`, `pr-description`) | Skills/agentes propios del cliente (`<proyecto>-deploy`, `<proyecto>-brand-guardian`…) |
| `DECISIONS.md` base (decisiones del kit) | `DECISIONS.md`, `context/PROJECT.md`, `metrics/runs.jsonl` del proyecto |
| — | `settings.json` del proyecto (permisos, y el hook TDD **si el proyecto lo activa**) |

Prueba rápida: si sirve en otro proyecto sin cambiarle una coma → kit. Si nombra al cliente, su dominio o su stack concreto → proyecto.

## 2. Resolución de rutas
Todo agente/comando resuelve una ruta `.claude/<x>` en este orden:
1. `<raíz del proyecto>/.claude/<x>` — **manda el proyecto** (permite sobrescribir un skill puntual sin forkear el agente).
2. `~/.claude/kit/.claude/<x>` — el kit.

`~/.claude/kit` es un symlink a la raíz de este repositorio (lo crea `scripts/install.sh`). **No se forkean agentes**: si un proyecto necesita otro comportamiento, lo declara en su `CLAUDE.md` (estilo, skills) o sobrescribe un skill; no copia el agente.

## 3. Secciones obligatorias del `CLAUDE.md` del proyecto
Plantilla lista en `templates/project/CLAUDE.md`.
1. **Descripción y estado** (qué es, para quién, fase actual).
2. **Stack** (lenguaje, framework, versión, BD, hosting).
3. **Estilo de arquitectura** — una línea `Estilo: <hexagonal | clean | clean/by-layer | onion | frontend-component>`; el agente de arquitectura la lee para elegir reglas.
4. **Comandos** (suite de tests, script de arquitectura `lint:arch`, build, lint).
5. **Skills del proyecto** (tabla ruta → cuándo leerla; marca cuáles son de seguridad).
6. **Convenciones** (idioma, nombres, **dónde viven los tests**).
7. **Deuda conocida / excepciones autorizadas** (`EXC-<n> | regla | motivo | autorizó | fecha`).

Opcional: `@~/.claude/kit/AGENTS.md` para que el proyecto adopte las reglas inviolables del flujo completo (orquestador, fases, métricas) en vez de redefinirlas.

## 4. Hook TDD (opt-in por proyecto)
`hooks/tdd-gate.sh` bloquea escribir código de producción sin RED confirmado. **No se activa globalmente** (rompería sitios, scripts y trabajo sin TDD). Un proyecto lo activa en su `.claude/settings.json`:
```json
{ "hooks": { "PreToolUse": [ { "matcher": "Write|Edit", "hooks": [ { "type": "command", "command": "bash ~/.claude/kit/.claude/hooks/tdd-gate.sh" } ] } ] } }
```
El estado efímero (`.claude/.tdd-state/`) vive en el proyecto y va en su `.gitignore`.

## 5. Orden del flujo (el que se quiere en cualquier stack)
System Design → DDD → Arquitectura de capas → TDD → Implementación → Calidad/Seguridad. Hoy el kit cubre desde «DDD-lite + Arquitectura» en adelante; System Design y un skill de DDD propio son la siguiente mejora.

## 6. Verificación de referencias
`scripts/check-project.py <raíz-del-repo>` lee los `CLAUDE.md` del proyecto y comprueba que cada ruta citada (`.claude/...`, `~/.claude/kit/...`, `stacks/...`, `architecture/...`, `quality/...`) resuelva en el proyecto o en el kit. Es la garantía de «solo referencias»: corre antes de commitear cambios de `CLAUDE.md` o del kit.

## 7. Cómo agregar conocimiento
- Reutilizable → un skill del kit (stack en `stacks/<stack>/` y listado en su `index.md`; patrón de calidad en `quality/`). Un agente nuevo solo si es un rol/fase con veredicto propio.
- Del negocio de un proyecto → `skills/projects/<proyecto>/` en ese repo.
- Los aprendizajes de un proyecto que merezcan subir al kit pasan por la retrospectiva (el usuario aprueba) y se llevan al kit en su propio commit.
