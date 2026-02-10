package ec.com.desing.oop.generalization;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de ejemplo que demuestra el concepto de generalización.
 * 
 * La generalización es el proceso de crear una clase base (superclase)
 * que contiene características comunes de varias clases relacionadas.
 * 
 * En este ejemplo:
 * - Vehicle es la clase generalizada (superclase)
 * - Car y Motorcycle son clases especializadas (subclases) que heredan de Vehicle
 * 
 * Ventajas de la generalización:
 * - Reutilización de código común
 * - Consistencia en el diseño
 * - Facilita el mantenimiento
 * - Permite polimorfismo
 * - Reduce la duplicación de código
 */
public class GeneralizationExample {
    
    public static void main(String[] args) {
        System.out.println("=== Ejemplo de Generalización ===\n");
        
        // Crear instancias de las clases especializadas
        Car car = new Car(
            "Toyota",
            "Camry",
            2023,
            35000.0,
            "Rojo",
            4,
            "Gasolina",
            true
        );
        
        Motorcycle motorcycle = new Motorcycle(
            "Honda",
            "CBR600RR",
            2023,
            12000.0,
            "Negro",
            600,
            false,
            "Sport"
        );
        
        // Demostrar herencia: métodos heredados de Vehicle
        System.out.println("--- Usando métodos heredados de Vehicle ---");
        car.start();
        car.stop();
        
        System.out.println();
        motorcycle.start();
        motorcycle.stop();
        
        // Demostrar métodos específicos de cada clase
        System.out.println("\n--- Métodos específicos de cada clase ---");
        car.openTrunk();
        car.honk();
        
        System.out.println();
        motorcycle.wheelie();
        motorcycle.revEngine();
        
        // Mostrar información completa
        System.out.println("\n--- Información de los vehículos ---");
        car.displayInfo();
        System.out.println();
        motorcycle.displayInfo();
        
        // Demostrar polimorfismo: tratar diferentes tipos como Vehicle
        System.out.println("\n--- Demostración de Polimorfismo ---");
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car);
        vehicles.add(motorcycle);
        vehicles.add(new Car("Ford", "Mustang", 2022, 45000.0, "Azul", 2, "Gasolina", false));
        vehicles.add(new Motorcycle("Yamaha", "R1", 2023, 18000.0, "Amarillo", 1000, false, "Sport"));
        
        System.out.println("Lista de vehículos (usando generalización):");
        for (Vehicle vehicle : vehicles) {
            System.out.println("- " + vehicle.getBrand() + " " + vehicle.getModel() + 
                             " (" + vehicle.getYear() + ") - $" + vehicle.getPrice());
            vehicle.start(); // Polimorfismo: cada uno ejecuta su propia implementación
        }
        
        // Demostrar acceso a atributos comunes
        System.out.println("\n--- Acceso a atributos comunes ---");
        System.out.println("Precio del carro: $" + car.getPrice());
        System.out.println("Precio de la moto: $" + motorcycle.getPrice());
        
        // Cambiar precio usando método heredado
        car.setPrice(32000.0);
        System.out.println("Nuevo precio del carro: $" + car.getPrice());
        
        System.out.println("\n=== Ventajas de la Generalización ===");
        System.out.println("1. Vehicle define características comunes (brand, model, year, etc.)");
        System.out.println("2. Car y Motorcycle heredan estas características");
        System.out.println("3. Cada clase especializada agrega sus propias características");
        System.out.println("4. Se puede tratar diferentes vehículos de manera uniforme (polimorfismo)");
        System.out.println("5. Cambios en Vehicle afectan a todas las subclases");
        System.out.println("6. Reduce duplicación de código");
    }
}

