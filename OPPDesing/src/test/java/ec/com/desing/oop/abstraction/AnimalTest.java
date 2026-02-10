package ec.com.desing.oop.abstraction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para las clases de abstracción
 */
@DisplayName("Tests para clases de abstracción")
class AnimalTest {
    
    private Dog dog;
    private Cat cat;
    
    @BeforeEach
    void setUp() {
        dog = new Dog("Max", 3, "Labrador");
        cat = new Cat("Luna", 2, true);
    }
    
    @Test
    @DisplayName("Debería crear un perro correctamente")
    void shouldCreateDog() {
        assertNotNull(dog);
        assertEquals("Max", dog.getName());
        assertEquals(3, dog.getAge());
        assertEquals("Labrador", dog.getBreed());
        assertEquals("Perro", dog.getType());
    }
    
    @Test
    @DisplayName("Debería crear un gato correctamente")
    void shouldCreateCat() {
        assertNotNull(cat);
        assertEquals("Luna", cat.getName());
        assertEquals(2, cat.getAge());
        assertTrue(cat.isIndoor());
        assertEquals("Gato", cat.getType());
    }
    
    @Test
    @DisplayName("Debería usar polimorfismo con Animal")
    void shouldUsePolymorphism() {
        Animal animal1 = new Dog("Bella", 5, "Golden Retriever");
        Animal animal2 = new Cat("Simba", 1, false);
        
        assertEquals("Perro", animal1.getType());
        assertEquals("Gato", animal2.getType());
        
        // Verificar que los métodos abstractos están implementados
        assertDoesNotThrow(() -> animal1.makeSound());
        assertDoesNotThrow(() -> animal2.makeSound());
        assertDoesNotThrow(() -> animal1.move());
        assertDoesNotThrow(() -> animal2.move());
    }
    
    @Test
    @DisplayName("Debería heredar métodos concretos de Animal")
    void shouldInheritConcreteMethods() {
        assertEquals("Max", dog.getName());
        assertEquals(3, dog.getAge());
        assertDoesNotThrow(() -> dog.sleep());
    }
}

