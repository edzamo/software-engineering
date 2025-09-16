package ec.com.pattern.creational.abstractfactory.payment.concretefactory;

import ec.com.pattern.creational.abstractfactory.payment.AbstractPaymentFactory;
import ec.com.pattern.creational.abstractfactory.payment.concrete.PaymentMethod;
import ec.com.pattern.creational.abstractfactory.payment.concrete.impl.Credit;
import ec.com.pattern.creational.abstractfactory.payment.concrete.impl.Debit;   

public class PaymentMethodFactory implements AbstractPaymentFactory<PaymentMethod> {

    @Override
    public PaymentMethod create(String type) {
        if (type.equals("DEBIT")) {
            return new Debit();
        } else if (type.equals("CREDIT")) {
            return new Credit();
        }
        return null;
    }
    
}
