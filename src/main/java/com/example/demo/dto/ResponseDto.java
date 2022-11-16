package com.example.demo.dto;

public class ResponseDto {

    private float currentTemperature;

    float forecastedMinTemp;

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public float getForecastedMinTemp() {
        return forecastedMinTemp;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public void setForecastedMinTemp(float forecastedMinTemp) {
        this.forecastedMinTemp = forecastedMinTemp;
    }
}
