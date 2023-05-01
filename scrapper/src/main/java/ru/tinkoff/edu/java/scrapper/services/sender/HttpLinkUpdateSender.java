package ru.tinkoff.edu.java.scrapper.services.sender;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.clients.BotClient;
import ru.tinkoff.edu.java.scrapper.dtos.requests.LinkUpdateRequest;

@RequiredArgsConstructor
public class HttpLinkUpdateSender implements LinkUpdateSender {
    private final BotClient botClient;

    @Override
    public void send(LinkUpdateRequest linkUpdateRequest) {
        botClient.updateLink(linkUpdateRequest);
    }
}
