package ec.com.desing.oop.generalization;

/**
 * Clase especializada (subclase): Motorcycle hereda de Vehicle.
 * 
 * Motorcycle es otra especialización de Vehicle que agrega características
 * específicas de las motocicletas.
 */
public class Motorcycle extends Vehicle {
    
    private int engineCapacity; // en cc (centímetros cúbicos)
    private boolean hasSidecar;
    private String bikeType; // Sport, Cruiser, Touring, etc.
    
    public Motorcycle(String brand, String model, int year, double price, String color,
                      int engineCapacity, boolean hasSidecar, String bikeType) {
        super(brand, model, year, price, color);
        this.engineCapacity = engineCapacity;
        this.hasSidecar = hasSidecar;
        this.bikeType = bikeType;
    }
    
    // Métodos específicos de Motorcycle
    public void wheelie() {
        System.out.println("¡El " + brand + " " + model + " está haciendo un wheelie!");
    }
    
    public void revEngine() {
        System.out.println("Vroom vroom! El motor de " + engineCapacity + "cc está rugiendo");
    }
    
    @Override
    public void start() {
        super.start();
        System.out.println("La motocicleta está lista para rodar.");
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Tipo: Motocicleta");
        System.out.println("Capacidad del motor: " + engineCapacity + " cc");
        System.out.println("Tiene sidecar: " + (hasSidecar ? "Sí" : "No"));
        System.out.println("Tipo de moto: " + bikeType);
        System.out.println("================================");
    }
    
    // Getters específicos
    public int getEngineCapacity() {
        return engineCapacity;
    }
    
    public boolean hasSidecar() {
        return hasSidecar;
    }
    
    public String getBikeType() {
        return bikeType;
    }
}

