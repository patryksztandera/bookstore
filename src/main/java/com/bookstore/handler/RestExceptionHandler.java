package com.bookstore.handler;

import com.bookstore.exceptions.ApiException;
import com.bookstore.exceptions.BadRequestException;
import com.bookstore.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({BadRequestException.class, NotFoundException.class})
    public ResponseEntity<ErrorResponse> handle(ApiException e, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getTime(),
                e.getStatus().value(),
                e.getStatus().getReasonPhrase(),
                e.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }
}
