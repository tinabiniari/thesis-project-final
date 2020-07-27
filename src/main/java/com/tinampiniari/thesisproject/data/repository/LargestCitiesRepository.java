package com.tinampiniari.thesisproject.data.repository;

import java.util.Optional;

import com.tinampiniari.thesisproject.data.model.LargestCities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LargestCitiesRepository extends JpaRepository<LargestCities, Long> {

}
