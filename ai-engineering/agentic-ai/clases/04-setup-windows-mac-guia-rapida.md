# Clase 04 · Semana 1, día 1 — Setup en Windows y Mac (guía rápida)

> ⬅️ [03 · Configurar el entorno](03-configurar-entorno-costos-mentalidad.md) · ➡️ [05 · Clave de OpenAI, `.env` y primer notebook](05-openai-api-key-env-notebook.md)
> 🗓️ **Semana 1 · Día 1** · tipo: 🟠 preparación · ⏱️ **Guía corta a propósito**
> **Por qué es corta:** el instructor dedica dos clases largas a instalar Cursor y configurar Windows/Mac. A nivel senior eso se resuelve siguiendo el `README` del repo. Aquí solo quedan las **trampas** y los **comandos clave**, que son lo único que ahorra tiempo.

---

## 🍎 Con manzanas

Montar el local es igual en todas las ciudades, pero cada ciudad tiene **sus propios permisos municipales** (Windows y Mac). Esta guía no enseña a poner el mostrador: solo avisa **dónde suelen multarte**.

---

## 1 · Los 5 pasos, en una línea cada uno

| # | Paso | Comando / acción | Nota |
|---|---|---|---|
| 1 | Instalar el IDE | **Cursor** (o VS Code) | Cursor es un *fork* de VS Code: se ve igual. Plan gratis suficiente; el Pro (20 USD/mes) no es necesario. |
| 2 | Tener **Git** | `git -v` | Si no responde, instalarlo y **reiniciar** el IDE. |
| 3 | Clonar el repo y abrirlo | Carpeta de proyectos → clonar `ed-donner/agents` | ⚠️ Abrir la carpeta **`agents`**, no la carpeta padre. Arriba debe leerse "agents". |
| 4 | Extensiones | **Python** (de Anysphere) y **Jupyter** (de Microsoft) | Instalar **solo una** extensión de Python, no dos. |
| 5 | **uv** + dependencias | `uv --version` → `uv self update` → **`uv sync`** | Ver sección 3. |

Terminal en el IDE: `Ctrl` + `` ` `` (tilde invertida). En Windows es **PowerShell**.

## 2 · Trampas por sistema operativo

### 🪟 Windows (cuatro "trampas" del instructor)

| Trampa | Síntoma | Qué hacer |
|---|---|---|
| **Permisos de ejecución de scripts** | "No tienes permiso para ejecutar scripts" | Buscar la política de ejecución de PowerShell y habilitarla. |
| **Antivirus / VPN / firewall** | Errores raros en la instalación | Probar desactivándolos temporalmente. |
| **Límite de 260 caracteres en rutas** | Error extraño con rutas largas | Habilitar rutas largas en Windows. |
| **Microsoft Build Tools** | Falla compilar algún paquete (se necesitará en la semana de CrewAI) | Instalarlo **antes de la semana 3**. |

### 🍎 Mac

- Puede pedir instalar las **herramientas de línea de comandos de Xcode** (solo las herramientas de desarrollador, no todo Xcode).
- Linux: se siguen las instrucciones del repo o las de Mac; son casi iguales.

## 3 · `uv sync`: el comando importante

`uv sync` hace, de una vez:

1. Instala la **versión correcta de Python** si hace falta.
2. Instala **todas las dependencias** del proyecto.
3. Crea un **entorno virtual** en la carpeta **`.venv`**.

> 🍎 El repartidor llega, trae los ingredientes exactos y los deja en una **caja cerrada** (`.venv`) para que no se mezclen con los de otros proyectos.

Con uv tarda unos minutos; "a la antigua" tardaría 5 a 10 veces más. Si da error: **pregunta 11 de las FAQ** del instructor (por ejemplo, bloqueos de seguridad corporativa). Hay que resolverlo ahora, porque si no, fallará todo después.

## 4 · Qué debe dominar cada nivel

| Nivel | Debe poder... |
|---|---|
| 🟢 **Junior** | Seguir el `README`, abrir la carpeta correcta y ejecutar `uv sync`. |
| 🟡 **Mid** | Explicar qué es un entorno virtual (`.venv`) y por qué se usa uno por proyecto. Diagnosticar fallos típicos (permisos, VPN, rutas largas). |
| 🔴 **Senior** | Defender el uso de un gestor reproducible (uv + lockfile) para que el equipo y CI tengan el mismo entorno; evitar "en mi máquina funciona". |

## 🔗 Relacionado

- [Clase 03](03-configurar-entorno-costos-mentalidad.md) — los 5 pasos y los costos.
- [Clase 05](05-openai-api-key-env-notebook.md) — pasos 4 y 5 del instructor: clave y `.env`.
- [Índice del curso](../README.md)
