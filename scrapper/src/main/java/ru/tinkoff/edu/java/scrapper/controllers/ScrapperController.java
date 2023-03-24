package ru.tinkoff.edu.java.scrapper.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dtos.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.RemoveLinkRequest;

@RestController
@Log4j2
public class ScrapperController {
    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<String> registerChat(@PathVariable("id") String id) {
        log.info("Registering chat: {}", id);
        return ResponseEntity.ok("Successfully registered chat " + id);
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable("id") String id) {
        log.info("Deleting chat: {}", id);
        return ResponseEntity.ok(String.format("Successfully deleted chat %s", id));
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinkResponse> getLinks(@RequestHeader("Tg-Chat-Id") String chatId) {
        log.info("Getting links for chat: {}", chatId);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(@RequestHeader("Tg-Chat-Id") String chatId,
                                                @RequestBody AddLinkRequest request) {
        log.info("Adding link for chat: {}", chatId);
        log.info("Request: {}", request);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> deleteLink(@RequestHeader("Tg-Chat-Id") String chatId,
                                                   @RequestBody RemoveLinkRequest request) {
        log.info("Deleting link for chat: {}", chatId);
        log.info("Request: {}", request);

        return ResponseEntity.ok(null);
    }
}
