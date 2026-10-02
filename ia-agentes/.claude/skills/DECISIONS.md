# Decisiones de diseño (ADR-lite)

Una entrada por decisión. Fecha de todas: 2026-09-24. Origen: ciclo completo en coffee-shop (Java/Spring hexagonal).

## D-01 `@Service`/`@Transactional` en `application`
- Contexto: INV-02 prohibía todo framework en `application`, lo que forzaba un `BeanConfig` solo para cablear casos de uso.
- Decisión: casos de uso con `@Service`; `@Transactional` solo en métodos que escriben en más de un puerto (INV-12/INV-18).
- Consecuencia: un solo save ya es atómico en su adaptador; se exige test de rollback. Excepción solo Java/Spring (Quarkus análogo); otros stacks no la heredan.

## D-02 Sin BeanConfig ni decoradores transaccionales por defecto
- Contexto: el usuario rechazó clases que solo repiten lo que una anotación resuelve.
- Decisión: composition root = clase de arranque + component-scan; decorador/`TransactionTemplate` válido pero no obligatorio.
- Consecuencia: principio de proporcionalidad (ARCH-W07); ante una regla desproporcionada se pregunta al usuario.

## D-03 Dominio sin subcarpeta `model`
- Contexto: `domain/model/...` añadía un nivel sin valor.
- Decisión: dominio organizado por agregado/concepto (`domain/order`, `domain/payment`); enums junto a su agregado.
- Consecuencia: `domain/model` solo aparece como anti-patrón en los skills (lo verifica el workflow).

## D-04 Mapeo manual por defecto; MapStruct bajo umbral
- Contexto: prueba real con MapStruct 1.6.3 en coffee-shop (3 agregados, ~4 campos por tipo, dominio inmutable).
- Decisión: manual + test de ida y vuelta (INV-11); MapStruct solo con umbrales de `spring-boot/mapstruct.md`.
- Consecuencia: medido +23 líneas escritas, 565 generadas, 232→237 tests; ahorro solo con ≥8 campos planos por tipo. `@ObjectFactory` prohibido con colecciones inmutables (`UnsupportedOperationException` en runtime).

## D-05 `PUT` para reemplazo y DTOs en `adapter.in.web.dto`
- Contexto: endpoints de reemplazo completo y DTOs mezclados con mappers.
- Decisión: `PUT` para reemplazo; DTOs de transporte en `adapter.in.web.dto`.
- Consecuencia: DTOs nunca cruzan al dominio (INV-06).

## D-06 `Clock` declarado en la clase de arranque
- Contexto: un `@Configuration` solo para un bean `Clock` era sobreingeniería.
- Decisión: `@Bean Clock` en la clase `@SpringBootApplication`.
- Consecuencia: tests de contexto con su propio `Clock` requieren `@Primary` o nombre distinto.

## D-07 H2 en tests, riesgo declarado frente a MySQL
- Contexto: CI simple y offline.
- Decisión: `@DataJpaTest` con H2; el riesgo (dialecto, tipos, locking) se declara en el proyecto.
- Consecuencia: si hay SQL nativo se exige además Testcontainers con el motor real.
