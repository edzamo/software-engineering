package com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.input.port;

import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.payment.CreditCard;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.order.Order;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.payment.Payment;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.payment.Receipt;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface OrderingCoffee {

  Mono<Order> placeOrder(Order order);

  Mono<Order> updateOrder(UUID orderId, Order order);

  Mono<Void> cancelOrder(UUID orderId);

  Mono<Payment> payOrder(UUID orderId, CreditCard creditCard);

  Mono<Receipt> readReceipt(UUID orderId);

  Mono<Order> takeOrder(UUID orderId);

}
