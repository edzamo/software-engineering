package ec.com.pattern.creational.factorymethod.cevichemanabita;

/**
 * Concrete Product.
 */
public class FishCeviche extends Ceviche {

    public FishCeviche() {
        name = "Fish Ceviche (Tuna)";
        ingredients.add("fresh fish chunks");
        ingredients.add("plantain chips (chifles)");
    }
}
