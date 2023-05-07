package ru.tinkoff.edu.java.bot.clients;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dtos.ListLinkResponse;

@Service
@Log4j2
@RequiredArgsConstructor
public class ScrapperClientImpl implements ScrapperClient {
    private static final String CHAT_ID_HEADER = "Tg-Chat-Id";
    private static final String TG_CHAT_URI = "/tg-chat/{chatId}";
    private static final String LINKS_URI = "/links";
    private static final String LINK_KEY = "link";
    private final WebClient webClient;

    // Всегда должен возвращает true, даже если чат уже зарегистрирован
    // Возвращает false, если произошла ошибка на сервере
    public boolean registerChat(String chatId) {
        return Boolean.TRUE.equals(webClient.post()
            .uri(TG_CHAT_URI, chatId)
            .retrieve()
            .toBodilessEntity()
            .map(response -> {
                if (response.getStatusCode().is5xxServerError()) {
                    log.error("Error while registering chat, got status code: {}", response.getStatusCode());
                }
                return true;
            })
            .onErrorReturn(false)
            .block());
    }

    // Возвращает true, если чат был удален успешно
    // Возвращает false, если чат не был найден или произошла ошибка на сервере
    public boolean removeChat(String chatId) {
        return Boolean.TRUE.equals(webClient.delete()
            .uri(TG_CHAT_URI, chatId)
            .retrieve()
            .toBodilessEntity()
            .map(response -> {
                if (response.getStatusCode().is5xxServerError()) {
                    log.error("Error while removing chat, got status code: {}", response.getStatusCode());
                }
                return true;
            })
            .onErrorReturn(false)
            .block());
    }

    // Всегда должен возвращать успешный ответ
    public ListLinkResponse getTrackedLinks(String chatId) {
        return webClient.get()
            .uri(LINKS_URI)
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
            .uri(LINKS_URI)
            .header(CHAT_ID_HEADER, chatId)
            .bodyValue(Map.of(LINK_KEY, link))
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
            .uri(LINKS_URI)
            .header(CHAT_ID_HEADER, chatId)
            .bodyValue(Map.of(LINK_KEY, link))
            .retrieve()
            .onStatus(HttpStatusCode::is5xxServerError, response -> {
                log.error("Error while removing link, got status code: {}", response.statusCode());
                return Mono.just(new RuntimeException("Error while removing link"));
            })
            .bodyToMono(JsonNode.class)
            .map(jsonNode -> jsonNode.get("id").asLong())
            .onErrorResume(throwable -> Mono.just(-1L))
            .block();
    }
}
