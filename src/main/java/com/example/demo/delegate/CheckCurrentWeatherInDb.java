package com.example.demo.delegate;

import com.example.demo.model.CityForecast;
import com.example.demo.service.CityForecastService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.demo.config.ProcessVariableConstants.*;

/**
 * This class is related with process task "Check current weather in db"
 * If database stores current weather data, this data fetch from database and fill process variables:
 * LATITUDE, LONGITUDE, CURRENT_TEMPERATURE. Process variable IS_PRESENT_CURRENT set to TRUE.
 * If not, process variable IS_PRESENT_CURRENT set to FALSE.
 * This variable has influence on decision in gateway "is current weather in db"
 *
 */
@Component
public class CheckCurrentWeatherInDb implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CheckCurrentWeatherInDb.class);
    private final CityForecastService cityForecastService;

    public CheckCurrentWeatherInDb(CityForecastService cityForecastService) {
        this.cityForecastService = cityForecastService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Task: Check current weather in db");
        execution.setVariable(IS_PRESENT_CURRENT, false);
        String city = (String) execution.getVariable(CITY);
        String country = (String) execution.getVariable(COUNTRY);
        Optional<CityForecast> currentWeatherInDb = cityForecastService.getCurrent(city, country);
        if (currentWeatherInDb.isPresent()) {
            log.info("Current weather was found");
            CityForecast current = currentWeatherInDb.get();
            execution.setVariable(LATITUDE, current.getLatitude());
            execution.setVariable(LONGITUDE, current.getLongitude());
            execution.setVariable(CURRENT_TEMPERATURE, current.getTemperature());
            execution.setVariable(IS_PRESENT_CURRENT, true);
        } else {
            log.info("Current weather was not found");
        }
    }
}
