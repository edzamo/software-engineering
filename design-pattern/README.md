# Patrones de Diseño (GoF)

Píldoras de los patrones de diseño clásicos (Gang of Four), agrupados por categoría.

## Cobertura actual

```mermaid
graph TB
    subgraph creacionales[" Creacionales — documentado "]
        AF["Abstract Factory"]
        B["Builder"]
        FM["Factory Method"]
        P["Prototype"]
        S["Singleton"]
    end
    subgraph estructurales[" Estructurales — pendiente "]
        AD["Adapter"]
        BR["Bridge"]
        C["Composite"]
        D["Decorator"]
        F["Facade"]
        FL["Flyweight"]
        PR["Proxy"]
    end
    subgraph comportamiento[" De comportamiento — parcial "]
        COR["Chain of Responsibility"]
        CMD["Command"]
        IT["Iterator"]
        ST["Strategy"]
        V["Visitor"]
    end

    style creacionales fill:#0d7d72,color:#fff
    style estructurales fill:#f5e2d2,color:#111
    style comportamiento fill:#d9efec,color:#111
```

| Categoría | Patrones | Doc |
|---|---|---|
| **Creacionales** | Factory Method, Abstract Factory, Builder, Prototype, Singleton | [`creational-patterns.md`](creational-patterns.md) — con caso de ejemplo en [`factory-method-example-es.md`](factory-method-example-es.md) / [`-en.md`](factory-method-example-en.md) |
| **De comportamiento** | Chain of Responsibility, Command, Iterator | [`behavioral-patterns.md`](behavioral-patterns.md) |
| **De comportamiento** | Strategy, Visitor | ⏳ Pendiente de documentar |
| **Estructurales** | Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy | ⏳ Pendiente de documentar |

## Cuándo usar cada categoría (repaso rápido)

- **Creacionales** — cómo se instancian objetos, ocultando la lógica de creación (`new`) del código que los usa. Se usan cuando construir un objeto es complejo, condicional, o necesita desacoplarse de una implementación concreta.
- **Estructurales** — cómo se componen clases/objetos en estructuras más grandes sin acoplar sus implementaciones (envolver, adaptar, componer).
- **De comportamiento** — cómo se reparten responsabilidades y se comunican los objetos entre sí (algoritmos intercambiables, cadenas de manejo, iteración).

Relacionado: [`solid-principles/`](../solid-principles) — los patrones de diseño son, en gran parte, aplicaciones concretas de SOLID (ej. Strategy/Factory Method resuelven OCP; Adapter resuelve DIP).
