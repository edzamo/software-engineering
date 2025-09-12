package ec.com.pattern.creational.abstractfactory.furniturestore;

public class ChairFurnitureFactory implements AbstractFactory<Chair> {
    @Override
    public Chair create(String type) {
        if (type.equalsIgnoreCase("MODERN")) {
            return new ModernChair();
        } else if (type.equalsIgnoreCase("VICTORIAN")) {
            return new VictorianChair();
        }
        return null;
    }


 

}
