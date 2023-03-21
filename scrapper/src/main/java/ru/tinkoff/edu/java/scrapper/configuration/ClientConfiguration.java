package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Value("${github.base-url}")
    private String githubBaseUrl;
    @Value("${stackoverflow.base-url}")
    private String stackoverflowBaseUrl;

    @Bean("github_client")
    public WebClient githubClient() {
        return WebClient.builder()
                .baseUrl(githubBaseUrl)
                .build();
    }

    @Bean("stackoverflow_client")
    public WebClient stackoverflowClient() {
        return WebClient.builder()
                .baseUrl(stackoverflowBaseUrl)
                .build();
    }
}
