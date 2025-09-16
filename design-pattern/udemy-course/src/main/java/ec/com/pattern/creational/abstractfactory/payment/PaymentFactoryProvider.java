package ec.com.pattern.creational.abstractfactory.payment;



import ec.com.pattern.creational.abstractfactory.payment.concretefactory.CardFactory;
import ec.com.pattern.creational.abstractfactory.payment.concretefactory.PaymentMethodFactory;

public class PaymentFactoryProvider {
    
    public static AbstractPaymentFactory getFactory(String choice) {
        if (choice.equalsIgnoreCase("CREDIT_CARD")) {
            return new CardFactory();
        } else if (choice.equalsIgnoreCase("PAYPAL")) {
            return new PaymentMethodFactory();
        } 
        return null;
    }
}
