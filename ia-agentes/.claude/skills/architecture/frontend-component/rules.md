# Arquitectura de Componentes Frontend — Reglas

Agnóstica de framework (React, Vue, Angular). Para features que son solo UI o solo backend se declara explícitamente; no se fuerza el molde hexagonal a un componente visual. Mismas invariantes de disciplina que `hexagonal/clean/onion` (sin código de producción sin RED, veredicto `BLOCKED` detiene el flujo), pero con un modelo de capas apropiado a UI.

## 1. Capas (de adentro hacia afuera)
1. **Dominio de UI (state/lógica pura)**: reducers, selectores, hooks/composables de lógica (sin JSX/template, sin llamadas a red directas). Testeable sin renderizar.
2. **Componentes de presentación ("tontos")**: reciben props, emiten eventos, sin side-effects ni fetch. Un solo propósito visual.
3. **Componentes contenedor/página**: orquestan estado + llamadas a servicios; componen presentacionales.
4. **Servicios de acceso a datos (puertos de salida)**: capa de API/cliente HTTP — interfaz propia, nunca `fetch`/`axios` esparcido en componentes.
5. **Infraestructura de framework**: routing, store global, configuración de build.

## 2. Regla de dependencia
Un componente de presentación no conoce servicios de datos ni store global directamente. La lógica de dominio de UI no importa el framework de render (sin JSX dentro de un hook de lógica pura).

## 3. Reglas
- **FE-01** Lógica de negocio/validación no vive duplicada dentro del componente si ya existe en el backend correspondiente (la UI valida para UX, el backend es la fuente de verdad).
- **FE-02** Un componente, una responsabilidad visual; si mezcla layout + fetch + lógica compleja, se divide (contenedor vs presentacional).
- **FE-03** Llamadas a red solo a través de la capa de servicios (puerto de salida), nunca inline en el componente.
- **FE-04** Estado global (store) solo para lo que de verdad es compartido entre rutas/componentes lejanos; estado local por defecto.
- **FE-05** Props/inputs tipados explícitamente (TypeScript, PropTypes o equivalente); sin `any` salvo justificación.
- **FE-06** Efectos secundarios (`useEffect`/`watch`/lifecycle) declaran sus dependencias completas; sin loops infinitos silenciosos.
- **FE-07** Accesibilidad mínima: elementos interactivos con rol/label, contraste y foco manejable por teclado (WCAG AA como umbral, no aspiracional).
- **FE-08** Sin lógica de negocio en el archivo de estilos ni en el markup (clases condicionales complejas se extraen a una función testeada).

## 4. Testabilidad
- Lógica de dominio de UI: tests unitarios puros, sin renderizar.
- Componentes de presentación: test de render con Testing Library (o equivalente) por estado/prop relevante, sin implementación interna.
- Contenedores: test de integración con mocks solo del servicio de datos (puerto de salida), igual que un caso de uso backend mockea puertos de salida.
- Sin tests que dependan de clases CSS o estructura DOM interna (`data-testid` o rol accesible, no `.class > div:nth-child`).

## 5. Estructura sugerida
```
src/
├── domain/              # hooks/composables de lógica pura, reducers, selectores
├── components/          # presentacionales, sin fetch
├── pages|containers/    # orquestan estado + servicios
├── services/            # cliente HTTP / puertos de salida de datos
└── app/                 # routing, store, configuración de arranque
```

## 6. Anti-patrones
- Componente que hace `fetch` directo y también renderiza.
- Prop drilling de más de 2-3 niveles en vez de composición o store acotado.
- Lógica de validación de negocio solo en el cliente (sin espejo/fuente de verdad en backend).
- `useEffect`/watcher que dispara otro efecto en cascada sin necesidad.
- Estado global para algo que solo usa un componente y sus hijos directos.

## 7. Relación con el resto del flujo
`arch-validator-agent` usa este archivo cuando `stack.language` es `frontend` (o la feature se declara `architecture_style: frontend-component`). Las fases 2-4 (`tdd-driver-agent`, `code-reviewer-agent`, `security-agent`) siguen aplicando igual; el checklist OWASP de `skills/quality/owasp-security/checklists.md` sigue vigente para XSS, dependencias y manejo de tokens en cliente.

## 8. Checklist
- [ ] ¿Los componentes presentacionales están libres de fetch/side-effects?
- [ ] ¿Existe una capa de servicios como puerto de salida único hacia la red?
- [ ] ¿El estado global está justificado (no todo es global por comodidad)?
- [ ] ¿Hay test de lógica de dominio de UI sin renderizar?
- [ ] ¿Accesibilidad mínima cubierta (roles, foco, contraste)?
