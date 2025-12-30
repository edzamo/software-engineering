package ec.com.desing.oop.descomposition;

/**
 * Ejemplo de descomposición: La clase Person se descompone usando
 * la clase Address para representar la dirección.
 * 
 * En lugar de tener todos los campos de dirección directamente en Person,
 * descomponemos la dirección en una clase separada (Address).
 * 
 * Esto permite:
 * - Reutilizar Address en otras clases
 * - Mantener Person más simple y enfocada
 * - Separar responsabilidades
 */
public class Person {
    
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address; // Objeto descompuesto
    
    public Person(String id, String firstName, String lastName, String email, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }
    
    public String getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        }
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void displayInfo() {
        System.out.println("=== Información de la Persona ===");
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + getFullName());
        System.out.println("Email: " + email);
        System.out.println("Dirección: " + address.getFullAddress());
        System.out.println("================================");
    }
}

