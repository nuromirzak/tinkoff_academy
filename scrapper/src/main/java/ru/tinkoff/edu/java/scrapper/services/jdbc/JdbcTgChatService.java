package ru.tinkoff.edu.java.scrapper.services.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.repo.JdbcChatRepo;
import ru.tinkoff.edu.java.scrapper.services.TgChatService;

@Component
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final JdbcChatRepo jdbcChatRepo;

    @Override
    public boolean register(long tgChatId) {
        return jdbcChatRepo.add(tgChatId);
    }

    @Override
    public boolean unregister(long tgChatId) {
        return jdbcChatRepo.remove(tgChatId);
    }
}
