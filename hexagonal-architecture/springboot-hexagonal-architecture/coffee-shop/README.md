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
