package ec.com.desing.oop.encapsulation;

/**
 * Clase de ejemplo que demuestra cómo usar la encapsulación.
 * Muestra cómo los datos están protegidos y solo se acceden mediante métodos públicos.
 */
public class EncapsulationExample {
    
    public static void main(String[] args) {
        System.out.println("=== Ejemplo de Encapsulación ===\n");
        
        // Ejemplo 1: BankAccount
        System.out.println("--- Ejemplo 1: Cuenta Bancaria ---");
        BankAccount account1 = new BankAccount("ACC001", "Juan Pérez", 1000.0, "SAVINGS");
        BankAccount account2 = new BankAccount("ACC002", "María García", 500.0, "CHECKING");
        
        // Acceso controlado mediante métodos públicos
        account1.displayAccountInfo();
        
        // Intentar acceder directamente a los atributos privados causaría error:
        // account1.balance = 5000; // ERROR: balance tiene acceso private
        
        // En su lugar, usamos métodos públicos con validación
        account1.deposit(500.0);
        account1.withdraw(200.0);
        
        // Intentar retirar más de lo disponible
        account1.withdraw(2000.0); // Será rechazado
        
        // Transferencia entre cuentas
        System.out.println("\n--- Transferencia entre cuentas ---");
        account1.transfer(account2, 300.0);
        
        account1.displayAccountInfo();
        account2.displayAccountInfo();
        
        // Ejemplo 2: Student
        System.out.println("\n--- Ejemplo 2: Estudiante ---");
        Student student = new Student("STU001", "Carlos Rodríguez", 20);
        
        student.displayStudentInfo();
        
        // Inscribir cursos mediante métodos públicos
        student.enrollCourse("Programación Orientada a Objetos");
        student.enrollCourse("Estructuras de Datos");
        student.enrollCourse("Base de Datos");
        
        // Intentar inscribirse dos veces en el mismo curso
        student.enrollCourse("Programación Orientada a Objetos");
        
        // Actualizar GPA con validación
        student.updateGpa(3.5);
        student.updateGpa(5.0); // Será rechazado (debe estar entre 0 y 4)
        
        // Verificar inscripción
        System.out.println("\n¿Está inscrito en 'Base de Datos'? " + 
                          student.isEnrolledIn("Base de Datos"));
        
        // Eliminar un curso
        student.dropCourse("Estructuras de Datos");
        
        student.displayStudentInfo();
        
        // Demostrar que getCourses() retorna una copia
        System.out.println("\n--- Demostración de protección de datos ---");
        var coursesCopy = student.getCourses();
        coursesCopy.add("Curso Falso"); // Esto no afecta la lista original
        System.out.println("Cursos en la copia: " + coursesCopy.size());
        System.out.println("Cursos en el estudiante: " + student.getCourseCount());
        
        // Intentar modificar edad con validación
        student.setAge(25);
        student.setAge(-5); // Será rechazado
        student.setAge(150); // Será rechazado
        
        System.out.println("\nEdad final: " + student.getAge());
    }
}

