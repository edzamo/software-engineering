package ec.edzamo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FizzBuzzTest {

  private FizzBuzz fizzBuzz;

  @BeforeEach
  void setUp() {
    fizzBuzz = new FizzBuzz();
  }


  @Test
  void shouldReturnFizzWhenDivisibleBy3() {
    assertEquals("Fizz", fizzBuzz.play(3));
    assertEquals("Fizz", fizzBuzz.play(6));
  }

  @Test
  void shouldReturnBuzzWhenDivisibleBy5() {
    assertEquals("Buzz", fizzBuzz.play(5));
    assertEquals("Buzz", fizzBuzz.play(10));
  }

  @Test
  void shouldReturnFizzBuzzWhenDivisibleBy3And5() {
    assertEquals("FizzBuzz", fizzBuzz.play(15));
    assertEquals("FizzBuzz", fizzBuzz.play(30));
  }

  @Test
  void shouldReturnNumberWhenNotDivisibleBy3Or5() {
    assertEquals("1", fizzBuzz.play(1));
    assertEquals("2", fizzBuzz.play(2));
    assertEquals("4", fizzBuzz.play(4));
  }
}
