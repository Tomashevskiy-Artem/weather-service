package university.productionpraktik.weather_service.dto;

import java.util.List; // добавляем возможность применения списков

public class GeocodingResponse {
    private List<CityDTO> results;

    public GeocodingResponse() {}

    public List<CityDTO> getResults() {
        return results;
    }

    public void setResults(List<CityDTO> results) {
        this.results = results;
    }
}
