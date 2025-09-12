package ec.com.pattern.creational.factorymethod.cevichemanabita;

import java.util.ArrayList;
import java.util.List;

/**
 * Product.
 * Abstract class representing a Ceviche. It defines the common structure.
 */
public abstract class Ceviche {

    protected String name;
    protected List<String> ingredients = new ArrayList<>();

    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Adding base of lime, onion, and cilantro.");
        ingredients.forEach(ingredient -> System.out.println("Adding " + ingredient));
        System.out.println("Ceviche is ready to be served!");
    }

    public String getName() {
        return name;
    }
}
