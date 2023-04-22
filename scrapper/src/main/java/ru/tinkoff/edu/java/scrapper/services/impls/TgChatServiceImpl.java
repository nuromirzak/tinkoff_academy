package ru.tinkoff.edu.java.scrapper.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;
import ru.tinkoff.edu.java.scrapper.services.TgChatService;

@Component
@RequiredArgsConstructor
public class TgChatServiceImpl implements TgChatService {
    private final ChatRepo chatRepo;

    @Override
    public boolean register(long tgChatId) {
        return chatRepo.add(tgChatId);
    }

    @Override
    public boolean unregister(long tgChatId) {
        return chatRepo.remove(tgChatId);
    }
}
