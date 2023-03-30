package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Value("${app.scrapper_url:http://localhost:8081}")
    private String scrapperUrl;

    @Bean("scrapper_client")
    public WebClient scrapperClient() {
        return WebClient.builder()
                .baseUrl(scrapperUrl)
                .build();
    }
}
