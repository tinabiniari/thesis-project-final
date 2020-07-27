package com.tinampiniari.thesisproject.web.controller;

import com.tinampiniari.thesisproject.ThesisProjectProperties;
import com.tinampiniari.thesisproject.data.model.City;
import com.tinampiniari.thesisproject.integration.ows.Coord;
import com.tinampiniari.thesisproject.integration.ows.ForecastService;
import com.tinampiniari.thesisproject.integration.stormglass.Chart;
import com.tinampiniari.thesisproject.integration.stormglass.History;
import com.tinampiniari.thesisproject.integration.stormglass.HistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class HistoryController {


    @Autowired
    HistoryService historyService;

    @Autowired
    ForecastService forecastService;

    @Autowired
    ThesisProjectProperties properties;


    @GetMapping("/history")
    public ModelAndView getHistory(@ModelAttribute("history") History history, @ModelAttribute("city") City city, @ModelAttribute("coord") Coord coord, ModelAndView modelAndView) {
        modelAndView.setViewName("history");
        modelAndView.addObject("predictionType","history");
        return modelAndView;
    }

    @PostMapping("/history/city")
    public ModelAndView getHistoryByCity(@ModelAttribute("history") History history, @ModelAttribute("city") City city, @ModelAttribute("coord") Coord coord, ModelAndView modelAndView, @RequestParam("city") String name, @RequestParam("country") String country) throws JSONException {
        modelAndView.setViewName("history");
        try {
            String currentDate = LocalDate.now().toString();
            String daysAgo = LocalDate.now().minusDays(10).toString();
            Double lat = historyService.getCoordByCity(name,country).get(0);
            Double lon = historyService.getCoordByCity(name,country).get(1);
            HashMap<String, Double> averageDailyWeather = historyService.getAverageDailyWeather(lat, lon, daysAgo, currentDate);
            modelAndView.addObject("avgWeather",averageDailyWeather);
            modelAndView.addObject("charts", historyService.getHistoryChart(lat, lon, daysAgo, currentDate));

        } catch (HttpClientErrorException e){
            modelAndView.addObject("errorField","Sorry, the city or country you are searching for,is not found");
            modelAndView.addObject("class","my-4 p-2 rounded");
            modelAndView.addObject("style","color:#680e0e;font-size:16px;background:#c1172f36");
        }
        return modelAndView;
    }


    @PostMapping("/history/location")
    public ModelAndView getHistoryByLocation(@ModelAttribute("history") History history, @ModelAttribute("city") City city,
                                             @ModelAttribute("coord") Coord coord, ModelAndView modelAndView, @RequestParam("lon") Double lon,
                                             @RequestParam("lat") Double lat)  {
        modelAndView.setViewName("history");
        try {
            String currentDate = LocalDate.now().toString();
            String daysAgo = LocalDate.now().minusDays(10).toString();
            String cityName = forecastService.getCityByCoord(lon, lat);
            HashMap<String, Double> averageDailyWeather = historyService.getAverageDailyWeather(lat, lon, daysAgo,
                    currentDate);
            modelAndView.addObject("avgWeather", averageDailyWeather);
            modelAndView.addObject("charts", historyService.getHistoryChart(lat, lon, daysAgo, currentDate));
            modelAndView.addObject("cityName", cityName);
        }
        catch (HttpClientErrorException e){
            modelAndView.addObject("errorField","Sorry, the city or country you are searching for,is not found");
            modelAndView.addObject("class","my-4 p-2 rounded");
            modelAndView.addObject("style","color:#680e0e;font-size:16px;background:#c1172f36");
        }
        return modelAndView;
    }

    @GetMapping("/historyCard/location")
    public ModelAndView getHistoryCard(@ModelAttribute("history") History history, @ModelAttribute("city") City city,
                                       @ModelAttribute("coord") Coord coord, ModelAndView modelAndView, @RequestParam("lon") Double lon,
                                       @RequestParam("lat") Double lat)  {
        modelAndView.setViewName("historyWeatherCard");
        String currentDate = LocalDate.now().toString();
        String daysAgo = LocalDate.now().minusDays(10).toString();
        String cityName = forecastService.getCityByCoord(lon, lat);
        HashMap<String, Double> averageDailyWeather = historyService.getAverageDailyWeather(lat, lon, daysAgo,
                currentDate);
        modelAndView.addObject("avgWeather", averageDailyWeather);
        modelAndView.addObject("charts", historyService.getHistoryChart(lat, lon, daysAgo, currentDate));
        modelAndView.addObject("cityName", cityName);
        return modelAndView;
    }

}
