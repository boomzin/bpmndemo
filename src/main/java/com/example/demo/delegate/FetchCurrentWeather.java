package com.example.demo.delegate;

import com.example.demo.dto.WeatherApiResponse;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.camunda.spin.json.SpinJsonNode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.demo.config.ProcessVariableConstants.*;
import static org.camunda.spin.Spin.JSON;

@Component
public class FetchCurrentWeather implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FetchCurrentWeather.class);

    @Value("${weather.url}")
    private String url;

    @Value("${weather.api_secret}")
    private String apiKey;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        WeatherApiResponse apiResponse = null;
        String city = (String) execution.getVariable(CITY);
        String country = (String) execution.getVariable(COUNTRY);
        log.info("Task: Fetch current weather");
        log.info("WeatherClient: fetch current weather for " + city + ", " + country);
        HttpConnector httpConnector = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request = httpConnector.createRequest().url(String.format("%s/data/2.5/weather?q=%s,%s&appid=%s", url, city, country, apiKey)).get();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");

        request.setRequestParameter("headers", headers);

        HttpResponse response = request.execute();
        if (response.getStatusCode() == 200 || !response.getResponse().isEmpty()) {
            log.info("WeatherClient: fetched successfully");
            apiResponse = JSON(response.getResponse()).mapTo(WeatherApiResponse.class);
            execution.setVariable("latitude", apiResponse.getCoord().getLat());
            execution.setVariable("longitude", apiResponse.getCoord().getLon());
            execution.setVariable("currentTemperature", apiResponse.getMain().getTemp());
        }
        response.close();
    }
}
