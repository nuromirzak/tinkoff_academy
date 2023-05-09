package ru.tinkoff.edu.java.bot.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dtos.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.dtos.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.service.HttpLinkUpdateReceiver;

@RestController
@Log4j2
@RequiredArgsConstructor
public class LinksController {
    private final HttpLinkUpdateReceiver linkUpdateReceiver;

    @PostMapping("/updates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Обновление обработано",
                     content = @Content),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class),
                                        mediaType = "application/json"))
    })
    public ResponseEntity<?> updateLink(@RequestBody LinkUpdateRequest request) {
        log.info("\u001B[34m" + "Got request: {}", request);

        linkUpdateReceiver.receiveUpdate(request);

        return ResponseEntity.ok().build();
    }

    @Hidden
    @GetMapping("/test")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}
