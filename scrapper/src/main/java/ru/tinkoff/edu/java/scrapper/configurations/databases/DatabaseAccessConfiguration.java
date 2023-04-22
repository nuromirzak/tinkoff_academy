package ru.tinkoff.edu.java.scrapper.configurations.databases;

import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;

public interface DatabaseAccessConfiguration {
    public ChatRepo chatRepo();

    public LinkRepo linkRepo();
}
