package com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.input.port;

import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.order.Order;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface PreparingCoffee {

  Mono<Order> startPreparingOrder(UUID orderId);

  Mono<Order> finishPreparingOrder(UUID orderId);
}
