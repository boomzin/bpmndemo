package com.example.demo.service;

import com.example.demo.repository.CityForecastRepository;
import org.springframework.stereotype.Service;

@Service
public class CityForecastService {

    private final CityForecastRepository cityForecastRepository;

    public CityForecastService(CityForecastRepository cityForecastRepository) {
        this.cityForecastRepository = cityForecastRepository;
    }
}
