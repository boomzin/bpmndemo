package com.example.demo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "city_forecast")
@Access(AccessType.FIELD)
public class CityForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String city;
    private String country;
    private String latitude;
    private String longitude;
    private long timestampUTC;
    private float temperature;

    public CityForecast() {
    }

    public CityForecast(Long id, String city, String country, String latitude, String longitude, long timestampUTC, float temperature) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestampUTC = timestampUTC;
        this.temperature = temperature;
    }

    public long getTimestampUTC() {
        return timestampUTC;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityForecast that = (CityForecast) o;
        return timestampUTC == that.timestampUTC && latitude.equals(that.latitude) && longitude.equals(that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, timestampUTC);
    }
}
