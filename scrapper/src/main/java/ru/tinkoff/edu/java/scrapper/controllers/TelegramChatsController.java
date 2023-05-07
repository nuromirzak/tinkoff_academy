package ru.tinkoff.edu.java.scrapper.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;
import ru.tinkoff.edu.java.scrapper.dtos.responses.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.services.TelegramChatService;

@RestController
@Log4j2
@RequestMapping("/tg-chat")
public class TelegramChatsController {
    private final TelegramChatService telegramChatService;

    @Autowired
    public TelegramChatsController(TelegramChatService telegramChatService) {
        this.telegramChatService = telegramChatService;
    }

    @PostMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован",
                     content = @Content),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class),
                                        mediaType = "application/json"))
    })
    public ResponseEntity<?> registerChat(@PathVariable("id") String id) {
        log.info("Registering chat: {}", id);

        telegramChatService.registerChat(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат успешно удалён",
                     content = @Content),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Чат не существует",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class),
                                        mediaType = "application/json"))
    })
    public ResponseEntity<?> deleteChat(@PathVariable("id") String id) {
        log.info("Deleting chat: {}", id);

        if (!telegramChatService.deleteChat(id)) {
            throw new NotFoundException("Chat with id " + id + " not found");
        }

        return ResponseEntity.ok().build();
    }
}
