package ru.tinkoff.edu.java.scrapper.configurations.databases;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repo.ChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaLinkRepo;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JpaAccessConfiguration implements DatabaseAccessConfiguration {
    private final JpaChatRepo jpaChatRepo;
    private final JpaLinkRepo jpaLinkRepo;
    private final JpaChatLinkRepo jpaChatLinkRepo;

    @Override
    @Bean
    public ChatRepo chatRepo() {
        return jpaChatRepo;
    }

    @Override
    @Bean
    public LinkRepo linkRepo() {
        return jpaLinkRepo;
    }

    @Override
    public ChatLinkRepo chatLinkRepo() {
        return jpaChatLinkRepo;
    }
}
