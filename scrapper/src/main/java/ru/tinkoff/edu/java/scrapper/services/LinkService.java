package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dtos.Link;

import java.util.Collection;

public interface LinkService {
    Link add(long tgChatId, String url);
    boolean remove(long tgChatId, String url);
    Collection<Link> listAll(long tgChatId);
}
