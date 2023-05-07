package ru.tinkoff.edu.java.bot.clients;

import ru.tinkoff.edu.java.bot.dtos.ListLinkResponse;

public interface ScrapperClient {
    boolean registerChat(String chatId);

    boolean removeChat(String chatId);

    ListLinkResponse getTrackedLinks(String chatId);

    Long addLinkToTrack(String chatId, String link);

    Long removeLinkFromTrack(String chatId, String link);
}
