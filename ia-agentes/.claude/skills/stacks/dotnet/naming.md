# .NET (C# / .NET 8+) — Convenciones de Nombres y Estilo

## Nombres
| Elemento | Convención | Ejemplo |
|----------|-----------|---------|
| Namespace | `PascalCase`, `Empresa.Producto.Capa` | `Acme.Adoption.Domain` |
| Clase, record, struct, enum | `PascalCase` | `Adopter`, `AdoptionId` |
| Interfaz | `I` + `PascalCase` | `IAdopterRepository` |
| Método, propiedad, evento | `PascalCase` | `RegisterAsync`, `Email` |
| Parámetro, variable local | `camelCase` | `birthDate` |
| Campo privado | `_camelCase` | `_repository` |
| Constante | `PascalCase` | `MaxRetries` |
| Genéricos | `T` o `TEntity` | `IRepository<TEntity>` |
| Métodos asíncronos | sufijo `Async` | `FindByEmailAsync` |
| Tests | `Clase_Metodo_Escenario` o `Debe_X_Cuando_Y` | `Register_DuplicateEmail_Throws` |

## Estructura de solución
```
src/
├── Acme.Adoption.Domain/
├── Acme.Adoption.Application/
├── Acme.Adoption.Infrastructure/      # adaptadores de salida
├── Acme.Adoption.Api/                 # adaptadores de entrada + composition root
tests/
├── Acme.Adoption.Domain.Tests/
├── Acme.Adoption.Application.Tests/
├── Acme.Adoption.ArchitectureTests/
```

## Estilo del lenguaje (C# 12 / .NET 8)
- `<Nullable>enable</Nullable>` y `<TreatWarningsAsErrors>true</TreatWarningsAsErrors>`.
- `record`/`record struct` para Value Objects y DTOs; `init` en propiedades; `required` para obligatorias.
- Constructores primarios (C# 12) para inyección en servicios sencillos.
- File-scoped namespaces; `var` solo si el tipo es evidente.
- Pattern matching con `switch` expressions y jerarquías cerradas (`sealed` + análisis de exhaustividad).
- Colecciones: `IReadOnlyList<T>`; expresiones de colección `[..]`.
- Tiempo: `TimeProvider` inyectado (no `DateTime.Now`).
- `async`/`await` de punta a punta; `CancellationToken` en toda operación de I/O; sin `.Result`/`.Wait()`; `ConfigureAwait(false)` en librerías.
- `IDisposable`/`IAsyncDisposable` con `using`.
- Sin `null!` para silenciar advertencias; sin `dynamic`.

## Analizadores
- `.editorconfig` versionado, Roslyn analyzers, StyleCop, `dotnet format` en CI.
