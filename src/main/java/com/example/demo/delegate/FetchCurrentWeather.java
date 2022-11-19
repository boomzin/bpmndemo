package com.example.demo.delegate;

import com.example.demo.dto.WeatherApiResponse;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.demo.config.ProcessVariableConstants.*;
import static org.camunda.spin.Spin.JSON;

/**
 * This class is related with process task "Fetch current weather"
 * Trying to get data from external weather service.
 * If successful then assign this data with process variables:
 * LATITUDE, LONGITUDE, CURRENT_TEMPERATURE, TIMESTAMP.
 * If not, set information to process variable ERROR_FETCHING_MESSAGE and throw new BpmnError connected with process error boundary event "errorFetchingWeather"
 *
 */
@Component
public class FetchCurrentWeather implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FetchCurrentWeather.class);

    @Value("${weather.url}")
    private String url;

    @Value("${weather.api_secret}")
    private String apiKey;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        WeatherApiResponse apiResponse;
        String city = (String) execution.getVariable(CITY);
        String country = (String) execution.getVariable(COUNTRY);
        log.info("Task: Fetch current weather");
        log.info("Fetch current weather for " + city + ", " + country);
        HttpConnector httpConnector = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request = httpConnector.createRequest().url(String.format("%s/data/2.5/weather?q=%s,%s&appid=%s", url, city, country, apiKey)).get();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");

        request.setRequestParameter("headers", headers);

        HttpResponse response = request.execute();
        if (response.getStatusCode() == 200) {
            log.info("Fetched successfully");
            apiResponse = JSON(response.getResponse()).mapTo(WeatherApiResponse.class);
            execution.setVariable(LATITUDE, apiResponse.getCoord().getLat());
            execution.setVariable(LONGITUDE, apiResponse.getCoord().getLon());
            execution.setVariable(CURRENT_TEMPERATURE, apiResponse.getMain().getTemp());
            execution.setVariable(TIMESTAMP, apiResponse.getTimestamp());
        } else {
            log.info("Fetching failed");
            execution.setVariable(ERROR_FETCHING_MESSAGE, "Error fetching current weather");
            throw new BpmnError(ERROR_FETCHING_WEATHER);
        }
        response.close();
    }
}
