package com.software.design.architecture.hexagonal.platzi.webadapter.domain.application.interfaces.persistence;


import com.software.design.architecture.hexagonal.platzi.webadapter.domain.model.Flight;
import com.software.design.architecture.hexagonal.platzi.webadapter.domain.model.Route;
import java.time.LocalDate;
import java.util.List;

public interface FlightRepository {

    List<Flight> search(Route route, LocalDate departureDate);

    void save(Flight flight);

}
