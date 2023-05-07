package ru.tinkoff.edu.java.scrapper.repo;

import java.time.Duration;
import java.util.List;
import ru.tinkoff.edu.java.scrapper.dtos.Link;

public interface LinkRepo {
    int add(Link link);

    List<Link> findAll();

    boolean remove(String link);

    List<Link> findLinksByChatId(long chatId);

    int removeAll();

    List<Link> findLinksToScrap(Duration duration);
}
