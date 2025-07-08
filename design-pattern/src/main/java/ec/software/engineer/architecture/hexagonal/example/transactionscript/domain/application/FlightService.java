package ec.software.engineer.architecture.hexagonal.example.transactionscript.domain.application;

import ec.software.engineer.architecture.hexagonal.example.transactionscript.domain.model.Flight;
import ec.software.engineer.architecture.hexagonal.example.transactionscript.domain.application.interfaces.FlightRepository;
import java.time.LocalDate;
import java.util.List;


public class FlightService {


    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> search(String origin, String destination, LocalDate departureDate, int passengers) {

        // Validate search criteria
        if (origin == null || destination == null || departureDate == null) {
            throw new IllegalArgumentException("Search criteria are invalid.");
        }

        // Perform search based on the given parameters
        List<Flight> flights = flightRepository.search(origin, destination, departureDate);


        for (Flight flight : flights) {
            // Check if flight is full
            if (flight.getAvailableSeats() < passengers) {
                flight.setAvailable(false);
            }
        }

        return flights;
    }
    
}
