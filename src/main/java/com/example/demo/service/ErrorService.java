package com.example.demo.service;

import com.example.demo.model.ErrorFetching;
import com.example.demo.repository.ErrorRepository;
import org.springframework.stereotype.Service;

@Service
public class ErrorService {

    private final ErrorRepository errorRepository;

    public ErrorService(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    public void save (ErrorFetching errorFetching) {
        errorRepository.saveAndFlush(errorFetching);
    }
}
