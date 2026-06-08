package university.productionpraktik.weather_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import university.productionpraktik.weather_service.dto.CityDTO;
import university.productionpraktik.weather_service.dto.ForecastResponse;
import university.productionpraktik.weather_service.dto.GeocodingResponse;
import university.productionpraktik.weather_service.dto.WeatherInCityDTO;
import university.productionpraktik.weather_service.service.WeatherService;

@RestController // создаём класс, обрабатывающий HTTP-запрос
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/city") // при GET-запросе на /city вызвать метод ниже
    public CityDTO CityCoordinate(@RequestParam String name) { // аннотация @RequestParam берёт name из URL
        return weatherService.CityCoordinate(name);
    }

    @GetMapping("/weather")
    public WeatherInCityDTO getWeather(@RequestParam String city) {
        return weatherService.getWeather(city);
    }
}
