# Fit, liderazgo y logística (etapas 1 y 3 del proceso)

Este documento cubre lo que **no** es técnico puro: la entrevista inicial con el Head of Engineering, la final con el Founder, y la logística del rol. Fuente: PDF de perfilamiento de talento de SaludTools y la conversación con la reclutadora.

## 1. El proceso completo

Ver la tabla de estado en [`README.md`](README.md).

## 2. Propósito del rol (tal como lo define SaludTools)

> "Referente técnico dentro del equipo. Responsable de la calidad arquitectónica de las funcionalidades que construye, de mentorizar a perfiles junior e intermedios, y de participar en las decisiones técnicas que afectan la evolución de la plataforma."

Frase clave para repetir con tus propias palabras en la etapa 1: **no es solo escribir código, es ser referente técnico + mentor + parte de las decisiones de arquitectura**, sin tener jerarquía formal sobre nadie.

## 3. Responsabilidades — con ejemplos propios para cada una

Preparate un ejemplo concreto de tu experiencia para cada fila (2-3 frases, formato situación → acción → resultado):

| Responsabilidad | Tu ejemplo (completar antes de la entrevista) |
|---|---|
| Diseño de arquitecturas / decisiones de escalabilidad, mantenibilidad, seguridad, costos | |
| Liderar refactorizaciones | |
| Definir estándares (SOLID, Clean Code, DDD — ver [`solid-principles/`](../../../solid-principles), [`clean-code/`](../../../clean-code), [`ddd/`](../../../ddd)) | |
| Code reviews exigentes pero constructivas | |
| Mentorizar developers junior/intermedios, pair programming | |
| Corresponsabilidad en producción — incidentes y post-mortems | |
| Evaluar tecnologías nuevas para adopción | |
| Redactar ADRs / documentación técnica | |

## 4. Entregables esperados a 6 meses (esto es lo que van a evaluar en la etapa 3)

- Diagnóstico técnico del módulo que te asignen.
- Liderazgo de **al menos una iniciativa estratégica**.
- ADRs y documentación entregados.
- Plan de mentoría **activo** con al menos un perfil junior o intermedio.
- Reducción **medible** de deuda técnica.

> Preguntas que calzan acá: "¿Cómo medirías una reducción de deuda técnica?", "¿Cómo elegís qué mentorizar primero en un perfil junior?", "Contame de una iniciativa técnica que lideraste de punta a punta."

## 5. Habilidades blandas — cómo las van a sondear

- **Liderazgo técnico sin jerarquía formal** — te van a preguntar cómo lográs que alguien haga algo sin ser su jefe. Respuesta ideal: con criterio técnico visible, code reviews útiles (no solo "esto está mal"), y explicando el "por qué", no imponiendo.
- **Explicar a no técnicos (Producto)** — practicá explicar un concepto técnico (ej: "qué es un circuit breaker" o "por qué reactivo") en una frase, sin jerga, con una analogía del dominio salud.
- **Madurez ante incertidumbre** — tené lista una historia real de una decisión tomada sin toda la información, y cómo la revisaste después.

## 6. Preguntas típicas de la etapa 1 (Head of Engineering) y 3 (Founder)

- "¿Por qué SaludTools? ¿Por qué un producto de salud?"
- "Contame de un conflicto técnico con un compañero y cómo lo resolviste."
- "¿Cómo mentorizás a alguien que está atascado sin darle la respuesta directa?"
- "¿Qué preguntarías vos si fueras el entrevistador?"
- "¿Cómo manejás el desacuerdo con una decisión de arquitectura ya tomada por el equipo?"

## 7. Logística del rol (confirmar que la aceptás conscientemente, no improvisar en la entrevista)

- **Modalidad:** 100% remota, full time, contrato de prestación de servicios indefinido.
- **Horario:** lunes a viernes 8:00–18:00 hora Colombia (UTC-5) — ajustar a tu zona horaria real.
- **Honorarios:** USD 3.000–3.100/mes, pago vía OnTop.
- **Despliegues nocturnos:** 1-2 veces por semana, ~21:00 hs Colombia.
- **Guardia rotativa semanal:** sin compensación adicional automática; horas extraordinarias (ej. fines de semana) se evalúan caso a caso (pago extra, tiempo libre, o combinación).
- **Stack de trabajo actual:** Bitbucket (experiencia en GitHub/Azure DevOps es transferible).

