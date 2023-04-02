package ru.tinkoff.edu.java.bot.dtos;

import java.util.List;

public record LinkUpdateRequest(Long id, String url, String description, List<Long> tgChatIds) {
}
