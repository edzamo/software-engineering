package ec.com.pattern.creational.abstractfactory.payment.concrete.impl;

import ec.com.pattern.creational.abstractfactory.payment.concrete.PaymentMethod;

public class Debit implements PaymentMethod {
    @Override
    public String doPayment() {
        return "Payment with DEBIT";
    }

}
