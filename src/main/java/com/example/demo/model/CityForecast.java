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

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setTimestampUTC(long timestampUTC) {
        this.timestampUTC = timestampUTC;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
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
