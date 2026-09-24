# Catálogo de Code Smells y Refactorizaciones

Agnóstico de lenguaje. **Regla de oro**: refactoriza solo con la suite en verde y en micro-pasos.

## 1. Smells → Refactorización
### Bloaters
| Smell | Señal | Refactorización |
|-------|-------|-----------------|
| Long Method | >20 líneas, varios niveles de abstracción | Extract Method, Replace Temp with Query, Decompose Conditional |
| Large Class / God Object | Muchas responsabilidades | Extract Class, Extract Interface |
| Primitive Obsession | `String email`, `int cents` | Replace Primitive with Value Object |
| Long Parameter List | >4 parámetros | Introduce Parameter Object, Preserve Whole Object |
| Data Clumps | Mismos campos viajan juntos | Extract Class / Value Object |

### Object-Orientation Abusers
| Smell | Señal | Refactorización |
|-------|-------|-----------------|
| Switch/instanceof repetido | Mismo `switch` en varios sitios | Replace Conditional with Polymorphism / sealed types + pattern matching |
| Temporary Field | Campos usados solo a veces | Extract Class, Introduce Null Object |
| Refused Bequest | Subclase ignora herencia | Replace Inheritance with Delegation |

### Change Preventers
| Smell | Señal | Refactorización |
|-------|-------|-----------------|
| Divergent Change | Una clase cambia por razones distintas | Extract Class (SRP) |
| Shotgun Surgery | Un cambio toca muchas clases | Move Method/Field, Inline Class |
| Parallel Inheritance Hierarchies | Jerarquías espejo | Move Method, colapsar jerarquía |

### Dispensables
| Smell | Señal | Refactorización |
|-------|-------|-----------------|
| Duplicate Code | Bloques repetidos | Extract Method, Pull Up Method, Template Method |
| Dead Code | Sin referencias | Remove Dead Code |
| Speculative Generality | Abstracciones no usadas | Collapse Hierarchy, Inline Class |
| Comments (explicativos) | Comentario explica código confuso | Extract Method, Rename |
| Lazy Class | Clase sin valor | Inline Class |

### Couplers
| Smell | Señal | Refactorización |
|-------|-------|-----------------|
| Feature Envy | Método usa más datos de otra clase | Move Method |
| Inappropriate Intimacy | Clases conocen internals ajenos | Move Method, Hide Delegate |
| Message Chains | `a.b().c().d()` | Hide Delegate, Extract Method |
| Middle Man | Delegación pura | Remove Middle Man |

### Específicos de arquitectura y dominio
| Smell | Señal | Refactorización |
|-------|-------|-----------------|
| Anemic Domain Model | Entidades solo getters/setters | Move Behavior into Entity |
| Leaky Abstraction | Puerto expone tipos de infraestructura | Introduce Domain Type, mapear en adaptador |
| Framework in Core | Anotaciones de framework en dominio | Extract Adapter / Separate Model |
| Temporal Coupling | Métodos deben llamarse en cierto orden | Introduce Builder / Factory, constructor completo |
| Null Returns | Retorna `null` | Introduce Optional / Null Object / Result |
| Exceptions as Flow | Excepciones para control normal | Replace Exception with Result / Guard |

## 2. Recetas paso a paso
### Extract Method
1. Identifica un fragmento con un propósito claro.
2. Crea método con nombre de intención.
3. Copia el fragmento; pasa como parámetros las variables locales usadas.
4. Reemplaza el fragmento por la llamada. Ejecuta tests.

### Replace Primitive with Value Object
1. Crea tipo inmutable con validación en el constructor.
2. Añade tests de invariantes (RED→GREEN).
3. Sustituye usos de forma incremental (Parallel Change).
4. Elimina la primitiva expuesta.

### Replace Conditional with Polymorphism
1. Identifica el discriminador (tipo/estado).
2. Crea interfaz/sealed type con un método por rama.
3. Mueve cada rama a una implementación.
4. Sustituye el condicional por invocación polimórfica; ejecuta tests tras cada rama.

### Move Method (Feature Envy)
1. Copia el método a la clase cuyos datos usa.
2. Ajusta referencias; delega desde el original.
3. Ejecuta tests; elimina el original.

### Introduce Parameter Object
1. Crea clase con los parámetros agrupados.
2. Añade parámetro nuevo manteniendo el antiguo (Parallel Change).
3. Migra llamadores; elimina parámetros viejos.

### Parallel Change (Expand–Migrate–Contract)
1. **Expand**: agrega la nueva forma junto a la vieja.
2. **Migrate**: mueve los consumidores uno a uno.
3. **Contract**: elimina la vieja.
Úsalo para cambios de firma, puertos y contratos públicos.

## 3. Protocolo seguro
1. Suite verde (línea base).
2. Un solo cambio mecánico.
3. Ejecutar tests.
4. Verde → commit pequeño. Rojo → revertir.
5. No mezclar refactor con cambio de comportamiento en el mismo commit.

## 4. Prioridad
1. Violaciones arquitectónicas (Framework in Core, Leaky Abstraction).
2. Duplicación de conocimiento y God classes.
3. Primitive Obsession y condicionales repetidos.
4. Nombres y legibilidad.

## 5. Criterios de parada
- Sin duplicación de conocimiento.
- Cada clase con una razón de cambio.
- Métodos dentro de umbrales (`quality/clean-code/rules.md`).
- No refactorices "por si acaso": solo cuando el smell tenga costo real.
