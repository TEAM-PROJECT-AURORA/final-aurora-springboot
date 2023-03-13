package com.root34.aurora.exception.dto;

import org.springframework.http.HttpStatus;


public class ApiExceptionDto {
    private int state;
    private String message;

    public ApiExceptionDto(HttpStatus state, String message){
        this.state = state.value();
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiExceptionDto{" +
                "state=" + state +
                ", message='" + message + '\'' +
                '}';
    }
}