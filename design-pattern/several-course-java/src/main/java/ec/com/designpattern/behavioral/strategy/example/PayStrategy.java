package ec.com.designpattern.behavioral.strategy.example;

public interface PayStrategy {

  boolean pay(int paymentAmount);

  void collectPaymentDetails();
}
