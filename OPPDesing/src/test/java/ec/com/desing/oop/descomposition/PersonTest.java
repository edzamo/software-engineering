package ec.com.desing.oop.descomposition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para las clases de descomposición
 */
@DisplayName("Tests para Descomposición")
class PersonTest {
    
    private Address address;
    private Person person;
    private Student student;
    
    @BeforeEach
    void setUp() {
        address = new Address("Av. Test 123", "Quito", "Pichincha", "170101", "Ecuador");
        person = new Person("P001", "Juan", "Pérez", "juan@email.com", address);
        student = new Student("P002", "María", "García", "maria@email.com", 
                             address, "STU001", "Ingeniería");
    }
    
    @Test
    @DisplayName("Debería crear una Person correctamente")
    void shouldCreatePerson() {
        assertNotNull(person);
        assertEquals("P001", person.getId());
        assertEquals("Juan", person.getFirstName());
        assertEquals("Pérez", person.getLastName());
        assertEquals("Juan Pérez", person.getFullName());
        assertEquals(address, person.getAddress());
    }
    
    @Test
    @DisplayName("Debería crear un Student correctamente")
    void shouldCreateStudent() {
        assertNotNull(student);
        assertEquals("P002", student.getId());
        assertEquals("STU001", student.getStudentId());
        assertEquals("Ingeniería", student.getMajor());
        assertEquals(0.0, student.getGpa());
    }
    
    @Test
    @DisplayName("Student debería heredar de Person")
    void studentShouldInheritFromPerson() {
        assertEquals("María García", student.getFullName());
        assertEquals("maria@email.com", student.getEmail());
        assertEquals(address, student.getAddress());
    }
    
    @Test
    @DisplayName("Debería poder cambiar la dirección de Person")
    void shouldChangePersonAddress() {
        Address newAddress = new Address("Nueva Calle", "Cuenca", "Azuay", "010101", "Ecuador");
        person.setAddress(newAddress);
        
        assertEquals(newAddress, person.getAddress());
    }
    
    @Test
    @DisplayName("Debería validar el email al establecerlo")
    void shouldValidateEmail() {
        String originalEmail = person.getEmail();
        person.setEmail("invalid-email"); // Sin @
        
        assertEquals(originalEmail, person.getEmail()); // No cambia
    }
    
    @Test
    @DisplayName("Student debería actualizar GPA correctamente")
    void shouldUpdateStudentGpa() {
        student.updateGpa(3.5);
        assertEquals(3.5, student.getGpa());
    }
    
    @Test
    @DisplayName("Student debería rechazar GPA inválido")
    void shouldRejectInvalidGpa() {
        student.updateGpa(3.0);
        student.updateGpa(5.0); // Inválido
        student.updateGpa(-1.0); // Inválido
        
        assertEquals(3.0, student.getGpa()); // No cambia
    }
}

