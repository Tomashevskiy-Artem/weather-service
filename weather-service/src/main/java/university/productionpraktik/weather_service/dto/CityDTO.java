package university.productionpraktik.weather_service.dto;

public class CityDTO {
    private String name;
    private double latitude;
    private double longitude;
    private String country;

    public CityDTO () {} // заполнение объекта будет происходит через сеттеры
                         // по мере прохождения по JSON

    // Геттеры
    public String getName() {
        return name;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getCountry() {
        return country;
    }

    // Сеттеры
    public void setName(String name) {
        this.name = name;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setCountry(String country) {
        this.country = country;
    }


}
