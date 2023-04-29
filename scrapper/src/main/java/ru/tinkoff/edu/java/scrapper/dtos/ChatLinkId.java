package ru.tinkoff.edu.java.scrapper.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatLinkId implements Serializable {
    private Long chatId;
    private Long linkId;
}

