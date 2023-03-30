package ru.tinkoff.edu.java.bot.clients;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dtos.ListLinkResponse;

import java.util.Collections;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class ScrapperClient {
    private final WebClient webClient;
    private static final String CHAT_ID_HEADER = "Tg-Chat-Id";

    // Всегда должен возвращает true, даже если чат уже зарегистрирован
    // Возвращает false, если произошла ошибка на сервере
    public boolean registerChat(String chatId) {
        return Boolean.TRUE.equals(webClient.post()
                .uri("/tg-chat/{chatId}", chatId)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .onErrorResume(throwable -> {
                    log.error("Error while registering chat", throwable);
                    return Mono.just(false);
                })
                .block());
    }

    // Возвращает true, если чат был удален успешно
    // Возвращает false, если чат не был найден или произошла ошибка на сервере
    public boolean removeChat(String chatId) {
        return Boolean.TRUE.equals(webClient.delete()
                .uri("/tg-chat/{chatId}", chatId)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .onErrorResume(throwable -> {
                    log.error("Error while registering chat", throwable);
                    return Mono.just(false);
                })
                .block());
    }

    // Всегда должен возвращать успешный ответ
    public ListLinkResponse getTrackedLinks(String chatId) {
        return webClient.get()
                .uri("/links")
                .header(CHAT_ID_HEADER, chatId)
                .retrieve()
                .bodyToMono(ListLinkResponse.class)
                .onErrorResume(throwable -> {
                    log.error("Error while getting tracked links", throwable);
                    return Mono.just(new ListLinkResponse(Collections.emptyList(), 0));
                })
                .block();
    }

    // Возвращает id добавленной ссылки
    // Возвращает -1, если произошла ошибка на сервере
    // Возвращает null, если не было добавлено
    public Long addLinkToTrack(String chatId, String link) {
        log.info("Adding link: {}", link);
        return webClient.post()
                .uri("/links")
                .header(CHAT_ID_HEADER, chatId)
                .bodyValue(Map.of("link", link))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.get("id").asLong())
                .onErrorResume(throwable -> {
                    log.error("Error while adding link", throwable);
                    return Mono.just(-1L);
                })
                .block();
    }

    // Возвращает id удаленной ссылки
    // Возвращает -1, если произошла ошибка на сервере, включая 404
    public Long removeLinkFromTrack(String chatId, String link) {
        return webClient.method(HttpMethod.DELETE)
                .uri("/links")
                .header(CHAT_ID_HEADER, chatId)
                .bodyValue(Map.of("link", link))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.get("id").asLong())
                .onErrorResume(throwable -> {
                    log.error("Error while removing link", throwable);
                    return Mono.just(-1L);
                })
                .block();
    }
}
