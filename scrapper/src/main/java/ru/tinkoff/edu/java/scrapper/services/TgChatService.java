package ru.tinkoff.edu.java.scrapper.services;

public interface TgChatService {
    boolean register(long tgChatId);

    boolean unregister(long tgChatId);
}
