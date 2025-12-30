package ec.com.desing.oop.generalization;

/**
 * Clase generalizada (superclase): Representa características comunes
 * de todos los vehículos.
 * 
 * La generalización es el proceso de extraer características comunes
 * de múltiples clases y colocarlas en una clase base (superclase).
 * 
 * Esta clase contiene atributos y métodos que son comunes a todos los vehículos.
 */
public class Vehicle {
    
    protected String brand;
    protected String model;
    protected int year;
    protected double price;
    protected String color;
    
    public Vehicle(String brand, String model, int year, double price, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.color = color;
    }
    
    // Métodos comunes a todos los vehículos
    public void start() {
        System.out.println(brand + " " + model + " está encendiendo...");
    }
    
    public void stop() {
        System.out.println(brand + " " + model + " se ha detenido.");
    }
    
    public void displayInfo() {
        System.out.println("=== Información del Vehículo ===");
        System.out.println("Marca: " + brand);
        System.out.println("Modelo: " + model);
        System.out.println("Año: " + year);
        System.out.println("Precio: $" + price);
        System.out.println("Color: " + color);
    }
    
    // Getters
    public String getBrand() {
        return brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public int getYear() {
        return year;
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getColor() {
        return color;
    }
    
    // Setters
    public void setPrice(double price) {
        if (price > 0) {
            this.price = price;
        }
    }
    
    public void setColor(String color) {
        this.color = color;
    }
}

