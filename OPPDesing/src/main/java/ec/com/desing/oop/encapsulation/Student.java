package ec.com.desing.oop.encapsulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Otro ejemplo de encapsulación: clase Student que demuestra
 * cómo proteger los datos y controlar el acceso mediante métodos.
 */
public class Student {
    
    // Atributos privados
    private String studentId;
    private String name;
    private int age;
    private List<String> courses;
    private double gpa; // Grade Point Average (Promedio de calificaciones)
    
    // Constructor
    public Student(String studentId, String name, int age) {
        this.studentId = studentId;
        this.name = name;
        this.age = age >= 0 ? age : 0; // Validación
        this.courses = new ArrayList<>();
        this.gpa = 0.0;
    }
    
    // Getters
    public String getStudentId() {
        return studentId;
    }
    
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public List<String> getCourses() {
        // Retornar una copia para evitar modificación externa directa
        return new ArrayList<>(courses);
    }
    
    public double getGpa() {
        return gpa;
    }
    
    // Setters con validación
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }
    
    public void setAge(int age) {
        if (age >= 0 && age <= 120) { // Validación razonable
            this.age = age;
        } else {
            System.out.println("Error: La edad debe estar entre 0 y 120 años");
        }
    }
    
    // Métodos de negocio encapsulados
    
    /**
     * Agrega un curso a la lista del estudiante
     * @param courseName nombre del curso
     */
    public void enrollCourse(String courseName) {
        if (courseName != null && !courseName.trim().isEmpty()) {
            if (!courses.contains(courseName)) {
                courses.add(courseName);
                System.out.println(name + " se ha inscrito en: " + courseName);
            } else {
                System.out.println("El estudiante ya está inscrito en: " + courseName);
            }
        }
    }
    
    /**
     * Elimina un curso de la lista del estudiante
     * @param courseName nombre del curso
     */
    public void dropCourse(String courseName) {
        if (courses.remove(courseName)) {
            System.out.println(name + " ha eliminado el curso: " + courseName);
        } else {
            System.out.println("El estudiante no está inscrito en: " + courseName);
        }
    }
    
    /**
     * Actualiza el GPA del estudiante
     * @param newGpa nuevo promedio
     */
    public void updateGpa(double newGpa) {
        if (newGpa >= 0.0 && newGpa <= 4.0) { // Validación: GPA típicamente entre 0 y 4
            this.gpa = newGpa;
            System.out.println("GPA actualizado para " + name + ": " + gpa);
        } else {
            System.out.println("Error: El GPA debe estar entre 0.0 y 4.0");
        }
    }
    
    /**
     * Obtiene el número de cursos en los que está inscrito
     * @return número de cursos
     */
    public int getCourseCount() {
        return courses.size();
    }
    
    /**
     * Verifica si el estudiante está inscrito en un curso específico
     * @param courseName nombre del curso
     * @return true si está inscrito, false en caso contrario
     */
    public boolean isEnrolledIn(String courseName) {
        return courses.contains(courseName);
    }
    
    /**
     * Muestra la información del estudiante
     */
    public void displayStudentInfo() {
        System.out.println("=== Información del Estudiante ===");
        System.out.println("ID: " + studentId);
        System.out.println("Nombre: " + name);
        System.out.println("Edad: " + age);
        System.out.println("GPA: " + gpa);
        System.out.println("Cursos inscritos (" + courses.size() + "):");
        if (courses.isEmpty()) {
            System.out.println("  - No hay cursos inscritos");
        } else {
            courses.forEach(course -> System.out.println("  - " + course));
        }
        System.out.println("==================================");
    }
}

