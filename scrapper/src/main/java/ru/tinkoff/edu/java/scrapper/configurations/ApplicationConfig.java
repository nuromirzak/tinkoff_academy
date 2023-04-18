package ru.tinkoff.edu.java.scrapper.configurations;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull Scheduler scheduler,
                                @NotNull String dbUrl,
                                @NotNull String dbUsername,
                                @NotNull String dbPassword) {
}