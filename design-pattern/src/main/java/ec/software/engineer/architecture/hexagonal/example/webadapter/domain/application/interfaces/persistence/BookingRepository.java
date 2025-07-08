package ec.software.engineer.architecture.hexagonal.example.webadapter.domain.application.interfaces.persistence;


import ec.software.engineer.architecture.hexagonal.example.webadapter.domain.model.Booking;

public interface BookingRepository {
    void save(Booking booking);
}
