# Clean / Hexagonal Architecture en .NET 8+

Implementa las reglas de `skills/architecture/*` en C#. ASP.NET Core y EF Core son detalles de infraestructura.

## 1. Proyectos y referencias permitidas
| Proyecto | Referencia a |
|----------|--------------|
| Domain | (nada; solo BCL) |
| Application | Domain |
| Infrastructure | Application, Domain |
| Api (host) | Application, Infrastructure (composition root) |

`Domain.csproj` y `Application.csproj` **sin** `Microsoft.AspNetCore.*`, `Microsoft.EntityFrameworkCore.*`, `Newtonsoft.Json`, SDK cloud.

## 2. Dominio
```csharp
public sealed record Email
{
    public string Value { get; }
    private Email(string value) => Value = value;

    public static Email Create(string raw)
    {
        ArgumentException.ThrowIfNullOrWhiteSpace(raw);
        var normalized = raw.Trim().ToLowerInvariant();
        if (!normalized.Contains('@')) throw new InvalidEmailException(normalized);
        return new Email(normalized);
    }
}
```
- Entidades con comportamiento y estado privado; setters `private`/`init`.
- Errores de dominio tipados; resultados con `Result<T>` o jerarquía sellada.

## 3. Aplicación
- Puertos de entrada: `IRegisterAdopter` o handlers CQRS; puertos de salida: `IAdopterRepository`, `IClock`.
- Si se usa **MediatR**: solo como mecanismo de despacho en Application; los handlers no dependen de `HttpContext`. Alternativa sin librería: interfaces directas.
- Validación de comandos con **FluentValidation** como *pipeline behavior*; invariantes de dominio en el dominio.
- Sin `DbContext`, `IActionResult` ni `ILogger` de infraestructura específicos en lógica de negocio (usar `ILogger<T>` abstracto solo si el equipo lo acepta).

## 4. Infraestructura
- `DbContext` **solo** aquí; entidades EF ≠ entidades de dominio (o configuración Fluent API sin atributos en dominio).
- Repositorios implementan puertos y mapean; `AsNoTracking()` en lecturas; sin exponer `IQueryable`.
- Migraciones EF Core versionadas; `HasQueryFilter` para multi-tenant/soft-delete con cuidado.
- Clientes HTTP: `HttpClientFactory` + Polly/`Microsoft.Extensions.Http.Resilience` (retry con jitter, timeout, circuit breaker).
- Traducir excepciones técnicas (`DbUpdateException`) a errores de aplicación.

## 5. API / Composition Root
- Minimal APIs o Controllers delgados; DTOs propios; `ProblemDetails` para errores (`AddProblemDetails`, `IExceptionHandler`).
- Registro DI en métodos de extensión por capa: `AddApplication()`, `AddInfrastructure(config)`.
- Autenticación JWT Bearer: validar `Issuer`, `Audience`, `Lifetime`, firma; `RequireAuthorization()` por defecto (`FallbackPolicy`).
- Autorización por recurso con `IAuthorizationService` (anti-IDOR).
- Rate limiting (`AddRateLimiter`), CORS explícito, HSTS, cabeceras de seguridad.
- Secretos: User Secrets/Key Vault; nunca en `appsettings.json` versionado.
- Options pattern con `ValidateDataAnnotations().ValidateOnStart()`.

## 6. Tests
| Nivel | Herramientas |
|-------|-------------|
| Dominio/aplicación | xUnit + FluentAssertions/Shouldly + NSubstitute/Moq (solo puertos) |
| Integración | `WebApplicationFactory` + Testcontainers |
| Arquitectura | **NetArchTest** / ArchUnitNET |
| Mutación | Stryker.NET |

```csharp
[Fact]
public void Domain_should_not_depend_on_infrastructure()
{
    var result = Types.InAssembly(typeof(Adopter).Assembly)
        .ShouldNot().HaveDependencyOnAny(
            "Acme.Adoption.Application", "Acme.Adoption.Infrastructure",
            "Microsoft.EntityFrameworkCore", "Microsoft.AspNetCore")
        .GetResult();

    Assert.True(result.IsSuccessful);
}
```

## 7. Anti-patrones
- Entidades de dominio con atributos `[Key]`, `[Column]`, `[JsonProperty]`.
- Handlers que reciben `HttpContext`.
- Repositorio genérico que expone `IQueryable` al dominio.
- `Service Locator` (`IServiceProvider` inyectado en lógica de negocio).
- `async void`, `.Result`, `.Wait()`.
- `DateTime.Now` en dominio.

## 8. Checklist
- [ ] Domain/Application sin paquetes de framework en el `.csproj`.
- [ ] `Nullable` habilitado; warnings como errores.
- [ ] `CancellationToken` propagado; `TimeProvider` inyectado.
- [ ] Autorización por defecto y por recurso.
- [ ] NetArchTest en CI.
