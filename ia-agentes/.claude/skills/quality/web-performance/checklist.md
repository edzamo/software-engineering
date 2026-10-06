# Rendimiento web — checklist (Core Web Vitals)

Umbrales «bueno» medidos en campo/laboratorio (móvil primero): **LCP ≤ 2,5 s**, **INP ≤ 200 ms**, **CLS ≤ 0,1**. Lighthouse móvil ≥ 90 en Rendimiento como meta de partida.

## Antes de dar por terminada una página
1. **JavaScript:** el mínimo necesario. Sin framework si un `<script>` pequeño basta; hidratación diferida (`client:visible`/`idle`) en islas. Presupuesto orientativo: < 50 KB de JS propio por página sin islas.
2. **Imágenes:** formato moderno (WebP/AVIF), dimensiones `width`/`height` (sin CLS), `loading="lazy"` salvo la imagen LCP (`fetchpriority="high"`), tamaño según `images.md`.
3. **Fuentes:** `font-display: swap`, solo los pesos usados, subset si se puede, precarga (`preload`) de la fuente crítica; fuentes propias antes que de terceros.
4. **CSS:** crítico en línea o archivo único pequeño; sin librerías enteras para usar tres clases.
5. **Terceros** (analítica, mapas, chat, píxeles): cada uno es deuda de rendimiento y de privacidad; cargar tras interacción o con `defer`/`async`, y registrar su CSP (`owasp-security/static-sites.md`).
6. **Caché:** activos con hash en el nombre → `Cache-Control: public, max-age=31536000, immutable`; HTML con revalidación corta. En hosting estático suele configurarse en `_headers`/`netlify.toml`.
7. **Layout estable:** reservar espacio para imágenes, embeds y banners; nada que empuje contenido tras la carga.
8. **Móvil real:** probar en 3G/4G lento simulado, no solo en escritorio.

## Cómo medir
- Lighthouse (móvil) o PageSpeed Insights sobre la URL de preview/producción; WebPageTest si hace falta detalle.
- Registrar el resultado en el cierre de la tarea («LCP x s, CLS y»); un empeoramiento respecto a la línea base es un hallazgo `SHOULD_FIX`.

## Anti-patrones
Imágenes de varios MB; carrusel con JS pesado en la home; fuentes sin `swap`; scripts de terceros bloqueantes; layout que salta al cargar; pruebas solo en la máquina del desarrollador.
