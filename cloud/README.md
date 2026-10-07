# Cloud — Azure, AWS y GCP

Una carpeta por nube, con la misma idea: **entender qué servicio hace qué**, con manzanas y peras, para poder diseñar y explicar en una entrevista.

| Nube | Carpeta | Estado | Prioridad para Arkano |
|---|---|---|---|
| **Azure** | [`azure/`](azure) | ✅ Mapa, mensajería (Kafka), Kubernetes, Redis, datos, seguridad y DevOps | **Principal** (Arkano es partner de Microsoft) |
| **AWS** | [`aws/`](aws) | ✅ Servicios core, S3, RDS, EC2, DynamoDB, observabilidad, LocalStack | Para comparar (es tu nube más fuerte) |
| **GCP** | [`gcp/`](gcp) | ⏳ Pendiente (solo equivalencias) | Baja |

## 🍎 Con manzanas

Las tres nubes son **tres centros comerciales** con los mismos tipos de locales: cómputo, datos, mensajería, seguridad. Cambian los **nombres de los locales**, no lo que hacen. Si entiendes **qué necesita tu frutería** (¿una cola? ¿un libro de ventas? ¿una caja fuerte?), puedes alquilar el local equivalente en cualquiera.

## Dónde ir según la pregunta

| Si te preguntan... | Ve a |
|---|---|
| **Event Hubs, Service Bus, Event Grid** (el Kafka de Azure) | [`azure/mensajeria-event-hubs-service-bus.md`](azure/mensajeria-event-hubs-service-bus.md) |
| **AKS, Kubernetes, Container Apps** | [`azure/aks-kubernetes.md`](azure/aks-kubernetes.md) |
| **Redis** | [`azure/redis-cache.md`](azure/redis-cache.md) |
| **Cosmos DB, Key Vault, Managed Identity, Application Insights, Azure DevOps** | [`azure/datos-seguridad-y-devops.md`](azure/datos-seguridad-y-devops.md) |
| **Un system design en Azure** | [`azure/README.md`](azure/README.md) (sección 3) |
| **Servicios de AWS (S3, RDS, DynamoDB, EC2)** | [`aws/README.md`](aws/README.md) |
| **Mi pasarela / mis transferencias, rediseñadas en la nube** | [`../system-design/caso-transferencias-asincronas.md`](../system-design/caso-transferencias-asincronas.md) y [`../system-design/caso-pasarela-pagos-cloud.md`](../system-design/caso-pasarela-pagos-cloud.md) |

## La tabla de equivalencias (la que más sirve)

| Pieza | **Azure** | AWS | GCP |
|---|---|---|---|
| **Máquinas virtuales** | Virtual Machines | EC2 | Compute Engine |
| **Contenedores gestionados (sin Kubernetes a la vista)** | Container Apps | Fargate / App Runner | Cloud Run |
| **Kubernetes** | **AKS** | EKS | GKE |
| **Funciones (serverless)** | Azure Functions | Lambda | Cloud Run functions |
| **Base relacional** | Azure SQL / PostgreSQL | RDS / Aurora | Cloud SQL / AlloyDB |
| **NoSQL a escala** | **Cosmos DB** | DynamoDB | Firestore / Spanner |
| **Archivos** | Blob Storage | S3 | Cloud Storage |
| **Caché Redis** | **Azure Cache for Redis** | ElastiCache | Memorystore |
| **Cola con DLQ, sesiones, programación** | **Service Bus** | SQS (+ SNS) | Pub/Sub |
| **Log de eventos (Kafka)** | **Event Hubs** | MSK / Kinesis | Pub/Sub / Kafka gestionado |
| **Eventos "pasó algo"** | Event Grid | EventBridge | Eventarc |
| **Orquestación con compensación** | Durable Functions / Logic Apps | Step Functions | Workflows |
| **Disparo programado por pedido** | Mensaje programado de Service Bus | EventBridge Scheduler | Cloud Tasks |
| **Identidad de usuarios** | Entra ID | Cognito | Identity Platform |
| **Identidad de servicios (sin contraseñas)** | **Managed Identity** | IAM Role | Workload Identity |
| **Secretos** | Key Vault | Secrets Manager | Secret Manager |
| **API Gateway** | API Management | API Gateway | API Gateway / Apigee |
| **Observabilidad** | Azure Monitor + Application Insights | CloudWatch + X-Ray | Cloud Monitoring + Trace |
| **CI/CD** | Azure DevOps | CodePipeline | Cloud Build |
| **Infraestructura como código** | Bicep / Terraform | CloudFormation / CDK | Terraform |

> Los nombres y los niveles cambian con frecuencia: confirma en la documentación oficial antes de citar números exactos.
