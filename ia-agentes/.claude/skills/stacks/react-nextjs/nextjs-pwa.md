# Next.js + React (PWA) — reglas

Portable: no menciona ningún proyecto. Las reglas de negocio del frontend de un proyecto están en `../../projects/<proyecto>/frontend.md`.

## Convenciones
- **FE-01** `strict: true`; prohibido `any` sin comentario justificado. Componentes funcionales y hooks; nunca clases.
- **FE-02** Tailwind CSS (+ shadcn/ui cuando aplique); sin CSS-in-JS ni módulos CSS sueltos salvo excepción justificada.
- **FE-03** Formularios con React Hook Form + **Zod**: el esquema de Zod es la **única** fuente de validación (pantalla y forma que viaja al backend). No dupliques reglas a mano.
- **FE-04** Server Components por defecto (App Router); `"use client"` solo donde hay estado, efectos o eventos del navegador.
- **FE-05** Archivos de componente en PascalCase; hooks en kebab-case con prefijo `use-` (`use-consent-draft.ts`, función `useConsentDraft`). Organización por capa (`clean-frontend.md`): componentes en `ui/<feature>`, hooks en `hooks/`; `src/app/` solo compone.

## Patrones
- **FE-06 Una sola vista para pantalla e impresión/PDF.** La ruta de impresión renderiza los mismos componentes que la pantalla con los datos ya resueltos (sin interactividad). Si un componente cambia entre modos, usa una prop explícita (`mode: 'screen' | 'print'`), nunca JSX duplicado. La ruta de impresión **no es pública**: exige un token de corta vida.
- **FE-07 Marca por datos (white-label).** Nada de texto, color o logo de un cliente hardcodeado en componentes de contenido: llega desde la configuración y se aplica con **CSS variables** vía un `ThemeProvider`. Sin marca disponible, aspecto neutro. Sin selector de plantillas por cliente hasta que haya una necesidad real.
- **FE-08 Formularios schema-driven.** Preguntas, textos y reglas por caso vienen del backend; nunca se hardcodea el contenido de un caso concreto en el componente. El frontend refleja las decisiones del backend; **no reimplementa reglas de negocio**.
- **FE-09 Offline acotado.** Las operaciones locales (completar, firmar) funcionan sin red. Al confirmar: intentar enviar; si falla por red, guardar el payload en IndexedDB vía el Service Worker, avisar "guardado localmente" y reintentar con Background Sync (o el evento `online` como respaldo) preservando la hora real del evento. Sin modo offline de paneles ni historiales.
- **FE-10 Firma táctil.** Canvas HTML5 con Pointer Events (`onPointerDown/Move/Up`); exportar con `toDataURL('image/png')`. Botón de confirmar deshabilitado hasta que haya trazo; lienzo de tamaño razonable para no inflar el PNG.

## Pruebas
- **FE-11** Testing Library + Jest orientados a comportamiento (`getByRole`, `getByLabelText`); sin red real en unitarios (fakes de puertos o cliente HTTP falso; `msw` si hay integración, en `apps/web/test/`). Specs unitarios junto al archivo. Mínimo un test por paso del flujo con camino feliz y un caso de bloqueo. Más en `../node-typescript/testing.md`.

## Relación con otros skills
Contrato de datos: `../node-typescript/openapi-contract.md` (los esquemas de Zod reflejan los DTO del backend; un desvío se reporta como drift). Estructura del frontend: `clean-frontend.md`. Estructura del backend: `../node-typescript/nestjs-clean.md`. Seguridad: `../../quality/owasp-security/checklists.md`.

## Anti-patrones
- Plantilla o componente paralelo "solo para el PDF".
- Colores/textos de marca en el JSX; `if (tenant === ...)` para elegir plantilla.
- Reimplementar reglas de negocio del backend en el cliente.
- Datos sensibles en `localStorage` (la cola offline usa IndexedDB y se vacía al enviar).
- `NEXT_PUBLIC_*` con claves de servicio o identificadores que no deban ser públicos.
