package ec.com.desing.oop.abstraction;

/**
 * Clase abstracta que demuestra el concepto de abstracción.
 * Define el comportamiento común que todas las clases hijas deben implementar.
 */
public abstract class Animal {
    
    protected String name;
    protected int age;
    
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Métodos concretos (con implementación)
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void sleep() {
        System.out.println(name + " está durmiendo...");
    }
    
    // Métodos abstractos (sin implementación - deben ser implementados por las clases hijas)
    public abstract void makeSound();
    
    public abstract void move();
    
    public abstract String getType();
    
    // Método que usa los métodos abstractos
    public void displayInfo() {
        System.out.println("Tipo: " + getType());
        System.out.println("Nombre: " + name);
        System.out.println("Edad: " + age + " años");
        System.out.print("Sonido: ");
        makeSound();
        System.out.print("Movimiento: ");
        move();
    }
}

