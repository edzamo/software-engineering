# Entrevistas — bitácora de procesos

Carpeta viva: acá se va sumando un subdirectorio por cada proceso de entrevista técnica, con lo específico de esa empresa/rol (logística, entregables esperados, preguntas de fit) más los links a las fuentes técnicas de estudio, que viven de forma genérica en el resto del repo.

> La idea es no repetir contenido: lo técnico general (reactivo, microservicios, AWS, arquitectura) vive una sola vez en sus propias carpetas ([`frameworks/spring-boot/`](../frameworks/spring-boot), [`microservices-patterns/`](../microservices-patterns), etc.). Acá solo queda lo puntual de cada proceso y el mapa de a dónde ir a repasar cada cosa.

## Kaizen — el método

[`kaizen/`](kaizen) es lo primero que hay que leer antes de cualquier entrevista: el protocolo de 7 pasos para evaluaciones técnicas en vivo (con o sin IA), un caso resuelto de punta a punta con rúbrica de autoevaluación y las retrospectivas de cada intento.

## Procesos

Los procesos cerrados se mueven a [`archivo/`](archivo).

| Proceso | Rol | Stack | Estado | Carpeta |
|---|---|---|---|---|
| SaludTools | Desarrollador(a) Senior | Java 21, Spring WebFlux, DDD, AWS, microservicios | Cerrado ([retrospectiva](kaizen/retrospectiva-2026-09-evaluacion-con-ia.md)) | [`archivo/saludtools-desarrollador-senior/`](archivo/saludtools-desarrollador-senior) |

## Cómo se arma cada proceso nuevo

1. Carpeta con el nombre `<empresa>-<rol>/`.
2. Un `README.md` con: estado del proceso, checklist del día de la entrevista/prueba, preguntas para hacerles, y una tabla que linkea cada tema técnico a su carpeta genérica en el repo.
3. Un archivo aparte (ej. `fit-y-liderazgo.md`) para lo que es específico de esa empresa y no aplica a otro proceso (logística, entregables esperados, contactos).
4. Al cerrar el proceso (pase o no): retrospectiva en [`kaizen/`](kaizen), una fila en su registro PDCA, y la carpeta del proceso se mueve a `archivo/`.
