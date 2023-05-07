package ru.tinkoff.edu.java.scrapper.repo;

import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import java.util.List;

public interface ChatRepo {
    boolean add(long chatId);

    boolean remove(long chatId);

    List<Chat> findAll();

    int removeAll();

    List<Chat> findChatsByLikeLink(String url);
}
