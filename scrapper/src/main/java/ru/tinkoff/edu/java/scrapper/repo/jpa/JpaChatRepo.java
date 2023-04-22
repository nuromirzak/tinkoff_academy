package ru.tinkoff.edu.java.scrapper.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;

import java.util.List;

@Repository
public interface JpaChatRepo extends ChatRepo, JpaRepository<Chat, Long> {
    @Override
    @Query(value = "INSERT INTO chat (chat_id) VALUES (?1)", nativeQuery = true)
    boolean add(long chatId);

    @Override
    @Query(value = "DELETE FROM chat WHERE chat_id = ?1", nativeQuery = true)
    boolean remove(long chatId);

    @Override
    @Query(value = "SELECT * FROM chat", nativeQuery = true)
    List<Chat> findAll();

    @Override
    @Query(value = "DELETE FROM chat", nativeQuery = true)
    int removeAll();

    @Override
    @Query(value = "SELECT * FROM link WHERE link_id IN (SELECT link_id FROM link_chat WHERE chat_id = ?1)", nativeQuery = true)
    List<Link> findLinksByChatId(long chatId);

    @Override
    @Query(value = "INSERT INTO link_chat (chat_id, link_id) VALUES (?1, ?2)", nativeQuery = true)
    boolean addLinkToChat(long chatId, long linkId);

    @Override
    @Query(value = "DELETE FROM link_chat WHERE chat_id = ?1 AND link_id = ?2", nativeQuery = true)
    boolean removeLinkFromChat(long chatId, long linkId);

    @Override
    @Query(value = "SELECT * FROM chat WHERE chat_id IN (SELECT chat_id FROM link_chat WHERE link_id IN (SELECT link_id FROM link WHERE url LIKE ?1))", nativeQuery = true)
    List<Chat> findChatsByLikeLink(String url);
}