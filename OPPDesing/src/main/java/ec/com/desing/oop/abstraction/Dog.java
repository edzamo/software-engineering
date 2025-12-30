package ec.com.desing.oop.abstraction;

/**
 * Implementación concreta de Animal.
 * Debe implementar todos los métodos abstractos de la clase Animal.
 */
public class Dog extends Animal {
    
    private String breed;
    
    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }
    
    @Override
    public void makeSound() {
        System.out.println("Guau guau!");
    }
    
    @Override
    public void move() {
        System.out.println("El perro está corriendo");
    }
    
    @Override
    public String getType() {
        return "Perro";
    }
    
    public String getBreed() {
        return breed;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
}

