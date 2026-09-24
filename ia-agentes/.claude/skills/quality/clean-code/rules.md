# Clean Code — Reglas

Agnóstico de lenguaje. Los umbrales son valores por defecto; un stack puede endurecerlos.

## 1. SOLID
- **SRP**: una clase/módulo, una razón de cambio. Señal de alerta: nombres con "And/Manager/Helper/Utils" o >200 líneas.
- **OCP**: extiende por composición/polimorfismo, no editando condicionales (`switch` por tipo repetido → estrategia/sealed types).
- **LSP**: subtipos honran el contrato (precondiciones no más fuertes, postcondiciones no más débiles). Nada de `UnsupportedOperationException` en implementaciones.
- **ISP**: interfaces pequeñas por rol (≤7 métodos).
- **DIP**: depender de abstracciones; inyección por constructor.

## 2. DRY, KISS, YAGNI
- **DRY**: elimina duplicación de *conocimiento*, no de texto accidental. Regla de tres: abstrae a la tercera repetición.
- **KISS**: la solución más simple que cumple los requisitos y tests.
- **YAGNI**: no implementes lo que aún no se necesita.

## 3. Nombres
- Revelan intención: `elapsedDays`, no `d`.
- Sin desinformación ni prefijos húmedos (`strName`, `IUser`, `m_`).
- Clases: sustantivos; métodos: verbos; booleanos: `is/has/can/should`.
- Un concepto = una palabra (no mezclar `fetch/get/retrieve`).
- Lenguaje ubicuo del dominio; nada de nombres técnicos en el dominio.
- Longitud proporcional al alcance. Evita abreviaturas ambiguas.
- Constantes con nombre en lugar de números/strings mágicos.

## 4. Funciones
- Hacen **una** cosa, a **un** nivel de abstracción.
- ≤20 líneas; ≤4 parámetros (agrupar en objeto si más).
- Sin *flag arguments* booleanos (dividir en dos funciones).
- Sin efectos secundarios ocultos; separar comandos y consultas (CQS).
- Nivel de anidamiento ≤3; preferir *guard clauses* / early return.
- Complejidad ciclomática ≤10 (>15 es MUST_FIX).

## 5. Comentarios
- El código explica el *qué*; el comentario solo el *por qué* (decisión no obvia, referencia a requisito).
- Prohibido: código comentado, comentarios de redundancia, diarios de cambios, TODO sin ticket.
- Documentación pública (API) permitida y concisa.

## 6. Manejo de errores
- Excepciones/errores tipados con significado de dominio; no códigos de retorno mágicos.
- Nunca tragar excepciones (`catch {}`); registrar o propagar con contexto.
- No devolver ni pasar `null` como control de flujo: usa `Optional`/`Option`/`Result`/Null Object.
- Fallar rápido: validar precondiciones en el borde y en constructores de Value Objects.
- Los mensajes de error no filtran datos sensibles.

## 7. Objetos y datos
- Encapsulación: estado privado; exponer comportamiento, no estructura (Tell, Don't Ask).
- **Ley de Demeter**: evitar cadenas `a.b().c().d()`.
- Preferir inmutabilidad (Value Objects, records/data classes).
- Evitar *primitive obsession*: `Email`, `Money`, `AdopterId` en lugar de `String/Decimal`.
- Composición sobre herencia.

## 8. Tests
- Legibles como especificación; AAA; un concepto por test.
- Nombres descriptivos; sin lógica en tests; datos con builders.

## 9. Formato y organización
- Formateo automático (formatter de CI); nada de discusiones de estilo.
- Regla del periódico: lo importante arriba, detalles abajo.
- Cohesión: elementos relacionados cerca; imports sin comodines innecesarios.
- Módulos por característica/dominio, no por tipo técnico.

## 10. Umbrales resumen
| Métrica | Umbral | Severidad si excede |
|---------|--------|---------------------|
| Líneas por método | 20 | SHOULD_FIX |
| Parámetros | 4 | SHOULD_FIX |
| Complejidad ciclomática | 10 / 15 | SHOULD_FIX / MUST_FIX |
| Anidamiento | 3 | SHOULD_FIX |
| Líneas por clase | 200 | SHOULD_FIX |
| Métodos por interfaz | 7 | SHOULD_FIX |
| Duplicación | 3 ocurrencias | SHOULD_FIX |

## 11. Checklist de revisión
- [ ] ¿Cada función hace una sola cosa?
- [ ] ¿Los nombres se entienden sin comentarios?
- [ ] ¿Hay `null`, excepciones tragadas o números mágicos?
- [ ] ¿Hay tipos primitivos donde debería haber Value Objects?
- [ ] ¿Las dependencias se inyectan por constructor?
- [ ] ¿Se respeta la ley de Demeter?
