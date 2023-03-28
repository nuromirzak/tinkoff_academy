package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dtos.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.RemoveLinkRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
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
    public void addLink(String chatId, AddLinkRequest addLinkRequest) {
        List<String> links = chatLinks.getOrDefault(chatId, new ArrayList<>());
        String link = addLinkRequest.link();
        if (!links.contains(link)) {
            links.add(link);
            chatLinks.put(chatId, links);
        }
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
