package ec.com.pattern.creational.factorymethod.cevichemanabita;

/**
 * Concrete Product.
 */
public class ShrimpCeviche extends Ceviche {

    public ShrimpCeviche() {
        name = "Shrimp Ceviche";
        ingredients.add("cooked shrimp");
        ingredients.add("tomato sauce");
        ingredients.add("fried plantains (patacones)");
    }
}
