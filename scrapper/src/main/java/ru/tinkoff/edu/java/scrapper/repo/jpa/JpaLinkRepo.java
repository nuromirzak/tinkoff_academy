package ru.tinkoff.edu.java.scrapper.repo.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.responses.LinkProperties;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaLinkRepo extends LinkRepo, JpaRepository<Link, Long> {
    @Override
    @Query(value = "INSERT INTO link (url, last_updated, json_props) VALUES (?1, ?2, ?3)", nativeQuery = true)
    @Modifying
    default int add(Link link) {
        OffsetDateTime lastUpdated = link.getLastUpdated() == null ? OffsetDateTime.now() : link.getLastUpdated();
        return addAndReturnInt(link.getUrl(), lastUpdated, link.getJsonProps());
    }

    @Modifying
    @Query(value = "INSERT INTO link (url, last_updated, json_props) VALUES (?1, ?2, ?3)", nativeQuery = true)
    int addAndReturnInt(String url, OffsetDateTime lastUpdated, LinkProperties jsonProps);

    @Override
    @Query(value = "SELECT * FROM link", nativeQuery = true)
    List<Link> findAll();

    @Override
    default boolean remove(String link) {
        return removeAndReturnInt(link) == 1;
    }

    @Query(value = "DELETE FROM link WHERE url = ?1", nativeQuery = true)
    @Modifying
    int removeAndReturnInt(String link);

    @Override
    @Query(value = "DELETE FROM link", nativeQuery = true)
    @Modifying
    int removeAll();
}
