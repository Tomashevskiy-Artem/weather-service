package university.productionpraktik.weather_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import university.productionpraktik.weather_service.dto.*;
import university.productionpraktik.weather_service.service.WeatherService;

import java.util.List;

@RestController // создаём класс, обрабатывающий HTTP-запрос
@RequestMapping("/api/v1/weather") // надбавка к обрабатываемому запросу (задаём общий URL-префикс)
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
/*
    @GetMapping("/city") // при GET-запросе на /city вызвать метод ниже
    public CityDTO CityCoordinate(@RequestParam String name) { // аннотация @RequestParam берёт name из URL
        return weatherService.CityCoordinate(name);
    }
*/
    @GetMapping("/current") // при GET-запросе на /current вызвать метод ниже
    public WeatherInCityDTO getWeather(@RequestParam String city) {
        return weatherService.getWeather(city);
    }

    @GetMapping("/history") // при GET-запросе на /history вызвать метод ниже
    public List<WeatherHistoryDTO> getHistory(@RequestParam String city) {
        return weatherService.getHistory(city);
    }
}
