package ec.com.pattern.creational.abstractfactory.payment.concrete.impl;

import ec.com.pattern.creational.abstractfactory.payment.concrete.Card;

public class MasterCard implements Card {
  

    @Override
    public String getCardNumber() {
       return "5454545454545454";
    }

    @Override
    public String getCardType() {
        return "MASTERCARD";
    }

}
