package com.architecture.hexagonal.coffeeshop.coffeeshopapplication.domain.payment;

import java.time.LocalDate;
import java.util.UUID;

public record Payment(UUID orderId, CreditCard creditCard, LocalDate paid) {

}


