package university.productionpraktik.weather_service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class MeteoClient { // будет работать с open-meteo.com
    private final WebClient webclient;

    public MeteoClient() {
        this.webclient = WebClient.create("https://geocoding-api.open-meteo.com");
    }
}
