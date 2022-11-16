package com.example.demo.delegate;

import com.example.demo.model.CityForecast;
import com.example.demo.model.Response;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;

import static com.example.demo.config.ProcessVariableConstants.*;

@Component
public class ComposeResponse implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CheckForecastsInDb.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Task: Compose answer");
        long startAtTomorrow = LocalDate.now().atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC).getEpochSecond();
        long endAtThirdDay = LocalDate.now().atTime(LocalTime.MAX).plusDays(3).toInstant(ZoneOffset.UTC).getEpochSecond();
        List<CityForecast> forecasts = (List<CityForecast>) execution.getVariable(FORECASTS);
        float currentTemp = (float) execution.getVariable(CURRENT_TEMPERATURE);
        String uniqueId = (String) execution.getVariable(UNIQUEID);
        Comparator<CityForecast> compareByTemp = (o1, o2) -> Float.compare(o1.getTemperature(), o2.getTemperature());
        CityForecast forecastWithMinTemp = forecasts.stream().filter(x -> x.getTimestampUTC() >= startAtTomorrow && x.getTimestampUTC() < endAtThirdDay).min(compareByTemp).get();
        Response response = new Response();
        response.setUniqueId(uniqueId);
        response.setCurrentTemp(currentTemp);
        response.setForecastedMinTemp(forecastWithMinTemp.getTemperature());
        execution.setVariable(RESPONSE, response);
    }
}
