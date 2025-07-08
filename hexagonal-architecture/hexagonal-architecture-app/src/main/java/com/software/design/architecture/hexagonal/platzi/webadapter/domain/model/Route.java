package com.software.design.architecture.hexagonal.platzi.webadapter.domain.model;

import lombok.Data;


@Data
public class Route {

    private final String origin;
    private final String destination;

    public Route(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public boolean isValid(){
        if(this.origin == null || this.origin.isBlank()){
            return false;
        }

        if(this.destination == null || this.destination.isBlank()){
            return false;
        }

        return !this.destination.equalsIgnoreCase(this.origin);
    }

}
