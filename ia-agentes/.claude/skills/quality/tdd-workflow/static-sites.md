# TDD en sitios estáticos (Astro y similares)

El ciclo RED → GREEN → REFACTOR de `protocol.md` aplica **al comportamiento**, no al contenido ni al estilo. En un sitio estático casi todo es contenido: probar lo que tiene lógica y delegar el resto a compuertas del build.

## Qué lleva TDD (RED primero, con evidencia)
- **Lógica pura** en `src/lib/` (filtrado de catálogo, formateo de precios/fechas, validaciones, generación de enlaces/UTM): tests unitarios (Vitest o el runner del proyecto), Dado/Cuando/Entonces.
- **Esquemas de datos** (JSON o content collections): un test que falle con un dato inválido (precio negativo, `id` repetido, imagen inexistente) antes de añadir la regla al esquema.
- **Islas con estado** (si las hay): test de render por estado relevante con Testing Library, sin acoplarse al DOM interno.
- **Funciones serverless / formularios**: TDD completo como cualquier caso de uso (validación del lado servidor, anti-spam, errores).

## Qué NO lleva TDD (pero tiene compuerta)
| Cambio | Compuerta en lugar de test |
|---|---|
| Copy, textos, orden de secciones | Revisión humana / guardián de marca del proyecto |
| Estilos, tokens, layout | `astro build` + revisión visual + checklist de rendimiento |
| Imágenes nuevas | Presupuesto de peso (`quality/web-performance/images.md`) |
| Configuración sin lógica | `astro check` + `astro build` |

## Compuertas del sitio (siempre)
1. `astro check` sin errores de tipos.
2. `astro build` sin errores.
3. Enlaces internos y activos referenciados existen (verificación de build o script).
4. Checklist de `quality/web-performance/checklist.md` en las páginas tocadas.
5. Smoke e2e (Playwright) solo para flujos críticos (contacto, reserva), no para cada página.

## Declararlo
El `CLAUDE.md` del proyecto indica qué carpetas llevan TDD (normalmente `src/lib/` y `functions/`) y los comandos de las compuertas. El orquestador no aplica el pipeline a lo que esta tabla clasifica como «sin TDD», pero **sí** exige la compuerta.
