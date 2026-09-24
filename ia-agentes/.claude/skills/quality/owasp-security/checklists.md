# OWASP Security — Checklists

Agnóstico de framework. Cada ítem se marca `PASS | FAIL | N/A` con evidencia (archivo:línea).

## A01 — Broken Access Control
- [ ] Denegar por defecto; toda ruta exige autorización explícita.
- [ ] Autorización en el **servidor** a nivel de recurso (anti-IDOR): verificar propiedad del objeto, no solo el rol.
- [ ] Sin confianza en identificadores/roles enviados por el cliente.
- [ ] CORS con lista blanca de orígenes (nada de `*` con credenciales).
- [ ] Rate limiting en endpoints sensibles.
- [ ] Deshabilitado el listado de directorios; tokens invalidados en logout.

## A02 — Cryptographic Failures
- [ ] TLS 1.2+ en tránsito; HSTS.
- [ ] Contraseñas con Argon2id/bcrypt/scrypt (nunca MD5/SHA1/SHA-256 sin sal/iteraciones).
- [ ] Cifrado en reposo de datos sensibles (AES-GCM / KMS); claves fuera del código.
- [ ] Aleatoriedad criptográfica (`SecureRandom`/equivalente) para tokens.
- [ ] Sin algoritmos obsoletos (DES, RC4, ECB).
- [ ] Datos sensibles no se registran ni se devuelven en respuestas.

## A03 — Injection
- [ ] Consultas **parametrizadas**/prepared statements; sin concatenar SQL/NoSQL/LDAP/OS.
- [ ] Validación por lista blanca en el borde (tipo, longitud, formato, rango).
- [ ] Codificación de salida contextual (HTML/JS/URL) contra XSS; CSP configurada.
- [ ] Sin `eval`/ejecución dinámica ni shell con entrada de usuario.
- [ ] Deserialización de fuentes no confiables deshabilitada o restringida (tipos permitidos).

## A04 — Insecure Design
- [ ] Modelado de amenazas (STRIDE) para flujos críticos.
- [ ] Límites de negocio (cuotas, montos, reintentos) en el dominio.
- [ ] Separación de entornos y de privilegios (mínimo privilegio).
- [ ] Casos de abuso cubiertos con tests.

## A05 — Security Misconfiguration
- [ ] Sin credenciales/config por defecto; actuators/consolas de depuración protegidos o deshabilitados.
- [ ] Cabeceras de seguridad: `Content-Security-Policy`, `X-Content-Type-Options`, `X-Frame-Options`/`frame-ancestors`, `Referrer-Policy`.
- [ ] Mensajes de error genéricos; sin stack traces al cliente.
- [ ] Configuración por entorno; secretos vía gestor (Vault/Secrets Manager).
- [ ] Contenedores: usuario no root, imagen mínima, FS de solo lectura cuando aplica.

## A06 — Vulnerable and Outdated Components
- [ ] SCA en CI (Dependabot/OWASP Dependency-Check/Trivy); fallo en CVSS ≥ 7.
- [ ] Dependencias fijadas (lockfile) y de fuentes confiables.
- [ ] Imágenes base actualizadas; SBOM generado.

## A07 — Identification & Authentication Failures
- [ ] MFA disponible en cuentas privilegiadas.
- [ ] Política de contraseñas (longitud ≥12, verificación contra listas filtradas).
- [ ] Protección contra fuerza bruta (bloqueo progresivo/CAPTCHA).
- [ ] Sesiones: ID aleatorio, rotación al login, expiración, cookies `HttpOnly; Secure; SameSite`.
- [ ] Recuperación de contraseña con tokens de un solo uso y corta vida.

### JWT
- [ ] Algoritmo **fijado** en el servidor (rechazar `none`, evitar confusión HS/RS).
- [ ] Validar `signature`, `iss`, `aud`, `exp`, `nbf`; tolerancia de reloj ≤60 s.
- [ ] Vida corta del access token (≤15 min); refresh token rotativo y revocable.
- [ ] Claves rotables (JWKS); `kid` validado contra lista conocida.
- [ ] Sin datos sensibles en el payload; tokens no almacenados en `localStorage` si hay riesgo XSS.

### OAuth2 / OIDC
- [ ] Authorization Code + **PKCE** para clientes públicos; sin Implicit ni ROPC.
- [ ] `redirect_uri` con coincidencia exacta; parámetro `state`/`nonce` verificado.
- [ ] Scopes mínimos; validación de `aud` y scope en el resource server.
- [ ] Client secrets solo en clientes confidenciales y fuera del repo.

## A08 — Software & Data Integrity Failures
- [ ] Pipelines CI/CD con permisos mínimos; artefactos firmados/verificados.
- [ ] Verificación de integridad en actualizaciones y dependencias (hashes).
- [ ] Sin deserialización insegura ni carga dinámica de código no confiable.

## A09 — Security Logging & Monitoring Failures
- [ ] Se registran login, fallos de acceso, cambios de privilegios, validaciones fallidas con ID de correlación.
- [ ] **Sin** secretos, tokens ni PII en logs; saneamiento contra *log injection* (CRLF).
- [ ] Alertas ante patrones anómalos; logs inmutables y con retención.

## A10 — SSRF
- [ ] URLs de salida en lista blanca (hosts/esquemas/puertos); bloquear rangos internos y metadata cloud (169.254.169.254).
- [ ] Resolución DNS validada tras redirecciones; desactivar redirects automáticos o revalidar.
- [ ] Sin reenviar respuestas crudas del destino al cliente.

## Secretos y repositorio
- [ ] Sin claves/tokens en código, tests, `.env` versionados ni historial git.
- [ ] `.gitignore` cubre artefactos sensibles; escaneo (gitleaks/trufflehog) en CI.

## Sanitización y validación (resumen)
| Contexto | Control |
|----------|---------|
| SQL/NoSQL | Parámetros/ORM parametrizado |
| HTML | Escape contextual + CSP |
| Shell | Evitarlo; si es inevitable, lista de argumentos + lista blanca |
| Archivos | Validar tipo real (magic bytes), tamaño, nombre generado en servidor, fuera del webroot |
| JSON/XML | Esquema estricto; XXE deshabilitado |
| Logs | Codificar CR/LF; enmascarar PII |

## Severidad
| Nivel | Ejemplos | Acción |
|-------|----------|--------|
| CRITICAL | RCE, SQLi explotable, secretos expuestos, auth bypass | BLOCK + rotación |
| HIGH | IDOR, XSS almacenado, JWT sin validación de firma | BLOCK |
| MEDIUM | Falta de rate limit, cabeceras ausentes | Warn con ticket |
| LOW/INFO | Hardening recomendado | Backlog |

## Reglas adicionales por lecciones aprendidas
- [ ] Los `record`/DTO con datos sensibles (PAN, CVV, tokens) sobrescriben `toString` y nunca se loguean.
- [ ] No se almacena PAN completo ni CVV (PCI DSS): tokenizar o guardar últimos 4 dígitos.
- [ ] Un fallo de negocio no devuelve 500 con detalle interno; `ProblemDetail` genérico + `traceId`.
- [ ] Entrada validada en el borde (`@Valid`, cantidades > 0, listas no vacías, enums válidos).
