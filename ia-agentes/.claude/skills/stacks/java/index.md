# Stack Java — índice

Carga **siempre** `common-naming.md` y luego según el proyecto (detecta con `pom.xml`/`build.gradle`: `java.version`, `release`, dependencias):

| Condición | Archivo (en esta carpeta) |
|-----------|---------------------------|
| Java 8 – 16 | `java8-11/rules.md` |
| Java 17 – 21+ | `java17-21-plus/rules.md` |
| `spring-boot-starter-*` (MVC, WebFlux, Data, Security) | `spring-boot/best-practices.md` |
| `io.quarkus` | `quarkus/best-practices.md` |

Regla transversal: el framework nunca entra en `domain` ni `application`. Ver `skills/architecture/hexagonal/rules.md`.
