package ru.tinkoff.edu.java.scrapper.services.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.configurations.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.JdbcChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.JdbcChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.JdbcLinkRepo;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepo jdbcLinkRepo;
    private final JdbcChatRepo jdbcChatRepo;
    private final JdbcChatLinkRepo jdbcChatLinkRepo;

    @Override
    public Link add(long tgChatId, String url) {
        Link link = new Link();
        link.setUrl(url);

        long linkId = jdbcLinkRepo.add(link);
        link.setLinkId(linkId);

        jdbcChatLinkRepo.addLinkToChat(tgChatId, linkId);

        return link;
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        Link link = new Link();
        link.setUrl(url);

        long linkId = jdbcLinkRepo.add(link);

        return jdbcChatLinkRepo.removeLinkFromChat(tgChatId, linkId);
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return jdbcLinkRepo.findLinksByChatId(tgChatId);
    }

    @Override
    public Collection<Link> findAll() {
        return jdbcLinkRepo.findAll();
    }

    @Override
    public List<Chat> findFollowers(String url) {
        return jdbcChatRepo.findChatsByLikeLink(url);
    }

    @Override
    public List<Link> findLinksToScrap(Duration checkInterval) {
        return jdbcLinkRepo.findLinksToScrap(checkInterval);
    }
}
