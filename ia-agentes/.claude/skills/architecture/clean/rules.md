# Clean Architecture — Reglas

Agnóstico de lenguaje y framework.

## 1. Círculos concéntricos (de dentro hacia afuera)
1. **Entities**: reglas de negocio empresariales (entidades, value objects, políticas). Máxima estabilidad.
2. **Use Cases (Interactors)**: reglas de negocio de la aplicación; orquestan entidades.
3. **Interface Adapters**: Controllers, Presenters, Gateways (implementaciones de puertos), mappers.
4. **Frameworks & Drivers**: web, BD, UI, dispositivos, SDKs.

## 2. Regla de dependencia
El código fuente solo depende hacia **adentro**. Nada en un círculo interno conoce nombres, tipos o formatos de un círculo externo.

## 3. Reglas
- **CA-01** Entities sin dependencias externas.
- **CA-02** Cada Use Case tiene un **Input Boundary** (interfaz de entrada) y un **Output Boundary** (interfaz del presentador).
- **CA-03** El Use Case devuelve un **Response Model** simple (estructura de datos) al Output Boundary; nunca entidades ni objetos del framework.
- **CA-04** El **Presenter** convierte el Response Model en un **View Model** listo para la vista; la vista es "tonta".
- **CA-05** Los **Controllers** convierten la petición externa en **Request Model** y llaman al Input Boundary.
- **CA-06** Los **Gateways** (interfaces definidas en la capa de Use Cases) se implementan en la capa de Interface Adapters / Frameworks.
- **CA-07** Los datos cruzan fronteras como estructuras simples aisladas (DTOs), no como filas de BD ni entidades ORM.
- **CA-08** El framework es un detalle: puede reemplazarse sin tocar Entities ni Use Cases.
- **CA-09** La BD, la web y la UI son plug-ins de la aplicación.
- **CA-10** Un Use Case por intención del usuario; nombre de negocio.

## 4. Flujo típico
```
Controller → Input Boundary (Use Case) → Gateway (interfaz) → [impl. externa]
                        │
                        └→ Output Boundary (Presenter) → View Model → Vista
```

## 5. Estructura sugerida
```
core/
├── entities/
└── usecases/
    ├── <caso>/ (input boundary, output boundary, request, response, interactor)
    └── gateways/
adapters/
├── controllers/
├── presenters/
└── gateways/         # implementaciones
infrastructure/       # frameworks, drivers, config
main/                 # composition root
```

## 6. Anti-patrones
- Use Case que devuelve entidad ORM o JSON de HTTP.
- Controller con lógica de negocio.
- Entities con anotaciones de framework.
- Presenter llamado desde la entidad.
- Use Case que conoce el mecanismo de entrega (HTTP status codes).

## 7. Relación con Hexagonal
Equivalencia: *Input Boundary* = puerto de entrada; *Gateway* = puerto de salida; *Controller/Presenter* = adaptadores. Aplica las mismas invariantes de dependencia de `skills/architecture/hexagonal/invariants.json`.

## 8. Checklist
- [ ] ¿Entities y Use Cases compilan sin frameworks?
- [ ] ¿Hay Input/Output Boundary por Use Case?
- [ ] ¿Los Response/Request Models son estructuras simples?
- [ ] ¿Los gateways se definen en el círculo del Use Case?
- [ ] ¿La vista no contiene lógica?
