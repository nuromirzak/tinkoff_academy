package ru.tinkoff.edu.java.bot.configuration.receiver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.MyTelegramBot;
import ru.tinkoff.edu.java.bot.service.LinkUpdateReceiver;
import ru.tinkoff.edu.java.bot.service.RabbitLinkUpdateReceiver;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class QueueLinkUpdateReceiverConfig {
    @Bean
    public LinkUpdateReceiver linkUpdateReceiver(
            MyTelegramBot bot
    ) {
        return new RabbitLinkUpdateReceiver(bot);
    }
}
