package com.example.demo.model;


import javax.persistence.*;
/**
 * Entity object, stored in database, contains results of process work: current temperature adn minimal temperature forecasted for three days
 */
@Entity
@Table(name = "response")
@Access(AccessType.FIELD)
public class Response {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    String uniqueId;

    float currentTemp;
    float forecastedMinTemp;

    public String getUniqueId() {
        return uniqueId;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public float getForecastedMinTemp() {
        return forecastedMinTemp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setCurrentTemp(float currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void setForecastedMinTemp(float forecastedMinTemp) {
        this.forecastedMinTemp = forecastedMinTemp;
    }
}
