package ru.tinkoff.edu.java.scrapper.repo.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import java.util.List;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaChatRepo extends JpaRepository<Chat, Long> {
    @Modifying
    int removeChatByChatId(long chatId);

    @Modifying
    int removeAllBy();

    List<Chat> findChatsByLinks_UrlLike(String url);

    Chat findChatByChatId(long chatId);
}