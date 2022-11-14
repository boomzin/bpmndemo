package com.example.demo.web;

import com.example.demo.service.CamundaStartService;
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

    public ForecastController(CamundaStartService camundaStartService) {
        this.camundaStartService = camundaStartService;
    }

    @GetMapping(value = "temperature")
    public String getTemp(@RequestParam String city, @RequestParam String country) {
        String uniqueID = UUID.randomUUID().toString();
        camundaStartService.startProcessByMessage(city, country, uniqueID);
        return uniqueID;
    }
}
