package ec.com.desing.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Main
 */
@DisplayName("Tests para Main")
class MainTest {
    
    private Main main;
    
    @BeforeEach
    void setUp() {
        main = new Main("Test");
    }
    
    @Test
    @DisplayName("Debería crear una instancia de Main correctamente")
    void shouldCreateMainInstance() {
        assertNotNull(main);
        assertEquals("Test", main.getName());
    }
    
    @Test
    @DisplayName("Debería establecer y obtener el nombre correctamente")
    void shouldSetAndGetName() {
        main.setName("NewName");
        assertEquals("NewName", main.getName());
    }
}

