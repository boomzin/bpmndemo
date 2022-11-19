package com.example.demo.web;

import com.example.demo.dto.ResponseDto;
import com.example.demo.service.CamundaStartService;
import com.example.demo.service.ResponseService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ForecastController {
    private final CamundaStartService camundaStartService;
    private final ResponseService responseService;

    public ForecastController(CamundaStartService camundaStartService, ResponseService responseService) {
        this.camundaStartService = camundaStartService;
        this.responseService = responseService;
    }

    /**
     * Getting request with city name and country code, return unique id of process
     */
    @GetMapping(value = "temperature")
    public String getTemp(@RequestParam String city, @RequestParam String country) {
        String uniqueID = UUID.randomUUID().toString();
        camundaStartService.startProcessByMessage(city, country, uniqueID);
        return uniqueID;
    }

    /**
     * Getting request with unique id, return current temperature and minimal temperature forecasted for three days or error message
     */
    @GetMapping(value = "response")
    public ResponseDto getResponse(@RequestParam String uniqueId) {
        return responseService.getResponse(uniqueId);
    }
}
