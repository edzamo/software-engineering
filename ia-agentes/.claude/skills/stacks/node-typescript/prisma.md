# Prisma + PostgreSQL — reglas

Prisma es un **detalle de infraestructura**: vive en `frameworks/data-services/prisma/` (`PrismaXxxRepository`, `PrismaService`, `PrismaModule` y `mappers/`). Esquema en `prisma/schema.prisma`, semilla en `prisma/seed.ts`. Portable a cualquier proyecto con Prisma.

## Reglas
- **PR-01** Los tipos de `@prisma/client` (modelos, enums, `Prisma.*`) nunca cruzan hacia `core` ni `use-cases`; el repositorio los pasa por un mapper (`frameworks/data-services/prisma/mappers`) que los traduce a la entidad. Si no hay diferencia, el mapper es trivial pero la frontera se mantiene. El repositorio traduce los errores de Prisma (P2025, P2002) a `ApplicationError`.
- **PR-02** **Sin pérdida de estado:** todo estado del dominio se guarda y recupera sin pérdida. Si hay mapeo, test de ida y vuelta por cada valor de enum/estado. Los enums de Prisma se convierten a uniones de literales en el dominio, con conversión explícita (sin `as`).
- **PR-03** Un `PrismaXxxRepository` por puerto de repositorio de `core/ports`.
- **PR-04** Escrituras que tocan más de una tabla van en **una transacción** definida en el repositorio. Si además hay que disparar trabajo en segundo plano, el pendiente se registra en la misma transacción antes de intentar nada.
- **PR-05** JSONB solo para datos que de verdad varían; lo demás en columnas tipadas. El JSON se valida al leerlo (Zod o guardas), no se asume.
- **PR-06** Migraciones versionadas (`prisma migrate`); nunca editar una migración aplicada; cambios destructivos con plan de datos.
- **PR-07** El seed es **idempotente** y solo contiene datos de demostración, nunca datos reales.
- **PR-08** Campos sensibles no se escriben en logs ni en mensajes de error; `toString` de las entidades con datos sensibles, sobrescrito.
- **PR-09 (serverless)** En funciones (Lambda/Netlify) la conexión pasa por el pooler del proveedor (modo transacción) con un único `PrismaClient` reutilizado entre invocaciones cálidas.
- **PR-10** Retención/borrado de datos es un caso de uso propio, no un script suelto.

## Anti-patrones
- Devolver un modelo Prisma desde un caso de uso o un controlador.
- `prisma.$queryRaw` con interpolación de strings.
- Estados del dominio que colapsan en un solo valor de base de datos.
- Archivos con datos sensibles en buckets públicos.
- Generar IDs de negocio o fechas dentro del repositorio en lugar de recibirlos del caso de uso.

## Checklist
- [ ] ¿Ningún tipo de Prisma aparece en `domain` ni `use-cases` de un módulo?
- [ ] ¿Hay test de ida y vuelta por cada enum/estado mapeado?
- [ ] ¿Las escrituras multi-tabla están en una transacción?
- [ ] ¿La migración es reversible o tiene plan de datos?
