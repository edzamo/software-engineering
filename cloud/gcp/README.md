# GCP (Google Cloud) — pendiente

> **Estado:** ⏳ por desarrollar. Por ahora solo están las **equivalencias** para no quedarse en blanco; el desarrollo completo (con manzanas y peras) vendrá cuando toque.
>
> Mientras tanto: [`../README.md`](../README.md) (tabla de las tres nubes) · [`../azure/`](../azure) · [`../aws/`](../aws) · el diseño de transferencias en GCP: [`../../system-design/caso-transferencias-asincronas.md`](../../system-design/caso-transferencias-asincronas.md) (sección 10.4).

## Las equivalencias esenciales

| Pieza | GCP | Equivalente en Azure | En AWS |
|---|---|---|---|
| Contenedores sin Kubernetes a la vista | **Cloud Run** | Container Apps | Fargate |
| Kubernetes | **GKE** | AKS | EKS |
| Funciones | Cloud Run functions | Functions | Lambda |
| Base relacional | Cloud SQL / AlloyDB / Spanner | Azure SQL / PostgreSQL | RDS / Aurora |
| NoSQL | Firestore | Cosmos DB | DynamoDB |
| Archivos | Cloud Storage | Blob Storage | S3 |
| Mensajería (cola y pub/sub) | **Pub/Sub** (orden por clave, dead-letter) | Service Bus / Event Hubs | SQS / SNS / MSK |
| Entrega programada | **Cloud Tasks** (`scheduleTime`) y Cloud Scheduler | Mensaje programado de Service Bus | EventBridge Scheduler |
| Orquestación | **Workflows** | Durable Functions | Step Functions |
| Secretos | Secret Manager | Key Vault | Secrets Manager |
| Identidad de servicios | Workload Identity | Managed Identity | IAM Role |

## Por desarrollar

- [ ] Mapa mental de GCP con manzanas
- [ ] Pub/Sub a fondo (frente a Kafka y Service Bus)
- [ ] GKE y Cloud Run
- [ ] Datos, seguridad y observabilidad
