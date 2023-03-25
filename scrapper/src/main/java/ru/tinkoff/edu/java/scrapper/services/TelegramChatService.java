package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dtos.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.RemoveLinkRequest;

public interface TelegramChatService {
    public void registerChat(String id);

    public boolean deleteChat(String id);

    public ListLinkResponse getLinks(String chatId);

    public void addLink(String chatId, AddLinkRequest addLinkRequest);

    public LinkResponse deleteLink(String chatId, RemoveLinkRequest addLinkRequest);
}
