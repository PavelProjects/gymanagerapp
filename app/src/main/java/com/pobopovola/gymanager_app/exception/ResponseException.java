package com.pobopovola.gymanager_app.exception;

import org.springframework.http.HttpStatus;

public class ResponseException extends RuntimeException{
    private final HttpStatus status;

    public ResponseException(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
