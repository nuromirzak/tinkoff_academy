package ru.tinkoff.edu.java.scrapper.repo;

import ru.tinkoff.edu.java.scrapper.dtos.Link;

import java.util.List;

public interface LinkRepo {
    long add(Link link);

    List<Link> findAll();

    boolean remove(String link);

    List<Link> findLinksByChatId(long chatId);

    int removeAll();
}
