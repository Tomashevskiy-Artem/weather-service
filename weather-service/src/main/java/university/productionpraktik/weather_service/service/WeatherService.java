package university.productionpraktik.weather_service.service;

import university.productionpraktik.weather_service.client.OpenMeteoClient;
import university.productionpraktik.weather_service.client.ForecastClient;
import org.springframework.stereotype.Service;
import university.productionpraktik.weather_service.dto.ForecastResponse;
import university.productionpraktik.weather_service.dto.GeocodingResponse;
import university.productionpraktik.weather_service.dto.CityDTO;
import university.productionpraktik.weather_service.dto.WeatherInCityDTO;

import java.util.List;

@Service
public class WeatherService {
    private final OpenMeteoClient openmeteoClient;
    private final ForecastClient forecastClient;

    //  пример применения Dependency Injection
    //  (WeatherService зависит от OpenMeteoClient)
    public WeatherService(OpenMeteoClient openmeteoClient, ForecastClient forecastClient) {
        this.openmeteoClient = openmeteoClient;
        this.forecastClient = forecastClient;
    }

    // Создадим метод, который пользуется запросом на координаты города
    // из нашего OpenMeteoClient
    public CityDTO CityCoordinate (String city) {
        GeocodingResponse response = openmeteoClient.CityCoordinate(city);

        if (response.getResults() == null || response.getResults().isEmpty()) {
            return null;
        } // обработка случаев, когда:
            // 1. когда на запрос open-meteo возвращает null
            // 2. когда на запрос возвращается {results:[]}

        return response.getResults().get(0);
    }
/*
    public ForecastResponse getWeather(String city) {
        // получение данных по городу от геокодинга
        GeocodingResponse response = openmeteoClient.CityCoordinate(city);

        // проверка на ошибки
        if (response.getResults() == null || response.getResults().isEmpty()) {
            return null;
        }

        // берём только первый найденный город
        CityDTO cityDTO = response.getResults().get(0);

        // делаем запрос на определение температуры по данным координатам
        return forecastClient.getResponse(cityDTO.getLatitude(), cityDTO.getLongitude());
    }
*/
    public WeatherInCityDTO getWeather(String city) {
        // получение данных по городу от геокодинга
        GeocodingResponse response = openmeteoClient.CityCoordinate(city);

        // проверка на ошибки
        if (response.getResults() == null || response.getResults().isEmpty()) {
            return null;
        }

        // берём только первый найденный город
        CityDTO cityDTO = response.getResults().get(0);

        ForecastResponse forecastResponse = forecastClient.getResponse(cityDTO.getLatitude(), cityDTO.getLongitude());

        WeatherInCityDTO weatherInCityDTO = new WeatherInCityDTO();
        weatherInCityDTO.setCity(cityDTO.getName());
        weatherInCityDTO.setCountry(cityDTO.getCountry());
        weatherInCityDTO.setTemperature(forecastResponse.getCurrent().getTemperature_2m());

        return weatherInCityDTO;
    }

}
