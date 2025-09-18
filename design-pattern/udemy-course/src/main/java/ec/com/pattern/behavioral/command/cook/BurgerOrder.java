package ec.com.pattern.behavioral.command.cook;

/**
 * Concrete Command: A specific order for a burger.
 */
public class BurgerOrder implements Order {
    private final Chef chef;

    public BurgerOrder(Chef chef) {
        this.chef = chef;
    }

    @Override
    public void execute() {
        chef.cookBurger();
    }
}