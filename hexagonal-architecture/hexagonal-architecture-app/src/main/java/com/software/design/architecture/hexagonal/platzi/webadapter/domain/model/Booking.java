package com.software.design.architecture.hexagonal.platzi.webadapter.domain.model;


import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Booking {

    private String id;
    private User user;
    private Flight flight;
    private int passengers;

    public Booking(String id, User user, Flight flight, int passengers) {
        this.id = id;
        this.user = user;
        this.flight = flight;
        this.passengers = passengers;
    }

}
