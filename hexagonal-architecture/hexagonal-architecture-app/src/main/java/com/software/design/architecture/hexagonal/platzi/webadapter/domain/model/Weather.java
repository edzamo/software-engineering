package com.software.design.architecture.hexagonal.platzi.webadapter.domain.model;


import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Weather {
    private double temperature;
    private double humidity;
    private double pressure;

    public Weather(double temperature, double humidity, double pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return String.format("Temperature: %.2f°C, Humidity: %.2f%%, Pressure: %.2fhPa", temperature, humidity, pressure);
    }
}

