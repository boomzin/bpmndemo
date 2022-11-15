package com.example.demo.delegate;

import com.example.demo.model.CityForecast;
import com.example.demo.service.CityForecastService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.example.demo.config.ProcessVariableConstants.*;

@Component
public class WriteForecastsToDb implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WriteForecastsToDb.class);
    private final CityForecastService cityForecastService;

    public WriteForecastsToDb(CityForecastService cityForecastService) {
        this.cityForecastService = cityForecastService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Task: Write forecasts in db");

        Object latestInDb = execution.getVariable(FORECAST_LATEST_IN_DB);
        final long startTime = latestInDb == null ? LocalDate.now().toEpochSecond(LocalTime.now(),ZoneOffset.UTC) : (long) latestInDb;
        List<CityForecast> forecasts = (List<CityForecast>) execution.getVariable(FORECASTS);
        forecasts.stream().filter(x -> x.getTimestampUTC() >= startTime).forEach(cityForecastService::save);
    }
}
