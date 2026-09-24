# Arquitectura Hexagonal (Puertos y Adaptadores)

Origen: Alistair Cockburn (2005). Referencia de convenciones: `buckpal` (Tom
Hombergs, *Get Your Hands Dirty on Clean Architecture*).

## Invariantes (no negociables, en cualquier lenguaje)

1. **El dominio no importa nada de infraestructura ni de frameworks.** Ni
   anotaciones de ORM, ni decoradores HTTP, ni SDKs de nube. Es Java/TS/Python
   plano.
2. **Los puertos definen la frontera de la aplicación**, no del dominio
   puro. Un puerto de entrada expone un caso de uso; uno de salida es lo que
   el caso de uso necesita del exterior (persistencia, otro servicio, reloj).
3. **Un adapter siempre implementa un puerto.** Nunca se referencia un
   adapter directo desde `domain`/`application` — solo la interfaz.
4. **Los DTOs de infraestructura no cruzan al dominio.** Se mapean a Value
   Objects en el adapter de entrada, y de vuelta a primitivos/DTOs al salir.
   Nunca serializar una entidad de dominio directo en una respuesta HTTP.
5. **Una excepción que representa un hecho de negocio** (no existe, transición
   inválida) vive en el dominio, no en la capa de aplicación — es vocabulario
   del negocio, no un detalle de cableado. Se traduce a un código de
   transporte (HTTP, gRPC) en el adapter de entrada.
6. **Contrato vs. dato:** una interfaz solo cuando hay (o puede haber) más de
   una implementación intercambiable — eso son los puertos. Un Command o un
   DTO es un dato inmutable, nunca una interfaz.
7. **La concurrencia es infraestructura.** Reactivo vs. hilos (Java), event
   loop vs. workers (Node), async vs. sync (Python) es una decisión del
   adapter; nunca se filtra al dominio ni a la aplicación.

## Sobre "casos de uso"

El artículo original de Cockburn no exige "caso de uso" como artefacto de
código — solo dice que la especificación funcional se hace contra la
interfaz del hexágono interior. Lo obligatorio es el **puerto**; la
convención `XxxUseCase` → `XxxService` viene de la síntesis práctica de
`buckpal` (mezclando con Clean Architecture de Robert Martin). Vale aclararlo
si preguntan por qué "caso de uso" no está en la definición original.

## Cuándo elegirla (vs. clean, onion o capas simples)

- **Elegí hexagonal** cuando vas a intercambiar adapters reales (varias
  formas de persistencia, varios clientes de entrada: HTTP + mensajería) o
  cuando el dominio tiene reglas propias que merecen aislarse de un
  framework.
- **Elegí capas/MVC simple** para un CRUD con poca o ninguna regla de
  negocio: el costo de los puertos no se paga solo.
- Hexagonal, Clean y Onion son **variantes del mismo principio** (dominio
  aislado, dependencias hacia adentro) con distinto vocabulario — ver
  `architecture-clean` y `architecture-onion` para las diferencias de
  nomenclatura, no de fondo.

## Checklist de validación (para revisar código existente)

- [ ] Ningún archivo de `domain/` importa un framework o un driver.
- [ ] Cada adapter de salida implementa una interfaz que vive en
      `application/port/out` (o su equivalente).
- [ ] Ningún controller/router contiene una regla de negocio (solo mapea
      HTTP ↔ comando).
- [ ] Ninguna entidad de ORM se usa directamente como modelo de dominio.
- [ ] Las excepciones de negocio no son genéricas
      (`IllegalStateException`/`Error` sin nombre propio).

## Tests de arquitectura ejecutables (hacen fallar el build, no solo el prompt)

Una invariante que solo vive en un prompt puede ignorarse bajo presión — es
literalmente lo que falló en una evaluación real (ver
`entrevistas/kaizen/` del repo). Estos tests la hacen cumplir en CI.

**Java — ArchUnit** (`testImplementation 'com.tngtech.archunit:archunit-junit5:1.3.0'`):

```java
@AnalyzeClasses(packages = "com.demo.mascotas")
class ArchitectureTest {

    @ArchTest
    static final ArchRule domain_no_depende_de_infraestructura =
        noClasses().that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage("..infrastructure..", "..application..");

    @ArchTest
    static final ArchRule domain_no_usa_frameworks =
        noClasses().that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage(
                "org.springframework..", "jakarta.persistence..");

    @ArchTest
    static final ArchRule adapters_implementan_puertos =
        classes().that().resideInAPackage("..infrastructure.adapter..")
            .should().dependOnClassesThat().resideInAPackage("..application.port..");
}
```

**TypeScript — `dependency-cruiser`** (`.dependency-cruiser.cjs`):

```js
module.exports = {
  forbidden: [
    {
      name: 'domain-no-depende-de-infraestructura',
      severity: 'error',
      from: { path: '^src/.+/domain' },
      to: { path: '^src/.+/(infrastructure|application)' },
    },
    {
      name: 'domain-no-usa-nest-ni-orm',
      severity: 'error',
      from: { path: '^src/.+/domain' },
      to: { path: '^node_modules/(@nestjs|typeorm|prisma)' },
    },
  ],
};
```

Correr con: `npx depcruise src --config .dependency-cruiser.cjs`.

**Python — `import-linter`** (`.importlinter` o sección en `pyproject.toml`):

```ini
[importlinter]
root_package = app

[importlinter:contract:1]
name = El dominio no depende de infraestructura ni aplicación
type = forbidden
source_modules = app.domain
forbidden_modules = app.infrastructure, app.application
```

Correr con: `lint-imports`.

## Referencias

- Cockburn, A. — *Hexagonal Architecture* (2005), alistair.cockburn.us/hexagonal-architecture.
- Hombergs, T. — *Get Your Hands Dirty on Clean Architecture* (2019), github.com/thombergs/buckpal.
