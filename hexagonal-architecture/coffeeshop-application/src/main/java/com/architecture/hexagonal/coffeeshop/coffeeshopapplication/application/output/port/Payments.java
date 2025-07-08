package com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.output.port;

import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.payment.Payment;
import java.util.UUID;

public interface Payments {

  Payment findPaymentByOrderId(UUID orderId);
  Payment save(Payment payment);
}
