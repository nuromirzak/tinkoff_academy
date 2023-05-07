package ru.tinkoff.edu.java.scrapper.services.impls;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

@Service("telegramChatService")
@RequiredArgsConstructor
public class TelegramChatServiceImpl implements TelegramChatService {
    private final LinkService linkService;
    private final TgChatService tgChatService;

    @Override
    public boolean registerChat(String id) {
        return tgChatService.register(Long.parseLong(id));
    }

    @Override
    public boolean deleteChat(String id) {
        return tgChatService.unregister(Long.parseLong(id));
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
        Optional<Link> link = linkService.add(Long.parseLong(chatId), addLinkRequest.link());

        if (link.isEmpty()) {
            throw new RuntimeException("Error while adding link");
        }

        Link savedLink = link.get();
        Long linkId = savedLink.getLinkId();
        String url = savedLink.getUrl();

        return new LinkResponse(linkId, url);
    }

    @Override
    public LinkResponse deleteLink(String chatId, RemoveLinkRequest addLinkRequest) {
        if (!linkService.remove(Long.parseLong(chatId), addLinkRequest.link())) {
            throw new RuntimeException("Error while deleting link");
        }

        return new LinkResponse(0, addLinkRequest.link());
    }
}
