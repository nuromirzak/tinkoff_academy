package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dtos.requests.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.responses.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.requests.RemoveLinkRequest;

public interface TelegramChatService {
    public void registerChat(String id);

    public boolean deleteChat(String id);

    public ListLinkResponse getLinks(String chatId);

    public LinkResponse addLink(String chatId, AddLinkRequest addLinkRequest);

    public LinkResponse deleteLink(String chatId, RemoveLinkRequest addLinkRequest);
}
