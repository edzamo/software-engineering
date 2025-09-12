package ec.com.pattern.creational.abstractfactory.payment.concreteFactory;

import ec.com.pattern.creational.abstractfactory.payment.AbstracFactory;
import ec.com.pattern.creational.abstractfactory.payment.concrete.PaymentMethod;
import ec.com.pattern.creational.abstractfactory.payment.concrete.impl.Credit;
import ec.com.pattern.creational.abstractfactory.payment.concrete.impl.Debit;   

public class PaymentMethodFactory implements AbstracFactory<PaymentMethod> {

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
