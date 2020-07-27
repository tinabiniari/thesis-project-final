package com.tinampiniari.thesisproject.integration.stormglass;

import com.tinampiniari.thesisproject.ThesisProjectProperties;
import com.tinampiniari.thesisproject.integration.ows.ForecastService;
import com.tinampiniari.thesisproject.integration.ows.OWMForecast;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class HistoryService {

    private final static String HISTORY_URL = "https://api.stormglass.io/v1/weather/point?&lat={lat}&lng={lon}&start={startDate}&end={endDate}&key={stormglasskey}";

    private final String stormglasskey;

    private final ForecastService forecastService;

    public HistoryService(ForecastService forecastService,ThesisProjectProperties properties) {
        this.stormglasskey = properties.getApi().getStormglasskey();
        this.forecastService = forecastService;
    }

    public History getHistoryUrl(Double lat, Double lon, String startDate, String endDate){
        URI url = new UriTemplate(HISTORY_URL).expand(lat, lon,startDate,endDate, this.stormglasskey);
        return forecastService.invoke(url, History.class);
    }

    public History getHistory(Double lat, Double lon, String startDate, String endDate) {
        History history = this.getHistoryUrl(lat, lon,startDate,endDate);
        return history;
    }

    public LinkedHashMap<String,Double> getAverageDailyWeather(Double lat, Double lon,String startDate, String endDate){
        History history = this.getHistoryUrl(lat, lon, startDate, endDate);
        double temp = 0;
        String str = " ";
        LinkedHashMap<String,Double> avgtemp = new LinkedHashMap<>();
        for (int i = 0; i < history.getHours().size() - 1 ; i++) {
            if(!(str.equals(history.getHours().get(i).getTime().substring(0,10)))){
                str = history.getHours().get(i).getTime().substring(0,10);
                temp = 0;
                temp += history.getHours().get(i).getAirTemperature().get(1).getValue();
                avgtemp.put(fixDate(str),Math.round((temp/24) * 10) / 10.0);
            }
            else{
                temp += history.getHours().get(i).getAirTemperature().get(1).getValue();
                avgtemp.put(fixDate(str),Math.round((temp/24) * 10) / 10.0);
            }


        }
        return avgtemp;
    }

    public String fixDate(String date){
        String year = date.substring(0,4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);
        String fixedDate = day + '-' +month + '-' + year;
        return fixedDate;
    }

    
    public ArrayList<Double> getCoordByCity(String city, String country){
        OWMForecast owmForecast = forecastService.getForecastUrlByCity(city,country);
        Double lat = owmForecast.getCity().coord.getLat();
        Double lon = owmForecast.getCity().coord.getLon();
        ArrayList<Double> coords = new ArrayList<>();
        coords.add(lat);
        coords.add(lon);
        return coords;
    }

    public List<Chart> getHistoryChart(Double lat, Double lon, String daysAgo, String currentDate) {
        List<Chart> charts = new ArrayList<>();
        HashMap<String, Double> averageDailyWeather = getAverageDailyWeather(lat, lon, daysAgo,
                currentDate);
        int i = 1;
        for (Map.Entry<String, Double> entry : averageDailyWeather.entrySet()) {
            Chart chart = new Chart();
            chart.setX(i);
            chart.setY((int) Math.round(entry.getValue()));
            chart.setLabel(entry.getKey());
            charts.add(chart);
            i++;
        }
        return charts;
    }

}
