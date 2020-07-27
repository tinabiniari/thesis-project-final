package com.tinampiniari.thesisproject.service;

import com.tinampiniari.thesisproject.data.model.SavedCities;
import com.tinampiniari.thesisproject.data.repository.SavedCitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedCitiesService {

    @Autowired
    UserService userService;

    @Autowired
    SavedCitiesRepository citiesRepository;

    public String check(SavedCities savedCities, String lat, String lon, Long userId, String city){
        String message = "You have succesfuly added "+city+ " in your list!";
        if(citiesRepository.countByLatAndLonAndUserId(lat,lon,userId) > 0){
            message = showMessage(city);
        }
        else {
            saveCity(savedCities);
        }
        return message;
    }

    public String showMessage(String city){
        String alreadyAddedCity = "You have already add "+city+ " in your list!";
        return alreadyAddedCity;
    }

    public SavedCities saveCity(SavedCities savedCities) {

        return citiesRepository.save(savedCities);

    }

    public List<SavedCities> getSavedCitiesById(Long userId) {
        return citiesRepository.findByUserId(userId);
    }

    public void deleteCity(Long id){
        citiesRepository.deleteById(id);
    }



}
