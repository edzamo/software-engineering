package ec.com.pattern.creational.factorymethod.payment;

public class PaypalPayment implements Payment {
    public void doPayment( ) {
        System.out.println("Making payment with PayPal");
    }
    
}
