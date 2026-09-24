"""Valida el hook `.claude/hooks/tdd_gate.py`: que bloquee lógica sin test y
deje pasar el scaffold, los contratos y todo lo que está fuera de alcance —
en Java, TypeScript y Python, y en capas de hexagonal, clean u onion.

Uso:
    python3 -m unittest discover -s agent-harness/tests -v
"""
from __future__ import annotations

import os
import sys
import tempfile
import unittest
from pathlib import Path

TESTS_DIR = Path(__file__).resolve().parent
HOOKS_DIR = TESTS_DIR.parent / ".claude" / "hooks"
sys.path.insert(0, str(HOOKS_DIR))

import tdd_gate  # noqa: E402  (import tardío, después de tocar sys.path)


class GateTestCase(unittest.TestCase):
    """Proyecto temporal por test; `src` es la carpeta donde se escribe."""

    def setUp(self):
        self._tmp = tempfile.TemporaryDirectory()
        self.root = Path(self._tmp.name)
        os.environ.pop("TDD_GATE", None)

    def tearDown(self):
        self._tmp.cleanup()
        os.environ.pop("TDD_GATE", None)

    def write(self, rel: str, content: str) -> dict:
        return {"tool_name": "Write",
                "tool_input": {"file_path": str(self.src / rel), "content": content}}

    def allowed(self, payload: dict) -> bool:
        return tdd_gate.decide(payload)[0]


class TestJava(GateTestCase):
    LOGICA = "public class Mascota { void adoptar() { this.estado = ADOPTADA; } }"
    STUB = "public class Mascota { void adoptar() { throw new UnsupportedOperationException(); } }"

    def setUp(self):
        super().setUp()
        module = self.root / "mascotas-service"
        (module).mkdir()
        (module / "build.gradle").write_text("")
        self.src = module / "src/main/java/com/demo/mascotas"
        self.test = module / "src/test/java/com/demo/mascotas"
        self.src.mkdir(parents=True)
        self.test.mkdir(parents=True)

    def test_bloquea_logica_de_dominio_sin_test(self):
        self.assertFalse(self.allowed(self.write("domain/Mascota.java", self.LOGICA)))

    def test_permite_logica_si_un_test_nombra_la_clase(self):
        (self.test / "MascotaTest.java").write_text("class MascotaTest { Mascota m; }")
        self.assertTrue(self.allowed(self.write("domain/Mascota.java", self.LOGICA)))

    def test_permite_stub_nuevo_del_scaffold(self):
        self.assertTrue(self.allowed(self.write("domain/Mascota.java", self.STUB)))

    def test_bloquea_sobrescribir_un_stub_existente_con_logica(self):
        target = self.src / "domain/Mascota.java"
        target.parent.mkdir(parents=True)
        target.write_text(self.STUB)
        self.assertFalse(self.allowed(self.write("domain/Mascota.java", self.LOGICA)))

    def test_bloquea_edit_que_implementa_sin_test(self):
        target = self.src / "application/service/AdoptarService.java"
        target.parent.mkdir(parents=True)
        target.write_text(self.STUB.replace("Mascota", "AdoptarService"))
        payload = {"tool_name": "Edit", "tool_input": {
            "file_path": str(target),
            "old_string": "throw new UnsupportedOperationException();",
            "new_string": "return repo.save(m);"}}
        self.assertFalse(self.allowed(payload))

    def test_permite_contratos_y_datos(self):
        casos = {
            "application/port/in/AdoptarUseCase.java": "public interface AdoptarUseCase {}",
            "domain/EstadoAdopcion.java": "public enum EstadoAdopcion { DISPONIBLE }",
            "application/port/in/AdoptarCommand.java": "public record AdoptarCommand(String id) {}",
            "domain/TransicionInvalidaException.java": "public class TransicionInvalidaException {}",
        }
        for rel, content in casos.items():
            with self.subTest(archivo=rel):
                self.assertTrue(self.allowed(self.write(rel, content)))

    def test_bloquea_capas_de_clean_y_onion_sin_test(self):
        for rel in ("usecases/AdoptarMascota.java", "entities/Mascota.java", "core/Mascota.java"):
            with self.subTest(archivo=rel):
                self.assertFalse(self.allowed(self.write(rel, self.LOGICA)))

    def test_fuera_de_alcance_no_se_evalua(self):
        self.assertTrue(self.allowed(self.write("infrastructure/adapter/in/web/MascotaController.java", self.LOGICA)))
        self.assertTrue(self.allowed(self.write("README.md", "x")))

    def test_escape_explicito(self):
        os.environ["TDD_GATE"] = "off"
        self.assertTrue(self.allowed(self.write("domain/Mascota.java", self.LOGICA)))


