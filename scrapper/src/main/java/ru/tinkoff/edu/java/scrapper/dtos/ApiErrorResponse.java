package ru.tinkoff.edu.java.scrapper.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ApiErrorResponse(
        String description,
        @JsonProperty(required = true)
        String code,
        @JsonProperty(required = true)
        String exceptionName,
        @JsonProperty(required = true)
        String exceptionMessage,
        List<String> stacktrace
) {
}
