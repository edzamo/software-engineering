# Onion Architecture — Reglas

Agnóstico de lenguaje y framework.

## 1. Capas (del centro a la periferia)
1. **Domain Model (núcleo)**: entidades, value objects, agregados.
2. **Domain Services**: lógica de dominio que no pertenece a una sola entidad; interfaces de repositorio del dominio.
3. **Application Services**: casos de uso, orquestación, transacciones, DTOs de aplicación.
4. **Infraestructura / UI / Tests (periferia)**: persistencia, web, mensajería, integraciones externas.

## 2. Regla de dependencia
Todas las dependencias apuntan **al centro**. Las capas externas dependen de las internas; nunca al revés. La infraestructura implementa las interfaces declaradas en capas internas.

## 3. Reglas
- **ON-01** El Domain Model no depende de ninguna otra capa.
- **ON-02** Las interfaces de repositorio se declaran en el dominio (o en Domain Services) y se implementan en infraestructura.
- **ON-03** Los Application Services dependen de Domain Model/Services, no de infraestructura.
- **ON-04** La UI y la infraestructura son intercambiables.
- **ON-05** El modelo de dominio es la base de todo el sistema y se diseña primero (Domain-Driven).
- **ON-06** Las entidades encapsulan comportamiento (modelo rico); sin setters públicos que rompan invariantes.
- **ON-07** Los DTOs de aplicación no se filtran hacia el dominio.
- **ON-08** Las transacciones y coordinación entre agregados viven en Application Services, no en el dominio.
- **ON-09** Cada capa se prueba de forma aislada sustituyendo las externas por dobles.
- **ON-10** Sin ciclos entre módulos.

## 4. Diferencias sutiles con Hexagonal
- Onion enfatiza el **modelo de dominio central y capas concéntricas** (domain services / application services).
- Hexagonal enfatiza **puertos y adaptadores** según la dirección de la interacción (in/out).
- Ambos comparten la inversión de dependencias; prioriza las invariantes comunes en `hexagonal/invariants.json` cuando se auditan dependencias.

## 5. Estructura sugerida
```
src/
├── domain-model/        # entidades, VOs, agregados, eventos
├── domain-services/     # servicios de dominio + interfaces de repositorio
├── application/         # servicios de aplicación, DTOs
└── infrastructure/      # repositorios, clientes, web, config
```

## 6. Anti-patrones
- Repositorio con implementación dentro del dominio.
- Application Service que instancia una clase de infraestructura.
- Entidades anémicas con lógica en servicios.
- Domain Services que llaman a APIs externas.
- Capas que se saltan niveles hacia afuera.

## 7. Checklist
- [ ] ¿El núcleo compila solo?
- [ ] ¿Las interfaces de repositorio están en el centro?
- [ ] ¿La infraestructura solo implementa contratos internos?
- [ ] ¿Las invariantes viven en las entidades/agregados?
- [ ] ¿El composition root es la única pieza que conoce todo?
