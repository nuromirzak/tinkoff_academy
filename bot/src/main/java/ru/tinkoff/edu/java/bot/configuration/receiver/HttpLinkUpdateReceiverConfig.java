package ru.tinkoff.edu.java.bot.configuration.receiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.MyTelegramBot;
import ru.tinkoff.edu.java.bot.service.HttpLinkUpdateReceiver;

@Configuration
public class HttpLinkUpdateReceiverConfig {
    @Bean
    public HttpLinkUpdateReceiver httpLinkUpdateReceiver(
        MyTelegramBot bot
    ) {
        return new HttpLinkUpdateReceiver(bot);
    }
}
