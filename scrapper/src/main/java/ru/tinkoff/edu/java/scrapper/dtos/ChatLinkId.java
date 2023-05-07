package ru.tinkoff.edu.java.scrapper.dtos;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatLinkId implements Serializable {
    private Long chatId;
    private Long linkId;
}

