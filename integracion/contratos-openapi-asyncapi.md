# Contratos: OpenAPI y AsyncAPI

## 🍎 Con manzanas
Un **formulario de pedido** impreso: antes de que nadie cocine o compre, todos acuerdan qué campos tiene, cuáles son obligatorios y qué respuesta vuelve. **OpenAPI** es el formulario de la *ventanilla* (pregunta y respuesta, HTTP). **AsyncAPI** es el formulario del *timbre* (un aviso que se publica en un canal y nadie contesta).

## 1 · Qué es cada uno

| | OpenAPI | AsyncAPI |
|---|---|---|
| Describe | API síncrona (REST) | Eventos/mensajes (Kafka, AMQP, MQTT) |
| Unidad | Ruta + método | Canal + operación (enviar/recibir) |
| Sirve para | Documentar, generar clientes, validar, simular (mock) | Lo mismo para eventos |
| Esquema de datos | JSON Schema | JSON Schema, Avro, Protobuf |

**Contrato primero (API First):** se escribe el contrato, se revisa con quien lo consume y *después* se implementa. Permite trabajar en paralelo contra un mock.

## 2 · Mismo pago, dos contratos

**OpenAPI 3.1 (crear un pago)**
```yaml
openapi: 3.1.0
info: { title: Pagos, version: 1.0.0 }
paths:
  /v1/pagos:
    post:
      summary: Crea un pago
      parameters:
        - in: header
          name: Idempotency-Key
          required: true
          schema: { type: string }
      requestBody:
        required: true
        content:
          application/json:
            schema: { $ref: '#/components/schemas/PagoNuevo' }
      responses:
        '201': { description: Creado }
        '409': { description: Clave de idempotencia repetida con otro cuerpo }
components:
  schemas:
    PagoNuevo:
      type: object
      required: [monto, moneda, cuentaOrigen]
      properties:
        monto: { type: string, description: Decimal como texto }
        moneda: { type: string, enum: [PEN, USD] }
        cuentaOrigen: { type: string }
```

**AsyncAPI 3.0 (evento `PagoConfirmado`)** 🔎 validar con la CLI de AsyncAPI
```yaml
asyncapi: 3.0.0
info: { title: Eventos de pagos, version: 1.0.0 }
channels:
  pagos.confirmados:
    address: pagos.confirmados.v1
    messages:
      PagoConfirmado:
        payload:
          type: object
          required: [pagoId, monto, moneda, ocurridoEn]
          properties:
            pagoId: { type: string }
            monto: { type: string }
            moneda: { type: string }
            ocurridoEn: { type: string, format: date-time }
operations:
  publicarPagoConfirmado:
    action: send
    channel: { $ref: '#/channels/pagos.confirmados' }
```

## 3 · Versionado

| Cambio | ¿Rompe? | Qué hacer |
|---|---|---|
| Agregar un campo opcional | No | Mismo `v1`, versión menor |
| Quitar o renombrar un campo, volverlo obligatorio | **Sí** | Nueva versión (`v2` / nuevo canal), convivir un tiempo, deprecar |
| Eventos con consumidores desconocidos | Riesgo alto | Registro de esquemas y compatibilidad (hacia atrás) antes de publicar |

## 4 · Qué debe dominar cada nivel

| 🟢 Junior | 🟡 Mid | 🔴 Senior |
|---|---|---|
| Leer un contrato y generar el cliente | Escribir uno, versionarlo y validar cambios en el pipeline | Política de deprecación, contrato como producto, revisión de cambios que rompen |

## 5 · Práctica
Los ejercicios (validar los contratos, generar un mock y un cliente) viven en el repo de ejercicios, no aquí.

Relacionado: [`README.md`](README.md) · [`microservices-patterns/README.md`](../microservices-patterns/README.md) (API-first) · [`system-design/03-api-styles.md`](../system-design/03-api-styles.md).
