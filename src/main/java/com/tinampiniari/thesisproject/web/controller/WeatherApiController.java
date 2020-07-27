package com.tinampiniari.thesisproject.web.controller;

import com.tinampiniari.thesisproject.integration.ows.Weather;
import com.tinampiniari.thesisproject.integration.ows.CurrentService;
import com.tinampiniari.thesisproject.integration.stormglass.HistoryService;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("api/weather")
public class WeatherApiController {

    private final CurrentService currentService;

    private final HistoryService historyService;

    public WeatherApiController(CurrentService currentService, HistoryService historyService) {
        this.currentService = currentService;
        this.historyService = historyService;
    }

    @RequestMapping("/current/{city}/{country}")
    public Weather getWeather(@PathVariable String city,@PathVariable String country){
        return (Weather) this.currentService.getWeather(city,country);
    }

    @RequestMapping("/history/{city}/{country}")
    public JSONObject getHistory(@PathVariable String city,@PathVariable String country){
        String currentDate = LocalDate.now().toString();
        String daysAgo = LocalDate.now().minusDays(10).toString();
        Double lat = historyService.getCoordByCity(city,country).get(0);
        Double lon = historyService.getCoordByCity(city,country).get(1);
        HashMap<String, Double> averageDailyWeather = historyService.getAverageDailyWeather(lat, lon, daysAgo, currentDate);
        JSONObject jsonObject = new JSONObject(averageDailyWeather);
        return jsonObject;
    }

}
