package ru.tinkoff.edu.java.scrapper.repo.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.ChatLink;
import ru.tinkoff.edu.java.scrapper.dtos.ChatLinkId;
import ru.tinkoff.edu.java.scrapper.repo.ChatLinkRepo;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaChatLinkRepo extends ChatLinkRepo, JpaRepository<ChatLink, ChatLinkId> {
    @Override
    default boolean addLinkToChat(long chatId, long linkId) {
        return addLinkToChatAndReturnInt(chatId, linkId) == 1;
    }

    @Query(value = "INSERT INTO link_chat (chat_id, link_id) VALUES (?1, ?2)", nativeQuery = true)
    @Modifying
    int addLinkToChatAndReturnInt(long chatId, long linkId);

    @Override
    default boolean removeLinkFromChat(long chatId, long linkId) {
        return removeLinkFromChatAndReturnInt(chatId, linkId) == 1;
    }

    @Query(value = "DELETE FROM link_chat WHERE chat_id = ?1 AND link_id = ?2", nativeQuery = true)
    @Modifying
    int removeLinkFromChatAndReturnInt(long chatId, long linkId);
}

