package ru.tinkoff.edu.java.scrapper.services.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.JdbcChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.JdbcLinkRepo;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinkRepo jdbcLinkRepo;
    private final JdbcChatRepo jdbcChatRepo;

    @Override
    public Link add(long tgChatId, String url) {
        Link link = new Link();
        link.setUrl(url);

        long linkId = jdbcLinkRepo.add(link);
        link.setLinkId(linkId);

        jdbcChatRepo.addLinkToChat(tgChatId, linkId);

        return link;
    }

    @Override
    public boolean remove(long tgChatId, String url) {
        Link link = new Link();
        link.setUrl(url);

        long linkId = jdbcLinkRepo.add(link);

        return jdbcChatRepo.removeLinkFromChat(tgChatId, linkId);
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return jdbcChatRepo.findLinksByChatId(tgChatId);
    }
}
