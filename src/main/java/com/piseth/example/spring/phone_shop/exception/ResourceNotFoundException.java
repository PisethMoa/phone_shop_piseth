package com.piseth.example.spring.phone_shop.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {
    /*
    public ResourceNotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
    */
    public ResourceNotFoundException(String resourceName, Long id) {
        super(HttpStatus.NOT_FOUND, String.format("%s with id = %d not found.", resourceName, id));
    }
}
