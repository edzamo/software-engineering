package ec.com.desing.oop.descomposition;

/**
 * Clase de ejemplo que demuestra el concepto de descomposición.
 * 
 * La descomposición es el proceso de dividir un problema complejo
 * en partes más pequeñas y manejables. En este ejemplo:
 * 
 * 1. Person (objeto principal) - usa Address (objeto descompuesto)
 * 2. Student (clase hija) - hereda de Person y reutiliza Address
 * 
 * Ventajas de la descomposición:
 * - Separación de responsabilidades
 * - Reutilización de código (Address puede usarse en otras clases)
 * - Facilita el mantenimiento
 * - Hace el código más legible
 */
public class DecompositionExample {
    
    public static void main(String[] args) {
        System.out.println("=== Ejemplo de Descomposición ===\n");
        
        // Crear objeto descompuesto: Address
        Address address1 = new Address(
            "Av. Principal 123",
            "Quito",
            "Pichincha",
            "170101",
            "Ecuador"
        );
        
        Address address2 = new Address(
            "Calle Secundaria 456",
            "Guayaquil",
            "Guayas",
            "090101",
            "Ecuador"
        );
        
        // Crear Person (objeto principal que usa Address descompuesto)
        System.out.println("--- Objeto 1: Person ---");
        Person person = new Person(
            "P001",
            "Juan",
            "Pérez",
            "juan.perez@email.com",
            address1
        );
        person.displayInfo();
        
        // Crear Student (clase hija que hereda de Person y reutiliza Address)
        System.out.println("\n--- Objeto 2: Student (hijo de Person) ---");
        Student student = new Student(
            "P002",
            "María",
            "García",
            "maria.garcia@email.com",
            address2,
            "STU001",
            "Ingeniería de Software"
        );
        student.updateGpa(3.8);
        student.displayInfo();
        
        // Demostrar reutilización de Address
        System.out.println("\n--- Demostración de Reutilización ---");
        System.out.println("La clase Address puede reutilizarse en diferentes objetos:");
        System.out.println("Person usa: " + person.getAddress().getFullAddress());
        System.out.println("Student usa: " + student.getAddress().getFullAddress());
        
        // Cambiar dirección de Person (demostrando flexibilidad)
        System.out.println("\n--- Cambiando dirección de Person ---");
        Address newAddress = new Address(
            "Nueva Calle 789",
            "Cuenca",
            "Azuay",
            "010101",
            "Ecuador"
        );
        person.setAddress(newAddress);
        person.displayInfo();
        
        System.out.println("\n=== Ventajas de la Descomposición ===");
        System.out.println("1. Address es reutilizable en Person, Student y otras clases");
        System.out.println("2. Person está más enfocada (no maneja detalles de dirección)");
        System.out.println("3. Student hereda de Person y reutiliza la descomposición");
        System.out.println("4. Fácil de mantener: cambios en Address afectan a todos");
        System.out.println("5. Separación clara de responsabilidades");
    }
}
