package university.productionpraktik.weather_service.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity // данная аннотация говорит, что этот класс должен храниться в бд
@Table(name = "weather_snapshot") // благодаря данной аннотации даётся уточнение,
                                  // в какой таблице должна храниться информация

public class WeatherSnapshot {
    @Id // определение первичного ключа таблицы
    private UUID id;
    @Column(name = "city")
    private String city;
    @Column(name = "provider")
    private String provider;
    @Column(name = "temperature_celsius")
    private double temperatureCelsius;
    @Column(name = "wind_speed")
    private double windSpeed;
    @JdbcTypeCode(SqlTypes.JSON) // перевод string в json
    @Column(name = "raw_payload")
    private JsonNode rawPayload;
    @Column(name = "observed_at")
    private OffsetDateTime observedAt;
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    public WeatherSnapshot() {}

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getProvider(){
        return provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public double getTemperatureCelsius(){
        return temperatureCelsius;
    }
    public void setTemperatureCelsius(double temperatureCelsius){
        this.temperatureCelsius = temperatureCelsius;
    }

    public double getWindSpeed(){
        return windSpeed;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public JsonNode getRawPayload(){
        return rawPayload;
    }
    public void setRawPayload(JsonNode rawPayload) {
        this.rawPayload = rawPayload;
    }

    public OffsetDateTime getObservedAt() {
        return observedAt;
    }
    public void setObservedAt(OffsetDateTime observedAt) {
        this.observedAt = observedAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
