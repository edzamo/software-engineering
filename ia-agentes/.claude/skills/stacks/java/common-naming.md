# Java — Convenciones de Nombres Comunes (8 → 21+)

## Paquetes
- Minúsculas, sin guiones ni guiones bajos: `com.acme.adoption.domain`.
- Dominio inverso de la organización + bounded context + capa.
- Estructura: `domain` (por agregado/concepto, sin `model`), `application.in` / `application.out` (también válido `application.port.in/out`), `application.service`, `adapter.in.<tec>`, `adapter.out.<tec>`. `bootstrap` es opcional: el composition root es la clase de arranque.

## Tipos
| Elemento | Convención | Ejemplo |
|----------|-----------|---------|
| Clase / record / enum | `UpperCamelCase`, sustantivo | `Adopter`, `AdoptionId` |
| Interfaz de puerto de entrada | Verbo de negocio | `RegisterAdopter`, `ApproveAdoption` |
| Interfaz de puerto de salida | Rol de negocio | `AdopterRepository`, `NotificationSender` |
| Adaptador de persistencia | `<Agregado>PersistenceAdapter` (fake: `InMemory<Agregado>Repository`) | `AdopterPersistenceAdapter` |
| Otros adaptadores de salida | `<Rol>Client` / `<Rol>Publisher` (evitar sufijo `Service`) | `PaymentGatewayClient`, `AdoptionEventPublisher` |
| Servicio de aplicación | `<Caso>Service` o `<Caso>Interactor` | `RegisterAdopterService` |
| Controlador | `<Recurso>Controller` | `AdopterController` |
| Excepción | `<Causa>Exception` | `AdopterAlreadyExistsException` |
| Evento | Pasado | `AdopterRegistered` |
| Comando/Query | `<Acción>Command` / `<Consulta>Query` | `RegisterAdopterCommand` |
| Test | `<Clase>Test` / `<Clase>IT` | `RegisterAdopterServiceTest` |
| Anotación | Sustantivo/adjetivo | `@Idempotent` |
| Type parameter | Una letra mayúscula significativa | `T`, `ID`, `R` |

## Miembros
- Métodos y variables: `lowerCamelCase`; métodos con verbo (`register`, `findByEmail`).
- Booleanos: `isX`, `hasX`, `canX`.
- Constantes: `UPPER_SNAKE_CASE` (`static final`).
- Sin prefijos húmedos (`I`, `Abstract` salvo necesidad real, `m_`).
- Métodos de consulta: `find*` (opcional), `get*` (garantizado o lanza), `exists*`, `count*`.
- Fábricas estáticas: `of`, `from`, `create`, `empty`.

## Tests
- Métodos: `debe_<resultado>_cuando_<condición>` o `should<Result>When<Condition>`; también se acepta camelCase descriptivo, siempre consistente dentro del proyecto.
- Un archivo de test por clase bajo prueba; datos con `*Builder`/`*Mother`.

## Prohibido
- Nombres técnicos en el dominio (`JpaX`, `DtoX`, `EntityX`).
- Abreviaturas ambiguas, `Utils`/`Helper`/`Manager` sin responsabilidad clara.
- Nombres en idiomas mezclados dentro del mismo módulo.
