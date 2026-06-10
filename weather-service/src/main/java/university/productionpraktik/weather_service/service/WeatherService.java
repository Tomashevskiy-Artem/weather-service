package university.productionpraktik.weather_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import university.productionpraktik.weather_service.client.OpenMeteoClient;
import university.productionpraktik.weather_service.client.ForecastClient;
import org.springframework.stereotype.Service;
import university.productionpraktik.weather_service.dto.*;
import university.productionpraktik.weather_service.entity.WeatherSnapshot;
import university.productionpraktik.weather_service.repository.WeatherSnapshotRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WeatherService {
    private final OpenMeteoClient openmeteoClient;
    private final ForecastClient forecastClient;
    private final WeatherSnapshotRepository repository;
    private final ObjectMapper objectMapper;

    //  пример применения Dependency Injection
    //  (WeatherService зависит от OpenMeteoClient)
    public WeatherService(OpenMeteoClient openmeteoClient,
                          ForecastClient forecastClient,
                          WeatherSnapshotRepository repository,
                          ObjectMapper objectMapper) {
        this.openmeteoClient = openmeteoClient;
        this.forecastClient = forecastClient;
        this.repository = repository;
        this.objectMapper = objectMapper;
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
        // провекрка на неправильный ввод значения после city= в запросе
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City can't be empty!!!");
        }

        // берём только первый найденный город
        CityDTO cityDTO = response.getResults().get(0);

        ForecastResponse forecastResponse = forecastClient.getResponse(cityDTO.getLatitude(), cityDTO.getLongitude());

        WeatherSnapshot snapshot = new WeatherSnapshot();

        snapshot.setId(UUID.randomUUID());
        snapshot.setCity(city);
        snapshot.setProvider("open-meteo");
        snapshot.setTemperatureCelsius(forecastResponse.getCurrent().getTemperature_2m());
        snapshot.setWindSpeed(forecastResponse.getCurrent().getWind_speed_10m());
        snapshot.setRawPayload(objectMapper.valueToTree(forecastResponse)); // переводим java-объект в json-дерево
        snapshot.setObservedAt(OffsetDateTime.now());
        snapshot.setCreatedAt(OffsetDateTime.now());

        repository.save(snapshot);

        WeatherInCityDTO weatherInCityDTO = new WeatherInCityDTO();
        weatherInCityDTO.setCity(cityDTO.getName());
        weatherInCityDTO.setCountry(cityDTO.getCountry());
        weatherInCityDTO.setTemperature(forecastResponse.getCurrent().getTemperature_2m());

        return weatherInCityDTO;
    }

    public List<WeatherHistoryDTO> getHistory(String city) {

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City can't be empty!!!");
        }

        List<WeatherSnapshot> snapshots =
                repository.findByCityOrderByCreatedAtDesc(city); // получаем данные из таблицы

        List<WeatherHistoryDTO> result = new ArrayList<>(); // создаём новый список

        for (WeatherSnapshot snapshot : snapshots) {

            WeatherHistoryDTO dto = new WeatherHistoryDTO();

            dto.setCity(snapshot.getCity());
            dto.setTemperature(snapshot.getTemperatureCelsius());
            dto.setWindSpeed(snapshot.getWindSpeed());
            dto.setObservedAt(snapshot.getObservedAt());

            result.add(dto); // в созданном дто создаём объекты на основе данных
                             // из списка результатов по запросу таблице
        }

        return result;
    }

}
