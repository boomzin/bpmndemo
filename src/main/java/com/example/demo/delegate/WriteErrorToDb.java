package com.example.demo.delegate;

import com.example.demo.model.ErrorFetching;
import com.example.demo.service.ErrorService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.demo.config.ProcessVariableConstants.ERROR_FETCHING_MESSAGE;
import static com.example.demo.config.ProcessVariableConstants.UNIQUEID;

@Component
public class WriteErrorToDb implements JavaDelegate {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WriteErrorToDb.class);
    private final ErrorService errorService;

    public WriteErrorToDb(ErrorService errorService) {
        this.errorService = errorService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Task: Write error to db");
        String errorMessage = (String) execution.getVariable(ERROR_FETCHING_MESSAGE);
        String uniqueId = (String) execution.getVariable(UNIQUEID);
        ErrorFetching errorFetching = new ErrorFetching(uniqueId, errorMessage);
        errorService.save(errorFetching);
    }
}
