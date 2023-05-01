package ru.tinkoff.edu.java.scrapper.configurations.senders;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.services.sender.LinkUpdateSender;
import ru.tinkoff.edu.java.scrapper.services.sender.ScrapperQueueProducer;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
@RequiredArgsConstructor
public class RabbitUpdateSenderConfiguration {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    @Bean
    public LinkUpdateSender linkUpdateSender() {
        return new ScrapperQueueProducer(rabbitTemplate, queue);
    }
}
