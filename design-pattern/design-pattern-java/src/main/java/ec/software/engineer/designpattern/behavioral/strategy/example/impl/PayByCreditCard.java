package ec.software.engineer.designpattern.behavioral.strategy.example.impl;


import ec.software.engineer.designpattern.behavioral.strategy.example.PayStrategy;
import ec.software.engineer.designpattern.behavioral.strategy.example.dto.CreditCard;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Concrete strategy. Implements credit card payment method.
 */
public class PayByCreditCard implements PayStrategy {

  private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
  private CreditCard card;


  /**
   * Collect credit card data.
   */
  @Override
  public boolean pay(int paymentAmount) {
    if (cardIsPresent()) {
      System.out.println("Paying " + paymentAmount + " using Credit Card.");
      card.setAmount(card.getAmount() - paymentAmount);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void collectPaymentDetails() {
    try {
      System.out.print("Enter the card number: ");
      String number = READER.readLine();
      System.out.print("Enter the card expiration date 'mm/yy': ");
      String date = READER.readLine();
      System.out.print("Enter the CVV code: ");
      String cvv = READER.readLine();
      card = new CreditCard(100000,number, date, cvv);

      // Validate credit card number...

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }


  private boolean cardIsPresent() {
    return card != null;
  }
}
