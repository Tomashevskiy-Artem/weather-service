package university.productionpraktik.weather_service.dto;

public class ForecastResponse {
    private WeatherDTO current;

    public ForecastResponse(){}

    public WeatherDTO getCurrent() {
        return current;
    }

    public void setCurrent(WeatherDTO current) {
        this.current = current;
    }

}
