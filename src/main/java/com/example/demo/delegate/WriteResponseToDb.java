package com.example.demo.delegate;

import com.example.demo.model.Response;
import com.example.demo.service.ResponseService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.demo.config.ProcessVariableConstants.RESPONSE;

@Component
public class WriteResponseToDb implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WriteResponseToDb.class);
    private final ResponseService responseService;

    public WriteResponseToDb(ResponseService responseService) {
        this.responseService = responseService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Task: Write response to db");
        Response response = (Response) execution.getVariable(RESPONSE);
        responseService.save(response);
    }
}
