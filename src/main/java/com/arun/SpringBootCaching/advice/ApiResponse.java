package com.arun.SpringBootCaching.advice;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {

    private T data;

    private ApiError apiError;

    private LocalDateTime timeStamp;

    public ApiResponse(){
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data){
        this();
        this.data=data;
    }

    public ApiResponse(ApiError apiError){
        this();
        this.apiError=apiError;
    }

    public ApiResponse(T data, ApiError apiError, LocalDateTime timeStamp){
        this.data=data;
        this.apiError=apiError;
        this.timeStamp=timeStamp;
    }

}
