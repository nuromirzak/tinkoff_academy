package ru.tinkoff.edu.java.scrapper.services.sender;

import ru.tinkoff.edu.java.scrapper.dtos.requests.LinkUpdateRequest;

public interface LinkUpdateSender {
    void send(LinkUpdateRequest linkUpdateRequest);
}
