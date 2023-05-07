package ru.tinkoff.edu.java.scrapper.repo.jpa;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;

@Repository
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public interface JpaChatRepo extends JpaRepository<Chat, Long> {
    @Modifying
    int removeChatByChatId(long chatId);

    @Modifying
    int removeAllBy();

    List<Chat> findChatsByLinksUrlLike(String url);

    Chat findChatByChatId(long chatId);
}
