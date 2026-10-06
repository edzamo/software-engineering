# Astro — buenas prácticas

Principio rector: **HTML estático por defecto, JavaScript solo donde hay interacción**. Cada decisión se mide contra `quality/web-performance/checklist.md`.

## Estructura (`src/`)
- `pages/` rutas (un archivo = una ruta); solo componen layout + componentes + datos. Sin lógica de negocio.
- `layouts/` esqueleto (`<head>`, metadatos, fuentes, scripts globales). Un layout base por tipo de página.
- `components/` piezas reutilizables `.astro`, presentacionales (reciben props, sin `fetch`). Un componente, una responsabilidad.
- `lib/` (o `utils/`) lógica pura en TypeScript (formateo, filtrado, validaciones): **es lo que se prueba con tests unitarios**.
- `data/` o `content/` datos estáticos. Preferir **content collections** con esquema Zod (`defineCollection`) cuando hay muchas entradas con forma estable; un JSON tipado alcanza para catálogos pequeños, validado al construir.
- `styles/` solo tokens/variables globales (`tokens.css`); el resto de estilos va co-ubicado en el componente (`<style>` con alcance por defecto).
- `public/` activos servidos tal cual (ya optimizados). Lo que Astro debe procesar va en `src/assets/`.

## Islas e interactividad
- Un componente de framework (React, Vue…) **no se hidrata** salvo que se pida con `client:*`. Elegir la directiva más perezosa que funcione: `client:visible` o `client:idle` antes que `client:load`; `client:only` solo si no tiene sentido renderizar en servidor.
- Para interacciones pequeñas (menú, contador, enlaces con tracking), un `<script>` vanilla en el componente suele bastar y evita cargar un framework.
- Estado compartido entre islas: lo mínimo (p. ej. nanostores); si hace falta mucho, probablemente la página debería ser una app, no un sitio Astro.

## Imágenes
- Contenido propio: `astro:assets` (`<Image />`/`<Picture />`) con dimensiones explícitas (evita CLS) y formatos modernos; o activos ya optimizados en `public/` según `quality/web-performance/images.md`.
- `alt` obligatorio (vacío solo si es decorativa). La imagen de portada (LCP) sin `loading="lazy"`; el resto sí.

## Variables de entorno
- Solo las prefijadas `PUBLIC_` llegan al cliente (`import.meta.env.PUBLIC_*`). **Nunca** un secreto con ese prefijo. Los secretos van a funciones/servidor, no al bundle.
- Una integración opcional (analítica) se activa solo si existe su variable: así desarrollo no genera tráfico falso.

## Datos y build
- Leer datos en tiempo de build (frontmatter de la página). Validar la forma de los datos al construir: un dato malformado debe **romper el build**, no la página en producción.
- `output: 'static'` por defecto. SSR/adapter solo si hay una necesidad concreta (formularios con servidor, auth); decláralo y justifícalo.

## SEO y accesibilidad
- Por página: `<title>`, `meta description`, `lang` en `<html>`, URL canónica, Open Graph si se comparte.
- Un solo `<h1>`; jerarquía de encabezados sin saltos; landmarks (`header/nav/main/footer`); foco visible; contraste WCAG AA (FE-07).
- Enlaces a WhatsApp/teléfono/mapas con `rel="noopener"` si abren pestaña nueva.

## Calidad y comandos típicos
- `astro check` (tipos) y `astro build` deben pasar sin errores ni advertencias nuevas.
- TypeScript `strict`. Lint/format según el proyecto (declarado en su `CLAUDE.md`).
- Dependencias mínimas: cada una pesa en el bundle y amplía la superficie de ataque.

## Anti-patrones
- Hidratar todo (`client:load` por defecto).
- Colores/tamaños hardcodeados en vez de tokens.
- Lógica de negocio dentro de `.astro` (sin test posible) en vez de `lib/`.
- Imágenes de varios MB en `public/`.
- Un secreto en una variable `PUBLIC_*`.
