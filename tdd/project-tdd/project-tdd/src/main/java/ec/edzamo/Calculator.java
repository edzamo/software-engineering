package ec.edzamo;

import java.math.BigDecimal;

public class Calculator {

  public int addingTwoNumbers(int a, int b) {

    return a + b;
  }

  public int subtract(int first, int second) {
    return first - second;
  }

  public BigDecimal multiply(BigDecimal a, BigDecimal b) {
    return a.multiply(b);
  }

  public String divide(String a, String b) {
    BigDecimal decimalA = new BigDecimal(a);
    BigDecimal decimalB = new BigDecimal(b);
    if (decimalA.scale() > 2 || decimalB.scale() > 2) {
      System.out.printf("here");
      throw new IllegalArgumentException("Too many decimal places.");
    }

    return decimalA.divide(decimalB).toString();
  }
}
