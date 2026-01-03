package ec.com.desing.oop.association;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssociationTest {

    @Test
    void teacherAndStudentAssociation() {
        Teacher teacher = new Teacher("Mrs. Smith");
        Student student = new Student("John");

        assertEquals("Mrs. Smith", teacher.getName());
        assertEquals("John", student.getName());
    }
}
