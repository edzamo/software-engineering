# Escalar de 0 a millones de usuarios

Cómo evoluciona la arquitectura de un sistema a medida que crece de un solo usuario a millones — cada etapa resuelve el cuello de botella que dejó la anterior. Es la progresión clásica de entrevista de system design.

## La evolución completa, en un diagrama

```mermaid
flowchart TB
    A["1. Un solo servidor<br/>(web + DB + cache juntos)"] --> B["2. Separar la DB<br/>del servidor web"]
    B --> C{"3. ¿Escalar vertical<br/>u horizontal?"}
    C -->|horizontal| D["4. Load Balancer<br/>+ varios servidores web"]
    D --> E["5. Replicación de DB<br/>master-slave"]
    E --> F["6. Capa de Cache"]
    F --> G["7. CDN para estáticos"]
    G --> H["8. Web tier sin estado<br/>(stateless)"]
    H --> I["9. Múltiples data centers<br/>+ GeoDNS"]
    I --> J["10. Message Queue<br/>para trabajo asíncrono"]
    J --> K["11. Logging, métricas<br/>y automatización"]
    K --> L["12. Sharding de la DB"]

    style A fill:#eaf0ec,color:#111
    style L fill:#0d7d72,color:#fff
```

## 1–2. Un servidor → separar la base de datos

Todo arranca en un solo servidor (web + DB + cache). El primer paso de escalado casi siempre es separar el **tier web** (tráfico) del **tier de datos** (base de datos), para poder escalar cada uno de forma independiente.

> 🧠 **Píldora — SQL vs NoSQL, la decisión rápida:**
> - **SQL** (MySQL, PostgreSQL, Oracle) — datos estructurados con relaciones claras, necesitás `JOIN`s, consistencia fuerte.
> - **NoSQL** (DynamoDB, Cassandra, MongoDB, Neo4j) — necesitás latencia muy baja, datos no estructurados/semi-estructurados, volumen masivo, o el modelo es clave-valor/grafo/documento.

## 3. Vertical vs horizontal scaling

| | Vertical ("scale up") | Horizontal ("scale out") |
|---|---|---|
| Cómo | Más CPU/RAM/disco al mismo servidor | Más servidores |
| Simplicidad | Más simple, sin cambios de arquitectura | Requiere load balancer, estado compartido |
| Límite | Tiene techo de hardware | Escala prácticamente sin límite |
| Riesgo | Single point of failure | Redundancia natural |

> 🧠 **Píldora:** vertical scaling es la respuesta correcta para un problema chico o de corto plazo; horizontal scaling es la que sostiene "millones de usuarios" — es la que se espera que defiendas en una entrevista.

## 4. Load Balancer

```mermaid
graph LR
    U1[Usuario] --> LB["Load Balancer<br/>(IP pública)"]
    U2[Usuario] --> LB
    U3[Usuario] --> LB
    LB -->|IP privada| S1[Servidor Web 1]
    LB -->|IP privada| S2[Servidor Web 2]
    LB -->|IP privada| S3[Servidor Web 3]
```

Los usuarios solo conocen la IP pública del load balancer; los servidores web quedan en una red privada, inalcanzables directamente desde internet. Resuelve: failover, disponibilidad, y picos de tráfico repartidos entre varios servidores.

## 5. Replicación de base de datos (master-slave)

```mermaid
graph TB
    App[Servidor Web] -->|writes| Master[(Master DB)]
    App -->|reads| Slave1[(Slave DB 1)]
    App -->|reads| Slave2[(Slave DB 2)]
    Master -.replica.-> Slave1
    Master -.replica.-> Slave2
```

- **Master:** todos los `INSERT`/`UPDATE`/`DELETE`.
- **Slaves:** todos los `SELECT` — se pueden agregar tantos como haga falta para repartir la carga de lectura.
- **Failover:** si un slave cae, se redirigen sus lecturas al master temporalmente. Si el master cae, se **promueve** un slave a nuevo master.

> 🧠 **Píldora:** la mayoría de los sistemas leen mucho más de lo que escriben — por eso escalar lecturas (más slaves) resuelve el 80% de la carga antes de tocar el master.

## 6. Cache (read-through)

```mermaid
sequenceDiagram
    participant Cliente
    participant App
    participant Cache
    participant DB

    Cliente->>App: GET /recurso
    App->>Cache: ¿existe la key?
    alt cache hit
        Cache-->>App: dato
    else cache miss
        App->>DB: query
        DB-->>App: dato
        App->>Cache: set(key, dato, TTL)
    end
    App-->>Cliente: respuesta
```

- Usar cache cuando el dato se **lee mucho** y se **modifica poco**.
- Definí una política de expiración (TTL): muy corta = recargás la DB seguido; muy larga = servís datos desatualizados.
- Eviction policy más común: **LRU** (Least Recently Used). También existen LFU y FIFO.
- Nunca un solo servidor de cache — es un single point of failure, hace falta distribuirlo.

## 7. CDN para contenido estático

Red de servidores geográficamente distribuidos que cachean assets estáticos (imágenes, video, CSS, JS) cerca del usuario.

