# System Design

Conceptos y patrones de diseño de sistemas a gran escala — la parte de "arquitecto" que no se ve en el código de un solo microservicio, sino en cómo se sostiene un sistema completo cuando pasa de 10 a millones de usuarios. Se arma incrementalmente, tema por tema.

## Temario / Progreso

| # | Tema | Archivo | Estado |
|---|---|---|---|
| 1 | Escalar de 0 a millones de usuarios | [`01-scale-from-zero-to-millions.md`](01-scale-from-zero-to-millions.md) | ✅ |
| 2 | Bases de datos: SQL vs NoSQL, ACID vs CAP theorem | [`02-databases-sql-vs-nosql.md`](02-databases-sql-vs-nosql.md) | ✅ |
| 3 | Estilos de API: REST vs GraphQL vs gRPC | [`03-api-styles.md`](03-api-styles.md) | ✅ |
| 4 | Back-of-the-envelope estimation | — | ⏳ |
| 5 | Diseño de un rate limiter | — | ⏳ |
| 6 | Diseño de un sistema de notificaciones | — | ⏳ |
| 7 | Diseño de un news feed / timeline | — | ⏳ |
| 8 | Diseño de un acortador de URLs | — | ⏳ |
| 9 | Casos de estudio reales (Netflix, Uber, Twitter, etc.) | — | ⏳ |

## Fuente

Los módulos parten del curso **[System Design Interview — ByteByteGo](https://bytebytego.com/courses/system-design-interview)**, resumidos a lo esencial (no son transcripciones completas) y con diagramas propios en Mermaid.

## Cómo seguimos trabajando

1. Se agrega **un tema a la vez**, en su propio archivo `NN-tema.md`.
2. Cada archivo prioriza diagramas y píldoras de repaso rápido sobre texto largo — este es material de consulta antes de una entrevista, no un libro.
3. Este índice se actualiza con cada módulo agregado.

Relacionado: [`microservices-patterns/`](../microservices-patterns) para patrones a nivel de comunicación entre servicios.

## Referencias

- Xu, A. — *System Design Interview – An Insider's Guide* (Vol. 1 y 2) — estructura y progresión de los temas de escalabilidad.
- [ByteByteGo](https://bytebytego.com/) (Alex Xu) — diagramas y explicaciones en video que sirvieron de base para este módulo.
