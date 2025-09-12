package ec.com.pattern.creational.abstractfactory.furniturestore;

public class SofaFurnitureFactory implements AbstractFactory<Sofa> {
    
    @Override
    public Sofa create(String type) {
        if (type.equalsIgnoreCase("MODERN")) {
            return new ModernSofa();
        } else if (type.equalsIgnoreCase("VICTORIAN")) {
            return new VictorianSofa();
        }
        return null;
    }

}
