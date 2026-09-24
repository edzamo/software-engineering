# OWASP Top 10 (2021) — checklist de revisión estática

Esto es revisión de código estática por un agente: señala el riesgo con
archivo/línea y una mitigación concreta. **No** reemplaza un SAST real
(Semgrep, CodeQL) corriendo en CI — es complementario, y ambos deberían
existir en un proyecto serio.

Si el proyecto adopta una edición más nueva del Top 10, verificar en
owasp.org el mapeo de categorías antes de asumir que esta lista sigue
vigente tal cual.

| # | Riesgo | Qué buscar | Ejemplo por stack |
|---|---|---|---|
| A01 | **Broken Access Control** | Falta de verificación de que el usuario autenticado puede operar sobre *ese* recurso (no solo que está logueado) | Un `GET /orders/{id}` sin validar que `id` pertenece al usuario, en cualquier stack |
| A02 | **Cryptographic Failures** | Datos sensibles sin cifrar en tránsito o en reposo, algoritmos débiles (MD5/SHA1 para passwords), secretos hardcodeados | `password: "changeme"` en config, JWT firmado con `HS256` y un secreto corto |
| A03 | **Injection** | Concatenación de input de usuario en una consulta, comando o template | SQL armado con strings en cualquier ORM, `eval`/`pickle`/`yaml.load` en Python, `child_process.exec` con input directo en Node |
| A04 | **Insecure Design** | Falta de límites de negocio (rate limiting, validación de montos), lógica de seguridad solo en el frontend | Un endpoint de transferencia sin límite de monto ni de frecuencia |
| A05 | **Security Misconfiguration** | CORS abierto (`*`) con credenciales, headers de seguridad ausentes, stack traces expuestos al cliente, debug mode en producción | `Access-Control-Allow-Origin: *` + `credentials: true` |
| A06 | **Vulnerable and Outdated Components** | Dependencias con versión fija muy vieja sin proceso de actualización | Versiones ancladas en `pom.xml`/`build.gradle`/`package.json`/`pyproject.toml` sin Dependabot/Renovate |
| A07 | **Identification and Authentication Failures** | Sesiones sin expiración, passwords sin hash fuerte (bcrypt/argon2), falta de protección contra fuerza bruta | Comparación de password en texto plano, JWT sin expiración |
| A08 | **Software and Data Integrity Failures** | Deserialización de datos no confiables, dependencias sin verificación de integridad (sin lockfile, sin checksum) | `pickle.loads`/deserialización Java insegura de input externo |
| A09 | **Security Logging and Monitoring Failures** | Eventos de seguridad (login fallido, cambio de permisos) que no se loguean, o que loguean datos sensibles | Logs sin correlation id, o que imprimen el password/token completo |
| A10 | **Server-Side Request Forgery (SSRF)** | Una URL controlada por el usuario usada para hacer un request desde el servidor sin validar destino | Un endpoint "importar desde URL" sin allowlist de hosts |

## Formato del hallazgo

```text
[A03 - Injection] UserRepository.java:47
Consulta armada con concatenación de string usando el username del request.
Sugerencia: usar @Query("... WHERE u.username = :username") con @Param, o el método derivado de Spring Data.
```

## Equivalentes por stack (para no limitarse al ejemplo de Java)

- **Injection:** consultas parametrizadas siempre — Spring Data/JPQL con
  `@Param`, Prisma/TypeORM con query builder (no `$queryRawUnsafe` con
  interpolación), SQLAlchemy con parámetros bindeados (no f-strings en SQL).
- **Secrets:** variables de entorno o un secret manager, nunca en el
  repositorio — ni en `application.yml`, ni en `.env` versionado, ni en
  `settings.py`.
- **Deserialización insegura:** evitar `pickle`/`yaml.load` (usar
  `yaml.safe_load`) en Python; evitar deserialización Java nativa de input
  externo; en Node, cuidado con `eval`/`Function()` sobre input de usuario.
- **Dependencias vulnerables:** `npm audit`/`pnpm audit`, `pip-audit`,
  `./gradlew dependencyCheckAnalyze` o Dependabot/Renovate configurado en CI.
