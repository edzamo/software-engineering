# 🧱 Coffee Shop Hexagonal Project

Proyecto base con arquitectura hexagonal usando Java 17+ y Spring Boot.

---

## ⚙️ Requisitos

- Java 17 o 21
- Docker
- Gradle

---

## 🚀 Ejecutar localmente

```bash
./gradlew bootRun
```

---

## 🐳 Construir y correr con Docker

```bash
./gradlew build
docker build -t mcp-hexagonal .
docker run -p 8080:8080 mcp-hexagonal
```

---

## ⚙️ Alternativa rápida (auto-generación)

```bash
chmod +x setup-hexagonal.sh
./setup-hexagonal.sh
```

> Esto generará toda la estructura del proyecto, archivos de configuración, Dockerfile y README.

---

## 🧪 Testing

```bash
./gradlew test
```

---

## 👤 Autor

Edwin Zamora  
✉️ ezamora@tudominio.com  
🔗 [LinkedIn](https://www.linkedin.com/in/ezamora)


SpringBoot Hexagonal Architecture
```
src/
├── main/
│   ├── java/
│   │   └── com/yourcompany/yourapp/
│   │       ├── application/                                 // Lógica de aplicación (casos de uso)
│   │       │   └── service/
│   │       │       └── CreateOrderService.java              // Servicio que orquesta la creación de órdenes
│   │
│   │       ├── domain/                                      // Lógica del dominio (modelo puro, sin frameworks)
│   │       │   ├── model/
│   │       │   │   ├── Order.java                           // Entidad del dominio
│   │       │   │   └── Product.java                         // Value Object
│   │       │   └── repository/
│   │       │       └── OrderRepository.java                 // Puerto de salida (interfaz para persistencia)
│   │
│   │       ├── infrastructure/                              // Capa de infraestructura (adaptadores, configuración)
│   │       │   ├── adapter/
│   │       │   │   ├── in/
│   │       │   │   │   └── rest/
│   │       │   │   │       └── OrderController.java         // Adaptador de entrada: controlador REST
│   │       │   │   └── out/
│   │       │   │       ├── persistence/
│   │       │   │       │   ├── JpaOrderRepository.java      // Implementación del repositorio usando JPA
│   │       │   │       │   └── OrderEntity.java             // Entidad JPA (mapeo a DB)
│   │       │   │       └── client/
│   │       │   │           └── ProductApiClient.java        // Cliente HTTP para obtener productos desde otro servicio
│   │       │   └── config/
│   │       │       ├── SwaggerConfig.java                   // Configuración de Swagger/OpenAPI
│   │       │       └── WebSecurityConfig.java               // Configuración de seguridad con Spring Security
│   │
│   │       └── YourAppApplication.java                      // Clase principal con @SpringBootApplication
│
│   └── resources/
│       ├── application.yml                                  // Archivo de configuración principal
│       ├── application-dev.yml                              // Configuración para entorno de desarrollo
│       ├── static/                                           // Archivos estáticos (solo si es web MVC)
│       ├── templates/                                        // Plantillas Thymeleaf u otras (si aplica)
│       └── banner.txt                                        // Banner de inicio de Spring Boot (opcional)
│
├── test/
│   ├── java/
│   │   └── com/yourcompany/yourapp/
│   │       ├── application/
│   │       │   └── service/
│   │       │       └── CreateOrderServiceTest.java          // Test del servicio de aplicación
│   │       ├── domain/
│   │       │   └── model/
│   │       │       └── OrderTest.java                       // Test de lógica de dominio
│   │       └── infrastructure/
│   │           └── adapter/
│   │               ├── in/
│   │               │   └── rest/
│   │               │       └── OrderControllerTest.java     // Test del controlador REST
│   │               └── out/
│   │                   └── persistence/
│   │                       └── JpaOrderRepositoryTest.java  // Test de la implementación JPA
│   └── resources/
│       └── test-application.yml                             // Configuración para entorno de test
│
├── build.gradle                                              // Build script del módulo raíz
├── settings.gradle                                           // Incluye los módulos del proyecto
├── gradle.properties                                         // Propiedades del proyecto (versiones, etc.)
├── gradlew                                                   // Wrapper script Unix
├── gradlew.bat                                               // Wrapper script Windows
├── gradle/
│   └── wrapper/                                              // Archivos del wrapper de Gradle
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties

```
