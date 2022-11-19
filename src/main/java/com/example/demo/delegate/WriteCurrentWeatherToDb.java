package com.example.demo.delegate;

import com.example.demo.model.CityForecast;
import com.example.demo.service.CityForecastService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.demo.config.ProcessVariableConstants.*;

/**
 * This class is related with process task "Write current weather to db"
 * Create instance of CityForecast.class.
 * Fill instance fields with data of process variables:
 * CITY, COUNTRY, LONGITUDE, LATITUDE, TIMESTAMP, CURRENT_TEMPERATURE.
 * Store instance to database.
 *
 */
@Component
public class WriteCurrentWeatherToDb implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WriteCurrentWeatherToDb.class);
    private final CityForecastService cityForecastService;

    public WriteCurrentWeatherToDb(CityForecastService cityForecastService) {
        this.cityForecastService = cityForecastService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Task: Write current weather in db");
        CityForecast cityForecast = new CityForecast();
        cityForecast.setCity((String) execution.getVariable(CITY));
        cityForecast.setCountry((String) execution.getVariable(COUNTRY));
        cityForecast.setLongitude((String) execution.getVariable(LONGITUDE));
        cityForecast.setLatitude((String) execution.getVariable(LATITUDE));
        cityForecast.setTimestampUTC((long) execution.getVariable(TIMESTAMP));
        cityForecast.setTemperature((float) execution.getVariable(CURRENT_TEMPERATURE));
        cityForecastService.save(cityForecast);
    }
}
