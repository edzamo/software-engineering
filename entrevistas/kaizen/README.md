# Kaizen — mejora continua para entrevistas técnicas

Carpeta de aprendizaje, no de procesos. Acá vive **el método** para rendir en una evaluación técnica en vivo (especialmente con IA) a nivel Senior / Tech Lead, los casos resueltos que lo ejemplifican y las retrospectivas de cada intento.

La lógica es la del Kaizen: cada entrevista, pase o no pase, deja una mejora concreta. Ninguna es un fracaso si alimenta el siguiente ciclo.

| Documento | Para qué sirve |
|---|---|
| Este `README.md` | El **protocolo**: qué hacer en cada fase de cualquier ejercicio. Leerlo 30 min antes de cada entrevista. |
| [`caso-adoptantes-mascotas.md`](caso-adoptantes-mascotas.md) | El protocolo aplicado a un enunciado real, minuto a minuto, con diagramas, ADRs, prompts, errores de IA a cazar, respuestas modelo y **rúbrica de autoevaluación**. |
| [`retrospectiva-2026-09-evaluacion-con-ia.md`](retrospectiva-2026-09-evaluacion-con-ia.md) | Qué pasó la primera vez, la causa raíz y las acciones de mejora. |

---

## 1. La idea central

> **Yo diseño, la IA teclea.**

En una evaluación con IA no miden si el código funciona (la IA lo hace funcionar). Miden **quién pensó la solución**. Para eso tiene que verse, en pantalla y en voz alta:

1. Que **analicé** el problema: dominio, reglas, preguntas.
2. Que **decidí** entre alternativas, con trade-offs.
3. Que la IA **ejecutó mi plan** y que **la corregí** cuando se equivocó.
4. Que sé **criticar** el resultado final.

El conocimiento técnico (reactivo, patrones, AWS) es condición necesaria, pero no alcanza. El diferencial es el **proceso visible**.

---

## 2. El protocolo en 7 pasos

| # | Paso | Tiempo aprox. | Qué produce | IA |
|---|---|---|---|---|
| 1 | **Entender**: reformular y preguntar | 5 min | Supuestos anotados | ❌ |
| 2 | **Modelar y dibujar** | 10 min | Diagramas en `DESIGN.md` | ❌ |
| 3 | **Decidir** con alternativas | 5 min | 2-3 ADRs cortos | ❌ |
| 4 | **Planificar** en slices | 3 min | Lista priorizada + qué queda fuera | ❌ |
| 5 | **Ejecutar** conduciendo a la IA | 40 min | Código + tests, slice por slice | ✅ |
| 6 | **Demostrar** | 5-7 min | Tests en verde + flujo feliz y de error | — |
| 7 | **Autocrítica** | 5-8 min | 3-5 mejoras para producción + lo que dejé fuera | — |

### Paso 1 — Entender

- Reformular: *"Entiendo que hay que construir X para que Y pueda Z. Lo difícil está en A y B, voy a poner el foco ahí."*
- Leer entre líneas: ¿qué frase del enunciado es la que **realmente** evalúan? (estados, comunicación, consistencia, concurrencia...). El CRUD casi nunca es lo evaluado.
- Hacer 3-4 preguntas: reglas de negocio ambiguas, cardinalidades, qué pasa al borrar, persistencia real o no, qué les interesa ver más.
- **Anotar supuestos.** Si no responden, el supuesto escrito ya muestra criterio.

### Paso 2 — Modelar y dibujar

No hace falta UML formal. Hacen falta **los diagramas que respondan preguntas**, en Mermaid dentro de `DESIGN.md` (2 minutos cada uno, se ven en el IDE y después son input para la IA):

