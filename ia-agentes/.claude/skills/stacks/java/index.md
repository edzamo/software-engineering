# Stack Java — índice

Carga **siempre** `common-naming.md` y luego según el proyecto (detecta con `pom.xml`/`build.gradle`: `java.version`, `release`, dependencias):

| Condición | Archivo (en esta carpeta) |
|-----------|---------------------------|
| Java 8 – 16 | `java8-11/rules.md` |
| Java 17 – 21+ | `java17-21-plus/rules.md` |
| `spring-boot-starter-*` (MVC, WebFlux, Data, Security) | `spring-boot/best-practices.md` |
| `spring-boot-starter-*` y se evalúa/usa **MapStruct** (`org.mapstruct` en `build.gradle` o propuesta de diseño) | `spring-boot/mapstruct.md` |
| `io.quarkus` | `quarkus/best-practices.md` |

Regla transversal: el framework nunca entra en `domain`; en `application` solo se permiten `org.springframework.stereotype.Service` y `org.springframework.transaction.annotation.*` (esta última solo en métodos multi-puerto, INV-12/INV-18), nada más. Esta excepción es propia de Java/Spring. Ver `skills/architecture/hexagonal/rules.md`.
