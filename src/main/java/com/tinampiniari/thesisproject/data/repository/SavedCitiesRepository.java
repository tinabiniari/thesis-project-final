package com.tinampiniari.thesisproject.data.repository;

import com.tinampiniari.thesisproject.data.model.SavedCities;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SavedCitiesRepository extends JpaRepository<SavedCities,Long> {

    List<SavedCities> findByUserId(Long userId);

    @Query(nativeQuery = true,value = "select count(*) from saved_cities where lat=? AND lon=? AND user_id=?;")
    Integer countByLatAndLonAndUserId(String lat, String lon, Long userId);




}
