package ec.software.engineer.architecture.hexagonal.example.transactionscript.domain.application.interfaces;

import ec.software.engineer.architecture.hexagonal.example.transactionscript.domain.model.Flight;
import java.time.LocalDate;
import java.util.List;


public interface FlightRepository {

    List<Flight> search(String origin, String destination, LocalDate departureDate);

    void save(Flight flight);

}
