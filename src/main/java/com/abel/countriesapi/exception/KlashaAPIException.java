package com.abel.countriesapi.exception;

import org.springframework.http.HttpStatus;

public class KlashaAPIException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public KlashaAPIException(HttpStatus badRequest, String invalidJwtSignature) {

        this.message = invalidJwtSignature;
        this.status = badRequest;
    }

    public KlashaAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
