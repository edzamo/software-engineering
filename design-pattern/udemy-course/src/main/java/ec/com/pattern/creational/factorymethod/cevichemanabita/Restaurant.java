package ec.com.pattern.creational.factorymethod.cevichemanabita;

/**
 * Creator.
 * Declares the factory method that returns an object of type Product.
 */
public abstract class Restaurant {

    /**
     * This is the Factory Method. Subclasses will implement it.
     */
    protected abstract Ceviche createCeviche(CevicheType type);

    public Ceviche orderCeviche(CevicheType type) {
        Ceviche ceviche = createCeviche(type);
        System.out.println("--- Ordering a " + ceviche.getName() + " ---");
        ceviche.prepare();
        return ceviche;
    }
}