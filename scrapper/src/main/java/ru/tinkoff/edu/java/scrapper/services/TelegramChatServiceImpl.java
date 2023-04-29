package ru.tinkoff.edu.java.scrapper.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.requests.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.requests.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.responses.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.repo.JdbcChatRepo;
import ru.tinkoff.edu.java.scrapper.services.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.services.jdbc.JdbcTgChatService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service("telegramChatService")
@RequiredArgsConstructor
public class TelegramChatServiceImpl implements TelegramChatService {
    private final JdbcLinkService jdbcLinkService;
    private final JdbcTgChatService jdbcTgChatService;

    @Override
    public void registerChat(String id) {
        jdbcTgChatService.register(Long.parseLong(id));
    }

    @Override
    public boolean deleteChat(String id) {
        jdbcTgChatService.unregister(Long.parseLong(id));
        return true;
    }

    @Override
    public ListLinkResponse getLinks(String chatId) {
        Collection<Link> links = jdbcLinkService.listAll(Long.parseLong(chatId));
        List<LinkResponse> linkResponses = links.stream()
                .map(link -> new LinkResponse(link.getLinkId(), link.getUrl()))
                .toList();
        return new ListLinkResponse(linkResponses, links.size());
    }

    @Override
    public LinkResponse addLink(String chatId, AddLinkRequest addLinkRequest) {
        Link link = jdbcLinkService.add(Long.parseLong(chatId), addLinkRequest.link());
        return new LinkResponse(link.getLinkId(), link.getUrl());
    }

    @Override
    public LinkResponse deleteLink(String chatId, RemoveLinkRequest addLinkRequest) {
        jdbcLinkService.remove(Long.parseLong(chatId), addLinkRequest.link());
        return new LinkResponse(0, addLinkRequest.link());
    }
}
