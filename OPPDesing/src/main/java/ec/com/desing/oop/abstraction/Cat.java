package ec.com.desing.oop.abstraction;

/**
 * Otra implementación concreta de Animal.
 * Cada clase concreta puede tener su propia implementación de los métodos abstractos.
 */
public class Cat extends Animal {
    
    private boolean isIndoor;
    
    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }
    
    @Override
    public void makeSound() {
        System.out.println("Miau miau!");
    }
    
    @Override
    public void move() {
        System.out.println("El gato está caminando sigilosamente");
    }
    
    @Override
    public String getType() {
        return "Gato";
    }
    
    public boolean isIndoor() {
        return isIndoor;
    }
    
    public void setIndoor(boolean indoor) {
        isIndoor = indoor;
    }
}

