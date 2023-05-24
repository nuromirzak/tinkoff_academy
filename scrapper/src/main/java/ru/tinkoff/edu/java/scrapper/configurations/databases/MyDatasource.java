package ru.tinkoff.edu.java.scrapper.configurations.databases;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import ru.tinkoff.edu.java.scrapper.configurations.ApplicationConfiguration;

@Configuration
@RequiredArgsConstructor
public class MyDatasource {
    private final ApplicationConfiguration applicationConfiguration;

    @Bean
    public DataSource postgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(applicationConfiguration.dbUrl());
        dataSource.setUsername(applicationConfiguration.dbUsername());
        dataSource.setPassword(applicationConfiguration.dbPassword());
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
