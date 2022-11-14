package com.example.demo.delegate;

import com.example.demo.model.CityForecast;
import com.example.demo.service.CityForecastService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.demo.config.ProcessVariableConstants.*;

@Component
public class CheckCurrentWeatherInDb implements JavaDelegate {

    private final CityForecastService cityForecastService;

    public CheckCurrentWeatherInDb(CityForecastService cityForecastService) {
        this.cityForecastService = cityForecastService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable(CURRENT_IS_PRESENT, false);
        String city = (String) execution.getVariable(CITY);
        String country = (String) execution.getVariable(COUNTRY);
        Optional<CityForecast> currentWeatherInDb = cityForecastService.getCurrent(city, country);
        if (currentWeatherInDb.isPresent()) {
            CityForecast current = currentWeatherInDb.get();
            execution.setVariable(LATITUDE, current.getLatitude());
            execution.setVariable(LONGITUDE, current.getLongitude());
            execution.setVariable(CURRENT_TEMPERATURE, current.getTemperature());
            execution.setVariable(CURRENT_IS_PRESENT, true);
        }
    }
}
