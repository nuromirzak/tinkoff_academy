package ru.tinkoff.edu.java.scrapper.dtos.responses;

import java.util.List;

public record ListLinkResponse(List<LinkResponse> links, int size) {
}
