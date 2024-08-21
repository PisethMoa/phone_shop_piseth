package com.piseth.example.spring.phone_shop.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handling custom API exceptions
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException apiException) {
        ErrorResponse errorResponse = new ErrorResponse(apiException.getHttpStatus(), apiException.getMessage());
        return ResponseEntity
                .status(apiException.getHttpStatus())
                .body(errorResponse);
    }
}
