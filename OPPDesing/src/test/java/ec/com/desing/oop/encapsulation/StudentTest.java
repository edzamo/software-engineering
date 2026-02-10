package ec.com.desing.oop.encapsulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Student
 */
@DisplayName("Tests para Student - Encapsulación")
class StudentTest {
    
    private Student student;
    
    @BeforeEach
    void setUp() {
        student = new Student("STU001", "Carlos Rodríguez", 20);
    }
    
    @Test
    @DisplayName("Debería crear un estudiante correctamente")
    void shouldCreateStudent() {
        assertNotNull(student);
        assertEquals("STU001", student.getStudentId());
        assertEquals("Carlos Rodríguez", student.getName());
        assertEquals(20, student.getAge());
        assertEquals(0.0, student.getGpa());
        assertTrue(student.getCourses().isEmpty());
    }
    
    @Test
    @DisplayName("Debería validar edad negativa")
    void shouldValidateNegativeAge() {
        Student negativeAgeStudent = new Student("STU002", "Test", -5);
        assertEquals(0, negativeAgeStudent.getAge());
    }
    
    @Test
    @DisplayName("Debería inscribir cursos correctamente")
    void shouldEnrollCourses() {
        student.enrollCourse("POO");
        student.enrollCourse("Estructuras de Datos");
        
        assertEquals(2, student.getCourseCount());
        assertTrue(student.isEnrolledIn("POO"));
        assertTrue(student.isEnrolledIn("Estructuras de Datos"));
    }
    
    @Test
    @DisplayName("Debería evitar inscribirse dos veces en el mismo curso")
    void shouldPreventDuplicateEnrollment() {
        student.enrollCourse("POO");
        student.enrollCourse("POO");
        
        assertEquals(1, student.getCourseCount());
    }
    
    @Test
    @DisplayName("Debería eliminar cursos correctamente")
    void shouldDropCourses() {
        student.enrollCourse("POO");
        student.enrollCourse("Base de Datos");
        
        student.dropCourse("POO");
        
        assertEquals(1, student.getCourseCount());
        assertFalse(student.isEnrolledIn("POO"));
        assertTrue(student.isEnrolledIn("Base de Datos"));
    }
    
    @Test
    @DisplayName("Debería actualizar GPA correctamente")
    void shouldUpdateGpa() {
        student.updateGpa(3.5);
        assertEquals(3.5, student.getGpa());
    }
    
    @Test
    @DisplayName("Debería rechazar GPA fuera del rango válido")
    void shouldRejectInvalidGpa() {
        student.updateGpa(3.0);
        student.updateGpa(-1.0);
        student.updateGpa(5.0);
        
        assertEquals(3.0, student.getGpa());
    }
    
    @Test
    @DisplayName("Debería actualizar edad con validación")
    void shouldUpdateAgeWithValidation() {
        student.setAge(25);
        assertEquals(25, student.getAge());
        
        student.setAge(-5);
        assertEquals(25, student.getAge()); // No cambia
        
        student.setAge(150);
        assertEquals(25, student.getAge()); // No cambia
    }
    
    @Test
    @DisplayName("Debería retornar una copia de la lista de cursos")
    void shouldReturnCopyOfCourses() {
        student.enrollCourse("POO");
        student.enrollCourse("Base de Datos");
        
        var coursesCopy = student.getCourses();
        coursesCopy.add("Curso Falso");
        
        // La lista original no debe verse afectada
        assertEquals(2, student.getCourseCount());
        assertFalse(student.isEnrolledIn("Curso Falso"));
    }
}

