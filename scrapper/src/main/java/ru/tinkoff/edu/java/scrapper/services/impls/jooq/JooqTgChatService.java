package ru.tinkoff.edu.java.scrapper.services.impls.jooq;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.repo.jooq.JooqChatRepo;
import ru.tinkoff.edu.java.scrapper.services.TgChatService;

@Service
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqTgChatService implements TgChatService {
    private final JooqChatRepo chatRepo;

    @Override
    public boolean register(long tgChatId) {
        return chatRepo.add(tgChatId);
    }

    @Override
    public boolean unregister(long tgChatId) {
        return chatRepo.remove(tgChatId);
    }
}
