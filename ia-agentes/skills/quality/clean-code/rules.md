# Clean Code — checklist de revisión

Las 4 capas, **en este orden de prioridad** — nunca discutir nombres de
variables si hay un problema de diseño sin resolver.

## 1. Correctitud (la más importante, siempre primero)

- ¿El código hace lo que dice? ¿Hay un test que falle si esto se revierte?
- Casos borde: `null`/`None`/`undefined`, colección vacía, negativos,
  concurrencia.
- ¿Un error se traga en silencio (`catch` vacío, `except: pass`,
  `.onErrorReturn` sin loggear)?

## 2. Diseño

- ¿La lógica de negocio está en la capa correcta (dominio/aplicación según
  la arquitectura del `DESIGN.md`), o se filtró a un controller, router o
  repository? Ver `architecture-hexagonal`/`architecture-clean`/`architecture-onion`.
- ¿Viola SOLID de forma evidente? (una clase con más de una razón para
  cambiar, una abstracción que ningún cliente usa, un cliente forzado a
  depender de métodos que no necesita).
- **DRY** — ¿la misma regla de negocio está duplicada en dos lugares que
  pueden desincronizarse?
- **KISS** — ¿hay una solución más simple que resuelve el mismo problema?
- **YAGNI** — ¿hay una abstracción, un parámetro o una capa de
  configuración que nadie pidió todavía?
- **Patrón forzado (over-engineering):** ¿se aplicó un patrón de diseño
  (Factory, Strategy, Observer) donde una función simple alcanzaba?

## 3. Legibilidad

- ¿Los nombres dicen qué hacen sin necesitar un comentario al lado?
- ¿Una función hace una sola cosa?
- **Idiomas modernos del lenguaje:** según la versión detectada del proyecto
  (`python3 .claude/scripts/detect_stack.py` + skill `stacks-<lenguaje>`),
  señalar como mejora **no bloqueante** un idioma disponible que no se
  aprovechó — ej. record patterns o `getFirst()` en Java 21+, `satisfies` o
  una unión de literales en vez de `enum` en TypeScript, `match` o
  `@dataclass(frozen=True)` en Python 3.10+. **Nunca** sugerir una feature de
  una versión mayor a la del proyecto.

### Patrones de diseño forzados (over-engineering)

Un patrón de diseño no es un logro en sí mismo — es la solución a un
problema concreto. Señalar cualquier patrón que aparezca sin que el
problema lo pida:

| Señal de alarma | Ejemplo típico |
|---|---|
| Una `Factory`/`AbstractFactory` para crear **un solo** tipo concreto, sin variación real | `PaymentFactory` que solo construye `CardPayment`, sin que exista otro tipo |
| Un `Strategy` con una sola implementación concreta | `DiscountStrategy` + `NoDiscount` como única clase, "por si después hay más" |
| Un `Builder` para un objeto de 2-3 campos, todos obligatorios | Un constructor normal alcanza; no hay telescoping constructor que resolver |
| Un `Decorator`/`Proxy` que envuelve sin agregar comportamiento real | Wrapper vacío que delega 1:1 sin lógica adicional |
| Capas de indirección (`interface` + `impl` + `factory`) sin segunda implementación real, ni planeada | Puerto/adaptador para un servicio interno que jamás se va a mockear ni reemplazar |

Nombrar el patrón, el problema real que resolvería, y por qué acá no aplica.
Si el patrón está bien aplicado (resuelve una variación real, presente o
inminente), decirlo también — no todo patrón es sobre-ingeniería.

## 4. Estilo

- Formato y convención de naming — lo que un linter debería resolver, no una
  discusión humana. Marcar siempre como **"nit"** (no bloqueante), nunca
  como razón para rechazar el PR.

## Formato del hallazgo

```text
[Capa] Archivo.ext:línea
<qué está mal, 1 línea>
Sugerencia: <corrección concreta>
```

Ejemplos:

```text
[Diseño] OrderService.java:34
Regla "no se puede cancelar un pedido enviado" duplicada acá y en OrderController:12 (DRY).
Sugerencia: mover la regla al método Order.cancel() del dominio.

[DRY] InvoiceService.java:12 y ReportService.java:20
Misma lógica de formateo de moneda repetida.
Sugerencia: extraer a un Value Object Money.

[Patrón forzado] PaymentFactory.java
Factory con una sola implementación real; no hay variabilidad que justifique el patrón.
Sugerencia: reemplazar por una función/constructor directo.

[Estilo - nit] UserController.ts:8
Nombre de variable abreviado sin necesidad (usr).
```
