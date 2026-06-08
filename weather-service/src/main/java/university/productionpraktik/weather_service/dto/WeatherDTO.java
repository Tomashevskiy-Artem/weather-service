package university.productionpraktik.weather_service.dto;

// DTO для определения температуры по координатам
public class WeatherDTO {
    private double temperature_2m;

    public WeatherDTO () {}

    public double getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(double temperature_2m) {
        this.temperature_2m = temperature_2m;
    }
}
