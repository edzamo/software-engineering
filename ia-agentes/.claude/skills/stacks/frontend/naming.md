# Frontend — Naming y estructura

Agnóstico de framework; donde React/Vue/Angular difieren, se indica.

## Componentes
- Archivo y componente en `PascalCase` (`OrderSummary.tsx`). Vue: `PascalCase.vue`; Angular: `kebab-case.component.ts` (convención propia del CLI).
- Un componente por archivo. Presentacional vs contenedor se distingue por carpeta (`components/` vs `pages/`/`containers/`), no por sufijo.

## Hooks / composables / lógica de dominio de UI
- React: `useAlgo` (hook). Vue: `useAlgo` (composable, Composition API). Angular: servicio inyectable `AlgoService`.
- Viven en `domain/` o `hooks/`/`composables/`, nunca mezclados con componentes visuales.

## Servicios de datos (puerto de salida)
- `algoService.ts` / `AlgoApi.ts` — un archivo por recurso/agregado, no un `api.ts` monolítico.
- Interfaz explícita (TypeScript `interface`) separada de la implementación concreta del cliente HTTP, igual que un puerto de salida backend — permite fake en tests.

## Tests
- Mismo nombre que el archivo probado + `.test.`/`.spec.` (`OrderSummary.test.tsx`).
- Testing Library (React Testing Library / Vue Testing Library) o equivalente: selectores por rol/texto accesible, no por clase CSS ni estructura DOM.
- Lógica de dominio de UI: test unitario puro (sin renderizar), mismo criterio que domain backend.

## Estilos
- Co-ubicados con el componente (`OrderSummary.module.css` o CSS-in-JS junto al `.tsx`), no en una carpeta `styles/` global salvo tokens/variables compartidas.

## TypeScript
- `strict: true`. Sin `any` implícito. Props/estado tipados; evitar `object`/`Function` genéricos.
