package ru.tinkoff.edu.java.scrapper.services.impls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dtos.requests.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.requests.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.responses.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.services.TelegramChatService;

@Service("telegramChatServiceStub")
public class TelegramChatServiceStub implements TelegramChatService {
    private final Map<String, List<String>> chatLinks =
        new HashMap<>();

    @Override
    public void registerChat(String id) {
        chatLinks.putIfAbsent(id, new ArrayList<>());
    }

    @Override
    public boolean deleteChat(String id) {
        return chatLinks.remove(id) != null;
    }

    @Override
    public ListLinkResponse getLinks(String chatId) {
        List<String> links = chatLinks.getOrDefault(chatId, Collections.emptyList());

        List<LinkResponse> linkResponses = links.stream()
            .map(link -> new LinkResponse(links.indexOf(link), link))
            .collect(Collectors.toList());

        return new ListLinkResponse(linkResponses, links.size());
    }

    @Override
    public LinkResponse addLink(String chatId, AddLinkRequest addLinkRequest) {
        List<String> links = chatLinks.get(chatId);
        String link = addLinkRequest.link();
        if (links == null || links.contains(link)) {
            return null;
        }
        links.add(link);
        return new LinkResponse(links.size() - 1, link);
    }

    @Override
    public LinkResponse deleteLink(String chatId, RemoveLinkRequest addLinkRequest) {
        List<String> links = chatLinks.get(chatId);
        String link = addLinkRequest.link();
        if (links == null || !links.contains(link)) {
            return null;
        }
        int index = links.indexOf(link);
        links.remove(link);
        return new LinkResponse(index, link);
    }
}
