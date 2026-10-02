# Arquitectura Hexagonal (Puertos y Adaptadores) — Reglas

Agnóstico de lenguaje y framework. Las invariantes verificables por máquina están en `invariants.json`.

> **Alcance de la excepción de `application` (INV-02/INV-12):** es **específica de Java/Spring** (y análoga en Quarkus). Cada stack define su propia excepción en su archivo de `skills/stacks/`; .NET, Node/TypeScript y Python **no la heredan**: allí `application` sigue sin framework. Clean (CA-xx) y Onion (ON-xx) heredan INV-01..18 con esa misma salvedad.

## 1. Capas y responsabilidades
| Capa | Contiene | Puede depender de |
|------|----------|-------------------|
| **Dominio** | Entidades, Value Objects, Agregados, Domain Services, Domain Events, errores de dominio | Solo de sí misma (y librería estándar del lenguaje) |
| **Aplicación** | Casos de uso (puertos de entrada), puertos de salida (interfaces), DTOs/comandos/queries de aplicación | Dominio |
| **Adaptadores de entrada (driving)** | REST/gRPC/CLI/consumers, mappers de transporte | Aplicación (puertos de entrada) |
| **Adaptadores de salida (driven)** | Persistencia, mensajería, clientes HTTP, cache, reloj/ID | Aplicación (implementan puertos de salida) y Dominio |
| **Composition Root** | Cableado (DI), configuración, arranque | Todas |

## 2. Regla de dependencia
```
Adaptadores de entrada ──► Aplicación ──► Dominio ◄── (implementa) ◄── Adaptadores de salida
```
- Las dependencias de código fuente apuntan **siempre hacia el dominio**.
- Los adaptadores de salida **implementan** interfaces definidas en aplicación/dominio (inversión de dependencias).
- El flujo de control puede ir hacia afuera; la dependencia de código, nunca.

## 3. Invariantes (INV-xx)
- **INV-01** El dominio no importa nada de aplicación, adaptadores ni frameworks.
- **INV-02** La aplicación no importa adaptadores ni frameworks (web, persistencia, serialización, Lombok, `@Autowired`). **Única excepción** (ver INV-12): `org.springframework.stereotype.Service` y `org.springframework.transaction.annotation.*` en los casos de uso. `domain` no importa nada de framework.
- **INV-03** Los adaptadores no se referencian entre sí.
- **INV-04** Cada interacción externa de I/O (BD, HTTP, cola, filesystem) se modela como **puerto**. Tiempo (`Clock`) e IDs aleatorios en el dominio son **WARN** (no bloquean) salvo que impidan tests deterministas; ver INV-13.
- **INV-05** Un adaptador solo traduce (mapear, serializar, llamar); no toma decisiones de negocio.
- **INV-06** Los modelos de transporte/persistencia nunca cruzan hacia el dominio; se mapean en el borde.
- **INV-07** El cableado ocurre solo en el composition root. En Spring, el composition root es la **clase de arranque (`@SpringBootApplication`) más el component-scan**; `@Service`/`@Component` en casos de uso y adaptadores es cableado permitido, y un `@Configuration` explícito (p. ej. para `Clock`, que puede declararse en la propia clase de arranque) solo se añade cuando se necesite. No se exige un paquete `bootstrap`. Prohibido cablear dentro de `domain`/`application` con lógica de construcción manual de adaptadores.
- **INV-08** Cada caso de uso es invocable y testeable sin infraestructura real.
- **INV-09** El dominio no lanza ni captura excepciones de infraestructura; los adaptadores de salida las traducen a errores de dominio/aplicación (incluidas las de Spring Data y locking optimista). Se tolera como **WARN** que un único punto de la entrada (`ApiExceptionHandler`) las traduzca, siempre que el proyecto lo declare explícitamente como deuda.
- **INV-10** Sin ciclos entre módulos/paquetes.
- **INV-11** El mapeo dominio ↔ persistencia es **biyectivo**: todo estado del dominio se puede guardar y recuperar sin pérdida (p. ej. `PAID/PREPARING/READY/TAKEN` no pueden colapsar en un único valor de BD). Se exige un test de **ida y vuelta** (`toEntity` → `toDomain`) por cada valor de enum/estado.
- **INV-12** `domain` sin Lombok ni ninguna anotación/tipo de framework. `application` sin Lombok, `@Slf4j`, `@Autowired` ni tipos web/persistencia; **excepción pragmática:** los casos de uso pueden llevar `@Service`, y `@Transactional` **solo en los métodos que escriben en más de un puerto** (INV-18); nunca a nivel de clase por defecto (metadato declarativo, evita un `@Configuration` solo para cablearlos). Inyección por constructor explícito. El test de arquitectura permite solo esas dos anotaciones (y los tipos de sus miembros) de `org.springframework` en `application`.
- **INV-13** (WARN) El dominio es dueño de su identidad y de las transiciones de estado. Tiempo e IDs aleatorios generados en el dominio son WARN salvo que impidan tests deterministas; se exige que el dominio ofrezca una sobrecarga que reciba el ID (p. ej. `Order.create(UUID, ...)`) y que el reloj entre por `Clock`. El **adaptador** no debe generar IDs de negocio ni fijar el reloj (WARN).
- **INV-14** Cada caso de uso tiene al menos un adaptador de entrada; un caso de uso sin adaptador es funcionalidad inalcanzable y se reporta como WARN.
- **INV-15** Los errores de dominio/aplicación (`NotFound`, transición inválida) se traducen a códigos de transporte en **un único** punto del adaptador de entrada (p. ej. 404/409/422), nunca a 500 por omisión.
- **INV-16** Los modelos de persistencia se nombran distinto del dominio (`OrderJpaEntity`, no `Order`) para evitar colisiones y nombres completamente calificados.
- **INV-17** Datos sensibles (PAN, secretos, PII) no se almacenan ni se serializan en `toString`; se enmascaran o se tokenizan en el borde.
- **INV-18** Un flujo que escribe en más de un puerto de salida (guardar orden + guardar pago) define su **unidad de trabajo** (transacción o compensación); nunca deja estado parcial. Basta con `@Transactional` **en ese método** del caso de uso (INV-12), no en toda la clase; los casos de uso de un solo `save` no lo necesitan; un decorador o `TransactionTemplate` es válido pero **no obligatorio**. Un test de rollback demuestra que no queda estado parcial.

