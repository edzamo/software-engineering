# <Proyecto>

<Qué es, para quién, estado actual.>

> Estándar de ingeniería: kit central en `~/.claude/kit` (agentes, comandos, skills genéricos). Contrato: `~/.claude/kit/PROJECT-CONTRACT.md`. Este archivo solo declara lo propio del proyecto.

## Stack
- Lenguaje / framework / versión:
- Datos / hosting:

## Arquitectura
Estilo: <hexagonal | clean | clean/by-layer | onion | frontend-component>

## Comandos
```bash
# tests:
# arquitectura (lint:arch):
# build:
```

## Skills del proyecto (los leen los agentes y los comandos)
| Skill | Cuándo |
|---|---|
| `.claude/skills/projects/<proyecto>/rules.md` | **Siempre**: reglas de negocio |
| `.claude/skills/projects/<proyecto>/security.md` | Seguridad (obligatorio para `security-agent`) |

## Convenciones
- Idioma:
- Nombres:
- Tests (dónde viven):

## Deuda conocida / excepciones autorizadas
`EXC-<n> | <regla/hallazgo> | <motivo> | <autorizó> | <AAAA-MM-DD>`
