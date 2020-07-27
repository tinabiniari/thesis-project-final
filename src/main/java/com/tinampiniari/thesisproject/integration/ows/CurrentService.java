package com.tinampiniari.thesisproject.integration.ows;

import com.tinampiniari.thesisproject.ThesisProjectProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CurrentService {

    private static final String CURRENT_CITY_URL =
            "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&units=metric&APPID={key}";


    private static final String CURRENT_COORD_URL = "http://api.openweathermap.org/data/2.5/weather?APPID=0d2e32378ac4069192f2bf25dd59577f&units=metric&lon={lon}&lat={lat}";

    private static final Logger logger = LoggerFactory.getLogger(CurrentService.class);

    private final RestTemplate restTemplate;

    private final String apiKey;

    public CurrentService(RestTemplateBuilder restTemplateBuilder,
                          ThesisProjectProperties properties) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = properties.getApi().getKey();
    }

    public Weather getWeather(String city, String country) {
        URI url = new UriTemplate(CURRENT_CITY_URL).expand(city, country, this.apiKey);
        return invoke(url, Weather.class);
    }

    public OWMApi getCurrentUrlByCity(String city,String country){
        URI url = new UriTemplate(CURRENT_CITY_URL).expand(city, country, this.apiKey);
        return invoke(url, OWMApi.class);
    }

    public OWMApi getCurrentUrlByCoord(Double lon,Double lat){
        URI url = new UriTemplate(CURRENT_COORD_URL).expand(lon, lat, this.apiKey);
        return invoke(url, OWMApi.class);
    }



    public ArrayList getCurrentByCity(String city,String country) {
        ArrayList<OWMApi> citySummary = new ArrayList<>();
        OWMApi owmApi=this.getCurrentUrlByCity(city, country);
        citySummary.add(owmApi);
        return citySummary;
    }

    public ArrayList getCurrentByCoord(Double lon, Double lat) {
        ArrayList<OWMApi> citySummary = new ArrayList<>();
        OWMApi owmApi=this.getCurrentUrlByCoord(lon, lat);
        citySummary.add(owmApi);
        return citySummary;
    }

    public Object getTime(){
        LocalDateTime dt = LocalDateTime.now();
        LocalTime minutes = dt.toLocalTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formatDateTime = minutes.format(formatter);
        String currentDayTime= dt.getDayOfWeek() + " " + formatDateTime;
        return currentDayTime;
    }

    public String getIconLink(String iconId){
        String urlIcon = "http://openweathermap.org/img/wn/"+iconId+"@2x.png";
        return urlIcon;
    }



    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> exchange = this.restTemplate
                .exchange(request, responseType);
        return exchange.getBody();
    }

}
