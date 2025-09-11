package ec.com.pattern.creational.factorymethod.payment;

public class CardPayment implements Payment {
    public void doPayment() {
        System.out.println("Making payment with Card");
    }

}
