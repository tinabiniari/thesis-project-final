package com.tinampiniari.thesisproject.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tinampiniari.thesisproject.data.model.City;
import com.tinampiniari.thesisproject.data.model.LargestCities;
import com.tinampiniari.thesisproject.data.repository.LargestCitiesRepository;
import com.tinampiniari.thesisproject.integration.airvisual.AirVisualApi;
import com.tinampiniari.thesisproject.integration.airvisual.AirVisualService;
import com.tinampiniari.thesisproject.integration.ows.Coord;
import com.tinampiniari.thesisproject.integration.ows.ForecastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

@Controller
public class AirQualityController {

    @Autowired
    AirVisualService airvisualService;

    @Autowired
    ForecastService forecastService;

    @Autowired
    LargestCitiesRepository largestCitiesRepo;

    @GetMapping("/air")
    public ModelAndView getAirQuality(@ModelAttribute("city") City city,@ModelAttribute("coord") Coord coord,ModelAndView modelAndView){
        modelAndView.setViewName("air");
        try {
            modelAndView.addObject("largestCities", airvisualService.getLargestCitiesAirQ());
        }catch (HttpClientErrorException e){
            modelAndView.addObject("errorPageMessage","Please refresh your page!");
            modelAndView.addObject("class","my-4 p-2 rounded");
            modelAndView.addObject("style","color:#680e0e;font-size:16px;background:#c1172f36");
        }
        return modelAndView;
    }



    @GetMapping("/air/city")
    public ModelAndView getAirQualityByCity(@ModelAttribute("city") City city, @ModelAttribute("coord") Coord coord,
                                            ModelAndView modelAndView, @RequestParam("city") String name, @RequestParam("country") String country) {
        modelAndView.setViewName("airQualityCard");
        Double lat = forecastService.getCoordByCity(name, country).get(0);
        Double lon = forecastService.getCoordByCity(name, country).get(1);
        modelAndView.addObject("largestCities", airvisualService.getLargestCitiesAirQ());
        try {
            modelAndView.addObject("lat", lat);
            modelAndView.addObject("lon", lon);
            modelAndView.addObject("airVisual", airvisualService.getAirVisualApi(lat, lon));

        } catch (Exception e) {
            modelAndView.addObject("failMessage", "Sorry, the city you are looking for is not valid");
            return modelAndView;
        }
        return modelAndView;
    }

    @GetMapping("/air/location")
    public ModelAndView getAirQualityByLocation(@ModelAttribute("city") City city, @ModelAttribute("coord") Coord coord,
                                                ModelAndView modelAndView, @RequestParam("lon") Double lon, @RequestParam("lat") Double lat) {
        modelAndView.setViewName("airQualityCard");
        modelAndView.addObject("largestCities", airvisualService.getLargestCitiesAirQ());
        modelAndView.addObject("lat", lat);
        modelAndView.addObject("lon", lon);
        modelAndView.addObject("airVisual", airvisualService.getAirVisualApi(lat, lon));
        return modelAndView;
    }


}