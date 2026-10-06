# Contrato OpenAPI first (NestJS)

El contrato OpenAPI 3.0 se escribe y se acuerda **antes** del primer controlador; la implementación se valida contra él, nunca al revés. Aplica en la Fase 1b y en la revisión (drift).

## Por qué
- Es lo primero que lee un revisor técnico para entender qué expone el servicio.
- Evita que la respuesta HTTP sea "lo que devuelve el ORM": fuerza a decidir el DTO de salida.
- Detecta antes los casos de error (¿qué devuelve una consulta inválida? ¿400 con qué forma?).

## Al diseñar `openapi.yaml`
1. Lista recursos y operaciones en texto plano ("crear un pedido", "pagar", "historial de un cliente") y luego escribe el YAML.
2. Schemas de dominio en `components/schemas`, reutilizados con `$ref`; nunca formas duplicadas inline.
3. Por path: método, parámetros (con `enum` si el filtro es cerrado), forma del 200 y al menos un error explícito (400/404/409/422) con su schema (`ErrorResponse` con `traceId`).
4. Ejemplos (`example`) en al menos un schema por endpoint.
5. Cambio incompatible = nueva versión de path (`/v2/...`), no una sorpresa.
6. **Datos sensibles:** el contrato no expone de más (ni hashes, ni tokens, ni datos de otros usuarios); los errores no filtran datos.
7. **Públicos vs autenticados:** los endpoints públicos se marcan explícitamente; todo lo demás exige autenticación.

## DTOs de respuesta en NestJS
Con `@nestjs/swagger`, cada endpoint necesita un DTO de respuesta (`class` + `@ApiProperty()`) referenciado con `@ApiOkResponse({ type: XxxResponseDto })`. Un `@ApiOkResponse({ description })` a secas deja el 200 sin schema en `/api-docs-json`.

## Drift check (implementación vs contrato)
- Cada DTO de respuesta (DTO en `controllers/dtos`) tiene los mismos campos, tipos y nullability que su schema.
- Cada controlador/método existe en el contrato con el mismo verbo y path; un endpoint fuera del contrato (o al revés) es un hallazgo.
- Los códigos que realmente devuelve el controlador, incluidos los del filtro global (`frameworks/http`), coinciden con los declarados.
- El spec vivo no debe divergir del `openapi.yaml` versionado; manda el `openapi.yaml` salvo decisión de migrar a spec autogenerado.
- Los esquemas de Zod del frontend reflejan estos DTO: un desvío se reporta, no se parcha solo del lado del cliente.

Formato del hallazgo:
```
[Drift] controllers/orders.controller.ts:18 vs openapi.yaml (POST /orders/{id}/payments)
El controlador devuelve `paymentStatus`, el contrato declara `status`.
Sugerencia: alinear el nombre (o actualizar el contrato si el cambio fue intencional).
```

## Si el código ya existe (retrofit)
Exporta el spec vivo (`GET /api-docs-json`), verifica que cada endpoint tenga `type` en `@ApiOkResponse`, deja al inicio de `openapi.yaml` un comentario que diga que se generó desde la implementación, y de ahí en adelante aplica el drift check normal.
