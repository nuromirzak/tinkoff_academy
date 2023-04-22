package ru.tinkoff.edu.java.scrapper.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;

import java.util.List;

@Repository
public interface JpaLinkRepo extends LinkRepo, JpaRepository<Link, Long> {
    @Override
    @Query(value = "INSERT INTO link (url, last_updated, json_props) VALUES (?1, ?2, ?3)", nativeQuery = true)
    long add(Link link);

    @Override
    @Query(value = "SELECT * FROM link WHERE url = ?1", nativeQuery = true)
    List<Link> findAll();

    @Override
    @Query(value = "DELETE FROM link WHERE url = ?1", nativeQuery = true)
    boolean remove(String link);

    @Override
    @Query(value = "DELETE FROM link", nativeQuery = true)
    int removeAll();
}
