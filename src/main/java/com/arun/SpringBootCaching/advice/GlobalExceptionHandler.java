package com.arun.SpringBootCaching.advice;

import com.arun.SpringBootCaching.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException e){

        ApiError apiError = ApiError
                .builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return helperHandler(apiError);

    }

    private ResponseEntity<ApiResponse<?>> helperHandler(ApiError apiError) {

        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }

}