| Diagrama | Responde a | Cuándo |
|---|---|---|
| Contexto / componentes (`flowchart`) | ¿Qué servicios, bases y colas hay y cómo se hablan? | Siempre que haya más de un servicio |
| Modelo de dominio (`classDiagram`) | ¿Qué entidades, value objects e invariantes hay? ¿Qué pertenece a qué contexto? | Siempre |
| Estados (`stateDiagram-v2`) | ¿Qué ciclo de vida tiene la entidad principal? | Si el enunciado menciona estados, flujos o aprobaciones |
| Secuencia (`sequenceDiagram`) | ¿Qué recorre la petición más importante, y dónde puede fallar? | Siempre, para el flujo clave |

Mientras dibujo, digo en voz alta **por qué**: *"Mascota referencia al adoptante solo por ID porque son contextos distintos"*.

### Paso 3 — Decidir con alternativas

Formato fijo para cada decisión importante (2-3 como máximo):

> **Opción A:** ... **Opción B:** ... **Elijo A porque** [volumen / equipo / simplicidad / tiempo], **sacrifico** ..., y **migraría a B si** ...

Si los términos *trade-off* o *ADR* no te salen naturales, repasá primero [`software-architectures/decisiones-de-arquitectura.md`](../../software-architectures/decisiones-de-arquitectura.md).

Decisiones típicas: sync vs async, quién es dueño de un dato, SQL vs NoSQL, monolito modular vs microservicios, en memoria vs persistencia real, WebFlux vs MVC + virtual threads.

### Paso 4 — Planificar en slices

- Slices **verticales**, ordenados por lo que más pesa en la evaluación (no por capas).
- Decir en voz alta qué queda **fuera** y por qué.
- *"Si el tiempo no alcanza, prefiero 3 slices impecables que 6 a medias."*

### Paso 5 — Ejecutar conduciendo a la IA

- La IA recibe **`DESIGN.md` + un slice concreto**, nunca el enunciado crudo con "hacé todo".
- **Nunca** pedirle que asuma el rol que me están evaluando ("actuá como arquitecto").
- Pedir **tests primero** y esperar mi OK antes de implementar.
- Por cada diff: **✅ acepto porque... / ✏️ corrijo porque... / ❌ descarto porque...**
- Si se equivoca, **frenar en el momento**: *"Paro acá, esto no es lo que quiero"*. Corregir a la IA en vivo es la mejor señal de seniority.
- Agentes propios: presentarlos en **1 minuto** como "mi forma de trabajar", no en 15.

Plantilla de prompt por slice:

```text
Contexto: DESIGN.md adjunto. [Arquitectura, versión de Java, restricciones].
Tarea SOLO del slice N: [qué, dónde].
Reglas que deben cumplirse: [lista de invariantes / casos].
1. Primero los tests para esas reglas. Mostrámelos y esperá mi OK.
2. Después la implementación mínima para ponerlos en verde.
No toques nada fuera de [paquete/módulo].
```

### Paso 6 — Demostrar

- Tests en verde, **mostrando primero los del dominio**.
- El flujo feliz **y** los flujos de error (404, 409, 422, servicio caído). Los errores bien manejados muestran más que el flujo feliz.

### Paso 7 — Autocrítica

Tener lista la respuesta a *"¿qué cambiarías?"* **antes** de que la hagan. Recorrer esta lista y elegir las 3-5 más relevantes:

| Área | Pregunta a hacerse |
|---|---|
| Persistencia | ¿Es en memoria? ¿Qué se toca al pasar a una base real? (solo el adapter) |
| Errores | ¿Un `Mono` vacío devuelve 404? ¿Hay manejo global? ¿Los códigos HTTP son los correctos? |
| Tests | ¿Qué casos borde faltan? ¿Hay contract tests entre servicios? |
| Consistencia | ¿Hay carreras entre servicios? ¿Idempotencia en reintentos? |
| Resiliencia | ¿Timeouts, retries, circuit breaker? |
| Observabilidad | ¿Correlation ID, logs estructurados, métricas? |
| Seguridad | ¿Autenticación, validación, PII en logs? (en salud y fintech, crítico) |
| Alcance | ¿Qué dejé fuera **a propósito** y por qué? |

---

## 3. Reglas de comunicación durante la sesión

