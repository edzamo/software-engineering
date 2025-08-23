package ec.software.engineer.architecture.hexagonal.example.webadapter.domain.application.interfaces.weather;


import ec.software.engineer.architecture.hexagonal.example.webadapter.domain.model.Weather;

public interface WeatherAdapter {

    Weather getWeather(String location);

}