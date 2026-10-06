# Stack Astro — índice (router)

Aplica a sitios Astro (estáticos por defecto; SSR/híbrido si el proyecto lo declara). Detecta con `package.json` (`astro`) y `astro.config.*`. Hereda `../frontend/index.md` (naming y TypeScript) y `../../architecture/frontend-component/rules.md`.

| Archivo | Cuándo |
|---------|--------|
| `best-practices.md` | Siempre: estructura, islas, imágenes, contenido/datos, variables de entorno, estilos, SEO/a11y |
| `../../quality/tdd-workflow/static-sites.md` | Al decidir **qué** se prueba (y qué no) en un sitio estático |
| `../../quality/web-performance/checklist.md` | Siempre antes de dar por terminada una página: Core Web Vitals, JS, fuentes, terceros |
| `../../quality/web-performance/images.md` | Al agregar o cambiar imágenes |
| `../../quality/owasp-security/static-sites.md` | Seguridad de un sitio estático (secretos, cabeceras, CSP, dependencias) |
| `../../quality/web-analytics/ga4.md` | Si el sitio mide tráfico con GA4 |

**Estilo de arquitectura:** `frontend-component`. Correspondencia con Astro: lógica pura → `src/lib/`; presentacionales → `src/components/*.astro`; contenedores → `src/pages/` y `src/layouts/`; datos → `src/data/` o content collections; islas (`client:*`) solo donde haga falta interacción.
