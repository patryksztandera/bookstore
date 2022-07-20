package com.bookstore.handler;

import java.time.ZonedDateTime;

public class ErrorResponse {

    private final ZonedDateTime timestamp;

    private final Integer status;

    private final String error;

    private final String message;

    private final String path;

    ErrorResponse(ZonedDateTime timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
