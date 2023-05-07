package ru.tinkoff.edu.java.scrapper.configurations.senders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.clients.BotClient;
import ru.tinkoff.edu.java.scrapper.services.sender.HttpLinkUpdateSender;
import ru.tinkoff.edu.java.scrapper.services.sender.LinkUpdateSender;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
@RequiredArgsConstructor
public class HttpUpdateSenderConfiguration {
    private final BotClient botClient;

    @Bean
    public LinkUpdateSender linkUpdateSender() {
        return new HttpLinkUpdateSender(botClient);
    }
}
