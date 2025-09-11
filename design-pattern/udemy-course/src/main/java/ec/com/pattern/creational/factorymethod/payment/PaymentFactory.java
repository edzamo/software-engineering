package ec.com.pattern.creational.factorymethod.payment;

import ec.com.pattern.creational.factorymethod.payment.enums.TypePayment;

public class PaymentFactory {

    public static Payment buildPayment(TypePayment typePayment) {
        switch (typePayment) {
            case CARD:
                return new CardPayment();
            case GOOGLE:
                return new GooglePayment();
            case PAYPAL:
                return new PaypalPayment();
            default:
                throw new IllegalArgumentException("No such payment");
        }
    }
}
