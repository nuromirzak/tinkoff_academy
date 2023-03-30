package ru.tinkoff.edu.java.bot.dtos;

import java.util.List;

public record ListLinkResponse(List<LinkResponse> links, int size) {
}
