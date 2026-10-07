# Tu práctica con Kafka: 4 pruebas de concepto

Resumen de los ejercicios de tu repositorio local `~/Documents/Personal/github/kafka` (cada uno tiene su propio `README.md` con el detalle), qué demuestra cada uno y **cómo contarlo en la entrevista**.

> Teoría: [`kafka.md`](kafka.md) · Comparación con RabbitMQ y colas: [`README.md` sección 5b](README.md#5b-kafka-vs-rabbitmq-vs-cola-gestionada-la-pregunta-clásica) · Quarkus/SmallRye: [`../frameworks/quarkus/smallrye-reactive-messaging.md`](../frameworks/quarkus/smallrye-reactive-messaging.md)

## 🍎 Con manzanas

Es la frutería con cuatro formas de usar el libro de ventas: **a mano** (cliente Java), con **un empleado que lo hace por ti** (Spring Kafka), con **cintas transportadoras configurables** (Spring Cloud Stream) y con **dos proveedores con llaves distintas** (multi-binder con Azure Event Hubs).

## Los ejercicios

| # | Ejercicio | Qué demuestra | Equivalente en Quarkus |
|---|---|---|---|
| 1 | **Cliente Java puro** (`kafka-clients`): `Producer` y `Consumer` sobre el topic `test10` | Key, partición, serialización, grupo, `poll`, `auto.offset.reset` | Lo que SmallRye hace por debajo |
| 2 | **Spring Kafka simple**: `KafkaTemplate`, `@KafkaListener`, `NewTopic`, endpoint REST | Productor y consumidor declarativos; creación de topics por código | `Emitter` y `@Incoming` |
| 3 | **Spring Cloud Stream funcional**: `Consumer<Message<String>>` y `StreamBridge` contra **Event Hubs** | Modelo de funciones y bindings; Kafka sobre Azure | Canales de SmallRye |
| 4 | **Multi-binder**: dos binders (`kafka1`, `kafka2`), cabeceras de banca, envelope CloudEvents, WebFlux | Varias conexiones en una app; headers de trazabilidad; mensaje de pago de archivos | Canales con distinta configuración y `OutgoingKafkaRecordMetadata` |

## Qué te permite decir en la entrevista

| Pregunta probable | Tu respuesta apoyada en la práctica |
|---|---|
| **¿Has usado Kafka?** | "Sí: en el banco y en pruebas de concepto propias, desde el cliente Java puro hasta Spring Cloud Stream." |
| **¿Has usado SmallRye Reactive Messaging o algo equivalente?** | "El equivalente en Spring: Spring Cloud Stream. Usé el modelo funcional, bindings y `StreamBridge`, que son los canales y el `Emitter` de SmallRye." |
| **¿Has usado Kafka en Azure?** | "Sí, con **Event Hubs por su endpoint compatible con Kafka**: `SASL_SSL` con la cadena de conexión como usuario, y dos binders porque cada hub tiene su política de acceso." |
| **¿Cómo viajan los metadatos?** | "Con headers: en una prueba copié cabeceras de canal, dispositivo, sesión y trazabilidad al mensaje." |
| **¿Qué formato de mensaje usaste?** | "Un envelope tipo CloudEvents con `specversion`, `type`, `source`, `id`, `time` y `data`." |
| **¿Qué aprendiste?** | "Tres cosas: no bloquear el event loop de WebFlux al publicar, definir grupo y DLQ en los consumidores, y **no dejar credenciales en el repositorio**." |
| **¿Qué mejorarías?** | Usa la tabla de observaciones de cada README: `acks=all` e idempotencia, commit manual, key por entidad, manejo de errores con DLT, secretos en un gestor. |

## Equivalencias para memorizar

| Spring Cloud Stream | SmallRye Reactive Messaging |
|---|---|
| Binding (`receive-in-0`) | Canal (`@Incoming("receive")`) |
| `Consumer<Message<String>>` como bean | Método con `@Incoming` |
| `StreamBridge.send` | `Emitter.send` |
| Binder (`kafka`) | Conector (`smallrye-kafka`) |
| `spring.cloud.stream.bindings.*` | `mp.messaging.incoming/outgoing.*` |
| `@KafkaListener` + `KafkaTemplate` (ejercicio 2) | `@Incoming` + `Emitter` |

## ⚠️ Un punto de seguridad que conviene resolver

Los ejercicios 3 y 4 tienen **claves de Azure Event Hubs** en archivos de configuración (y en artefactos de `build/`). Si el repositorio está en GitHub, hay que **rotar las claves, sacar `build/` y limpiar el historial**. Pasos en el README del ejercicio 4. Si te preguntan por seguridad de secretos, **es un ejemplo honesto de aprendizaje**: *"Dejé una cadena de conexión en una prueba de concepto; la rotamos y pasé a variables de entorno y Key Vault"*. (Solo dilo si lo haces.)

## Lo que no hiciste todavía (y cómo decirlo)

No hay práctica con **SmallRye** propiamente, ni con **Outbox**, **Schema Registry**, **Dead Letter Topic** o **Kafka Streams** en estos ejercicios. Dilo con naturalidad: *"Conozco el concepto y lo he visto en proyectos, pero no lo he configurado de punta a punta; así lo haría..."* y apoya en [`kafka.md`](kafka.md).

## Para consolidar (si tienes tiempo antes de la entrevista)

1. Ejecuta el ejercicio 1 con **3 particiones**, dos consumidores del mismo grupo y varias keys: observa `partition` y `offset` por mensaje.
2. Corrige el `StringSerializer` por `StringDeserializer` del ejercicio 2 y observa el resultado.
3. Añade `group` y `enableDlq` al ejercicio 3.
