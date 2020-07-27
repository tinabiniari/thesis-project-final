package com.tinampiniari.thesisproject.web.controller;

import com.tinampiniari.thesisproject.data.model.SavedCities;
import com.tinampiniari.thesisproject.data.model.User;
import com.tinampiniari.thesisproject.integration.ows.CurrentService;
import com.tinampiniari.thesisproject.integration.ows.OWMApi;
import com.tinampiniari.thesisproject.service.SavedCitiesService;
import com.tinampiniari.thesisproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class SavedCitiesController {

    @Autowired
    SavedCitiesService citiesService;

    @Autowired
    UserService userService;

    @Autowired
    CurrentService currentService;

    @Autowired
    SavedCitiesService savedCitiesService;

    @GetMapping("/savedCities")
    ModelAndView getSavedCities(@ModelAttribute("savedCities")SavedCities savedCities, Principal principal,ModelAndView modelAndView){
        modelAndView.setViewName("savedCities");
        User user = userService.findByEmail(principal.getName());
        List<SavedCities> savedCitiesById = citiesService.getSavedCitiesById(user.getUserId());
        modelAndView.addObject("savedCities",savedCitiesById);
        HashMap<Long,Object> arrayList = new HashMap<>();
        for (int i = 0; i <savedCitiesById.size() ; i++) {
            String lat = savedCitiesById.get(i).getLat();
            String lon = savedCitiesById.get(i).getLon();
            Double latD = Double.valueOf(lat);
            Double lonD = Double.valueOf(lon);
            arrayList.put(savedCitiesById.get(i).getId(),currentService.getCurrentByCoord(lonD,latD));

        }
        modelAndView.addObject("currentDate", currentService.getTime());
        modelAndView.addObject("myCities",arrayList);
        if(arrayList.size() > 3) {
            modelAndView.addObject("col","col-3");
        }
        else if(arrayList.size() == 3){
            modelAndView.addObject("col","col-4");
        }
        else if(arrayList.size() == 2){
            modelAndView.addObject("col","col-5");
        }
        else {
            modelAndView.addObject("col","col");
        }
        return modelAndView;
    }

    @GetMapping("/savedCitiesCard")
    ModelAndView getSavedCitiesCard(@ModelAttribute("savedCities")SavedCities savedCities, Principal principal,ModelAndView modelAndView){
        modelAndView.setViewName("savedCitiesCard");
        User user = userService.findByEmail(principal.getName());
        List<SavedCities> savedCitiesById = citiesService.getSavedCitiesById(user.getUserId());
        modelAndView.addObject("savedCities",savedCitiesById);
        HashMap<Long,Object> arrayList = new HashMap<>();
        for (int i = 0; i <savedCitiesById.size() ; i++) {
            String lat = savedCitiesById.get(i).getLat();
            String lon = savedCitiesById.get(i).getLon();
            Double latD = Double.valueOf(lat);
            Double lonD = Double.valueOf(lon);
            arrayList.put(savedCitiesById.get(i).getId(),currentService.getCurrentByCoord(lonD,latD));
        }
        modelAndView.addObject("currentDate", currentService.getTime());
        modelAndView.addObject("myCities",arrayList);
        if(arrayList.size() > 3) {
            modelAndView.addObject("col","col-3");
        }
        else if(arrayList.size() == 3){
            modelAndView.addObject("col","col-4");
        }
        else if(arrayList.size() == 2){
            modelAndView.addObject("col","col-5");
        }
        else {
            modelAndView.addObject("col","col");
        }
        return modelAndView;
    }

    @PostMapping(value = "/deleteCity/{cityId}")
    @ResponseBody
    public RedirectView deleteCity(@ModelAttribute("savedCities")SavedCities savedCities, Principal principal, HttpServletRequest request, ModelAndView modelAndView, @PathVariable String cityId, RedirectAttributes attributes){
        String cityName=request.getParameter("cityName");
        Long city_Id=Long.valueOf(cityId);
        savedCitiesService.deleteCity(city_Id);
        attributes.addFlashAttribute("succesMessage","You have deleted succesfully "+cityName+" from your saved cities!");
        attributes.addFlashAttribute("stylesuccesMessage","succesMessage");
        attributes.addFlashAttribute("visible","d-block ");
        return new RedirectView("/savedCities");

    }

}
