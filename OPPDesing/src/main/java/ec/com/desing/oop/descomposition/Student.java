package ec.com.desing.oop.descomposition;

/**
 * Clase hija de Person que demuestra herencia junto con descomposición.
 * 
 * Student hereda de Person y reutiliza la descomposición de Address.
 * Además, agrega funcionalidad específica de estudiante.
 */
public class Student extends Person {
    
    private String studentId;
    private String major;
    private double gpa;
    
    public Student(String id, String firstName, String lastName, String email, 
                   Address address, String studentId, String major) {
        super(id, firstName, lastName, email, address);
        this.studentId = studentId;
        this.major = major;
        this.gpa = 0.0;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public String getMajor() {
        return major;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setMajor(String major) {
        this.major = major;
    }
    
    public void updateGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 4.0) {
            this.gpa = gpa;
        }
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Información del Estudiante ===");
        System.out.println("ID Personal: " + getId());
        System.out.println("ID Estudiante: " + studentId);
        System.out.println("Nombre: " + getFullName());
        System.out.println("Email: " + getEmail());
        System.out.println("Carrera: " + major);
        System.out.println("GPA: " + gpa);
        System.out.println("Dirección: " + getAddress().getFullAddress());
        System.out.println("==================================");
    }
}

