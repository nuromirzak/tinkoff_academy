package test;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSourceConfig {
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
}
