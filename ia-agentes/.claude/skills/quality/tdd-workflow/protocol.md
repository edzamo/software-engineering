# Protocolo TDD Estricto (RED → GREEN → REFACTOR)

Agnóstico de lenguaje. La herramienta de test concreta se elige desde `skills/stacks/`.

## Ley fundamental
> **No se escribe código de producción sin un test fallido que lo exija.**

Las tres leyes (Uncle Bob):
1. No escribas código de producción hasta tener un test unitario que falle.
2. No escribas más de un test del necesario para fallar (no compilar cuenta como fallar).
3. No escribas más código de producción del necesario para pasar el test actual.

## Precondiciones
- Fase 1 aprobada (puertos, casos de uso, invariantes definidos).
- Entorno de test operativo: la suite ejecuta y está **verde** (línea base).
- Plan de tests ordenado de simple a complejo.

## Orden de construcción por capas
Andamiaje sin lógica (permitido antes del primer RED) → Dominio → Casos de uso → Adaptadores de salida → Adaptadores de entrada → Composition root. Cada capa cierra con suite verde y REFACTOR antes de abrir la siguiente.

## Paso a paso

### 0. Plan
1. Toma un caso de uso/puerto de entrada.
2. Lista comportamientos como `Dado/Cuando/Entonces`: camino feliz mínimo, variaciones, casos borde, errores de dominio, invariantes.
3. Ordena: caso degenerado → simple → general → excepciones.

### 1. RED — escribir el test primero
1. Escribe **un** test para el siguiente comportamiento, nombrado con lenguaje de negocio: `debe_<resultado>_cuando_<condición>`.
2. Estructura **AAA** (Arrange-Act-Assert), una razón para fallar.
3. Usa la API pública (puerto de entrada); define los contratos que aún no existen.
4. Dobles solo para **puertos de salida**, reloj, generador de IDs.
5. **Ejecuta** y captura la salida.
6. **Criterio de RED válido**: falla por la aserción o por símbolo faltante intencional. Nombre del test + mensaje deben coincidir con lo esperado.
7. **Gate**: sin evidencia de RED, no se avanza.

### 2. GREEN — mínimo para pasar
1. Elige la estrategia más simple:
   - **Fake it**: devolver la constante.
   - **Triangulate**: agregar un segundo test que obligue a generalizar.
   - **Obvious implementation**: solo si es trivial y seguro.
2. Escribe solo lo necesario para este test. Nada de "por si acaso".
3. Ejecuta **toda** la suite. Debe estar verde.
4. **Gate**: si un test previo falla, corrígelo antes de seguir (no avances con rojo).

### 3. REFACTOR — limpiar con red de seguridad
1. Solo con la suite en verde.
2. Elimina duplicación (test y producción), mejora nombres, extrae funciones/clases, aplica patrones del catálogo `skills/quality/refactoring/catalog.md`.
3. Micro-pasos: ejecuta tests tras cada cambio. Si falla, **revierte** el último paso.
4. No cambies comportamiento; no añadas tests nuevos de comportamiento.

### 4. Repetir
Regresa a RED con el siguiente ítem del plan. Termina cuando todos los comportamientos estén cubiertos y no queden refactors pendientes.

## Estrategias de ordenamiento
- **Outside-in (London)**: empieza por aceptación/caso de uso con dobles de puertos; baja al dominio.
- **Inside-out (Chicago)**: empieza por el dominio puro; sube a casos de uso.
- Recomendado en arquitecturas de puertos: outside-in para casos de uso + inside-out para lógica de dominio compleja.

## Pirámide de tests
| Nivel | Qué prueba | Dobles | Velocidad |
|-------|------------|--------|-----------|
| Dominio | Entidades/VOs/Servicios | Ninguno | ms |
| Caso de uso | Orquestación | Puertos de salida | ms |
| Contrato | Puerto ↔ adaptador | Real vs fake | s |
| Aceptación / E2E | Escenarios de negocio | Mínimos | s–min |

## Reglas de calidad de tests (F.I.R.S.T.)
- **F**ast, **I**ndependent, **R**epeatable, **S**elf-validating, **T**imely (escritos antes del código).
- Un concepto por test; sin condicionales ni bucles; sin dependencia de orden.
- Datos de prueba mediante *builders*/*object mothers*.
- Asserts sobre **comportamiento observable**, no sobre invocaciones internas.
- Tiempo, aleatoriedad e IDs: siempre inyectados.

## Anti-patrones (rechazar)
| Anti-patrón | Por qué |
|-------------|---------|
| Test escrito después del código | Pierde la función de diseño |
| Test que pasa en la primera ejecución | No demuestra nada |
| `assertTrue(true)`/sin aserciones | Falsa cobertura |
| Ignorar/skip de tests rotos | Oculta regresiones |
| Mock del dominio | Acopla y vacía el test |
| Test que replica la implementación | Frágil ante refactor |
| Múltiples comportamientos por ciclo | Ciclo demasiado grande |
| `Thread.sleep`/tiempos reales | Flakiness |

## Bug fixing
1. Reproduce con un test que **falle** (RED).
2. Corrige mínimamente (GREEN).
3. Refactoriza.
Nunca se corrige un bug sin test de regresión.

## Evidencia exigida por ciclo
```
test: <nombre>
comando: <cmd>
resultado_red: FAIL — <mensaje>
resultado_green: PASS — <n tests>
refactor: <descripción o "ninguno">
```

## Métricas de salida
- Cobertura de líneas ≥ 85%, ramas ≥ 75%, dominio ≥ 95%.
- Mutation score recomendado ≥ 70% en dominio.
- Cero tests deshabilitados.

## Tests obligatorios para arquitectura hexagonal
1. **Round-trip de mapeo**: para cada valor de estado/enum del dominio, `toDomain(toEntity(x)) == x`. Es el primer test RED al introducir un adaptador de persistencia.
2. **Test de arquitectura** (ArchUnit / NetArchTest / dependency-cruiser) creado en el primer ciclo y ejecutado en CI.
3. **Test de transición de estado** por cada arista válida e inválida del agregado.
4. **Test de contrato** compartido entre fake en memoria y adaptador real.
