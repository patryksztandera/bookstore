package com.bookstore.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final ZonedDateTime time;

    protected ApiException(final String message, final HttpStatus status) {
        super(message);
        this.time = ZonedDateTime.now();
        this.status = status;
    }

    public ApiException() {
        super("Internal Server Error");
        this.time = ZonedDateTime.now();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
