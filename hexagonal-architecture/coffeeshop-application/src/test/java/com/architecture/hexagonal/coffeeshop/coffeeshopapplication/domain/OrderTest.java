package com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.enums.Drink;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.enums.Location;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.enums.Milk;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.enums.Size;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.order.LineItem;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.order.Order;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderTest {

  private static Stream<Arguments> drinkCosts() {
    return Stream.of(
        arguments(1, Size.SMALL, BigDecimal.valueOf(4.0)),
        arguments(1, Size.LARGE, BigDecimal.valueOf(5.0)),
        arguments(2, Size.SMALL, BigDecimal.valueOf(8.0))
    );
  }

  @ParameterizedTest(name = "{0} drinks of size {1} cost {2}")
  @MethodSource("drinkCosts")
  void orderCostIsBasedOnQuantityAndSize(
      int quantity, Size size, BigDecimal expectedCost) {

    var order = new Order(Location.TAKE_AWAY, List.of(
        new LineItem(Drink.LATTE, Milk.WHOLE, Size.LARGE, quantity)
    ));

    assertThat(order.getCost()).isEqualTo(expectedCost);


  }

  @Test
  void orderCostIsSumOfLineItemCosts() {
    var order = new Order(Location.TAKE_AWAY, List.of(
        new LineItem(
            Drink.LATTE,
            Milk.SKIMMED,
            Size.LARGE, 10),
        new LineItem(Drink.ESPRESSO, Milk.SOY, Size.SMALL, 2)
    ));
    assertThat(order.getCost()).isEqualTo(BigDecimal.valueOf(9.0));
  }
}