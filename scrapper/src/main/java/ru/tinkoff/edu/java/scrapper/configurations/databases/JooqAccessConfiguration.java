package ru.tinkoff.edu.java.scrapper.configurations.databases;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import ru.tinkoff.edu.java.scrapper.repo.jooq.JooqChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.jooq.JooqChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.jooq.JooqLinkRepo;
import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqAccessConfiguration implements DatabaseAccessConfiguration {
    private final DataSource dataSource;

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public DSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(connectionProvider());
        config.set(SQLDialect.POSTGRES);
        config.set(new Settings()
                .withRenderNameCase(RenderNameCase.LOWER));
        config.set(new DefaultExecuteListenerProvider(
                new JooqExceptionTranslator()));
        return config;
    }

    @Override
    @Bean
    public JooqChatRepo chatRepo() {
        return new JooqChatRepo(dsl());
    }

    @Override
    @Bean
    public JooqLinkRepo linkRepo() {
        return new JooqLinkRepo(dsl());
    }

    @Override
    @Bean
    public JooqChatLinkRepo chatLinkRepo() {
        return new JooqChatLinkRepo(dsl());
    }
}
