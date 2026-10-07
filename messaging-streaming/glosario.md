# Glosario — mensajería, eventos, pagos y resiliencia

Cada término en **una frase**, y luego **con manzanas**: una analogía única, la **frutería**, para que todo se pueda contar igual de junior a senior.

> **Nivel:** 🟢 junior (hay que dominarlo desde el primer día) · 🟡 mid (se espera criterio al usarlo) · 🔴 senior (se espera explicar trade-offs, límites y alternativas).

## La frutería (la analogía que usamos en todos los documentos)

Tienes una frutería que vende manzanas. Hay **clientes** que piden, una **caja** que cobra, una **bodega** que despacha y **contabilidad** que registra. Un banco procesa los cobros. Todo lo demás son formas de **avisarse** entre esas áreas sin pisarse.

## Mensajería y eventos

| Nivel | Término | En una frase | 🍎 Con manzanas |
|---|---|---|---|
| 🟢 | **Mensaje** | Un paquete de datos que un sistema envía a otro | Una nota: "3 manzanas para Ana" |
| 🟢 | **Evento** | Un hecho que ya pasó, en pasado e inmutable | "Se vendieron 3 manzanas a Ana" |
| 🟢 | **Comando** | Una orden para que alguien haga algo | "Despacha 3 manzanas a Ana" |
| 🟢 | **Productor / Consumidor** | Quien escribe mensajes / quien los lee | La caja anota la venta / la bodega la lee |
| 🟢 | **Broker** | El servidor que guarda y entrega los mensajes | El mostrador donde se dejan las notas |
| 🟢 | **Cola** | Los mensajes se consumen **una vez** y desaparecen | La fila con tickets: cada ticket lo atiende **un** cajero y se rompe |
| 🟢 | **Pub/Sub** | Cada suscriptor recibe **su copia** | El dueño grita "¡llegaron manzanas!" y cada área lo oye y toma nota |
| 🟢 | **Log de eventos** | Los hechos se anexan a un registro que **no se borra al leerse** | El libro de ventas con líneas numeradas: nadie arranca hojas |
| 🟢 | **Topic** | Un canal con nombre para un tipo de evento | El cuaderno "Ventas de manzanas" |
| 🟢 | **Partición** | Un topic se divide para repartir el trabajo; el orden vale solo dentro de una | El cuaderno tiene secciones por cliente; el orden se respeta dentro de cada sección |
| 🟢 | **Offset** | La posición de un mensaje dentro de su partición | El número de línea del cuaderno |
| 🟢 | **Key** | Lo que decide a qué partición va un mensaje | El nombre del cliente decide en qué sección se anota |
| 🟢 | **Consumer group** | Consumidores que se reparten las particiones | Un equipo de contadores: cada sección la lee **uno solo** del equipo |
| 🟡 | **Replicación (RF)** | Copias de cada partición en distintos servidores | Fotocopias del cuaderno en 3 oficinas |
| 🟡 | **ISR** | Las copias que están al día con la original | Las oficinas que ya copiaron la última línea |
| 🟡 | **Lag** | Cuántos mensajes le faltan por leer a un consumidor | Las líneas del cuaderno que contabilidad aún no ha leído |
| 🟡 | **Rebalanceo** | Reasignar particiones cuando entra o sale un consumidor | Un contador se enferma y sus secciones se reparten |
| 🟡 | **Dead Letter Queue / Topic (DLQ, DLT)** | Destino de los mensajes que fallan siempre, para no bloquear | La bandeja "revisar a mano" para las notas ilegibles |
| 🟡 | **Schema Registry** | Guarda el formato de los mensajes y vigila que los cambios sean compatibles | El formulario oficial de la nota: si lo cambias, avisas a todos |
| 🔴 | **CDC (Change Data Capture)** | Convertir los cambios de una base de datos en eventos | Un empleado que lee el libro contable y avisa de cada cambio |

## Garantías

| Nivel | Término | En una frase | 🍎 Con manzanas |
|---|---|---|---|
| 🟢 | **At-most-once** | Se entrega 0 o 1 vez: puede perderse | Gritas el pedido una vez; si no oyeron, se perdió |
| 🟢 | **At-least-once** | Se entrega 1 o más veces: no se pierde, **puede duplicarse** | Sigues gritando hasta que te confirmen; a veces confirman dos veces |
| 🟡 | **Exactly-once** | El efecto ocurre una sola vez; en la práctica, at-least-once más idempotencia | Se entrega varias veces, pero el efecto se aplica una sola |
| 🟢 | **Idempotencia** | Repetir la operación da el mismo resultado que hacerla una vez | El sello "YA COBRADO": si el ticket llega otra vez, ves el sello y no cobras |
| 🟡 | **Consistencia eventual** | Los datos se alinean con un pequeño retraso | La pizarra de precios se actualiza unos segundos después de cambiar el libro |

