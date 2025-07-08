package com.software.design.architecture.hexagonal.platzi.webadapter.domain.application.interfaces.weather;


import com.software.design.architecture.hexagonal.platzi.webadapter.domain.model.Weather;

public interface WeatherAdapter {

    Weather getWeather(String location);

}