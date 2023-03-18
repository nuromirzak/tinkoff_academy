package ru.tinkoff.edu.java.bot.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dtos.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.dtos.LinkUpdateResponse;

@RestController
@Log4j2
public class LinksController {
    @PostMapping("/updates")
    public ResponseEntity<LinkUpdateResponse> updateLink(@RequestBody LinkUpdateRequest request) {
        log.info("\u001B[34m" + "Got request: {}", request);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/test")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}
