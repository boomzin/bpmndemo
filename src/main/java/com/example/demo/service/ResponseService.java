package com.example.demo.service;

import com.example.demo.dto.ErrorResponseDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.TempResponseDto;
import com.example.demo.model.ErrorFetching;
import com.example.demo.model.Response;
import com.example.demo.repository.ErrorRepository;
import com.example.demo.repository.ResponseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final ErrorRepository errorRepository;

    public ResponseService(ResponseRepository responseRepository, ErrorRepository errorRepository) {
        this.responseRepository = responseRepository;
        this.errorRepository = errorRepository;
    }

    public void save (Response response) {
        responseRepository.saveAndFlush(response);
    }

    /**
     * Search response in db. If response doesn`t exist then search error information in db. If such information doesn`t exist create instance of ErrorResponseDto.class with message "Error, reason unknown"
     */
    public ResponseDto getResponse(String uniqueId) {
        Optional<Response> response = responseRepository.getResponseByUniqueId(uniqueId);
        if (response.isPresent()) {
            return new TempResponseDto(response.get().getCurrentTemp(), response.get().getForecastedMinTemp());
        } else {
            Optional<ErrorFetching> errorFetching = errorRepository.getErrorByUniqueId(uniqueId);
            if (errorFetching.isPresent()) {
                return new ErrorResponseDto(errorFetching.get().getMessage());
            }
        }
        return new ErrorResponseDto("Error, reason unknown");
    }
}
