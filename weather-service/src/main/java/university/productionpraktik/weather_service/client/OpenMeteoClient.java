package university.productionpraktik.weather_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import university.productionpraktik.weather_service.dto.GeocodingResponse;


@Component // хочу поменять|попробовать на использоание @Bean и @Configuration
public class OpenMeteoClient { // будет работать с open-meteo.com
    private final WebClient webclient; // при создании объекта MeteoClient
                                        // он имеет HTTP-клиента

    public OpenMeteoClient() {
        this.webclient = WebClient.create("https://geocoding-api.open-meteo.com");
    }

    // Задача: клиент должен выполнить запрос (город -> координаты)
    public GeocodingResponse CityCoordinate(String city) {
        // ниже представлен Method Chaining:
        // каждый метод возвращает объект, который работает со следующим методом
        return webclient.get() // отправка GET запроса
                // формирование адреса запроса
                .uri("/v1/search?name=" + city)
                // пример: http://geocoding-api.open-meteo.com/v1/search?name=Moscow
                .retrieve() // выполнение запроса и получение ответа
                .bodyToMono(GeocodingResponse.class) // формирование содержимого ответа (body) на запрос
                                                     // в виде объекта GeocodingResponse
                                                    // Mono - ?
                .block();  // связь Mono и block() - ?
    }


}