- Si el CDN no tiene el archivo, lo pide al origen (tu servidor o S3), lo cachea con un TTL, y lo sirve desde ahí en los siguientes requests.
- Invalidar contenido: vía API del proveedor, o con **versionado de objetos** (`logo.png?v=2`) — más simple y más usado en la práctica.

## 8. Web tier sin estado (stateless)

```mermaid
graph LR
    subgraph stateful[" Con estado — problemático "]
        U1[Usuario A] -->|sticky session| S1[Servidor 1<br/>guarda su sesión]
    end
    subgraph stateless[" Sin estado — escalable "]
        U2[Usuario A] --> LB2[Load Balancer]
        LB2 --> S2[Cualquier servidor]
        S2 <--> Store[(Sesión compartida<br/>DB/Cache)]
    end
```

Sacar el estado de sesión del servidor web y guardarlo en un store compartido (DB, NoSQL o cache) permite que **cualquier** servidor atienda a **cualquier** usuario — sin eso, hacen falta "sticky sessions" que complican el auto-scaling y el manejo de fallas.

## 9. Múltiples data centers

Se usa **GeoDNS** para enrutar al usuario al data center más cercano. En operación normal se reparte tráfico entre data centers (ej. 50% US-East / 50% US-West); si uno cae, todo el tráfico se redirige al que sigue sano.

Desafíos: sincronizar datos entre data centers, manejar el caso de que el dato no esté replicado todavía en el destino del failover, y poder testear/desplegar de forma consistente en todas las ubicaciones.

## 10. Message Queue

```mermaid
graph LR
    P1[Productor] -->|publica mensaje| Q[("Cola")]
    P2[Productor] -->|publica mensaje| Q
    Q -->|consume| C1[Consumidor 1]
    Q -->|consume| C2[Consumidor 2]
```

Desacopla al productor del consumidor: el productor publica un mensaje aunque el consumidor esté caído; el consumidor procesa cuando puede, aunque el productor ya no esté activo. Productor y consumidor **escalan de forma independiente**. Caso típico: procesar imágenes subidas por el usuario de forma asíncrona en vez de bloquear el request.

## 11. Logging, métricas y automatización

- **Logging:** por servidor, o agregado en un servicio centralizado para poder correlacionar errores entre servicios.
- **Métricas:** a nivel host (CPU, memoria, I/O), a nivel de tier (toda la capa de DB/cache), y de negocio (usuarios activos diarios, retención, ingresos).
- **Automatización:** CI para verificar cada cambio, pipelines automatizados de build/test/deploy — necesario en cuanto hay más de un par de servidores para gestionar a mano.

## 12. Sharding de la base de datos

```mermaid
graph TB
    App[Servidor Web] -->|hash user_id % 4| Router{Shard router}
    Router --> S0[(Shard 0<br/>user_id % 4 = 0)]
    Router --> S1[(Shard 1<br/>user_id % 4 = 1)]
    Router --> S2[(Shard 2<br/>user_id % 4 = 2)]
    Router --> S3[(Shard 3<br/>user_id % 4 = 3)]
```

Cuando escalar el master verticalmente ya no alcanza (aunque existan instancias con 24 TB de RAM), se particiona la base en **shards** — cada uno con el mismo schema, pero con un subconjunto de los datos, repartidos según una **sharding key**.

**Los tres problemas reales de sharding:**
- **Resharding:** cuando un shard se llena o queda desbalanceado, hay que mover datos y actualizar la función de hash — **consistent hashing** es la solución estándar para minimizar cuántos datos se mueven.
- **Hotspot / celebrity key:** si la sharding key es algo como `user_id` y por mala suerte varias cuentas de altísimo tráfico caen en el mismo shard (el ejemplo clásico: varias celebridades con millones de seguidores en el mismo shard), ese shard se satura. Solución: asignar un shard dedicado a esas keys de alto tráfico, y particionar más si hace falta.
- **Joins y desnormalización:** hacer `JOIN` entre shards es costoso o directamente inviable — la salida típica es desnormalizar el modelo para que las queries frecuentes resuelvan con una sola tabla.

> 🧠 **Píldora:** elegir una buena sharding key es la decisión que más determina si el sharding funciona — tiene que distribuir la carga parejo, no solo el volumen de datos.

## Resumen — checklist para responder "¿cómo escalarías esto?"

- [ ] Web tier sin estado (stateless).
- [ ] Redundancia en cada capa (no single point of failure).
- [ ] Cachear todo lo que se lee frecuentemente y cambia poco.
- [ ] Soportar múltiples data centers.
- [ ] Servir assets estáticos desde un CDN.
- [ ] Escalar la capa de datos con sharding cuando vertical ya no alcanza.
- [ ] Separar responsabilidades en servicios independientes.
- [ ] Monitorear (logs + métricas) y automatizar el pipeline.

Relacionado: [`microservices-patterns/`](../microservices-patterns) profundiza en cómo se comunican esos servicios independientes entre sí (colas, eventos, resiliencia), y [`cloud-aws/`](../cloud-aws) en los servicios AWS concretos que implementan cada pieza de este diagrama (ELB, RDS + réplicas, ElastiCache, CloudFront, SQS/SNS, DynamoDB).
