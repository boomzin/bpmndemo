package com.example.demo.delegate;

import com.example.demo.dto.ForecastApiResponse;
import com.example.demo.model.CityForecast;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.config.ProcessVariableConstants.*;
import static org.camunda.spin.Spin.JSON;

@Component
public class FetchForecasts implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FetchForecasts.class);

    @Value("${weather.url}")
    private String url;

    @Value("${weather.api_secret}")
    private String apiKey;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ForecastApiResponse apiResponse;
        String city = (String) execution.getVariable(CITY);
        String country = (String) execution.getVariable(COUNTRY);
        String latitude = (String) execution.getVariable(LATITUDE);
        String longitude = (String) execution.getVariable(LONGITUDE);
        log.info("Task: Fetch forecasts");
        log.info("Fetch forecasts by 5 days, step 3 hours");
        HttpConnector httpConnector = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request = httpConnector.createRequest().url(String.format("%s/data/2.5/forecast?lat=%s&lon=%s&appid=%s", url, latitude, longitude, apiKey)).get();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");

        request.setRequestParameter("headers", headers);

        HttpResponse response = request.execute();
        if (response.getStatusCode() == 200) {
            log.info("Fetched successfully");
            apiResponse = JSON(response.getResponse()).mapTo(ForecastApiResponse.class);
            List<CityForecast> forecasts = apiResponse.getForecasts().stream()
                    .map(x-> new CityForecast(null,city, country, latitude, longitude, x.getTimestamp(), x.getMain().getTemp() ))
                    .collect(Collectors.toList());
            execution.setVariable(FORECASTS, Variables.objectValue(forecasts).create());
        } else {
            log.info("Fetching failed");
            execution.setVariable(ERROR_FETCHING_MESSAGE, "Error fetching forecasts");
            throw new BpmnError(ERROR_FETCHING_FORECASTS);
        }
        response.close();
    }
}
