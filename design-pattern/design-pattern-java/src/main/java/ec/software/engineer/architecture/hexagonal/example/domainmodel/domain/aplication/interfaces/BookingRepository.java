package ec.software.engineer.architecture.hexagonal.example.domainmodel.domain.aplication.interfaces;


import ec.software.engineer.architecture.hexagonal.example.domainmodel.domain.model.Booking;

public interface BookingRepository {
  void save(Booking booking);
}
