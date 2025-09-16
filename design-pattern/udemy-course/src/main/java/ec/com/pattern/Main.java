package ec.com.pattern;

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



public class Main {
    public static void main(String[] args) {

        factoryMethodPatternExample();
        abstractFactoryPatternExample();

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
}
