package ru.tinkoff.edu.java.scrapper.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Chat {
    private Long chatId;
    private OffsetDateTime regDate;
}