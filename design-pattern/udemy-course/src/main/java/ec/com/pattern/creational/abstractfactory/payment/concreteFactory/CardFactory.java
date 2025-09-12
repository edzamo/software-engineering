package ec.com.pattern.creational.abstractfactory.payment.concreteFactory;

import ec.com.pattern.creational.abstractfactory.payment.AbstracFactory;
import ec.com.pattern.creational.abstractfactory.payment.concrete.Card;
import ec.com.pattern.creational.abstractfactory.payment.concrete.impl.MasterCard;
import ec.com.pattern.creational.abstractfactory.payment.concrete.impl.Visa;      



public class CardFactory implements AbstracFactory<Card> {

    @Override
    public Card create(String type) {
        if (type.equals("VISA")) {
            return new Visa();
        } else if (type.equals("MASTERCARD")) {
            return new MasterCard();
        }
        return null;

    }
}
