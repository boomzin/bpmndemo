package com.example.demo.service;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import static com.example.demo.config.ProcessVariableConstants.*;

@Service
public class CamundaStartService {
    private final RuntimeService runtimeService;

    public CamundaStartService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public void startProcessByMessage(String city, String country, String uniqueID) {
        runtimeService.createMessageCorrelation("StartProcessMessage").setVariable(CITY, city).setVariable(COUNTRY, country).setVariable(UNIQUEID, uniqueID).correlate();
    }
}
