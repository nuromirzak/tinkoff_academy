package ru.tinkoff.edu.java.bot.configuration.receiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.MyTelegramBot;
import ru.tinkoff.edu.java.bot.service.RabbitLinkUpdateReceiver;

@Configuration
public class QueueLinkUpdateReceiverConfig {
    @Bean
    public RabbitLinkUpdateReceiver rabbitLinkUpdateReceiver(
        MyTelegramBot bot
    ) {
        return new RabbitLinkUpdateReceiver(bot);
    }
}
