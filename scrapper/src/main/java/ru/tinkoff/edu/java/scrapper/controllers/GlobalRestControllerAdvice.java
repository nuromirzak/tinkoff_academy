package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;
import ru.tinkoff.edu.java.scrapper.dtos.responses.ApiErrorResponse;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e, "Resource you are looking for is not found");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e, "Cannot parse request body");
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, Exception e, String description) {
        List<String> stacktrace = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .toList();
        return ResponseEntity.status(status).body(new ApiErrorResponse(
                description,
                String.valueOf(status.value()),
                e.getClass().getName(),
                e.getMessage(),
                stacktrace
        ));
    }
}
