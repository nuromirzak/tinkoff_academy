package ru.tinkoff.edu.java.scrapper.dtos;

import java.util.List;

public record ListLinkResponse(List<LinkResponse> links, int size) {
}
