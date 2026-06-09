package university.productionpraktik.weather_service.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import university.productionpraktik.weather_service.dto.ForecastResponse;
import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import java.time.Duration;

@Component
public class ForecastClient {
    private final WebClient webclient;

    // @Qualifier для возмоности использовать несколько WebClient-ов
    public ForecastClient(@Qualifier("forecastWebClient") WebClient webclient) {
        this.webclient = webclient;
    }

    public ForecastResponse getResponse(double latitude, double longitude){
        return webclient.get()
                .uri("/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current=temperature_2m")
                .retrieve()
                .bodyToMono(ForecastResponse.class)
                .block();
    }
}
