package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dtos.requests.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.requests.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.responses.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.ListLinkResponse;

public interface TelegramChatService {
    void registerChat(String id);

    boolean deleteChat(String id);

    ListLinkResponse getLinks(String chatId);

    LinkResponse addLink(String chatId, AddLinkRequest addLinkRequest);

    LinkResponse deleteLink(String chatId, RemoveLinkRequest addLinkRequest);
}