> Si algo de esto es un problema real para vos (horario, guardias sin pago fijo), es mejor plantearlo en la etapa 1, no descubrirlo después de una oferta.

## 8. Mensaje pendiente de enviar a la reclutadora (Ani) tras pasar la técnica

**Contexto:** no preguntar esto antes de saber si se pasó la entrevista técnica. Enviar recién cuando confirmen el avance a la siguiente etapa. Motivo: en un producto de salud con posible operación 24/7, un incidente de guardia puede no resolverse en minutos (infra caída, DB, dependencia externa) y tomar varias horas sin piso claro de compensación (ver sección 7 — "sin compensación adicional automática", "caso a caso").

> *Hola Ani, ¡gracias por la buena noticia! Antes de la próxima etapa quería consultarte un par de puntos sobre la logística del rol para tener todo claro:*
>
> *1) Sobre la guardia rotativa: ¿podrías darme más detalle de cómo funciona en la práctica? Puntualmente me interesa saber si puede incluir incidentes que tomen varias horas (por ejemplo, caída de infraestructura o servicios críticos), y si en esos casos hay algún tipo de compensación definida (pago extra, tiempo libre compensatorio), o si realmente se evalúa 100% caso a caso.*
>
> *2) ¿La guardia puede coincidir con fines de semana o feriados, y con qué frecuencia rota entre el equipo?*
>
> *3) Sobre los días libres/vacaciones: como al ser prestación de servicios entiendo que no hay vacaciones pagadas por ley, quería preguntar cómo lo manejan en la práctica con el equipo remoto. Por ejemplo, si quisiera tomar 15 días, ¿cómo es el proceso (aviso previo, aprobación, cobertura del trabajo mientras tanto)? Y como yo resido en Ecuador (no en Colombia), ¿los feriados/días no laborables se rigen por el calendario colombiano del equipo o puedo tomar los feriados de mi país?*
>
> *Lo pregunto porque, tratándose de un producto de salud, entiendo que puede haber incidentes de cierta criticidad, y quiero tener expectativas claras antes de avanzar. Quedo atento, ¡saludos!*

**Por qué esta redacción:** pide información, no exige nada — más fácil de responder y no posiciona como "difícil" antes de tener oferta. Menciona "criticidad"/"salud" para que la conexión con el riesgo la hagan ellos. Deja la puerta abierta para pedir después (ya en negociación de oferta) un mínimo garantizado por guardia o un ajuste en honorarios, en línea con lo discutido en la sección 7.

**Nota sobre vacaciones (contexto para vos, no para el mensaje):** al ser prestación de servicios, no hay obligación legal de vacaciones pagadas — ni bajo ley colombiana (no aplica, no es contrato laboral) ni bajo ley ecuatoriana (no te rige a vos como contratista de una empresa colombiana). En la práctica, cada empresa remota-first maneja esto por política interna, no por ley: puede ser "PTO ilimitado" de palabra pero sin pago si no trabajás esos días (ya que probablemente te facturan/pagan por período trabajado), o un número de días acordado en el contrato. Por eso vale la pena preguntar el mecanismo concreto (aviso, aprobación, si se descuenta del pago) antes de asumir que existe algo parecido a vacaciones pagadas. Sobre feriados: es razonable esperar que el horario laboral se rija por los feriados/calendario de Colombia (es donde está el equipo y probablemente los clientes/hospitales), no por los de Ecuador — pero confirmalo explícitamente en vez de asumirlo.

## 9. Checklist final antes de la etapa 1

- [ ] Un ejemplo concreto listo para cada fila de la tabla de la sección 3.
- [ ] Tu respuesta a "por qué SaludTools / por qué salud" ensayada en voz alta.
- [ ] Claridad sobre si el horario/guardias descritos en la sección 7 son viables para vos.
- [ ] 2-3 preguntas propias para hacerles (ver [`README.md`](README.md), sección "Preguntas para hacerles").
