package ru.tinkoff.edu.java.scrapper.repo.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Tables;
import ru.tinkoff.edu.java.scrapper.repo.ChatLinkRepo;

@RequiredArgsConstructor
@Repository("jooqChatLinkRepo")
public class JooqChatLinkRepo implements ChatLinkRepo {
    private final DSLContext dslContext;


    @Override
    public boolean addLinkToChat(long chatId, long linkId) {
        int count = dslContext.insertInto(Tables.LINK_CHAT, Tables.LINK_CHAT.CHAT_ID, Tables.LINK_CHAT.LINK_ID)
                .values(chatId, linkId)
                .execute();
        return count > 0;
    }

    @Override
    public boolean removeLinkFromChat(long chatId, long linkId) {
        int count = dslContext.deleteFrom(Tables.LINK_CHAT)
                .where(Tables.LINK_CHAT.CHAT_ID.eq(chatId))
                .and(Tables.LINK_CHAT.LINK_ID.eq(linkId))
                .execute();
        return count > 0;
    }
}
