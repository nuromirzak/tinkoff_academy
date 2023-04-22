package ru.tinkoff.edu.java.scrapper.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;

import java.util.List;

//@Repository
//@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaLinkRepo extends LinkRepo, JpaRepository<Link, Long> {
    @Override
    @Query(value = "INSERT INTO link (url, last_updated, json_props) VALUES (?1, ?2, ?3)", nativeQuery = true)
    @Modifying
    long add(Link link);

    @Override
    @Query(value = "SELECT * FROM link", nativeQuery = true)
    List<Link> findAll();

    @Override
    @Query(value = "DELETE FROM link WHERE url = ?1", nativeQuery = true)
    @Modifying
    boolean remove(String link);

    @Override
    @Query(value = "DELETE FROM link", nativeQuery = true)
    @Modifying
    int removeAll();
}
