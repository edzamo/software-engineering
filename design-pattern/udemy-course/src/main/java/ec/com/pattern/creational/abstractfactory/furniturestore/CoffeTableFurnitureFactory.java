package ec.com.pattern.creational.abstractfactory.furniturestore;

public class CoffeTableFurnitureFactory implements AbstractFactory<CoffeeTable> {
    @Override
    public CoffeeTable create(String type) {
        if (type.equalsIgnoreCase("MODERN")) {
            return new ModernCoffeeTable();
        } else if (type.equalsIgnoreCase("VICTORIAN")) {
            return new VictorianCoffeeTable();
        }
        return null;
    }
    
}
