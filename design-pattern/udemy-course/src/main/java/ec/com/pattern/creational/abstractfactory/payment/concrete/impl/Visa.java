package ec.com.pattern.creational.abstractfactory.payment.concrete.impl;

import ec.com.pattern.creational.abstractfactory.payment.concrete.Card;

public class Visa implements Card{

    @Override
    public String getCardNumber() {
      return "4111111111111111";
    }

    @Override
    public String getCardType() {
        return "VISA";
    }

}
