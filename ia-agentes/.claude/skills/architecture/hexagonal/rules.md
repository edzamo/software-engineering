# Arquitectura Hexagonal (Puertos y Adaptadores) — Reglas

Agnóstico de lenguaje y framework. Las invariantes verificables por máquina están en `invariants.json`.

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
- **INV-02** La aplicación no importa adaptadores ni frameworks (incluidas anotaciones de DI, web, persistencia, serialización).
- **INV-03** Los adaptadores no se referencian entre sí.
- **INV-04** Cada interacción externa (BD, HTTP, cola, filesystem, reloj, generador de IDs, aleatoriedad) se modela como **puerto**.
- **INV-05** Un adaptador solo traduce (mapear, serializar, llamar); no toma decisiones de negocio.
- **INV-06** Los modelos de transporte/persistencia nunca cruzan hacia el dominio; se mapean en el borde.
- **INV-07** El cableado ocurre solo en el composition root.
- **INV-08** Cada caso de uso es invocable y testeable sin infraestructura real.
- **INV-09** El dominio no lanza ni captura excepciones de infraestructura; los adaptadores las traducen a errores de dominio/aplicación.
- **INV-10** Sin ciclos entre módulos/paquetes.
- **INV-11** El mapeo dominio ↔ persistencia es **biyectivo**: todo estado del dominio se puede guardar y recuperar sin pérdida (p. ej. `PAID/PREPARING/READY/TAKEN` no pueden colapsar en un único valor de BD). Se exige un test de **ida y vuelta** (`toEntity` → `toDomain`) por cada valor de enum/estado.
- **INV-12** Cero Lombok y cero anotaciones de framework/DI (`@Service`, `@Component`, `@Transactional`, `@Autowired`, `@Slf4j`) en `domain` y `application`. El cableado y las transacciones viven en adaptadores/`bootstrap`.
- **INV-13** El dominio es dueño de su identidad y de las transiciones de estado; los adaptadores no generan IDs de negocio ni ajustan el reloj. Tiempo e IDs entran por puertos (`Clock`, `IdGenerator`).
- **INV-14** Cada caso de uso tiene al menos un adaptador de entrada; un caso de uso sin adaptador es funcionalidad inalcanzable y se reporta como WARN.
- **INV-15** Los errores de dominio/aplicación (`NotFound`, transición inválida) se traducen a códigos de transporte en **un único** punto del adaptador de entrada (p. ej. 404/409/422), nunca a 500 por omisión.
- **INV-16** Los modelos de persistencia se nombran distinto del dominio (`OrderJpaEntity`, no `Order`) para evitar colisiones y nombres completamente calificados.
- **INV-17** Datos sensibles (PAN, secretos, PII) no se almacenan ni se serializan en `toString`; se enmascaran o se tokenizan en el borde.
- **INV-18** Un flujo que escribe en más de un puerto de salida (guardar orden + guardar pago) define su **unidad de trabajo** (transacción o compensación) en el borde; nunca deja estado parcial.

## 4. Puertos
- **Puerto de entrada (driving)**: interfaz del caso de uso; verbo del negocio (`RegisterAdopter`, `ApproveAdoption`). Un caso de uso por interfaz.
- **Puerto de salida (driven)**: interfaz en lenguaje del dominio (`AdopterRepository`, `NotificationSender`); **nunca** nombres técnicos (`JpaRepository`, `KafkaProducer`).
- Tamaño: ≤7 métodos por puerto; preferir puertos por rol (ISP).
- Retornos y parámetros: tipos de dominio o DTOs de aplicación; nunca tipos del framework.

## 5. Adaptadores
- Un adaptador por tecnología por puerto (`PostgresAdopterRepository`, `InMemoryAdopterRepository`).
- Contiene mapeos `Modelo externo <-> Dominio`.
- Traduce errores técnicos a errores tipados del dominio.
- Debe tener **test de contrato** compartido con el fake en memoria.

## 6. Estructura de paquetes sugerida
```
<bounded-context>/
├── domain/            # entidades, VOs, eventos, errores
├── application/
│   ├── port/in/       # casos de uso
│   ├── port/out/      # puertos de salida
│   └── service/       # implementación de casos de uso
├── adapter/
│   ├── in/            # web, messaging, cli
│   └── out/           # persistence, http, messaging
└── bootstrap/         # composition root / configuración
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
- [ ] ¿El composition root es el único lugar que conoce implementaciones concretas?
- [ ] ¿Existe un test de ida y vuelta por cada estado/enum mapeado a persistencia?
- [ ] ¿Los errores se traducen en un único manejador de entrada?
- [ ] ¿Los tests de arquitectura automáticos (ArchUnit/dependency-cruiser/NetArchTest) están definidos?

## 9. Tests de arquitectura automatizados
Cada stack debe incluir una regla ejecutable en CI que codifique INV-01..INV-03 y INV-10 (ver skills de stack).
