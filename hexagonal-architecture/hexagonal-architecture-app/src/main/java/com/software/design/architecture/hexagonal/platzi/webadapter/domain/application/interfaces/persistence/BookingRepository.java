package com.software.design.architecture.hexagonal.platzi.webadapter.domain.application.interfaces.persistence;


import com.software.design.architecture.hexagonal.platzi.webadapter.domain.model.Booking;

public interface BookingRepository {
    void save(Booking booking);
}
