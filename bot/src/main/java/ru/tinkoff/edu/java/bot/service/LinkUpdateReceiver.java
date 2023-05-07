package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.bot.MyTelegramBot;
import ru.tinkoff.edu.java.bot.dtos.LinkUpdateRequest;

@RequiredArgsConstructor
public abstract class LinkUpdateReceiver {
    private final MyTelegramBot bot;

    public abstract void receiveUpdate(LinkUpdateRequest request);

    protected void sendUpdates(LinkUpdateRequest request) {
        bot.sendMailing(
            request.description(), request.tgChatIds()
        );
    }
}
