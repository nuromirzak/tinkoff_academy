package ru.tinkoff.edu.java.bot.clients;

import ru.tinkoff.edu.java.bot.dtos.ListLinkResponse;

public interface ScrapperClient {
    public boolean registerChat(String chatId);

    public boolean removeChat(String chatId);

    public ListLinkResponse getTrackedLinks(String chatId);

    public Long addLinkToTrack(String chatId, String link);

    public Long removeLinkFromTrack(String chatId, String link);
}
