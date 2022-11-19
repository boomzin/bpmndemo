package com.example.demo.config;
/**
 * This class contains constants of process variables names
 */
public interface ProcessVariableConstants {

    /**
     * City name
     */
    String CITY = "city";
    /**
     * Country code
     */
    String COUNTRY = "country";
    /**
     * Unique ID of process
     */
    String UNIQUEID ="uniqueID";
    /**
     * City longitude
     */
    String LONGITUDE ="longitude";
    /**
     * City latitude
     */
    String LATITUDE ="latitude";
    /**
     * Current temperature for city
     */
    String CURRENT_TEMPERATURE ="currentTemperature";
    /**
     * This variable contains false if current temperature doesn`t exist in database
     */
    String IS_PRESENT_CURRENT ="isPresentCurrent";
    /**
     * This variable contains false if database store forecasts less than three days
     */
    String IS_ENOUGH_FORECASTS_QUANTITY ="isEnoughForecasts";
    /**
     * This variable contains forecast latest in database
     */
    String FORECAST_LATEST_IN_DB ="forecastLatestInDb";
    /**
     * This variable contains time of current weather getting from external weather service
     */
    String TIMESTAMP ="timestamp";
    /**
     * This variable contains list of forecasts getting from external weather service
     */
    String FORECASTS = "forecasts";
    /**
     * This variable contains response entity with current temperature and minimal forecasted temperature for three days
     */
    String RESPONSE = "response";
    /**
     * This variable contains name of boundary event error
     */
    String ERROR_FETCHING_WEATHER = "errorFetchingWeather";
    /**
     * This variable contains name of boundary event error
     */
    String ERROR_FETCHING_FORECASTS = "errorFetchingForecasts";
    /**
     * This variable contains error message
     */
    String ERROR_FETCHING_MESSAGE = "errorFetchingMessage";
}
