# Patrones de diseño forzados (over-engineering)

Catálogo de calidad del kit (origen: Naru; los mappers, `ports/` y subcarpetas por tipo de pieza ya son parte del estándar). Un patrón no es un logro: resuelve un problema concreto. Se señala como hallazgo cuando aparece sin que el problema lo pida.

| Señal de alarma | Ejemplo típico |
|---|---|
| Factory/AbstractFactory para **un solo** tipo concreto, sin variación real | `MailFactory` que solo construye un `GmailMailer` |
| Strategy con una sola implementación y sin indicio de una segunda | `DeliveryStrategy` + un único `DriveDelivery` |
| Builder para un objeto de 2-3 campos obligatorios | Un constructor normal alcanza |
| Decorator/Proxy que delega 1:1 sin comportamiento | Wrapper vacío |
| Capas de indirección (interfaz + impl + factory) sin segunda implementación real ni necesidad de fake | Interfaz para un servicio interno que jamás se sustituye |
| Una interfaz por cada caso de uso | `CreateOrderUseCase` + `CreateOrderUseCaseImpl` |
| Subcarpetas por entidad dentro de una capa, o layout hexagonal in/out | `controllers/clients/`, `application/in/` |
| Mappers, presenters o entidades duplicadas sin diferencia ni formateo real | `OrderEntity` idéntica a `Order`; presenter que no formatea nada |
| Capas o subcarpetas vacías "por cumplir" | `frameworks/routes/` vacío |

## Cómo reportarlo
Nombra el patrón, el problema real que resolvería y por qué acá no aplica. Si el patrón está bien aplicado (resuelve una variación real, presente o inminente), dilo también: no todo patrón es sobre-ingeniería.

## Excepción importante
Un **puerto en `core/ports` con implementación en `frameworks` (backend) o `services` (frontend) y fake en memoria** para cada recurso externo (Prisma, correo, Drive, PDF) **sí está justificada**: se sustituye en tests y puede cambiar de proveedor. No se marca como over-engineering.

## Relacionados
DRY solo para conocimiento duplicado (no similitud casual), KISS y YAGNI: ver `../clean-code/rules.md`.