## Patrones

| Nivel | Término | En una frase | 🍎 Con manzanas |
|---|---|---|---|
| 🟡 | **Outbox** | Guardar el dato y el aviso en la misma transacción; otro proceso publica el aviso | En la misma hoja anotas "vendí 3 manzanas" y "avisar a bodega"; un mensajero lleva los avisos de la columna |
| 🟡 | **Polling Publisher** | Un proceso revisa cada cierto tiempo una tabla y publica lo pendiente | El mensajero repasa la columna "avisar" cada 5 minutos |
| 🟡 | **Saga** | Una operación larga en pasos, con pasos de deshacer si algo falla | Vender → cobrar → despachar. Si falla el despacho, **devolver el dinero** |
| 🟡 | **Compensación** | El paso que deshace un paso anterior | La devolución del dinero |
| 🟡 | **CQRS** | Un modelo para escribir y otro para consultar | El libro de ventas (escribir) y la pizarra de resumen (consultar) |
| 🔴 | **Event Sourcing** | El estado es la suma de todos los eventos | El stock no se guarda: se calcula sumando entradas y salidas del libro |
| 🔴 | **Strangler Fig** | Reemplazar un sistema viejo por partes, sin apagarlo de golpe | Reformas una sección de la tienda por vez, sin cerrarla |

## Resiliencia

| Nivel | Término | En una frase | 🍎 Con manzanas |
|---|---|---|---|
| 🟢 | **Timeout** | No esperar para siempre una respuesta | "Si en 5 segundos el proveedor no contesta, sigo" |
| 🟢 | **Retry + backoff + jitter** | Reintentar espaciando los intentos y con algo de azar | Llamas de nuevo a los 1, 2, 4 segundos; no todos al mismo instante |
| 🟡 | **Circuit Breaker** | Si un servicio falla mucho, dejas de llamarlo un tiempo | El fusible de la casa: salta, esperas, pruebas, y si todo bien vuelves a conectar |
| 🟡 | **Bulkhead** | Aislar recursos por dependencia | Una caja exclusiva para cada tipo de cliente: si una se atasca, las otras siguen |
| 🟡 | **Fallback** | Respuesta alternativa cuando algo falla | "Hoy no puedo confirmar el precio; te doy el de ayer y lo marco" |
| 🔴 | **Load shedding** | Descartar carga cuando se está saturado | Cierras la puerta un momento para atender a los que ya están dentro |

## Tiempo y programación

| Nivel | Término | En una frase | 🍎 Con manzanas |
|---|---|---|---|
| 🟢 | **Cron** | Una expresión que dice cuándo repetir algo | "Todos los lunes a las 8" |
| 🟢 | **Scheduler / Trigger / Job** | El motor / el cuándo / el qué | La agenda / la alarma / la tarea anotada |
| 🟡 | **Quartz** | Librería Java de agenda con tabla en base de datos | La agenda guardada en un cuaderno compartido |
| 🟡 | **Cluster (en Quartz)** | Varios nodos con la misma agenda; solo uno ejecuta cada tarea | Varios asistentes leen la misma agenda; el **primero que la tacha** la hace |
| 🟡 | **Misfire** | Una tarea que no corrió a su hora | Olvidaron la alarma porque la tienda estaba cerrada; al abrir, se hace **una vez** |
| 🔴 | **Workflow engine** | Orquesta procesos largos con esperas, reintentos y compensaciones | Un coordinador que lleva el pedido de punta a punta y recuerda dónde iba |

## Pagos

| Nivel | Término | En una frase | 🍎 Con manzanas |
|---|---|---|---|
| 🟢 | **Pasarela de pagos** | El sistema que procesa los cobros entre el comercio y el banco | La caja registradora conectada al banco |
| 🟡 | **Idempotency-Key** | Un identificador único por intento de pago | El número del ticket: si llega dos veces, es la misma compra |
| 🟡 | **PENDIENTE_CONFIRMACION** | Estado cuando el banco no respondió y **no sabes si cobró** | Mandaste el cobro y el cajero del banco no contesta: **no vuelvas a cobrar sin preguntar** |
| 🟡 | **Conciliación** | Comparar tus registros con los del banco | Cuadrar la caja al cierre del día |
| 🔴 | **Tokenización** | Reemplazar el dato sensible por un código sin valor | En vez de guardar la tarjeta, guardas un número de casillero |
| 🔴 | **Lock-in** | Quedar atado a un proveedor | Construir la tienda sobre un local que solo ese dueño puede alquilar |

Documentos relacionados: [`README.md`](README.md) · [`kafka.md`](kafka.md) · [`quartz-scheduler.md`](quartz-scheduler.md) · [`../system-design/caso-pasarela-pagos.md`](../system-design/caso-pasarela-pagos.md)
