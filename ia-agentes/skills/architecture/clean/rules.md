# Clean Architecture

Origen: Robert C. Martin (2012), evolución de Onion Architecture (Jeffrey
Palermo, 2008) con vocabulario propio.

## La Dependency Rule (la única regla real)

> El código fuente solo puede depender hacia adentro. Nada en un círculo
> interior puede saber nada de un círculo exterior.

Círculos, de adentro hacia afuera:

1. **Entities** — reglas de negocio empresariales, las más generales y
   estables. Equivalente a `domain/` en hexagonal.
2. **Use Cases** — reglas de negocio de la aplicación; orquestan Entities.
   Equivalente a `application/` en hexagonal. Definen **boundaries**
   (interfaces) que las capas externas implementan — el mismo rol que los
   puertos en hexagonal.
3. **Interface Adapters** — controllers, presenters, gateways: convierten
   datos entre el formato de Use Cases/Entities y el formato conveniente
   para frameworks/BD/web. Equivalente a `infrastructure/adapter/` en
   hexagonal.
4. **Frameworks & Drivers** — la capa más externa: web framework, ORM,
   dispositivos. El detalle, no el centro.

## Equivalencia con Hexagonal — es el mismo principio, otro vocabulario

| Hexagonal | Clean | Onion |
|---|---|---|
| `domain` | Entities | Domain Model |
| `application/port/in` + `service` | Use Cases (boundaries + interactors) | Application Services |
| `application/port/out` | Boundary de salida (interfaz definida en Use Cases) | Interfaz en Domain Services |
| `infrastructure/adapter` | Interface Adapters | Infrastructure |

Para las invariantes de dependencia y el checklist, ver `architecture-hexagonal`
— son las mismas reglas de fondo. Esta skill documenta lo que es **propio**
de Clean: el vocabulario (Entities/Use Cases/Interface Adapters) y el patrón
de **boundaries + interactors**.

## Boundaries e Interactors (lo distintivo de Clean)

- **Input Boundary:** interfaz que expone el caso de uso (equivalente al
  `port/in`). La implementa el **Interactor**.
- **Output Boundary:** interfaz que el Interactor usa para devolver el
  resultado (un **Presenter** la implementa) — es la inversión de
  dependencia que evita que el Use Case dependa del formato de salida
  (JSON, vista, etc.). Hexagonal normalmente no separa esto; es opcional
  adoptarlo si el proyecto ya usa clean con esta granularidad.
- **DTO/Request/Response Model:** estructuras de datos simples que cruzan el
  boundary, nunca las Entities directamente.

## Cuándo elegirla (vs. hexagonal u onion)

- Si el equipo o la organización ya usa el vocabulario de Robert Martin (Use
  Cases, Interactors, Presenters), mantené consistencia y elegí Clean.
- Si no hay una convención previa, **hexagonal** es más liviana de explicar y
  de escribir (menos capas de indirección que Interactor+Presenter+Boundary).
- No mezcles vocabularios en el mismo proyecto (ej. `UseCase` de Clean +
  `Port` de hexagonal a la vez) — elegí uno y sé consistente.

## Checklist de validación

- [ ] Nada en `entities/` importa de `usecases/`, `interfaceadapters/` ni
      `frameworks/`.
- [ ] Nada en `usecases/` importa de `interfaceadapters/` ni `frameworks/`.
- [ ] Los datos que cruzan un boundary son DTOs, nunca Entities.
- [ ] Un Interactor no conoce el formato de salida (HTTP, JSON): eso lo
      resuelve el Presenter/output boundary.

## Tests de arquitectura

Usá los mismos mecanismos que `architecture-hexagonal` (ArchUnit,
dependency-cruiser, import-linter), apuntando los paquetes/carpetas a
`entities`, `usecases`, `interfaceadapters`, `frameworks` en vez de
`domain`, `application`, `infrastructure`.

## Referencias

- Martin, R. C. — *Clean Architecture* (2017) y el post original
  "The Clean Architecture" (2012), blog.cleancoder.com.
