package ru.tinkoff.edu.java.bot.configuration.receiver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.MyTelegramBot;
import ru.tinkoff.edu.java.bot.service.HttpLinkUpdateReceiver;
import ru.tinkoff.edu.java.bot.service.LinkUpdateReceiver;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpLinkUpdateReceiverConfig {
    @Bean
    public LinkUpdateReceiver linkUpdateReceiver(
        MyTelegramBot bot
    ) {
        return new HttpLinkUpdateReceiver(bot);
    }
}
