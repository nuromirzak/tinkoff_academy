package ru.tinkoff.edu.java.scrapper.configurations.databases;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaChatRepo;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JpaAccessConfiguration implements DatabaseAccessConfiguration {
    private final EntityManager entityManager;

    @Override
    public ChatRepo chatRepo() {
        RepositoryFactorySupport factory = new JpaRepositoryFactory(entityManager);
        JpaChatRepo repo = factory.getRepository(JpaChatRepo.class);
        return repo;
    }

    @Override
    public LinkRepo linkRepo() {
        RepositoryFactorySupport factory = new JpaRepositoryFactory(entityManager);
        LinkRepo repo = factory.getRepository(LinkRepo.class);
        return repo;
    }
}
