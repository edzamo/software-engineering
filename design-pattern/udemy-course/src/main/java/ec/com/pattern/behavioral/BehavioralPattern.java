package ec.com.pattern.behavioral;

import ec.com.pattern.behavioral.chainofresponsability.creditcard.Card;
import ec.com.pattern.behavioral.chainofresponsability.servicedesk.ServiceDesk;

public class BehavioralPattern {

    public static void behavioralPattern() {
        System.out.println("=======+====== Behavioral Design Patterns ========");
        System.out.println("\nBehavioral Design Patterns Examples");

        chainOfResponsabilityCreditCard();
        chainOfResponsabilityServiceDesk();

        System.out.println("\nEnd of Behavioral Design Patterns Examples");
    }

    private static void chainOfResponsabilityCreditCard() {
        System.out.println("\nChain of Responsability Pattern Example :");

        Card card = new Card();
        card.crediCardRequest(20000);    


    
    }


    private static void chainOfResponsabilityServiceDesk() {
        System.out.println("\nChain of Responsability Pattern Example :");

        ServiceDesk serviceDesk = new ServiceDesk();
        serviceDesk.serviceDeskRequest("Basic Support");

    }



}
