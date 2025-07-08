package com.software.design.architecture.hexagonal.platzi.domainmodel.domain.aplication.interfaces;


import com.software.design.architecture.hexagonal.platzi.domainmodel.domain.model.Booking;

public interface BookingRepository {
  void save(Booking booking);
}
