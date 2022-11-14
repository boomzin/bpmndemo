package com.example.demo.repository;

import com.example.demo.model.CityForecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityForecastRepository extends JpaRepository<CityForecast, Integer> {

}
