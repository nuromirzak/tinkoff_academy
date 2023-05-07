package ru.tinkoff.edu.java.scrapper.configurations.databases;

import ru.tinkoff.edu.java.scrapper.repo.ChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;

public interface DatabaseAccessConfiguration {
    ChatRepo chatRepo();

    LinkRepo linkRepo();

    ChatLinkRepo chatLinkRepo();
}
