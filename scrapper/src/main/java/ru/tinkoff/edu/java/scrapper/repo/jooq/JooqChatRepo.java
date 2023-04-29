package ru.tinkoff.edu.java.scrapper.repo.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Tables;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.mappers.jooq.ChatMapper;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;

import java.util.List;

@RequiredArgsConstructor
public class JooqChatRepo implements ChatRepo {
    private final DSLContext dslContext;

    @Override
    public boolean add(long chatId) {
        int count = dslContext.insertInto(Tables.CHAT, Tables.CHAT.CHAT_ID)
                .values(chatId)
                .execute();
        return count > 0;
    }

    @Override
    public boolean remove(long chatId) {
        int count = dslContext.deleteFrom(Tables.CHAT)
                .where(Tables.CHAT.CHAT_ID.eq(chatId))
                .execute();
        return count > 0;
    }

    @Override
    public List<Chat> findAll() {
        return dslContext.selectFrom(Tables.CHAT)
                .fetch(new ChatMapper());
    }

    @Override
    public int removeAll() {
        return dslContext.deleteFrom(Tables.CHAT)
                .execute();
    }

    @Override
    public List<Chat> findChatsByLikeLink(String url) {
        return dslContext.selectFrom(Tables.CHAT)
                .where(Tables.CHAT.CHAT_ID.in(dslContext.select(Tables.LINK_CHAT.CHAT_ID)
                        .from(Tables.LINK_CHAT)
                        .where(Tables.LINK_CHAT.LINK_ID.in(dslContext.select(Tables.LINK.LINK_ID)
                                .from(Tables.LINK)
                                .where(Tables.LINK.URL.like(url))
                                .fetch()))
                        .fetch()))
                .fetchInto(Chat.class);
    }
}
