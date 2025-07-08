package com.software.design.architecture.hexagonal.platzi.webadapter.external.rest.controllers;


import com.software.design.architecture.hexagonal.platzi.webadapter.domain.application.FlightService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequiredArgsConstructor
public class HelloController {

  private final FlightService service;


  @GetMapping("/")
  public String index() {
    this.service.checkSetup();
    return "Greetings from Spring Boot!";
  }

}
