package ec.com.pattern;

import ec.com.pattern.creational.factorymethod.logist.Transport;
import ec.com.pattern.creational.factorymethod.logist.TransportFactory;
import ec.com.pattern.creational.factorymethod.logist.enums.TypeTransport;
import ec.com.pattern.creational.factorymethod.payment.Payment;
import ec.com.pattern.creational.factorymethod.payment.PaymentFactory;
import ec.com.pattern.creational.factorymethod.payment.enums.TypePayment;

public class Main {
    public static void main(String[] args) {
        System.out.println("Factory Method Pattern Example :");
        factoryMethodPatternExample();

    }

    private static void factoryMethodPatternExample() {

        Payment payment = PaymentFactory.buildPayment(TypePayment.GOOGLE);
        payment.doPayment();

        Transport transport = TransportFactory.buildTransport(TypeTransport.SHIP);
        transport.deliver();
    }
}
