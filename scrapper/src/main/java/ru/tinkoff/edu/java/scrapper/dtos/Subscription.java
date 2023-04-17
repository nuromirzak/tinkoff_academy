package ru.tinkoff.edu.java.scrapper.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Subscription {
    private Long chatId;
    private Long linkId;
}
