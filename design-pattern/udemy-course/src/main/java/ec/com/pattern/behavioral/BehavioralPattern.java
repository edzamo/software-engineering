package ec.com.pattern.behavioral;

import java.util.List;

import ec.com.pattern.behavioral.chainofresponsability.creditcard.Card;
import ec.com.pattern.behavioral.chainofresponsability.servicedesk.ServiceDesk;
import ec.com.pattern.behavioral.command.cook.BurgerOrder;
import ec.com.pattern.behavioral.command.cook.Chef;
import ec.com.pattern.behavioral.command.cook.Order;
import ec.com.pattern.behavioral.command.cook.PastaOrder;
import ec.com.pattern.behavioral.command.cook.WaiterInvoker;
import ec.com.pattern.behavioral.command.credicardstatus.CrediCardInvoker;
import ec.com.pattern.behavioral.command.credicardstatus.CreditActiveCommand;
import ec.com.pattern.behavioral.command.credicardstatus.CreditCard;

public class BehavioralPattern {

    public static void behavioralPattern() {
        System.out.println("=======+====== Behavioral Design Patterns ========");
        System.out.println("\nBehavioral Design Patterns Examples");

        chainOfResponsabilityCreditCard();
        chainOfResponsabilityServiceDesk();
        commandPattern();
        commandPatternCook();
        iteratorPattern();


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

    private static void commandPattern() {
        System.out.println("\nCommand Pattern Example :");
        CreditCard creditCard = new CreditCard();
        CreditCard creditCardDesactive= new CreditCard();

        CrediCardInvoker invoker = new CrediCardInvoker();
        invoker.setCommand(new CreditActiveCommand(creditCard));
        invoker.run();
        System.out.println("-------------------");
        invoker.setCommand(new CreditActiveCommand(creditCardDesactive));
        invoker.run();
    }

        private  static void commandPatternCook() {

        System.out.println("\nCommand Pattern Cook Example :");
        Chef chef = new Chef();
        WaiterInvoker waiter = new WaiterInvoker();

        System.out.println("Customer: I'd like a burger.");
        Order burgerOrder = new BurgerOrder(chef);
        waiter.takeOrder(burgerOrder);

        System.out.println("\nCustomer: And my friend would like pasta.");
        Order pastaOrder = new PastaOrder(chef);
        waiter.takeOrder(pastaOrder);

        // The waiter goes to the kitchen and gives the orders to the chef
        waiter.placeOrders();

        System.out.println("\nWaiter: Here is your meal!");
    }


private static void iteratorPattern() {
    System.out.println("\nIterator Pattern Example :");
ec.com.pattern.behavioral.iteractor.Card[] cards = new ec.com.pattern.behavioral.iteractor.Card[5];
    cards[0] = new ec.com.pattern.behavioral.iteractor.Card("Card 1");
    cards[1] = new ec.com.pattern.behavioral.iteractor.Card("Card 2");
    cards[2] = new ec.com.pattern.behavioral.iteractor.Card("Card 3");
    cards[3] = new ec.com.pattern.behavioral.iteractor.Card("Card 4");
    cards[4] = new ec.com.pattern.behavioral.iteractor.Card("Card 5");

    ec.com.pattern.behavioral.iteractor.List list = new ec.com.pattern.behavioral.iteractor.CardList(cards);
    ec.com.pattern.behavioral.iteractor.Iterator iterator = list.iterator();

    while (iterator.hasNext()) {
        ec.com.pattern.behavioral.iteractor.Card tarjeta= (ec.com.pattern.behavioral.iteractor.Card) iterator.next();
         System.out.println(tarjeta.getType());
    }

    
   
    }

}
