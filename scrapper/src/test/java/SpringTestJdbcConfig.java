import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import ru.tinkoff.edu.java.scrapper.repo.JdbcLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.JdbcChatRepo;

import javax.sql.DataSource;

@Configuration
public class SpringTestJdbcConfig {
    @Bean
    public DataSource postgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(IntegrationEnvironment.POSTGRE_SQL_CONTAINER.getJdbcUrl());
        dataSource.setUsername(IntegrationEnvironment.POSTGRE_SQL_CONTAINER.getUsername());
        dataSource.setPassword(IntegrationEnvironment.POSTGRE_SQL_CONTAINER.getPassword());
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(postgresDataSource());
    }

    @Bean
    public JdbcLinkRepo jdbcLinkRepo() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(postgresDataSource());
        return new JdbcLinkRepo(jdbcTemplate);
    }

    @Bean
    public JdbcChatRepo jdbcSubscriptionRepo() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(postgresDataSource());
        return new JdbcChatRepo(jdbcTemplate);
    }
}
