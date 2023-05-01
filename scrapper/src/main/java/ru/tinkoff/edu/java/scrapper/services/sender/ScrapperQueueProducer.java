package ru.tinkoff.edu.java.scrapper.services.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.edu.java.scrapper.dtos.requests.LinkUpdateRequest;

@RequiredArgsConstructor
public class ScrapperQueueProducer implements LinkUpdateSender {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    @Override
    public void send(LinkUpdateRequest linkUpdateRequest) {
        rabbitTemplate.convertAndSend(queue.getName(), linkUpdateRequest);
    }
}