- **Narrar decisiones, no acciones.** No "ahora creo el controller", sino "el controller solo traduce HTTP a comando; la regla vive en el dominio porque...".
- **Pregunta ambigua → aclarar:** *"Para responder lo que buscás: ¿te referís a X o a Y?"*. Nunca adivinar.
- **Decir lo que harías ≠ hacerlo.** Si el paso ideal es "entender la historia de usuario", hacerlo ahí mismo con el enunciado.
- **Explicar el problema, no la herramienta.** La herramienta es contexto de un minuto.
- **Mirar el reloj.** A mitad de tiempo, decir en voz alta qué slice se sacrifica si hace falta.

---

## 4. Senior vs Tech Lead

Un Senior **resuelve bien** el problema. Un Tech Lead además muestra cómo lo **resolvería un equipo**: prioriza con el negocio, pone en la balanza costo y operación, define estándares replicables, explica cómo adoptar la IA en el equipo y cierra con un roadmap. Tabla detallada en [`caso-adoptantes-mascotas.md` §11](caso-adoptantes-mascotas.md#11-senior-vs-tech-lead-qué-agrega-cada-nivel).

---

## 5. Plan de práctica

| Práctica | Frecuencia | Cómo |
|---|---|---|
| **Simulacro completo** | 1-2 por semana | Enunciado nuevo, 60 min cronometrados, grabándome. Primeros 25 min sin IA. Puntuar con la rúbrica de [`caso-adoptantes-mascotas.md` §12](caso-adoptantes-mascotas.md#12-rúbrica-de-autoevaluación-usala-después-de-cada-simulacro). |
| **Kata de diagramas** | 2-3 por semana, 15 min | Un enunciado → solo los 4 diagramas en Mermaid, sin código. |
| **Kata de alternativas** | 2 por semana, 10 min | Una decisión → A/B → trade-offs → decisión en 3 frases, en voz alta. Fuentes: [`system-design/`](../../system-design), [`microservices-patterns/`](../../microservices-patterns). |
| **Kata de autocrítica** | Cada vez que la IA me genere código | 3 cosas que cambiaría antes de aceptarlo. |

Enunciados para simulacros (mismo formato que el real: dos servicios + estados + comunicación):

1. **Pacientes y Citas**: estados `agendada → confirmada → atendida / cancelada / no-show`.
2. **Clientes y Pedidos**: estados `creado → pagado → enviado → entregado / devuelto`, validación de stock en otro servicio.
3. **Conductores y Viajes**: estados `solicitado → asignado → en curso → finalizado / cancelado`, asignar el conductor disponible más cercano.
4. **Bibliotecas y Préstamos**: estados `disponible → prestado → vencido → devuelto`, límite de préstamos por socio.

---

## 6. Checklist de 30 segundos (leer justo antes de entrar)

- [ ] Los primeros 25 minutos, **sin IA**.
- [ ] Reformulo, pregunto y anoto supuestos.
- [ ] Dibujo dominio + estados + secuencia (+ componentes).
- [ ] 2-3 decisiones con alternativas y trade-offs.
- [ ] La IA recibe **mi** `DESIGN.md`, slice por slice, tests primero.
- [ ] Corrijo en voz alta al menos un error de la IA.
- [ ] Demuestro flujo feliz y flujos de error.
- [ ] Cierro con 3-5 mejoras sin que me las pidan.
- [ ] Pregunta ambigua → pido aclaración.

---

## 7. Registro Kaizen (PDCA)

Una fila por entrevista o simulacro. **Plan** = qué quería mejorar; **Do** = cómo fue; **Check** = puntaje de la rúbrica; **Act** = qué cambio para la próxima.

| Fecha | Tipo | Plan (foco) | Check (rúbrica /22) | Act (próxima mejora) |
|---|---|---|---|---|
| 2026-09 | Entrevista real (evaluación con IA) | — (sin protocolo) | 4 | Adoptar este protocolo; primeros 25 min sin IA; dibujar siempre. [Retrospectiva](retrospectiva-2026-09-evaluacion-con-ia.md) |
| | | | | |
