# Seguridad de sitios estáticos (Jamstack)

Complementa `checklists.md` para sitios sin backend propio (Astro/Next export/Hugo) alojados en un CDN (Netlify, Vercel, Cloudflare Pages…). Superficie reducida pero real: secretos, cabeceras, dependencias, terceros y datos públicos.

## Comprobaciones
1. **Secretos filtrados** (código e historial):
   ```bash
   git log --all -p | grep -iE "(api[_-]?key|secret|token|password|-----BEGIN)" | head -50
   grep -rniE "(api[_-]?key|secret|token|password)\s*=\s*['\"][a-z0-9]" --include="*.{js,ts,astro,json,toml,env}" . | grep -v "node_modules\|\.env\.example"
   ```
   Un secreto commiteado exige **rotarlo**, no solo borrarlo. Variables expuestas al cliente (`PUBLIC_*`, `NEXT_PUBLIC_*`) **nunca** llevan secretos.
2. **`.gitignore`**: `node_modules/`, `.env` y variantes, carpetas de build (`dist/`, `.astro/`), originales pesados y documentación interna.
3. **Dependencias:** `npm audit --audit-level=high`. Nunca `npm audit fix --force` automático: proponer el fix y dejar decidir.
4. **Cabeceras de seguridad** (en `_headers`/`netlify.toml`/`vercel.json`): HSTS, `X-Content-Type-Options: nosniff`, `X-Frame-Options`/`frame-ancestors`, `Referrer-Policy`, `Permissions-Policy` y **Content-Security-Policy**. Verificar que no fueron borradas ni debilitadas.
5. **CSP y terceros:** cada servicio externo (analítica, mapas, fuentes, chat) requiere su dominio en la directiva correcta (`script-src`, `connect-src`, `img-src`, `font-src`, `frame-src`). Si falta, el navegador lo bloquea **en silencio**; si sobra (`*`, `unsafe-inline` sin motivo), se debilita la política. Probar en el navegador (consola) tras cada cambio.
6. **Datos públicos:** todo JSON/markdown en `src/` que se renderiza es público. Sin costos/márgenes, proveedores ni datos personales en campos visibles.
7. **Formularios y funciones serverless:** validación en servidor, anti-spam (honeypot mínimo, rate limit), sin credenciales en el bundle del cliente, CORS acotado, errores sin detalles internos.
8. **Enlaces externos** con `rel="noopener noreferrer"` si abren pestaña nueva.
9. **Cuentas y repo (acciones humanas):** 2FA en hosting y repositorio, visibilidad del repo, protección de la rama principal, tokens con alcance mínimo. Se listan como recordatorio; no se automatizan.

## Reporte
Qué se revisó, qué está bien, qué requiere acción; separa lo corregido de lo que necesita acción manual del dueño (2FA, visibilidad, rotar un secreto). Severidad según `checklists.md`; secretos reales siempre enmascarados.
