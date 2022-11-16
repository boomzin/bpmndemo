package com.example.demo.dto;

public class ErrorResponseDto implements ResponseDto {
    private String errorMessage;

    public ErrorResponseDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
