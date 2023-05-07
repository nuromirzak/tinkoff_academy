package ru.tinkoff.edu.java.bot.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.MyTelegramBot;
import ru.tinkoff.edu.java.bot.dtos.LinkUpdateRequest;

@RabbitListener(queues = "${app.queue-name}")
@Log4j2
public class RabbitLinkUpdateReceiver extends LinkUpdateReceiver {
    public RabbitLinkUpdateReceiver(MyTelegramBot bot) {
        super(bot);
    }

    @RabbitHandler
    @Override
    public void receiveUpdate(LinkUpdateRequest request) {
        this.sendUpdates(request);
    }

    @RabbitListener(queues = "${app.queue-name}.dlq")
    public void processFailedMessages(Message failedMessage) {
        log.error("Error while receiving update: " + failedMessage);
    }
}
