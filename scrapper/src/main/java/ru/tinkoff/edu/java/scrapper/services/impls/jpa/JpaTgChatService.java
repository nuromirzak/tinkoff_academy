package ru.tinkoff.edu.java.scrapper.services.impls.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaChatRepo;
import ru.tinkoff.edu.java.scrapper.services.TgChatService;
import java.time.OffsetDateTime;

@Service
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {
    private final JpaChatRepo chatRepo;

    @Override
    public boolean register(long tgChatId) {
        Chat chatToSave = new Chat();
        chatToSave.setChatId(tgChatId);
        chatToSave.setRegDate(OffsetDateTime.now());
        chatRepo.save(chatToSave);
        return true;
    }

    @Override
    public boolean unregister(long tgChatId) {
        return chatRepo.removeChatByChatId(tgChatId) > 0;
    }
}
