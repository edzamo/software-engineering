package com.architecture.hexagonal.coffeeshop.coffeeshopapplication.infrastructure.adapter.input.rest;

import com.architecture.hexagonal.coffeeshop.coffeeshopapplication.application.input.port.OrderingCoffee;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructoryrte021qw 2345
@Slf4j
public class OrderController {

  private final OrderingCoffee orderingCoffee;

  @PostMapping("/order")
  ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request,
      UriComponentsBuilder uriComponentsBuilder) {
    var order = orderingCoffee.placeOrder(request.toDomain());
    var location = uriComponentsBuilder.path("/order/{id}")
        .buildAndExpand(order.getId())
        .toUri();
    return ResponseEntity.created(location).body(OrderResponse.fromDomain(order));
  }

  @PostMapping("/order/{id}")
 Mono< ResponseEntity<OrderResponse>> updateOrder(@PathVariable UUID id,
      @RequestBody OrderRequest request) {
    var order = orderingCoffee.updateOrder(id, request.toDomain());
    return ResponseEntity.ok(OrderResponse.fromDomain(order));
  }

  @DeleteMapping("/order/{id}")
  Mono<ResponseEntity<Void>> cancelOrder(@PathVariable UUID id) {
    return this.orderingCoffee.cancelOrder(id)
        .log()
        .map(ResponseEntity::ok)
        .doOnSuccess(response -> log.info("Response with:{}",response))
        .doOnError(error -> log.error("Problem in: {} ", error.getMessage()));

  }

}
