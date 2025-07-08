package com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.paymet;

import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.input.port.PreparingCoffee;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.order.Order;
import java.util.UUID;
import reactor.core.publisher.Mono;

public class CoffeeMachine implements PreparingCoffee {

  @Override
  public Mono<Order> startPreparingOrder(UUID orderId) {
    return null;
  }

  @Override
  public Mono<Order> finishPreparingOrder(UUID orderId) {
    return null;
  }
}