## 4. Puertos
- **Puerto de entrada (driving)**: interfaz del caso de uso; verbo del negocio (`RegisterAdopter`, `ApproveAdoption`). Un caso de uso por interfaz.
- **Puerto de salida (driven)**: interfaz en lenguaje del dominio (`AdopterRepository`, `NotificationSender`); **nunca** nombres técnicos (`JpaRepository`, `KafkaProducer`).
- Tamaño: ≤7 métodos por puerto; preferir puertos por rol (ISP).
- Retornos y parámetros: tipos de dominio o DTOs de aplicación; nunca tipos del framework.

## 5. Adaptadores
- Un adaptador por tecnología por puerto. Convención: `<Agregado>PersistenceAdapter` para persistencia (`OrderPersistenceAdapter`, `InMemoryOrderRepository` para el fake), y `<Rol>Client` / `<Rol>Publisher` para el resto (`PaymentGatewayClient`, `OrderEventPublisher`). Evita el sufijo `Service` en adaptadores (reservado a casos de uso).
- Contiene mapeos `Modelo externo <-> Dominio`.
- Traduce errores técnicos a errores tipados del dominio.
- Debe tener **test de contrato** compartido con el fake en memoria.

## 6. Estructura de paquetes sugerida
```
<bounded-context>/
├── domain/            # entidades, VOs, eventos, errores (organizado por agregado/concepto: domain/order, domain/payment...; SIN subcarpeta `model`. Preferible agrupar por agregado/concepto (enums junto a su agregado); una carpeta `enums` se tolera si el dominio es pequeño)
├── application/
│   ├── in/            # casos de uso (también válido: port/in)
│   ├── out/           # puertos de salida (también válido: port/out)
│   └── service/       # implementación de casos de uso
├── adapter/
│   ├── in/            # web, messaging, cli
│   └── out/           # persistence, http, messaging
(bootstrap/)         # opcional: solo si hay configuración explícita; el composition root es la clase de arranque
```

## 7. Anti-patrones (bloquean)
- Entidad de dominio anotada con mapeo ORM o serialización.
- Caso de uso que recibe `HttpRequest`/`Message` del framework.
- Repositorio que expone `Query`/`Specification` del ORM al dominio.
- `Service` que llama directamente a un cliente HTTP concreto.
- Mapeo con pérdida de estado entre dominio y persistencia (ver INV-11).
- Enums duplicados sin uso (`Status` vs `OrderStatus`) entre capas sin mapeo explícito.
- Adaptador de salida con `throw new UnsupportedOperationException` ("TODO") registrado como bean activo.
- Dominio anémico: toda la lógica en servicios y entidades solo con getters/setters.
- "Shared kernel" que acumula utilidades técnicas usadas por el dominio.

## 8. Checklist de validación
- [ ] ¿Cada flecha de dependencia apunta al dominio?
- [ ] ¿Existe puerto para cada interacción externa?
- [ ] ¿Dominio y aplicación compilan sin frameworks en el classpath?
- [ ] ¿Existe fake en memoria para cada puerto de salida?
- [ ] ¿El cableado se limita a la clase de arranque + component-scan (y configuración explícita si hace falta)?
- [ ] ¿Existe un test de ida y vuelta por cada estado/enum mapeado a persistencia?
- [ ] ¿Los errores se traducen en un único manejador de entrada?
- [ ] ¿Los tests de arquitectura automáticos (ArchUnit/dependency-cruiser/NetArchTest) están definidos?

## 9. Tests de arquitectura automatizados
Cada stack debe incluir una regla ejecutable en CI que codifique INV-01..INV-03 y INV-10 (ver skills de stack).
