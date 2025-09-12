package ec.com.pattern.creational.abstractfactory.furniturestore;

public class FactoryProvider {
    
    public static AbstractFactory getFactory(String choice) {
        if (choice.equalsIgnoreCase("SOFA")) {
            return new SofaFurnitureFactory();
        } else if (choice.equalsIgnoreCase("COFFEETABLE")) {
            return new CoffeTableFurnitureFactory();
        } else if (choice.equalsIgnoreCase("CHAIR")) {
            return new ChairFurnitureFactory();
        }
        return null;
    }
}
