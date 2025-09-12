package ec.com.pattern.creational.factorymethod.cevichemanabita;

/**
 * Concrete Creator.
 * Implements the factory method to return an instance of a ConcreteProduct.
 */
public class ManabitaRestaurantFactory extends Restaurant {

    @Override
    protected Ceviche createCeviche(CevicheType type) {
        switch (type) {
            case FISH:
                return new FishCeviche();
            case SHRIMP:
                return new ShrimpCeviche();
            case MIXED:
                return new MixedCeviche();
            case JIPIJAPA_FISH:
                return new JipijapaCeviche();
            default:
                throw new IllegalArgumentException("Ceviche type not available: " + type);
        }
    }
}