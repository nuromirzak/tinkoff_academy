package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dtos.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.RemoveLinkRequest;

import java.util.*;

@Service
public class TelegramChatServiceStub implements TelegramChatService {
    private final Map<String, List<String>> chatLinks =
            new HashMap<>();

    @Override
    public void registerChat(String id) {
        if (chatLinks.containsKey(id)) {
            return;
        }
        chatLinks.put(id, new ArrayList<>());
    }

    @Override
    public boolean deleteChat(String id) {
        if (!chatLinks.containsKey(id)) {
            return false;
        }
        chatLinks.remove(id);
        return true;
    }

    @Override
    public ListLinkResponse getLinks(String chatId) {
        List<String> links = chatLinks.get(chatId);

        if (links == null) {
            return new ListLinkResponse(Collections.emptyList(), 0);
        }

        List<LinkResponse> linkResponses = new ArrayList<>();
        for (int i = 0; i < links.size(); i++) {
            linkResponses.add(new LinkResponse(i, links.get(i)));
        }
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
