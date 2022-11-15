package com.example.demo.delegate;

import com.example.demo.model.CityForecast;
import com.example.demo.repository.CityForecastRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.demo.config.ProcessVariableConstants.*;

@Component
public class WriteCurrentWeatherInDb implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WriteCurrentWeatherInDb.class);
    private final CityForecastRepository cityForecastRepository;

    public WriteCurrentWeatherInDb(CityForecastRepository cityForecastRepository) {
        this.cityForecastRepository = cityForecastRepository;
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
        cityForecastRepository.saveAndFlush(cityForecast);
    }
}
