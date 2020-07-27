package com.tinampiniari.thesisproject.integration.ows;

import com.tinampiniari.thesisproject.ThesisProjectProperties;
import com.tinampiniari.thesisproject.data.model.City;
import com.tinampiniari.thesisproject.integration.stormglass.History;
import com.tinampiniari.thesisproject.integration.stormglass.HistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.*;

@Service
public class ForecastService {

    private static final String FORECAST_CITY_URL = "https://api.openweathermap.org/data/2.5/forecast?q={city},{country}&&units=metric&APPID={key}";

    private static final String FORECAST_COORD_URL = "https://api.openweathermap.org/data/2.5/forecast?lon={lon}&lat={lat}&units=metric&appid={key}";

    private final RestTemplate restTemplate;

    private final String apiKey;

    @Autowired
    HistoryService historyService;

    public ForecastService(RestTemplateBuilder restTemplateBuilder,
                          ThesisProjectProperties properties) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = properties.getApi().getKey();
    }

    public OWMForecast getForecastUrlByCity(String city,String country){
        URI url = new UriTemplate(FORECAST_CITY_URL).expand(city, country, this.apiKey);
        return invoke(url, OWMForecast.class);
    }

    public OWMForecast getForecastUrlByCoord(Double lon,Double lat){
        URI url = new UriTemplate(FORECAST_COORD_URL).expand(lon, lat, this.apiKey);
        return invoke(url, OWMForecast.class);
    }


    public HashMap<String,ArrayList<ListInfo>> getForecastByCity(String city, String country){
        OWMForecast owmForecast = this.getForecastUrlByCity(city,country);
        return forecastInfo(owmForecast);

    }

    public HashMap<String,ArrayList<ListInfo>> getForecastByCoord(Double lon, Double lat){
        OWMForecast owmForecast = this.getForecastUrlByCoord(lon,lat);
        return forecastInfo(owmForecast);
    }

    public String getCityByCoord(Double lon,Double lat){
        OWMForecast owmForecast = this.getForecastUrlByCoord(lon,lat);
        String city = owmForecast.getCity().name;
        String country = owmForecast.getCity().country;
        String cityCountry = city + ',' + country;
        return cityCountry;

    }

    public ArrayList<Double> getCoordByCity(String city,String country){
        OWMForecast owmForecast = this.getForecastUrlByCity(city,country);
        Double lat = owmForecast.getCity().coord.getLat();
        Double lon = owmForecast.getCity().coord.getLon();
        ArrayList<Double> coords = new ArrayList<>();
        coords.add(lat);
        coords.add(lon);
        return coords;
    }

    public LinkedHashMap<String, ArrayList<ListInfo>> forecastInfo(OWMForecast owmForecast){
        List<ListInfo> list = owmForecast.getList();
        LinkedHashMap<String,ArrayList<ListInfo>> dateToArraylist = new LinkedHashMap<>();
        ArrayList<String> keyValues = new ArrayList<>();
        String str = " ";
        for (int i = 0; i < list.size() ; i++) {
            if(!(str.equals(list.get(i).getDt_txt().substring(0,10)))){
                str = list.get(i).getDt_txt().substring(0,10);
                ArrayList<ListInfo> infoPerHour = new ArrayList<>();
                keyValues.add(str);
                dateToArraylist.put(historyService.fixDate(str),infoPerHour);
                dateToArraylist.get(historyService.fixDate(str)).add(list.get(i));
            }
            else {
                dateToArraylist.get(historyService.fixDate(str)).add(list.get(i));
            }
        }

        return dateToArraylist;
    }

    public LinkedHashMap<String,Object> getFourDayForecast(Double lat, Double lon){
        OWMForecast forecast = this.getForecastUrlByCoord(lat, lon);
        double temp = 0;
        String str = " ";
        LinkedHashMap<String,Object> avgtemp = new LinkedHashMap<>();
        for (int i = 0; i < forecast.getList().size() - 1 ; i++) {
            if(!(str.equals(forecast.getList().get(i).getDt_txt().substring(0,10)))){
                str = forecast.getList().get(i).getDt_txt().substring(0,10);
                temp = 0;
                temp += forecast.getList().get(i).getMain().getTemp();
                avgtemp.put(historyService.fixDate(str),Math.round((temp/8) * 10) / 10.0);
            }
            else{
                temp += forecast.getList().get(i).getMain().getTemp();
                avgtemp.put(historyService.fixDate(str),Math.round((temp/8) * 10) / 10.0);
            }
       }
        Set<Map.Entry<String, Object>> entries = avgtemp.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        Map.Entry<String, Object> entry = null, firstEntry = null, lastEntry = null;

        while(iterator.hasNext()){

            entry = iterator.next();
            if(firstEntry == null)
                firstEntry = entry;
            lastEntry = entry;
        }
        avgtemp.remove(firstEntry.getKey());
        avgtemp.remove(lastEntry.getKey());
        return avgtemp;
    }


    public  <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> exchange = this.restTemplate
                .exchange(request, responseType);
        return exchange.getBody();
    }
}
