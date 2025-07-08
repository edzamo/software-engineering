package com.software.design.architecture.hexagonal.platzi.transactionscript.domain.application.interfaces;


import com.software.design.architecture.hexagonal.platzi.transactionscript.domain.model.Flight;
import java.time.LocalDate;
import java.util.List;

public interface FlightRepository {

  List<Flight> search(String origin, String destination, LocalDate departureDate);

  void save(Flight flight);

}
