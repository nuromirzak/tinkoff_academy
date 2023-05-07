package ru.tinkoff.edu.java.scrapper.repo;

import java.time.OffsetDateTime;
import java.util.List;
import ru.tinkoff.edu.java.scrapper.dtos.Link;

public interface LinkRepo {
    Link saveIfAbsentOrReturnExisting(Link link);

    List<Link> findAll();

    boolean removeLinkByUrlLike(String link);

    List<Link> findLinksByChatId(long chatId);

    int removeAll();

    List<Link> findLinksByLastScrappedBefore(OffsetDateTime lastScrapped);
}
