# Catálogo de refactorización (Fowler)

Mientras `quality-clean-code` dice **qué** buscar en una revisión, esto dice
**cómo arreglarlo** de forma segura: con la suite de tests en verde antes y
después de cada paso (ver `quality-tdd-workflow`). Nunca refactorizar sin la
red de tests corriendo.

## Regla de oro del refactor

Cada paso es pequeño y reversible: correr la suite, hacer un cambio
mecánico, correr la suite de nuevo. Si algo se rompe, se sabe exactamente
qué paso lo causó.

## Code smells más comunes → refactorización

| Smell | Señal | Refactorización |
|---|---|---|
| **Long Method / Long Function** | Más de ~20-30 líneas, hace varias cosas | *Extract Function* — dividir en funciones con nombres que expliquen cada paso |
| **Large Class** | Muchas responsabilidades, muchos campos que no se usan todos juntos | *Extract Class* — separar según qué campos/métodos cambian juntos (cohesión) |
| **Long Parameter List** | Más de 3-4 parámetros | *Introduce Parameter Object* — agrupar en un Value Object/record |
| **Duplicated Code** | La misma lógica en dos o más lugares | *Extract Function* + moverla a un lugar compartido; si es una regla de negocio, al dominio |
| **Primitive Obsession** | `String email`, `int cents` sueltos sin validación propia | *Replace Primitive with Object* — Value Object con invariantes (`Email`, `Money`) |
| **Feature Envy** | Un método usa más datos de otra clase que de la propia | *Move Function* — mover el método a la clase cuyos datos usa |
| **Anemic Domain Model** | Entidad con solo getters/setters, la lógica vive en un "Service" | *Move Function* de vuelta a la entidad; el service pasa a orquestar, no a decidir |
| **Switch/if-else repetido sobre un tipo** | El mismo `switch` sobre un enum/tipo aparece en varios lugares | *Replace Conditional with Polymorphism* (OOP) o *Replace Conditional with Pattern Matching exhaustivo* (Java 21 sealed + switch, TS discriminated union, Python `match`) |
| **Shotgun Surgery** | Un cambio de negocio obliga a tocar muchos archivos no relacionados | Revisar el límite de responsabilidad: probablemente falta una abstracción que agrupe esos cambios |
| **Data Clumps** | Los mismos 3-4 campos viajan juntos siempre como parámetros sueltos | *Introduce Parameter Object* — mismo remedio que Long Parameter List |
| **Comments explicando qué hace el código** | Un comentario que repite lo que ya dice el código | *Extract Function* con un nombre que reemplace al comentario; el comentario que sobrevive explica **por qué**, no **qué** |
| **Speculative Generality** | Una abstracción, hook de extensión o parámetro que nadie usa todavía (YAGNI) | *Inline* la abstracción hasta que un segundo caso de uso real la justifique |

## Cuándo NO refactorizar

- Sin tests que cubran el comportamiento actual — primero hay que agregar
  el test de caracterización (uno que documenta el comportamiento actual,
  aunque no sea "correcto"), después refactorizar.
- Mezclado con un cambio de comportamiento en el mismo commit — un commit de
  refactor no cambia ningún test; si hace falta cambiar un test, es una
  funcionalidad nueva, no un refactor.

## Referencias

- Fowler, M. — *Refactoring: Improving the Design of Existing Code* (2nd ed.,
  2018) — el catálogo completo y el detalle mecánico de cada refactorización.
