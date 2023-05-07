package ru.tinkoff.edu.java.scrapper.configurations.databases;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.repo.jdbc.JdbcChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.jdbc.JdbcChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.jdbc.JdbcLinkRepo;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration implements DatabaseAccessConfiguration {
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Bean
    public JdbcChatRepo chatRepo() {
        return new JdbcChatRepo(jdbcTemplate);
    }

    @Override
    @Bean
    public JdbcLinkRepo linkRepo() {
        return new JdbcLinkRepo(jdbcTemplate);
    }

    @Override
    @Bean
    public JdbcChatLinkRepo chatLinkRepo() {
        return new JdbcChatLinkRepo(jdbcTemplate);
    }
}
