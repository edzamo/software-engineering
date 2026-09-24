# Stack Python

Esta skill **no define el proceso** (eso es de los agentes: TDD primero, slices, decisiones consultadas). Define **cómo se hace en Python**.

## 0. Detectar antes de escribir

```bash
python3 .claude/scripts/detect_stack.py
```

Usá `version` (de `requires-python` o `.python-version`), `build_tool`, `frameworks` y `test_one`. **Nunca uses sintaxis de una versión mayor que la del proyecto.** En proyectos nuevos: Python 3.13+ con `uv`, `ruff` y `pytest`.

## 1. Tooling base

- **`uv`** para entornos y dependencias (`uv add`, `uv run`), o `poetry` si el proyecto ya lo usa.
- **`ruff`** para lint y formato, y **`mypy --strict`** o `pyright` para tipos. Type hints obligatorios en el código de dominio y aplicación.
- **`pyproject.toml`** como única fuente de configuración.

## 2. Qué usar según la versión

| Versión | Lo que se habilita (acumulativo) | Regla práctica |
|---|---|---|
| **3.10** | `match`/`case` (pattern matching), uniones `X \| Y`, `ParamSpec` | `match` sobre el estado en lugar de cadenas de `if`. |
| **3.11** | `asyncio.TaskGroup`, `ExceptionGroup`/`except*`, `typing.Self`, `tomllib`, ~25% más rápido | `TaskGroup` en lugar de `gather` suelto (cancela todo si una tarea falla). |
| **3.12** | Genéricos PEP 695 (`class Repo[T]:`, `type Id = str`), `@typing.override`, f-strings sin restricciones | Sintaxis nueva de genéricos en lugar de `TypeVar` suelto. |
| **3.13** | Build *free-threaded* experimental (sin GIL), JIT experimental, `TypeIs`, `ReadOnly` | No dependas del free-threading en producción. |
| **3.14** | Anotaciones evaluadas de forma diferida (PEP 649/749), template strings `t"..."` (PEP 750), free-threading soportado oficialmente (PEP 779), `compression.zstd` | Ya no hace falta `from __future__ import annotations` para referencias adelantadas. |

> Una versión nueva sale cada octubre. Para lo que no esté en esta tabla, verificá en docs.python.org ("What's New"): no inventes features.

## 3. Modelado de dominio en Python

- **Value Objects:** `@dataclass(frozen=True, slots=True)` con validación en `__post_init__`.
- **Entidades:** clase con métodos de comando (`iniciar_adopcion()`), sin mutación directa de atributos desde afuera. Estado como `enum.StrEnum` (3.11+) o `Literal[...]`.
- **Errores de dominio:** jerarquía propia (`class TransicionInvalida(DomainError)`), nunca `ValueError` genérico para reglas de negocio.
- **Puertos:** `typing.Protocol` (tipado estructural, sin herencia obligatoria) o `abc.ABC` si se quiere forzar la implementación.
- **Pydantic** solo en los bordes (DTOs de FastAPI), **no** en el dominio: el dominio no depende del framework.

## 4. TDD en Python

| Nivel | Herramienta | Notas |
|---|---|---|
| Dominio | pytest | Sin FastAPI ni mocks. `@pytest.mark.parametrize` para tablas de transiciones. |
| Aplicación | pytest + fakes | **Fakes en memoria** que cumplan el `Protocol`, antes que `unittest.mock`. |
| Async | `pytest-asyncio` (o `anyio`) | `async def test_...` con `@pytest.mark.asyncio`. |
| HTTP (FastAPI) | `httpx.AsyncClient` + `ASGITransport`, o `TestClient` | `app.dependency_overrides` para reemplazar los puertos. |
| Cliente HTTP | `respx` (httpx) o `responses` (requests) | Probar 200, 404, timeout y 5xx. |
| Integración | `testcontainers` | Base de datos real. |

- **Correr un solo test:** `uv run pytest -q tests/unit/domain/test_mascota.py::test_no_permite_adoptar_si_no_esta_disponible -x`.
- **Cómo se ve un RED válido:** `AssertionError`, `NotImplementedError` del stub o la excepción de dominio esperada. Un `ImportError` o `ModuleNotFoundError` **no es RED**: primero hace falta el stub.
- **Stub del scaffold:** `raise NotImplementedError("<regla>")`.
- **Ubicación:** `tests/unit/...` y `tests/integration/...`, espejando el paquete. Archivos `test_*.py` y funciones `test_<comportamiento>_cuando_<condición>`.
- **Fixtures:** en `conftest.py`, chicas y explícitas; nada de estado global compartido entre tests.

## 5. Layout según la arquitectura elegida

**Hexagonal (FastAPI):**

```text
src/<paquete>/
├── domain/              mascota.py, estado_adopcion.py, errors.py (sin FastAPI ni ORM)
├── application/
│   ├── ports/           mascota_repository.py (Protocol), adoptantes_client.py (Protocol)
│   └── services/        iniciar_adopcion.py (caso de uso, recibe puertos por constructor)
├── infrastructure/
│   ├── http/            routers FastAPI, schemas Pydantic, exception handlers
│   ├── persistence/     in_memory_mascota_repository.py, sqlalchemy_mascota_repository.py
│   └── clients/         adoptantes_http_client.py (httpx.AsyncClient con timeout)
└── main.py              composición: crea adapters e inyecta con Depends
tests/
├── unit/domain/ · unit/application/
└── integration/
```

**Clean:** `entities/` · `use_cases/` · `interface_adapters/` · `frameworks/`. **Onion:** `domain/` · `application/` · `infrastructure/`. Regla común: **`domain/` no importa FastAPI, SQLAlchemy ni Pydantic**. Se puede verificar con `import-linter`.

## 6. Docker

Multi-stage: `python:<versión>-slim` + `uv sync --frozen --no-dev` en el stage de build → copiar el `.venv` al runtime, usuario no root, `PYTHONDONTWRITEBYTECODE=1`, `PYTHONUNBUFFERED=1`, y servidor ASGI (`uvicorn` o `gunicorn -k uvicorn.workers.UvicornWorker`).

## 7. Antipatrones a señalar

Lógica en routers · modelos de SQLAlchemy o Pydantic usados como dominio · argumentos mutables por defecto (`def f(x=[])`) · `except Exception: pass` · I/O bloqueante (`requests`, drivers síncronos) dentro de `async def` · falta de type hints en el dominio · tests que dependen del orden de ejecución o de variables globales.
