package ru.tinkoff.edu.java.scrapper.repo.jooq;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.jooq.Record1;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Tables;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;

@RequiredArgsConstructor
public class JooqLinkRepo implements LinkRepo {
    private final DSLContext dslContext;

    @Override
    public int add(Link link) {
        return dslContext.insertInto(Tables.LINK)
            .set(Tables.LINK.URL, link.getUrl())
            .set(
                Tables.LINK.LAST_UPDATED,
                link.getLastUpdated() == null ? LocalDateTime.now() : link.getLastUpdated().toLocalDateTime()
            )
            .set(Tables.LINK.JSON_PROPS, link.getJsonProps() == null ? null : JSON.json(link.getJsonProps().toString())
            )
            .returning(Tables.LINK.LINK_ID)
            .fetchOne()
            .getLinkId();
    }

    @Override
    public List<Link> findAll() {
        return dslContext.selectFrom(Tables.LINK)
            .fetchInto(Link.class);
    }

    @Override
    public boolean remove(String link) {
        return dslContext.deleteFrom(Tables.LINK)
            .where(Tables.LINK.URL.eq(link))
            .execute() > 0;
    }

    @Override
    public List<Link> findLinksByChatId(long chatId) {
        List<Long> linkIdsOfChat = dslContext.select(Tables.LINK_CHAT.LINK_ID)
            .from(Tables.LINK_CHAT)
            .where(Tables.LINK_CHAT.CHAT_ID.eq(chatId)).stream().map(Record1::value1).toList();

        return dslContext.selectFrom(Tables.LINK)
            .where(Tables.LINK.LINK_ID.in(linkIdsOfChat))
            .fetchInto(Link.class);
    }

    @Override
    public int removeAll() {
        return dslContext.deleteFrom(Tables.LINK)
            .execute();
    }

    @Override
    public List<Link> findLinksToScrap(Duration duration) {
        return dslContext.selectFrom(Tables.LINK)
            .where(Tables.LINK.LAST_UPDATED.le(LocalDateTime.now().minus(duration)))
            .fetchInto(Link.class);
    }
}
