---
name: architecture-onion
description: Invariantes de Onion Architecture (Jeffrey Palermo) — anillos concéntricos, Domain Model/Domain Services/Application Services/Infrastructure, y su equivalencia con Hexagonal/Clean. Úsala cuando software-architect proponga onion, o para revisar si código existente la respeta.
---

<!-- GENERADO por compiler/compile.py — no editar a mano.
     Fuente: skills/architecture/onion/ -->

# Onion Architecture

Origen: Jeffrey Palermo (2008). Precursora directa de Clean Architecture
(2012); comparte con Hexagonal (2005) el mismo principio con otro
vocabulario — "anillos" en vez de "puertos y adaptadores".

## Los anillos, de adentro hacia afuera

1. **Domain Model** — entidades y Value Objects, sin lógica de aplicación ni
   dependencias externas. Equivalente a las Entities de Clean y al `domain`
   de hexagonal.
2. **Domain Services** — lógica de dominio que no encaja naturalmente en una
   sola entidad (ej. una regla que compara dos entidades). Definen
   interfaces que la Infrastructure implementa (mismo rol que los puertos de
   salida).
3. **Application Services** — orquestan Domain Services y Domain Model para
   resolver un caso de uso completo. Equivalente a `application/service` en
   hexagonal.
4. **Infrastructure / User Interface** — el anillo externo: web, persistencia,
   UI. Implementa las interfaces definidas en anillos internos.

## Regla de dependencia

Igual que Hexagonal y Clean: **todo apunta hacia el centro**. Ningún anillo
interior conoce un anillo exterior. La diferencia con Hexagonal es de
énfasis, no de mecánica: Onion habla de "anillos" y separa explícitamente
Domain Services de Application Services; Hexagonal agrupa eso en
`domain`/`application` y pone el foco en los puertos como frontera explícita
con adaptadores intercambiables.

## Equivalencia — ver tabla completa en `architecture-hexagonal`

| Onion | Hexagonal | Clean |
|---|---|---|
| Domain Model | `domain` | Entities |
| Domain Services (+ sus interfaces) | `domain` + `application/port/out` | Entities + boundary |
| Application Services | `application/service` | Use Cases (Interactors) |
| Infrastructure | `infrastructure/adapter` | Interface Adapters + Frameworks |

## Cuándo elegirla

En la práctica, hoy Onion, Clean y Hexagonal se consideran variantes
intercambiables del mismo principio ("Ports & Adapters" en sentido amplio).
Elegí Onion solo si el equipo ya tiene esa convención instalada o si el
dominio necesita separar explícitamente "reglas de una entidad" de "reglas
entre entidades" (Domain Services) de forma más granular que lo que
Hexagonal exige por defecto.

## Checklist de validación

- [ ] Nada en Domain Model importa de Domain Services, Application Services
      ni Infrastructure.
- [ ] Domain Services no importa Infrastructure — solo define la interfaz
      que Infrastructure implementa.
- [ ] Application Services no contiene reglas de negocio propias: orquesta,
      no decide.

## Tests de arquitectura

Mismos mecanismos que `architecture-hexagonal` (ArchUnit, dependency-cruiser,
import-linter), apuntando a los paquetes/carpetas equivalentes del proyecto.

## Referencias

- Palermo, J. — "The Onion Architecture" (2008), jeffreypalermo.com.
