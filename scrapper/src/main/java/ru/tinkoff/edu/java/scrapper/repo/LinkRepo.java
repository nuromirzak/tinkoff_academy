package ru.tinkoff.edu.java.scrapper.repo;

import ru.tinkoff.edu.java.scrapper.dtos.Link;

import java.util.List;

public interface LinkRepo {
    int add(Link link);

    List<Link> findAll();

    boolean remove(String link);

    int removeAll();
}
