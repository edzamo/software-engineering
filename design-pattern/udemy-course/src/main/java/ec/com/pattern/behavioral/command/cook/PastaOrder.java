package ec.com.pattern.behavioral.command.cook;

/**
 * Concrete Command: A specific order for pasta.
 */
public class PastaOrder implements Order {
    private final Chef chef;

    public PastaOrder(Chef chef) {
        this.chef = chef;
    }

    @Override
    public void execute() {
        chef.cookPasta();
    }
}