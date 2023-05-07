package ru.tinkoff.edu.java.bot.service;

import ru.tinkoff.edu.java.bot.MyTelegramBot;
import ru.tinkoff.edu.java.bot.dtos.LinkUpdateRequest;

public class HttpLinkUpdateReceiver extends LinkUpdateReceiver {
    public HttpLinkUpdateReceiver(MyTelegramBot bot) {
        super(bot);
    }

    @Override
    public void receiveUpdate(LinkUpdateRequest request) {
        this.sendUpdates(request);
    }
}
