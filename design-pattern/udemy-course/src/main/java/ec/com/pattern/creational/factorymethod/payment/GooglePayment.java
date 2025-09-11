package ec.com.pattern.creational.factorymethod.payment;

public class GooglePayment implements Payment {
    public void doPayment( ) {
        System.out.println("Making payment with Google Pay");
    }
}
