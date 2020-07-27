package com.tinampiniari.thesisproject.web.controller;

import com.tinampiniari.thesisproject.ThesisProjectProperties;
import com.tinampiniari.thesisproject.data.model.City;
import com.tinampiniari.thesisproject.data.model.SavedCities;
import com.tinampiniari.thesisproject.data.model.User;
import com.tinampiniari.thesisproject.integration.ows.Coord;
import com.tinampiniari.thesisproject.integration.ows.ForecastService;
import com.tinampiniari.thesisproject.integration.ows.OWMApi;
import com.tinampiniari.thesisproject.integration.ows.CurrentService;
import com.tinampiniari.thesisproject.integration.stormglass.HistoryService;
import com.tinampiniari.thesisproject.service.SavedCitiesService;
import com.tinampiniari.thesisproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class CurrentController {

    @Autowired
    CurrentService currentService;

    @Autowired
    ThesisProjectProperties projectProperties;

    @Autowired
    ForecastService forecastService;

    @Autowired
    SavedCitiesService citiesService;

    @Autowired
    UserService userService;

    @Autowired
    SavedCitiesService savedCitiesService;

    @Autowired
    HistoryService historyService;


    @GetMapping("/current")
    public ModelAndView getCurrentSearch(@ModelAttribute("city") City city,@ModelAttribute("coord") Coord coord,ModelAndView modelAndView) {
        modelAndView.setViewName("search");
        modelAndView.addObject("predictionType","current");
        modelAndView.addObject("title","Current Weather");
        modelAndView.addObject("description","You can check the current weather in any city, by filling the name and the country of the city.Also, you can save it in your favourite cities");
        return modelAndView;
    }

    @GetMapping("/current/city")
    public ModelAndView getCurrentCity(@Valid @ModelAttribute("city") City city, BindingResult bindingResult,@ModelAttribute("coord") Coord coord, ModelAndView modelAndView, @RequestParam("city") String name, @RequestParam("country") String country) {
        modelAndView.setViewName("currentWeatherCard");
        if((name == "") || (country == "")){
            bindingResult.rejectValue("city","error.city","Please fill in both fields");
        }
        if (bindingResult.hasErrors()) {
            return modelAndView;
        }
        try {
            OWMApi owmApi = (OWMApi) currentService.getCurrentByCity(name, country).get(0);
            String iconId = owmApi.weather.get(0).getIcon();
            modelAndView.addObject("cityList", currentService.getCurrentByCity(name, country));
            modelAndView.addObject("currentDate", currentService.getTime());
            ArrayList<Double> coordByCity = forecastService.getCoordByCity(name, country);
            modelAndView.addObject("fourDay", forecastService.getFourDayForecast(coordByCity.get(0), coordByCity.get(1)));
            modelAndView.addObject("iconLink", currentService.getIconLink(iconId));
        }catch (HttpClientErrorException ex){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Not found", ex
            );
        }
        ArrayList<String> namesOfDays = new ArrayList<>();
        for (int i = 1; i < 5 ; i++) {
            namesOfDays.add(LocalDate.now().getDayOfWeek().plus(i).toString());
        }
        modelAndView.addObject("namesOfDays",namesOfDays);
        return modelAndView;
    }

    @GetMapping("/current/location")
    public ModelAndView getCurrentLocation(@Valid @ModelAttribute("coord") Coord coord, @ModelAttribute("city") City city, ModelAndView modelAndView,Principal principal, @RequestParam("lon") Double lon, @RequestParam("lat") Double lat) {
        modelAndView.setViewName("currentWeatherCard");
        OWMApi owmApi = (OWMApi) currentService.getCurrentByCoord(lon, lat).get(0);
        String iconId = owmApi.weather.get(0).getIcon();
        modelAndView.addObject("cityList", currentService.getCurrentByCoord(lon,lat));
        modelAndView.addObject("currentDate", currentService.getTime());
        modelAndView.addObject("fourDay",forecastService.getFourDayForecast(coord.getLat(),coord.getLon()));
        modelAndView.addObject("iconLink", currentService.getIconLink(iconId));
        ArrayList<String> namesOfDays = new ArrayList<>();
        for (int i = 1; i < 5 ; i++) {
            namesOfDays.add(LocalDate.now().getDayOfWeek().plus(i).toString());
        }
        modelAndView.addObject("namesOfDays",namesOfDays);
        String currentDate = LocalDate.now().toString();
        String daysAgo = LocalDate.now().minusDays(10).toString();
        modelAndView.addObject("charts", historyService.getHistoryChart(lat, lon, daysAgo, currentDate));
        return modelAndView;
    }

    @PostMapping(value = "/current/save/{lat}/{lon}")
    @ResponseBody
    public String saveCity(@ModelAttribute("city") City city, @ModelAttribute("coord") Coord coord, Principal principal, HttpServletRequest request, @PathVariable String lat, @PathVariable String lon) {
        User user = userService.findByEmail(principal.getName());
        String name = forecastService.getCityByCoord(Double.valueOf(lon), Double.valueOf(lat));
        String cityName= name.substring(0,name.length()-3);
        SavedCities savedCities = new SavedCities();
        savedCities.setUserId(user.getUserId());
        savedCities.setLat(lat);
        savedCities.setLon(lon);
        String message = citiesService.check(savedCities,lat,lon,user.getUserId(), StringUtils.capitalize(cityName));
        return message;
    }



}
