package com.example.demo.service;

import com.example.demo.model.CityForecast;
import com.example.demo.repository.CityForecastRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class CityForecastService {

    private final CityForecastRepository cityForecastRepository;

    private final long PREVIOUS_HOURS;

    public CityForecastService(CityForecastRepository cityForecastRepository, @Value("${previous.hours}") final long count) {
        this.cityForecastRepository = cityForecastRepository;
        PREVIOUS_HOURS = 60*60* count;
    }

    public Optional<CityForecast> getCurrent(String city, String country) {
        long end = Instant.now().getEpochSecond();
        long start = end - PREVIOUS_HOURS;
        return cityForecastRepository.getCurrentWeather(city, country, start, end);
    }
}
