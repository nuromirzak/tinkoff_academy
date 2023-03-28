package ru.tinkoff.edu.java.bot.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BotConfig {
    private final String botName;
    private final String botToken;

    @Autowired
    public BotConfig(ApplicationConfig config) {
        this.botName = config.botUsername();
        this.botToken = config.botToken();
    }
}