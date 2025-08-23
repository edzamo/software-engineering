package ec.software.engineer.architecture.hexagonal.example.webadapter.external.weather;


import ec.software.engineer.architecture.hexagonal.example.webadapter.domain.application.interfaces.weather.WeatherAdapter;
import ec.software.engineer.architecture.hexagonal.example.webadapter.domain.model.Weather;

public class OpenWeatherMapAdapter implements WeatherAdapter {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5";
    private static final String API_KEY = "YOUR_API_KEY";

    @Override
    public Weather getWeather(String location) {
        return null;
    }
    
}
