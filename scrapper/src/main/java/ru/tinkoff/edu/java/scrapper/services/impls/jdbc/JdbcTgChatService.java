package ru.tinkoff.edu.java.scrapper.services.impls.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.repo.jdbc.JdbcChatRepo;
import ru.tinkoff.edu.java.scrapper.services.TgChatService;

@Service
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final JdbcChatRepo chatRepo;

    @Override
    public boolean register(long tgChatId) {
        return chatRepo.add(tgChatId);
    }

    @Override
    public boolean unregister(long tgChatId) {
        return chatRepo.remove(tgChatId);
    }
}
