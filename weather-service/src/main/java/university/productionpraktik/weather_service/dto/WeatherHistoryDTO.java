package university.productionpraktik.weather_service.dto;

import java.time.OffsetDateTime;

public class WeatherHistoryDTO {
    private String city;
    private double temperature;
    private double windSpeed;
    private OffsetDateTime observedAt;

    public WeatherHistoryDTO() {}

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public OffsetDateTime getObservedAt() {
        return observedAt;
    }
    public void setObservedAt(OffsetDateTime observedAt) {
        this.observedAt = observedAt;
    }
}
