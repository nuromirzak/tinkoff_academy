package ru.tinkoff.edu.java.scrapper.services;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;

public interface LinkService {
    Optional<Link> add(long tgChatId, String url);

    boolean remove(long tgChatId, String url);

    Collection<Link> listAll(long tgChatId);

    Collection<Link> findAll();

    List<Chat> findFollowers(String url);

    List<Link> findLinksToScrapSince(Duration checkInterval);
}
