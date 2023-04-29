package ru.tinkoff.edu.java.scrapper.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.requests.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.requests.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.responses.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.services.LinkService;
import ru.tinkoff.edu.java.scrapper.services.TelegramChatService;
import ru.tinkoff.edu.java.scrapper.services.TgChatService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("telegramChatService")
@RequiredArgsConstructor
public class TelegramChatServiceImpl implements TelegramChatService {
    private final LinkService linkService;
    private final TgChatService tgChatService;

    @Override
    public void registerChat(String id) {
        tgChatService.register(Long.parseLong(id));
    }

    @Override
    public boolean deleteChat(String id) {
        tgChatService.unregister(Long.parseLong(id));
        return true;
    }

    @Override
    public ListLinkResponse getLinks(String chatId) {
        Collection<Link> links = linkService.listAll(Long.parseLong(chatId));
        List<LinkResponse> linkResponses = links.stream()
                .map(link -> new LinkResponse(link.getLinkId(), link.getUrl()))
                .toList();
        return new ListLinkResponse(linkResponses, links.size());
    }

    @Override
    public LinkResponse addLink(String chatId, AddLinkRequest addLinkRequest) {
        Link link = linkService.add(Long.parseLong(chatId), addLinkRequest.link());

        Long linkId = Optional.ofNullable(link).map(Link::getLinkId).orElse(0L);
        String url = Optional.ofNullable(link).map(Link::getUrl).orElse("");

        return new LinkResponse(linkId, url);
    }

    @Override
    public LinkResponse deleteLink(String chatId, RemoveLinkRequest addLinkRequest) {
        linkService.remove(Long.parseLong(chatId), addLinkRequest.link());
        return new LinkResponse(0, addLinkRequest.link());
    }
}
