package com.example.demo.delegate;

import com.example.demo.model.CityForecast;
import com.example.demo.service.CityForecastService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;

import static com.example.demo.config.ProcessVariableConstants.*;

@Component
public class CheckForecastsInDb implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CheckForecastsInDb.class);
    private final CityForecastService cityForecastService;

    public CheckForecastsInDb(CityForecastService cityForecastService) {
        this.cityForecastService = cityForecastService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Task: Check forecasts in db");
        long startAtThirdDay = LocalDate.now().atStartOfDay().plusDays(3).toInstant(ZoneOffset.UTC).getEpochSecond();
        execution.setVariable(IS_ENOUGH_FORECASTS_QUANTITY, false);
        String longitude = (String) execution.getVariable(LONGITUDE);
        String latitude = (String) execution.getVariable(LATITUDE);
        List<CityForecast> forecastsFromDb = cityForecastService.checkForecastsInDb(longitude, latitude);
        if (forecastsFromDb.isEmpty()) {
            log.info("Forecasts not found");
        } else {
            log.info("Forecasts found");
            Comparator<CityForecast> compareByTimestamp = Comparator.comparingLong(CityForecast::getTimestampUTC);
            CityForecast forecastLatestInDb = forecastsFromDb.stream().max(compareByTimestamp).get();
            if (forecastLatestInDb.getTimestampUTC() < startAtThirdDay) {
                log.info("Forecasts not enough");
                execution.setVariable(FORECAST_LATEST_IN_DB, forecastLatestInDb.getTimestampUTC());
            } else {
                log.info("Forecasts enough");
                execution.setVariable(IS_ENOUGH_FORECASTS_QUANTITY, true);
                execution.setVariable(FORECASTS, Variables.objectValue(forecastsFromDb));
            }
        }
    }
}
