package com.software.design.architecture.hexagonal.platzi.webadapter.domain.application;


import com.software.design.architecture.hexagonal.platzi.webadapter.domain.application.interfaces.persistence.BookingRepository;
import com.software.design.architecture.hexagonal.platzi.webadapter.domain.application.interfaces.persistence.FlightRepository;
import com.software.design.architecture.hexagonal.platzi.webadapter.domain.model.Booking;
import com.software.design.architecture.hexagonal.platzi.webadapter.domain.model.Flight;
import com.software.design.architecture.hexagonal.platzi.webadapter.domain.model.Route;
import com.software.design.architecture.hexagonal.platzi.webadapter.domain.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;


    public List<Flight> search(Route route, LocalDate departureDate, int passengers) {

        // Validate search criteria
        if (!route.isValid() || departureDate == null) {
            throw new IllegalArgumentException("Search criteria are invalid.");
        }

        // Perform search based on the given parameters
        List<Flight> flights = flightRepository.search(route, departureDate);

        // Filter out flights that are full
        flights = flights.stream()
                .filter(flight -> flight.isAvailable(passengers))
                .collect(Collectors.toList());

        return flights;
    }

    public Booking bookFlight(User user, Flight flight, int passengers) {
        if (!flight.isAvailable(passengers)) {
            throw new IllegalArgumentException("The selected flight is not available for the given number of passengers.");
        }

        String bookingId = UUID.randomUUID().toString();
        Booking booking = new Booking(bookingId, user, flight, passengers);
        flight.addBooking(booking);

        bookingRepository.save(booking);

        return booking;
    }

    public void checkSetup() {
        System.out.println(this.bookingRepository.getClass().getSimpleName());
        System.out.println(this.flightRepository.getClass().getSimpleName());
    }
}
