package com.example.demo.service;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import static com.example.demo.config.ProcessVariableConstants.*;

/**
 * Start process. Set process variables: CITY, COUNTRY, UNIQUEID
 */
@Service
public class CamundaStartService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CamundaStartService.class);
    private final RuntimeService runtimeService;

    public CamundaStartService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public void startProcessByMessage(String city, String country, String uniqueID) {
        log.info("Starting process");
        runtimeService.createMessageCorrelation("StartProcessMessage").setVariable(CITY, city).setVariable(COUNTRY, country).setVariable(UNIQUEID, uniqueID).correlate();
    }
}
