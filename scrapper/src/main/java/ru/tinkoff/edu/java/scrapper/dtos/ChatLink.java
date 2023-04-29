package ru.tinkoff.edu.java.scrapper.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "link_chat")
@IdClass(ChatLinkId.class)
public class ChatLink {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Id
    @Column(name = "link_id")
    private Long linkId;
}

