package ru.tinkoff.edu.java.scrapper.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dtos.requests.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class BotClient {
    private final WebClient botWebClient;

    public void updateLink(LinkUpdateRequest linkUpdateRequest) {
        botWebClient.post()
            .uri("/updates")
            .bodyValue(linkUpdateRequest)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
