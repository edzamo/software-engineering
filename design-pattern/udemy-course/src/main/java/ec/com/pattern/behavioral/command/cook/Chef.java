package ec.com.pattern.behavioral.command.cook;

/**
 * Receiver: The object that performs the actual action.
 */
public class Chef {
    public void cookBurger() {
        System.out.println("Chef: Cooking a burger...");
    }

    public void cookPasta() {
        System.out.println("Chef: Cooking pasta...");
    }
}