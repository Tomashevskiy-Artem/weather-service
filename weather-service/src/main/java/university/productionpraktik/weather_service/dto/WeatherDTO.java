package university.productionpraktik.weather_service.dto;

// DTO для определения температуры по координатам
public class WeatherDTO {
    private String time;
    private double temperature_2m;
    private double wind_speed_10m;

    public WeatherDTO () {}

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public double getTemperature_2m() {
        return temperature_2m;
    }
    public void setTemperature_2m(double temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public double getWind_speed_10m() {
        return wind_speed_10m;
    }
    public void setWind_speed_10m(double wind_speed_10m) {
        this.wind_speed_10m = wind_speed_10m;
    }
}
