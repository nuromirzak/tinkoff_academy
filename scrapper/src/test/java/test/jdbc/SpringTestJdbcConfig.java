package test.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.repo.ChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.jdbc.JdbcChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.jdbc.JdbcChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.jdbc.JdbcLinkRepo;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SpringTestJdbcConfig {
    private final DataSource dataSource;

    @Bean("jdbcLinkRepo")
    public LinkRepo jdbcLinkRepo() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new JdbcLinkRepo(jdbcTemplate);
    }

    @Bean("jdbcChatRepo")
    public ChatRepo jdbcChatRepo() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new JdbcChatRepo(jdbcTemplate);
    }

    @Bean("jdbcChatLinkRepo")
    public ChatLinkRepo jdbcChatLinkRepo() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new JdbcChatLinkRepo(jdbcTemplate);
    }
}