package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;
import ru.tinkoff.edu.java.scrapper.dtos.ApiErrorResponse;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException e) {
        String description = "Resource you are looking for is not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
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
