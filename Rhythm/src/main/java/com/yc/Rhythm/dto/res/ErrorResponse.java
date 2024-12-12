package com.yc.Rhythm.dto.res;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse {
    
    private String code;

    private String status;

    private String message;

    private List<String> parameters = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime horodatage;

    public ErrorResponse() {
        horodatage = LocalDateTime.now();
    }

    public ErrorResponse(String code, HttpStatus status, String message) {
        this();
        this.status = status.toString();
        this.code = code;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
