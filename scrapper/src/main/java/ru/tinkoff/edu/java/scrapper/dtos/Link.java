package ru.tinkoff.edu.java.scrapper.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Link {
    private Long linkId;
    private String url;
    private OffsetDateTime lastUpdated;
}
