package ru.tinkoff.edu.java.scrapper.services.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Tables;
import ru.tinkoff.edu.java.scrapper.services.TgChatService;

@Service
@RequiredArgsConstructor
public class JooqTgChatService implements TgChatService {
    private final DSLContext context;

    @Override
    public boolean register(long tgChatId) {
        int count = context.insertInto(Tables.CHAT, Tables.CHAT.CHAT_ID)
                .values(tgChatId)
                .onDuplicateKeyIgnore()
                .execute();
        return count > 0;
    }

    @Override
    public boolean unregister(long tgChatId) {
        int count = context.deleteFrom(Tables.CHAT)
                .where(Tables.CHAT.CHAT_ID.eq(tgChatId))
                .execute();
        return count > 0;
    }
}