class TestTypeScript(GateTestCase):
    LOGICA = "export class Mascota { adoptar() { this.estado = 'ADOPTADA'; } }"

    def setUp(self):
        super().setUp()
        (self.root / "package.json").write_text("{}")
        self.src = self.root / "src/mascotas/domain"
        self.src.mkdir(parents=True)

    def test_bloquea_logica_sin_spec(self):
        self.assertFalse(self.allowed(self.write("mascota.entity.ts", self.LOGICA)))

    def test_permite_si_un_spec_importa_el_archivo(self):
        (self.src / "mascota.entity.spec.ts").write_text("import { Mascota } from './mascota.entity';")
        self.assertTrue(self.allowed(self.write("mascota.entity.ts", self.LOGICA)))

    def test_permite_spec_en_carpeta_test_separada(self):
        (self.root / "test").mkdir()
        (self.root / "test" / "mascota.test.ts").write_text("import { Mascota } from '../src/mascotas/domain/mascota.entity';")
        self.assertTrue(self.allowed(self.write("mascota.entity.ts", self.LOGICA)))

    def test_permite_stub_nuevo_y_archivos_solo_de_tipos(self):
        stub = "export class Mascota { adoptar(): void { throw new Error('Not implemented'); } }"
        tipos = "export interface MascotaRepository { save(m: Mascota): Promise<void>; }\nexport type Id = string;"
        self.assertTrue(self.allowed(self.write("mascota.entity.ts", stub)))
        self.assertTrue(self.allowed(self.write("mascota.repository.ts", tipos)))

    def test_no_evalua_el_propio_archivo_de_test(self):
        self.assertTrue(self.allowed(self.write("mascota.entity.spec.ts", "describe('x', () => {})")))


class TestPython(GateTestCase):
    LOGICA = "class Mascota:\n    def adoptar(self):\n        self.estado = 'ADOPTADA'\n"

    def setUp(self):
        super().setUp()
        (self.root / "pyproject.toml").write_text("[project]\nname='x'\n")
        self.src = self.root / "app/domain"
        self.src.mkdir(parents=True)
        (self.root / "tests").mkdir()

    def test_bloquea_logica_sin_test(self):
        self.assertFalse(self.allowed(self.write("mascota.py", self.LOGICA)))

    def test_permite_si_un_test_importa_el_modulo(self):
        (self.root / "tests" / "test_mascota.py").write_text("from app.domain.mascota import Mascota\n")
        self.assertTrue(self.allowed(self.write("mascota.py", self.LOGICA)))

    def test_permite_stub_nuevo_e_init(self):
        stub = "class Mascota:\n    def adoptar(self):\n        raise NotImplementedError\n"
        self.assertTrue(self.allowed(self.write("mascota.py", stub)))
        self.assertTrue(self.allowed(self.write("__init__.py", "from .mascota import Mascota\n")))

    def test_permite_puertos_como_protocol(self):
        port = "from typing import Protocol\n\nclass MascotaRepository(Protocol):\n    def save(self, m): ...\n"
        self.assertTrue(self.allowed(self.write("mascota_repository.py", port)))


if __name__ == "__main__":
    unittest.main()
