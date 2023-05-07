package ru.tinkoff.edu.java.scrapper.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.link_parser.parsers.GlobalLinkParser;

@Configuration
public class ClientConfiguration {
    @Value("${github.base-url:https://api.github.com}")
    private String githubBaseUrl;
    @Value("${stackoverflow.base-url:https://api.stackexchange.com/2.3}")
    private String stackoverflowBaseUrl;
    @Value("${bot.base-url:http://localhost:8080}")
    private String botBaseUrl;

    @Bean("githubWebClient")
    public WebClient githubClient() {
        return WebClient.builder()
            .baseUrl(githubBaseUrl)
            .build();
    }

    @Bean("stackoverflowWebClient")
    public WebClient stackoverflowClient() {
        return WebClient.builder()
            .baseUrl(stackoverflowBaseUrl)
            .build();
    }

    @Bean("botWebClient")
    public WebClient botClient() {
        return WebClient.builder()
            .baseUrl(botBaseUrl)
            .build();
    }

    @Bean
    public GlobalLinkParser globalLinkParser() {
        return new GlobalLinkParser();
    }

    @Bean
    public long schedulerIntervalMs(ApplicationConfiguration config) {
        long toMillis = config.scheduler().interval().toMillis();
        return toMillis;
    }
}
