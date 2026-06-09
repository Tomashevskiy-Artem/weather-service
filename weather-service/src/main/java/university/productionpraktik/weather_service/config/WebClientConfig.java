package university.productionpraktik.weather_service.config;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient forecastWebClient() {
        HttpClient httpClient = HttpClient.create() // создаём http-клиента
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000) // лимит на установление соединения
                .responseTimeout(Duration.ofSeconds(5)); // лимит на ожидание ответа
        return WebClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public WebClient openmeteoWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .responseTimeout(Duration.ofSeconds(5));

        return WebClient.builder()
                .baseUrl("https://geocoding-api.open-meteo.com")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
