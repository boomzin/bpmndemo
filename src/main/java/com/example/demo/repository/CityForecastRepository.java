package com.example.demo.repository;

import com.example.demo.model.CityForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityForecastRepository extends JpaRepository<CityForecast, Integer> {


    @Query("SELECT a " +
            "FROM CityForecast a " +
            "WHERE a.timestampUTC = (" +
                "SELECT min(b.timestampUTC) " +
                "FROM CityForecast b " +
                "WHERE b.city = :city AND b.country = :country AND b.timestampUTC >= :start AND b.timestampUTC <= :end)")
    Optional<CityForecast> getCurrentWeather(String city, String country, long start, long end);

    @Query("SELECT c FROM CityForecast c WHERE c.longitude = :lon AND c.latitude = :lat AND c.timestampUTC >= :start AND c.timestampUTC < :end")
    List<CityForecast> getForecastsByPeriod(String lon, String lat, long start, long end);
}
