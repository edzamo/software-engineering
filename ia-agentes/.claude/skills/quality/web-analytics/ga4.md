# Analítica web con Google Analytics 4 (GA4)

Auditoría y guía de uso para un sitio que mide con GA4. No usa la API de Google (sin credenciales): verifica que la **implementación** sea correcta y dice dónde mirar los datos.

## Principios
- **Opt-in por entorno:** el script se carga solo si existe la variable pública con el Measurement ID (p. ej. `PUBLIC_GA_MEASUREMENT_ID`). Sin ella no se mide: evita tráfico falso en desarrollo.
- **Un listener delegado** para eventos de clic (un `document.addEventListener("click", …)` en el layout) en lugar de tocar cada página; los eventos nuevos suman una rama.
- **CSP:** exige `https://www.googletagmanager.com` en `script-src` y `https://*.google-analytics.com` (y `https://*.analytics.google.com` si se usa) en `connect-src`. Si faltan, GA4 queda bloqueado sin error visible.
- **Privacidad:** no enviar datos personales en parámetros de eventos; evaluar la necesidad de aviso/consentimiento según la jurisdicción del negocio.

## Auditoría (4 pasos)
1. **ID configurado:** variable presente en `.env` local y en el hosting (el panel del hosting no se puede leer: pedir confirmación al usuario).
2. **Script conectado:** el layout contiene el `<script>` condicional que carga `gtag.js` y ejecuta `gtag("config", id)`.
3. **CSP lo permite:** buscar `googletagmanager` y `google-analytics` en la cabecera CSP del hosting.
4. **Eventos activos:** listar los `gtag("event", …)` del layout y su significado; verificar nombres consistentes (`click_whatsapp`, `click_maps`, …).

## Dónde ver los datos (guiar, no automatizable)
- **¿Funciona ahora?** Informes → Tiempo real (abrir el sitio en el móvil tras un deploy).
- **Origen (QR vs redes vs directo):** Informes → Adquisición → Adquisición de tráfico, filtrando `Source / Medium` (links con `utm_source`, `utm_medium`, `utm_campaign`).
- **Conversiones/eventos:** Informes → Interacción → Eventos (marcar como conversión los relevantes).
- **Campaña concreta:** Anuncios → Todas las campañas (lee `utm_campaign`).

## Qué NO hace
No crea la propiedad GA4 (requiere cuenta Google del negocio, una sola vez) ni lee métricas en vivo.
