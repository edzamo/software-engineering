package ec.com.desing.oop.generalization;

/**
 * Clase especializada (subclase): Car hereda de Vehicle.
 * 
 * Car es una especialización de Vehicle que agrega características
 * específicas de los automóviles.
 */
public class Car extends Vehicle {
    
    private int numberOfDoors;
    private String fuelType;
    private boolean hasSunroof;
    
    public Car(String brand, String model, int year, double price, String color,
               int numberOfDoors, String fuelType, boolean hasSunroof) {
        super(brand, model, year, price, color);
        this.numberOfDoors = numberOfDoors;
        this.fuelType = fuelType;
        this.hasSunroof = hasSunroof;
    }
    
    // Métodos específicos de Car
    public void openTrunk() {
        System.out.println("Abriendo el maletero del " + brand + " " + model);
    }
    
    public void honk() {
        System.out.println("¡Beep beep! El " + brand + " " + model + " está tocando la bocina");
    }
    
    @Override
    public void start() {
        super.start();
        System.out.println("El automóvil está listo para conducir.");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Tipo: Automóvil");
        System.out.println("Número de puertas: " + numberOfDoors);
        System.out.println("Tipo de combustible: " + fuelType);
        System.out.println("Tiene techo solar: " + (hasSunroof ? "Sí" : "No"));
        System.out.println("================================");
    }
    
    // Getters específicos
    public int getNumberOfDoors() {
        return numberOfDoors;
    }
    
    public String getFuelType() {
        return fuelType;
    }
    
    public boolean hasSunroof() {
        return hasSunroof;
    }
}

