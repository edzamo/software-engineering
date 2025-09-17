package ec.com.pattern.creational;

import ec.com.pattern.creational.abstractfactory.furniturestore.AbstractFactory;
import ec.com.pattern.creational.abstractfactory.furniturestore.Chair;
import ec.com.pattern.creational.abstractfactory.furniturestore.FactoryProvider;
import ec.com.pattern.creational.abstractfactory.furniturestore.Sofa;
import ec.com.pattern.creational.abstractfactory.payment.AbstractPaymentFactory;
import ec.com.pattern.creational.abstractfactory.payment.PaymentFactoryProvider;
import ec.com.pattern.creational.abstractfactory.payment.concrete.Card;
import ec.com.pattern.creational.abstractfactory.payment.concrete.PaymentMethod;
import ec.com.pattern.creational.factorymethod.logist.Transport;
import ec.com.pattern.creational.factorymethod.logist.TransportFactory;
import ec.com.pattern.creational.factorymethod.logist.enums.TypeTransport;
import ec.com.pattern.creational.factorymethod.payment.Payment;
import ec.com.pattern.creational.factorymethod.payment.PaymentFactory;
import ec.com.pattern.creational.factorymethod.payment.enums.TypePayment;
import ec.com.pattern.creational.prototype.PrototypeCard;

public class CreationalPattern {

    public static void patternCreational() {
        System.out.println("=======+====== Creational Design Patterns ========");
        System.out.println("Creational Design Patterns Examples");

        factoryMethodPatternExample();
        abstractFactoryPatternExample();
        builderPatternExample();
        prototypePatternExample();
        singletonPatternExample();


        System.out.println("\nEnd of Creational Design Patterns Examples");
        System.out.println("Behavioral Design Patterns Examples in progress...");

    }

    private static void factoryMethodPatternExample() {
        System.out.println("Factory Method Pattern Example :");

        Payment payment = PaymentFactory.buildPayment(TypePayment.GOOGLE);
        payment.doPayment();

        Transport transport = TransportFactory.buildTransport(TypeTransport.SHIP);
        transport.deliver();
    }

    @SuppressWarnings("rawtypes")
    private static void abstractFactoryPatternExample() {

        System.out.println("\n");
        System.out.println("Abstract Factory Pattern Example Payment:");
        AbstractPaymentFactory cardFactory = PaymentFactoryProvider.getFactory("CREDIT_CARD");
        Card card = (Card) cardFactory.create("VISA");
        System.out.println(card.getCardType());
        AbstractPaymentFactory paymentMethodFactory = PaymentFactoryProvider.getFactory("PAYPAL");
        PaymentMethod paymentMethod = (PaymentMethod) paymentMethodFactory.create("CREDIT");
        System.out.println(paymentMethod.doPayment());

        System.out.println("\n");
        System.out.println("Abstract Factory Pattern Example Furniture:");
        AbstractFactory abstractFactorySofa = FactoryProvider.getFactory("SOFA");
        Sofa sofa = (Sofa) abstractFactorySofa.create("MODERN");
        System.out.println(sofa.getStyle());

        AbstractFactory abstracFactoryChair = FactoryProvider.getFactory("CHAIR");
        Chair chair = (Chair) abstracFactoryChair.create("VICTORIAN");
        System.out.println(chair.sitOn());
        System.out.println(chair.hasLargeLegs());

    }

    private static void builderPatternExample() {
        System.out.println("\n");
        System.out.println("Builder Pattern Example :");

        ec.com.pattern.creational.builder.Card card = new ec.com.pattern.creational.builder.Card.Builder()
                .setCardNumber("1234 5678 9012 3456")
                .setCardHolder("John Doe")
                .setExpirationDate("12/25")
                .setCvv("123")
                .build();

        System.out.println(card);
    }

    private static void prototypePatternExample() {
        System.out.println("\n");
        System.out.println("Prototype Pattern Example :");

        PrototypeCard visaCard ;
        PrototypeCard amexCard ;
        try {
            ec.com.pattern.creational.prototype.PrototypeFactory.loadCards();
            visaCard = ec.com.pattern.creational.prototype.PrototypeFactory
                    .getInstance(ec.com.pattern.creational.prototype.PrototypeFactory.CardType.VISA);
            visaCard.getCard();
            System.out.println(visaCard);
            amexCard = ec.com.pattern.creational.prototype.PrototypeFactory
                    .getInstance(ec.com.pattern.creational.prototype.PrototypeFactory.CardType.AMEX);
            amexCard.getCard();
            System.out.println(amexCard);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


    private static void singletonPatternExample() {
        System.out.println("\n");
        System.out.println("Singleton Pattern Example :");
    
      ec.com.pattern.creational.singleton.Card.getINSTANCE().setName("name");
        System.out.println(ec.com.pattern.creational.singleton.Card.getINSTANCE().getName());
    }
}