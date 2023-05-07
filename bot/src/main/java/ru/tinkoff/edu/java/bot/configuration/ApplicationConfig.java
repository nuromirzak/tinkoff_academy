package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull String botToken,
                                @NotNull String botUsername,
                                @NotNull Boolean useQueue,
                                @NotNull String queueName,
                                @NotNull String exchangeName) {
}