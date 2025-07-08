package com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.order;

import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.input.port.PreparingCoffee;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.output.port.Orders;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.order.Order;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CoffeeMachine implements PreparingCoffee {

  private final Orders orders;


  @Override
  public Mono<Order> startPreparingOrder(UUID orderId) {
    return null;
  }

  @Override
  public Mono<Order> finishPreparingOrder(UUID orderId) {
    return null;
  }
}
