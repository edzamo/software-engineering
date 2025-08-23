package ec.software.engineer.designpattern.behavioral.strategy.example.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Dummy credit card class.
 */
@AllArgsConstructor
@Setter
@Getter
public class CreditCard {
  private int amount;
  private String number;
  private String date;
  private String cvv;
}
