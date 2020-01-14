package com.friday.addressparser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 2937277404217615493L;

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}

