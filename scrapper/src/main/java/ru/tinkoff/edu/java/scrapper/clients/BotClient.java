package ru.tinkoff.edu.java.scrapper.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dtos.requests.LinkUpdateRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BotClient {
    private final WebClient botWebClient;

    public void updateLink(Long id, String url, String description, List<Long> tgChatIds) {
        botWebClient.post()
                .uri("/updates")
                .bodyValue(new LinkUpdateRequest(id, url, description, tgChatIds))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
