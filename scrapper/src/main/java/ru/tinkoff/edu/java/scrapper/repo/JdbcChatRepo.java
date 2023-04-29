package ru.tinkoff.edu.java.scrapper.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.mappers.ChatMapper;
import ru.tinkoff.edu.java.scrapper.dtos.mappers.LinkMapper;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JdbcChatRepo {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_CHAT = "INSERT INTO chat (chat_id) VALUES (?)";
    private static final String SQL_DELETE_CHAT = "DELETE FROM chat WHERE chat_id = ?";
    private static final String SQL_FIND_CHAT = "SELECT * FROM chat";
    private static final String SQL_DELETE_ALL_CHAT = "DELETE FROM chat";
    private static final String SQL_FIND_CHATS_BY_LIKE_LINK = "SELECT * FROM chat WHERE chat_id IN (SELECT chat_id FROM link_chat WHERE link_id IN (SELECT link_id FROM link WHERE url LIKE ?))";

    public boolean add(long chatId) {
        int count = jdbcTemplate.update(SQL_INSERT_CHAT, chatId);
        return count > 0;
    }

    public boolean remove(long chatId) {
        int count = jdbcTemplate.update(SQL_DELETE_CHAT, chatId);
        return count > 0;
    }

    public List<Chat> findAll() {
        return jdbcTemplate.query(SQL_FIND_CHAT, new ChatMapper());
    }

    public int removeAll() {
        return jdbcTemplate.update(SQL_DELETE_ALL_CHAT);
    }

    public List<Chat> findChatsByLikeLink(String url) {
        return jdbcTemplate.query(SQL_FIND_CHATS_BY_LIKE_LINK, new ChatMapper(), url);
    }
}
