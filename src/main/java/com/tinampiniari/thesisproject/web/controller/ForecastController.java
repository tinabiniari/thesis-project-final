package com.tinampiniari.thesisproject.web.controller;

import com.tinampiniari.thesisproject.ThesisProjectProperties;
import com.tinampiniari.thesisproject.data.model.City;
import com.tinampiniari.thesisproject.integration.ows.Coord;
import com.tinampiniari.thesisproject.integration.ows.ForecastService;
import com.tinampiniari.thesisproject.integration.ows.ListInfo;
import com.tinampiniari.thesisproject.integration.airvisual.*;
import com.tinampiniari.thesisproject.integration.stormglass.Chart;
import com.tinampiniari.thesisproject.integration.ows.CurrentService;
import com.tinampiniari.thesisproject.integration.ows.ForecastChart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@Controller
public class ForecastController {

    @Autowired
    CurrentService currentService;

    @Autowired
    ThesisProjectProperties projectProperties;

    @Autowired
    ForecastService forecastService;

    @Autowired
    AirVisualService airvisualService;

    @GetMapping("/forecast")
    public ModelAndView getForecastSearch(@ModelAttribute("city") City city,@ModelAttribute("coord") Coord coord, ModelAndView modelAndView) {
        modelAndView.setViewName("search");
        modelAndView.addObject("title","Forecast Weather");
        modelAndView.addObject("predictionType","forecast");
        modelAndView.addObject("description","You can check the forecast weather in any city, by filling the name and the country of the city.");
        return modelAndView;
    }

    @GetMapping("/forecast/city")
    public ModelAndView getForecastCity(@Valid @ModelAttribute("city") City city,@ModelAttribute("coord") Coord coord, BindingResult bindingResult, ModelAndView modelAndView, @RequestParam("city") String name, @RequestParam("country") String country) {
        modelAndView.setViewName("forecastWeatherCard");
        if((name == "") || (country == "")){
            bindingResult.rejectValue("city","error.city","Please fill in both fields");
        }
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        modelAndView.addObject("listInfo",forecastService.getForecastByCity(name,country));
        modelAndView.addObject("latLon",forecastService.getCoordByCity(name, country));

        return modelAndView;
    }

    @GetMapping("/forecast/location")
    public ModelAndView getForecastLocation(@Valid @ModelAttribute("coord") Coord coord, @ModelAttribute("city") City city, BindingResult bindingResult, ModelAndView modelAndView, @RequestParam("lon") Double lon, @RequestParam("lat") Double lat) {
        modelAndView.setViewName("forecastWeatherCard");
        modelAndView.addObject("listInfo",forecastService.getForecastByCoord(lon,lat));
        modelAndView.addObject("cityCountry",forecastService.getCityByCoord(lon, lat));
        List<ForecastChart> charts = new ArrayList<>();
        int i = 1;
        for (Map.Entry<String, ArrayList<ListInfo>> entry : forecastService.getForecastByCoord(lon, lat).entrySet()) {
            ForecastChart chart = new ForecastChart();
            List<Double> temps = new ArrayList<>();
            temps.add(entry.getValue().get(0).getMain().getTemp_min());
            temps.add(entry.getValue().get(0).getMain().getTemp_max());
            chart.setX(i);
            chart.setY(temps);
            chart.setLabel(entry.getKey());
            charts.add(chart);
            i++;
        }
        modelAndView.addObject("charts", charts);
        return modelAndView;
    }

}
