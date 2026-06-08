package university.productionpraktik.weather_service.dto;


public class WeatherInCityDTO {
    private String city;
    private String country;
    private double temperature;

    public WeatherInCityDTO(){}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
