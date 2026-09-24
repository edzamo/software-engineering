# Python con Arquitectura Hexagonal (FastAPI / Flask / Django)

El framework web y el ORM son infraestructura; `domain/` y `application/` son Python puro.

## 1. Estructura
```
src/adoption/
├── domain/                 # entidades, VOs, errores (stdlib únicamente)
├── application/
│   ├── ports/{inbound.py, outbound.py}
│   └── services/register_adopter.py
├── adapters/
│   ├── inbound/http/       # FastAPI routers + schemas (pydantic)
│   └── outbound/persistence/  # SQLAlchemy models + repositorios
└── bootstrap.py            # composition root (wiring, settings)
tests/{unit,integration,architecture}/
```

## 2. Reglas
- **PY-01** `domain/` y `application/` no importan `fastapi`, `flask`, `django`, `sqlalchemy`, `pydantic`, `requests`, `boto3`.
- **PY-02** Puertos como `Protocol`; las implementaciones no heredan de ellos.
- **PY-03** Casos de uso = clases/funciones planas con dependencias por constructor; el cableado en `bootstrap.py` (FastAPI: `Depends` solo en adaptadores de entrada).
- **PY-04** Schemas pydantic solo en el adaptador de entrada; modelos SQLAlchemy solo en persistencia; mapeo explícito a dominio (INV-06). Nombres distintos: `OrderModel` vs `Order`.
- **PY-05** Mapeo estado dominio↔BD biyectivo con test de ida y vuelta por cada valor de `Enum` (INV-11).
- **PY-06** Transacciones (`Session`) gestionadas en el adaptador o en una `UnitOfWork` inyectada, nunca dentro del dominio (INV-18).
- **PY-07** Manejadores globales de excepciones (`app.add_exception_handler`) traducen `NotFound → 404`, transición inválida → `409`, validación → `422` (INV-15).
- **PY-08** Sin estado global mutable ni singleton por módulo; configuración con `pydantic-settings` validada al arrancar.
- **PY-09** Sin `datetime.now()`/`uuid4()` en dominio: puertos `Clock` e `IdGenerator`.

## 3. Seguridad
- Autenticación con `PyJWT`/`authlib`: algoritmo fijado, validar `iss`, `aud`, `exp`; dependencia global deny-by-default.
- Consultas parametrizadas (SQLAlchemy Core/ORM); nunca f-strings en SQL.
- CORS con lista blanca; límites de tamaño de body; rate limiting (`slowapi`).
- `yaml.safe_load`, nunca `pickle`/`eval` con datos externos.
- Secretos por variables de entorno/gestor; `repr` de datos sensibles enmascarado.

## 4. Tests
| Nivel | Herramienta |
|-------|-------------|
| Dominio/aplicación | pytest, sin framework, fakes en memoria para puertos |
| Adaptador HTTP | `fastapi.testclient.TestClient` / `httpx.AsyncClient` |
| Persistencia | Testcontainers + BD real |
| Contrato | mismo test parametrizado sobre fake y adaptador real |
| Arquitectura | **import-linter** |

Regla ejecutable (`pyproject.toml`):
```toml
[tool.importlinter]
root_package = "adoption"

[[tool.importlinter.contracts]]
name = "Domain is pure"
type = "forbidden"
source_modules = ["adoption.domain", "adoption.application"]
forbidden_modules = ["adoption.adapters", "fastapi", "flask", "django", "sqlalchemy", "pydantic", "requests", "boto3"]

[[tool.importlinter.contracts]]
name = "Adapters are isolated"
type = "independence"
modules = ["adoption.adapters.inbound", "adoption.adapters.outbound"]
```

## 5. Anti-patrones
- Modelos SQLAlchemy/pydantic usados como entidades de dominio.
- `Depends(get_db)` o `Request` dentro de casos de uso.
- `except Exception: pass`; `HTTPException` lanzada desde dominio.
- Estado de módulo mutable compartido entre requests.
- Colapsar estados de dominio en un solo valor de BD.

## 6. Checklist
- [ ] `import-linter` en CI; `domain`/`application` sin imports de framework.
- [ ] `mypy --strict` y `ruff` limpios.
- [ ] Puertos `Protocol` con fake en memoria y test de contrato.
- [ ] Manejo de errores centralizado; JWT validado; anti-IDOR.
- [ ] Test de ida y vuelta por cada estado mapeado.
