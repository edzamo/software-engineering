package com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.paymet;

import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.input.port.OrderingCoffee;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.output.port.Orders;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.output.port.Payments;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.payment.CreditCard;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.order.Order;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.payment.Payment;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.payment.Receipt;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoffeeShop implements OrderingCoffee {
  private final Orders orders;
  private final Payments payments;

  @Override
  public Mono<Order> placeOrder(Order order) {
    return null;
  }

  @Override
  public Mono<Order> updateOrder(UUID orderId, Order order) {
    return null;
  }

  @Override
  public Mono<Void> cancelOrder(UUID orderId) {
    return null;
  }

  @Override
  public Mono<Payment> payOrder(UUID orderId, CreditCard creditCard) {
    return null;
  }

  @Override
  public Mono<Receipt> readReceipt(UUID orderId) {
    return null;
  }

  @Override
  public Mono<Order> takeOrder(UUID orderId) {
    return null;
  }
}
