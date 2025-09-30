package ec.edzamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

class CalculatorTest {

  private Calculator calculator;

  @BeforeEach
  void initiation() {
    calculator = new Calculator();
  }

  @Test
  void shouldReturnAnInstanceOfCalculator() {
    assertNotNull(calculator);
  }

  @Test
  void shouldAcceptTwoNumberAndReturnAddBoth() {
    int add = calculator.addingTwoNumbers(2, 3);
    assertEquals(5, add);
  }


  @Test
  void shouldAcceptTwoNumberAndReturnSubtractBoth() {
    int resul = calculator.subtract(8, 6);
    assertEquals(2, resul);
  }

  @Test
  void shouldAcceptTwoNumberAndReturnMultiply() {
    BigDecimal multiply = calculator.multiply(BigDecimal.valueOf(2), BigDecimal.valueOf(2));
    assertEquals(BigDecimal.valueOf(4), multiply);
  }

  @Test
  void shouldAcceptTwoNumberAndReturnException() {
    BigDecimal multiply = calculator.multiply(BigDecimal.valueOf(2), BigDecimal.valueOf(2));
    // Verificamos que al comparar Integer con BigDecimal se lance AssertionFailedError
    assertThrows(AssertionFailedError.class, () -> {
      assertEquals(4, multiply); // aquí debe fallar
    });

  }


  @Test
  void shouldAcceptTwoNumberAndReturnDivideBoth() {
    String div = calculator.divide("4.5", "2.0");
    assertEquals("2.25", div);
  }

  @Test
  void shouldAcceptTwoNumberAndReturnDivideBothWithAnException() {
    String div = calculator.divide("40.5", "2.0");

    assertThrows(AssertionFailedError.class, () -> {
      assertEquals("2.25", div);
    });


  }

  @Test
  void shouldAcceptTwoNumberAndReturnDivideBothThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      calculator.divide("400.576", "2.89");
    });
    assertEquals("Too many decimal places.", exception.getMessage());

  }


}
