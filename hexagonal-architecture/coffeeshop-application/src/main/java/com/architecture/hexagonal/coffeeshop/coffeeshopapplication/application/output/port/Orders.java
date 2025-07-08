package com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.output.port;

import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.order.Order;
import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.exception.OrderNotFound;
import java.util.UUID;

public interface Orders {
  Order findOrderById(UUID orderId) throws OrderNotFound;
  Order save(Order order);
  void deleteById(UUID orderId);

}
