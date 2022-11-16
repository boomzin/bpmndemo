package com.example.demo.service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.model.Response;
import com.example.demo.repository.ResponseRepository;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;

    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    public void save (Response response) {
        responseRepository.saveAndFlush(response);
    }

    public ResponseDto getResponse(String uniqueId) {
        Response response = responseRepository.getResponseByUniqueId(uniqueId).get();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setCurrentTemperature(response.getCurrentTemp());
        responseDto.setForecastedMinTemp(response.getForecastedMinTemp());
        return responseDto;
    }
}
