package ec.software.engineer.architecture.hexagonal.example.domainmodel.domain.aplication.interfaces;


import ec.software.engineer.architecture.hexagonal.example.domainmodel.domain.model.Flight;
import ec.software.engineer.architecture.hexagonal.example.domainmodel.domain.model.Route;
import java.time.LocalDate;
import java.util.List;

public interface FlightRepository {
  List<Flight> search(Route route, LocalDate departureDate);

  void save(Flight flight);
}
