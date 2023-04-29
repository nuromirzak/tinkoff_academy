package ru.tinkoff.edu.java.scrapper.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JdbcChatLinkRepo {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_LINK_CHAT = "INSERT INTO link_chat (chat_id, link_id) VALUES (?, ?)";
    private static final String SQL_DELETE_LINK_CHAT = "DELETE FROM link_chat WHERE chat_id = ? AND link_id = ?";

    public boolean addLinkToChat(long chatId, long linkId) {
        int count = jdbcTemplate.update(SQL_INSERT_LINK_CHAT, chatId, linkId);
        return count > 0;
    }

    public boolean removeLinkFromChat(long chatId, long linkId) {
        int count = jdbcTemplate.update(SQL_DELETE_LINK_CHAT, chatId, linkId);
        return count > 0;
    }
}
