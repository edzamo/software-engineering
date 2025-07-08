package com.software.design.architecture.hexagonal.platzi.webadapter.external.weather;


import com.software.design.architecture.hexagonal.platzi.webadapter.domain.application.interfaces.weather.WeatherAdapter;
import com.software.design.architecture.hexagonal.platzi.webadapter.domain.model.Weather;

public class OpenWeatherMapAdapter implements WeatherAdapter {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5";
    private static final String API_KEY = "YOUR_API_KEY";

    @Override
    public Weather getWeather(String location) {
        return null;
    }
    
}
