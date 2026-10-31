# Integración de sistemas

Cómo se conectan sistemas que no se conocen entre sí (core, satélites, terceros): **quién recibe, quién traduce, quién transporta y quién vigila**. Los patrones de comunicación, saga y outbox viven en [`microservices-patterns/`](../microservices-patterns/README.md); aquí está el **mapa** y lo que faltaba.

## 🍎 Con manzanas
Una frutería con varias sucursales. La **puerta de la calle** (API Gateway) recibe a los clientes de afuera y revisa quién eres. La **banda transportadora** (broker/ESB) mueve cajas entre sucursales. Los **guardias de pasillo** (service mesh) vigilan cómo se hablan los empleados *dentro* del local. El **catálogo de formularios** (OpenAPI/AsyncAPI) dice cómo se llena cada pedido.

## 1 · Quién resuelve qué

| Pieza | Problema que resuelve | Tráfico | Ejemplo en la nube |
|---|---|---|---|
| **API Gateway** | Un solo punto de entrada: auth, límites, enrutamiento, versionado | Norte-sur (afuera → adentro) | Azure API Management, AWS API Gateway, GCP Apigee 🔎 |
| **Service mesh** (sidecars) | mTLS, reintentos y métricas entre servicios sin tocar el código | Este-oeste (servicio ↔ servicio) | Istio, Linkerd |
| **Broker de mensajes** | Desacoplar en el tiempo (colas y eventos) | Asíncrono | Kafka, RabbitMQ, Service Bus, Event Hubs |
| **ESB** | Bus central que **enruta, transforma y orquesta** entre sistemas heredados | Ambos | Mule, WSO2, IBM MQ/ACE |
| **iPaaS** | El ESB como servicio en la nube, con conectores listos | Ambos | Logic Apps, Boomi 🔎 |

> **Ojo:** el *service mesh* **no es un bus de integración**: no transforma ni enruta mensajes de negocio, solo gobierna cómo se hablan los servicios. El bus es el ESB (o el broker).

## 2 · ESB: por qué existió y por qué se abandona

| | ESB clásico | Microservicios + broker |
|---|---|---|
| Dónde vive la lógica | En el bus (transformaciones, reglas) | En cada servicio |
| Riesgo | El bus se vuelve el **cuello de botella y el monolito** | Más piezas que operar |
| Equipo | Uno central lo toca todo | Cada equipo es dueño de lo suyo |
| Regla común | *"Tuberías tontas, extremos inteligentes"* | |

Se sigue viendo en banca porque conecta **SOAP/MQ y cores antiguos**. Se migra poco a poco con *strangler fig* ([`microservices-patterns/`](../microservices-patterns/README.md)).

## 3 · Coreografía vs orquestación (resumen; detalle en la saga)

| | Coreografía | Orquestación |
|---|---|---|
| Quién decide el siguiente paso | Cada servicio reacciona a eventos | Un coordinador |
| Acoplamiento | Bajo | Mayor, hacia el orquestador |
| Ver "qué pasó" | Difícil (hay que reconstruir con trazas) | Fácil (el estado está en un lugar) |
| Cuándo | Pocos pasos, sin compensaciones complejas | Muchos pasos, compensaciones y auditoría |

## 4 · Contratos
- [`contratos-openapi-asyncapi.md`](contratos-openapi-asyncapi.md): un contrato de API y uno de evento de un mismo pago.

## 5 · Qué debe dominar cada nivel

| 🟢 Junior | 🟡 Mid | 🔴 Senior |
|---|---|---|
| Diferencia API, cola y evento | Cuándo gateway, cuándo broker; contrato primero | Dónde **no** poner lógica (bus gordo), cómo gobernar versiones y reuso |

## 6 · Pendiente de estudiar (se completa al graduarse)
OAuth2/OIDC y mTLS · observabilidad (trazas, métricas, logs) · CDC. Seguimiento privado; aquí solo entra el concepto ya cerrado.

Relacionado: [`system-design/03-api-styles.md`](../system-design/03-api-styles.md) · [`messaging-streaming/`](../messaging-streaming) · [`cloud/`](../cloud/README.md).
