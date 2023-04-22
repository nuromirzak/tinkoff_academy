package ru.tinkoff.edu.java.scrapper.repo.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.mappers.ChatMapper;
import ru.tinkoff.edu.java.scrapper.dtos.mappers.LinkMapper;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;

import java.util.List;

@RequiredArgsConstructor
public class JdbcChatRepo implements ChatRepo {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_CHAT = "INSERT INTO chat (chat_id) VALUES (?)";
    private static final String SQL_DELETE_CHAT = "DELETE FROM chat WHERE chat_id = ?";
    private static final String SQL_FIND_CHAT = "SELECT * FROM chat";
    private static final String SQL_DELETE_ALL_CHAT = "DELETE FROM chat";
    private static final String SQL_FIND_LINKS_BY_CHAT_ID = "SELECT * FROM link WHERE link_id IN (SELECT link_id FROM link_chat WHERE chat_id = ?)";
    private static final String SQL_INSERT_LINK_CHAT = "INSERT INTO link_chat (chat_id, link_id) VALUES (?, ?)";
    private static final String SQL_DELETE_LINK_CHAT = "DELETE FROM link_chat WHERE chat_id = ? AND link_id = ?";
    private static final String SQL_FIND_CHATS_BY_LIKE_LINK = "SELECT * FROM chat WHERE chat_id IN (SELECT chat_id FROM link_chat WHERE link_id IN (SELECT link_id FROM link WHERE url LIKE ?))";

    @Override
    public boolean add(long chatId) {
        int count = jdbcTemplate.update(SQL_INSERT_CHAT, chatId);
        return count > 0;
    }

    @Override
    public boolean remove(long chatId) {
        int count = jdbcTemplate.update(SQL_DELETE_CHAT, chatId);
        return count > 0;
    }

    @Override
    public List<Chat> findAll() {
        return jdbcTemplate.query(SQL_FIND_CHAT, new ChatMapper());
    }

    @Override
    public int removeAll() {
        return jdbcTemplate.update(SQL_DELETE_ALL_CHAT);
    }

    @Override
    public List<Link> findLinksByChatId(long chatId) {
        return jdbcTemplate.query(SQL_FIND_LINKS_BY_CHAT_ID, new LinkMapper(), chatId);
    }

    @Override
    public boolean addLinkToChat(long chatId, long linkId) {
        int count = jdbcTemplate.update(SQL_INSERT_LINK_CHAT, chatId, linkId);
        return count > 0;
    }

    @Override
    public boolean removeLinkFromChat(long chatId, long linkId) {
        int count = jdbcTemplate.update(SQL_DELETE_LINK_CHAT, chatId, linkId);
        return count > 0;
    }

    @Override
    public List<Chat> findChatsByLikeLink(String url) {
        return jdbcTemplate.query(SQL_FIND_CHATS_BY_LIKE_LINK, new ChatMapper(), url);
    }
}
