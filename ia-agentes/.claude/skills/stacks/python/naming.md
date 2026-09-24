# Python (3.11+) — Convenciones de Nombres y Estilo

## Nombres (PEP 8)
| Elemento | Convención | Ejemplo |
|----------|-----------|---------|
| Paquetes/módulos | `snake_case`, cortos | `adoption`, `register_adopter.py` |
| Clases, excepciones, Protocols | `PascalCase` | `Adopter`, `AdopterRepository` |
| Funciones, variables, métodos | `snake_case` | `find_by_email` |
| Constantes | `UPPER_SNAKE_CASE` | `MAX_RETRIES` |
| Privado por convención | prefijo `_` | `_normalize` |
| Booleanos | `is_/has_/can_` | `is_active` |
| Tests | `test_<comportamiento>` en `tests/` | `test_rejects_duplicate_email` |
| Puertos | `Protocol` o `ABC`, rol de negocio | `NotificationSender` |
| Adaptadores | tecnología + rol | `PostgresAdopterRepository`, `InMemoryAdopterRepository` |

## Configuración y herramientas
- `pyproject.toml` único; `requires-python >= 3.11`.
- Formato y lint: **ruff** (`ruff format`, `ruff check`); tipado estático: **mypy --strict** o pyright strict.
- Tests: **pytest** (+ `pytest-cov`, `hypothesis` opcional); mutación con `mutmut`.
- Seguridad: `bandit`, `pip-audit`, gitleaks.

## Estilo
- Type hints en toda API pública; sin `Any` implícito.
- Value Objects: `@dataclass(frozen=True, slots=True)` con validación en `__post_init__`; colecciones inmutables (`tuple`, `frozenset`).
- Variantes de dominio: `Enum`, o uniones tipadas con `match` estructural:
```python
match result:
    case Approved(id=i): ...
    case Rejected(reason=r): ...
    case _ as unreachable: assert_never(unreachable)
```
- Puertos con `typing.Protocol` (tipado estructural, sin herencia forzada).
- Errores: jerarquía propia (`DomainError`); nunca `except:` desnudo ni tragar excepciones.
- Tiempo e IDs inyectados (`Clock`, `IdGenerator`); prohibido `datetime.now()` en dominio.
- Sin mutable default arguments; sin efectos al importar módulos.
- `async`/`await` de punta a punta en adaptadores asíncronos; sin llamadas bloqueantes dentro de corrutinas.
- Sin `print`; `logging` estructurado sin PII/secretos.
