package ec.com.desing.oop.generalization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para las clases de generalización
 */
@DisplayName("Tests para Generalización")
class VehicleTest {
    
    private Car car;
    private Motorcycle motorcycle;
    
    @BeforeEach
    void setUp() {
        car = new Car("Toyota", "Camry", 2023, 35000.0, "Rojo", 4, "Gasolina", true);
        motorcycle = new Motorcycle("Honda", "CBR600RR", 2023, 12000.0, "Negro", 600, false, "Sport");
    }
    
    @Test
    @DisplayName("Debería crear un Car correctamente")
    void shouldCreateCar() {
        assertNotNull(car);
        assertEquals("Toyota", car.getBrand());
        assertEquals("Camry", car.getModel());
        assertEquals(2023, car.getYear());
        assertEquals(35000.0, car.getPrice());
        assertEquals(4, car.getNumberOfDoors());
        assertEquals("Gasolina", car.getFuelType());
        assertTrue(car.hasSunroof());
    }
    
    @Test
    @DisplayName("Debería crear un Motorcycle correctamente")
    void shouldCreateMotorcycle() {
        assertNotNull(motorcycle);
        assertEquals("Honda", motorcycle.getBrand());
        assertEquals("CBR600RR", motorcycle.getModel());
        assertEquals(2023, motorcycle.getYear());
        assertEquals(12000.0, motorcycle.getPrice());
        assertEquals(600, motorcycle.getEngineCapacity());
        assertFalse(motorcycle.hasSidecar());
        assertEquals("Sport", motorcycle.getBikeType());
    }
    
    @Test
    @DisplayName("Car debería heredar métodos de Vehicle")
    void carShouldInheritFromVehicle() {
        assertDoesNotThrow(() -> car.start());
        assertDoesNotThrow(() -> car.stop());
        assertEquals("Toyota", car.getBrand());
        assertEquals("Camry", car.getModel());
    }
    
    @Test
    @DisplayName("Motorcycle debería heredar métodos de Vehicle")
    void motorcycleShouldInheritFromVehicle() {
        assertDoesNotThrow(() -> motorcycle.start());
        assertDoesNotThrow(() -> motorcycle.stop());
        assertEquals("Honda", motorcycle.getBrand());
        assertEquals("CBR600RR", motorcycle.getModel());
    }
    
    @Test
    @DisplayName("Debería usar polimorfismo con Vehicle")
    void shouldUsePolymorphism() {
        Vehicle vehicle1 = new Car("Ford", "Mustang", 2022, 45000.0, "Azul", 2, "Gasolina", false);
        Vehicle vehicle2 = new Motorcycle("Yamaha", "R1", 2023, 18000.0, "Amarillo", 1000, false, "Sport");
        
        assertEquals("Ford", vehicle1.getBrand());
        assertEquals("Yamaha", vehicle2.getBrand());
        
        // Polimorfismo: cada uno ejecuta su propia implementación de start()
        assertDoesNotThrow(() -> vehicle1.start());
        assertDoesNotThrow(() -> vehicle2.start());
    }
    
    @Test
    @DisplayName("Debería poder cambiar el precio usando método heredado")
    void shouldChangePriceUsingInheritedMethod() {
        car.setPrice(32000.0);
        assertEquals(32000.0, car.getPrice());
        
        motorcycle.setPrice(11000.0);
        assertEquals(11000.0, motorcycle.getPrice());
    }
    
    @Test
    @DisplayName("Debería rechazar precios negativos")
    void shouldRejectNegativePrices() {
        double originalPrice = car.getPrice();
        car.setPrice(-1000.0);
        assertEquals(originalPrice, car.getPrice()); // No cambia
    }
}

