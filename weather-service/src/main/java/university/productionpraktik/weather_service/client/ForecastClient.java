package university.productionpraktik.weather_service.client;

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

    public ForecastClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000) // ожидание соединения 3 секунды
                .responseTimeout(Duration.ofSeconds(5)); // ожидание ответа в течение 5 секунд
        this.webclient = WebClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public ForecastResponse getResponse(double latitude, double longitude){
        return webclient.get()
                .uri("/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current=temperature_2m")
                .retrieve()
                .bodyToMono(ForecastResponse.class)
                .block();
    }
}
