package ru.tinkoff.edu.java.scrapper.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.tinkoff.edu.java.scrapper.dtos.requests.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.requests.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.responses.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.services.TelegramChatService;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/links")
public class LinksController {
    private static final String CHAT_ID_HEADER = "Tg-Chat-Id";
    private final TelegramChatService telegramChatService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены",
                    content = @Content(schema = @Schema(implementation = ListLinkResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<ListLinkResponse> getLinks(@RequestHeader(CHAT_ID_HEADER) String chatId) {
        log.info("Getting links for chat: {}", chatId);

        ListLinkResponse response = telegramChatService.getLinks(chatId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена",
                    content = @Content(schema = @Schema(implementation = LinkResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<LinkResponse> addLink(@RequestHeader(CHAT_ID_HEADER) String chatId,
                                                @RequestBody AddLinkRequest request) {
        log.info("Adding link for chat: {}", chatId);
        log.info("Request: {}", request);

        LinkResponse linkResponse = telegramChatService.addLink(chatId, request);

        return ResponseEntity.ok(linkResponse);
    }

    @DeleteMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана",
                    content = @Content(schema = @Schema(implementation = LinkResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class),
                            mediaType = "application/json"))
    })
    public ResponseEntity<LinkResponse> deleteLink(@RequestHeader(CHAT_ID_HEADER) String chatId,
                                                   @RequestBody RemoveLinkRequest request) {
        log.info("Deleting link for chat: {}", chatId);
        log.info("Request: {}", request);

        LinkResponse linkResponse = telegramChatService.deleteLink(chatId, request);

        if (linkResponse == null) {
            throw new NotFoundException("Link " + request.link() + " not found");
        }

        return ResponseEntity.ok(linkResponse);
    }
}
