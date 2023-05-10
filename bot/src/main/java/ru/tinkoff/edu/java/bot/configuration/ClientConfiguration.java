package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    private final String scrapperUrl;

    public ClientConfiguration(ApplicationConfig applicationConfig) {
        this.scrapperUrl = applicationConfig.scrapperUrl();
    }

    @Bean("scrapper_client")
    public WebClient scrapperClient() {
        return WebClient.builder()
            .baseUrl(scrapperUrl)
            .build();
    }
}
