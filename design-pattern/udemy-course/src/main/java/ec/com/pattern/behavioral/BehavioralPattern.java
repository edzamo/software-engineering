package ec.com.pattern.behavioral;

import ec.com.pattern.behavioral.chainofresponsability.Card;

public class BehavioralPattern {

    public static void behavioralPattern() {
        System.out.println("=======+====== Behavioral Design Patterns ========");
        System.out.println("\nBehavioral Design Patterns Examples");

        chainOfResponsabilityExample();

        System.out.println("\nEnd of Behavioral Design Patterns Examples");
    }

    private static void chainOfResponsabilityExample() {
        System.out.println("\nChain of Responsability Pattern Example :");

        Card card = new Card();
        card.crediCardRequest(20000);    


        System.out.println("End of Chain of Responsability Pattern Example");
    }

}
