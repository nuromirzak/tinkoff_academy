package ru.tinkoff.edu.java.scrapper.repo;

public interface ChatLinkRepo {
    boolean addLinkToChat(long chatId, long linkId);

    boolean removeLinkFromChat(long chatId, long linkId);
}
