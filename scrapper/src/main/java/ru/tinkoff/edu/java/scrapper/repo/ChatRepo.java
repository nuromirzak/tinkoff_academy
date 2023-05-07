package ru.tinkoff.edu.java.scrapper.repo;

import java.util.List;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;

public interface ChatRepo {
    boolean add(long chatId);

    boolean remove(long chatId);

    List<Chat> findAll();

    int removeAll();

    List<Chat> findChatsByLikeLink(String url);
}
