package com.tinampiniari.thesisproject.integration.airvisual;

import com.tinampiniari.thesisproject.ThesisProjectProperties;
import com.tinampiniari.thesisproject.data.model.LargestCities;
import com.tinampiniari.thesisproject.data.repository.LargestCitiesRepository;
import com.tinampiniari.thesisproject.integration.ows.ForecastService;
import com.tinampiniari.thesisproject.integration.ows.OWMForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class AirVisualService {

    private final static String AIR_VISUAL_SERVICE = "https://api.airvisual.com/v2/nearest_city?lat={lat}&lon={lon}1&key={airvisualKey}";

    private final String airvisualKey;

    private final ForecastService forecastService;

    @Autowired
    LargestCitiesRepository largestCitiesRepo;

    public AirVisualService(ForecastService forecastService, ThesisProjectProperties properties) {
        this.airvisualKey = properties.getApi().getAirVisualKey();
        this.forecastService = forecastService;
    }

    public AirVisualApi getAirVisualApi(Double lat, Double lon) {
        URI url = new UriTemplate(AIR_VISUAL_SERVICE).expand(lat, lon, this.airvisualKey);
        return forecastService.invoke(url, AirVisualApi.class);
    }

    public ArrayList<Object> getLargestCitiesAirQ(){
        List<LargestCities> cities = largestCitiesRepo.findAll();
        ArrayList<Object> airQuality = new ArrayList<>();
        for (LargestCities largestCities : cities) {
            airQuality.add(getAirVisualApi(largestCities.getLat(), largestCities.getLon()));
        }
        for (int i = 0; i < airQuality.size() ; i++) {
            if(((AirVisualApi) airQuality.get(i)).getCity().equals("Gazi")){
                ((AirVisualApi) airQuality.get(i)).setCity("Heraklion");
            }
        }
        return airQuality;
    }

    
   

}
