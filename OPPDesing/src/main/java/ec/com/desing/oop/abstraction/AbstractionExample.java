package ec.com.desing.oop.abstraction;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de ejemplo que demuestra cómo usar la abstracción.
 * Muestra cómo trabajar con clases abstractas y polimorfismo.
 */
public class AbstractionExample {
    
    public static void main(String[] args) {
        System.out.println("=== Ejemplo de Abstracción ===\n");
        
        // Crear instancias de las clases concretas
        Animal dog = new Dog("Max", 3, "Labrador");
        Animal cat = new Cat("Luna", 2, true);
        
        // Usar polimorfismo: tratar diferentes tipos como Animal
        System.out.println("--- Información del Perro ---");
        dog.displayInfo();
        dog.sleep();
        
        System.out.println("\n--- Información del Gato ---");
        cat.displayInfo();
        cat.sleep();
        
        // Ejemplo con una lista de animales (polimorfismo)
        System.out.println("\n--- Lista de Animales ---");
        List<Animal> animals = new ArrayList<>();
        animals.add(dog);
        animals.add(cat);
        animals.add(new Dog("Bella", 5, "Golden Retriever"));
        animals.add(new Cat("Simba", 1, false));
        
        // Iterar sobre la lista usando la abstracción
        animals.forEach(animal -> {
            System.out.println("\n" + animal.getType() + ":");
            animal.makeSound();
            animal.move();
        });
        
        // Demostrar que no se puede instanciar una clase abstracta
        // Animal animal = new Animal("Test", 1); // Esto causaría un error de compilación
    }
}

