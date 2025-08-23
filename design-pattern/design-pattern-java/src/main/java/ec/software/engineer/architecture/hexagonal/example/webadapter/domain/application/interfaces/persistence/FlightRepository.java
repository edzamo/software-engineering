package ec.software.engineer.architecture.hexagonal.example.webadapter.domain.application.interfaces.persistence;



import ec.software.engineer.architecture.hexagonal.example.webadapter.domain.model.Flight;
import ec.software.engineer.architecture.hexagonal.example.webadapter.domain.model.Route;
import java.time.LocalDate;
import java.util.List;

public interface FlightRepository {

    List<Flight> search(Route route, LocalDate departureDate);

    void save(Flight flight);

}
