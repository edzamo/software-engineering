package ec.com.pattern.creational.factorymethod.cevichemanabita;

/**
 * Concrete Product.
 */
public class MixedCeviche extends Ceviche {

    public MixedCeviche() {
        name = "Manabita-style Mixed Ceviche";
        ingredients.add("fish chunks");
        ingredients.add("shrimp");
        ingredients.add("octopus");
        ingredients.add("chifles and patacones");
    }
}
