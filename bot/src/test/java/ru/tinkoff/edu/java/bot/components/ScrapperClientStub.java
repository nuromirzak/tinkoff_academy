package ru.tinkoff.edu.java.bot.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.dtos.LinkResponse;
import ru.tinkoff.edu.java.bot.dtos.ListLinkResponse;

public class ScrapperClientStub implements ScrapperClient {
    private final Map<String, List<String>> chatLinks =
        new HashMap<>();

    @Override
    public boolean registerChat(String chatId) {
        chatLinks.putIfAbsent(chatId, new ArrayList<>());
        return true;
    }

    @Override
    public boolean removeChat(String chatId) {
        return chatLinks.remove(chatId) != null;
    }

    @Override
    public ListLinkResponse getTrackedLinks(String chatId) {
        List<String> links = chatLinks.getOrDefault(chatId, Collections.emptyList());

        List<LinkResponse> linkResponses = links.stream()
            .map(link -> new LinkResponse(links.indexOf(link), link))
            .collect(Collectors.toList());

        return new ListLinkResponse(linkResponses, links.size());
    }

    @Override
    public Long addLinkToTrack(String chatId, String link) {
        List<String> links = chatLinks.get(chatId);
        if (links == null || links.contains(link)) {
            return null;
        }
        links.add(link);
        return (long) links.size() - 1;
    }

    @Override
    public Long removeLinkFromTrack(String chatId, String link) {
        List<String> links = chatLinks.get(chatId);
        if (links == null || !links.contains(link)) {
            return null;
        }
        int index = links.indexOf(link);
        links.remove(link);
        return (long) index;
    }
}
