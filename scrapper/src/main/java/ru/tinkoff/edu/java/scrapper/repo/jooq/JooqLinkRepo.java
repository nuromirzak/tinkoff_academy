package ru.tinkoff.edu.java.scrapper.repo.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.jooq.Record1;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Tables;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
public class JooqLinkRepo implements LinkRepo {
    private final DSLContext dslContext;

    @Override
    public Link saveIfAbsentOrReturnExisting(Link link) {
        if (link.getLastUpdated() == null) {
            link.setLastUpdated(OffsetDateTime.now());
        }
        if (link.getLastScrapped() == null) {
            link.setLastScrapped(OffsetDateTime.now());
        }

        List<Link> existingLinks = dslContext.selectFrom(Tables.LINK)
                .where(Tables.LINK.URL.like(link.getUrl()))
                .fetchInto(Link.class);

        if (!existingLinks.isEmpty()) {
            return existingLinks.get(0);
        }

        long linkId = dslContext.insertInto(Tables.LINK)
                .set(Tables.LINK.URL, link.getUrl())
                .set(Tables.LINK.LAST_UPDATED, link.getLastUpdated().toLocalDateTime())
                .set(Tables.LINK.LAST_SCRAPPED, link.getLastScrapped().toLocalDateTime())
                .set(Tables.LINK.JSON_PROPS,
                        link.getJsonProps() == null ? null : JSON.json(link.getJsonProps().toString()))
                .returning(Tables.LINK.LINK_ID)
                .fetchOne()
                .getLinkId();

        link.setLinkId(linkId);
        return link;
    }

    @Override
    public List<Link> findAll() {
        return dslContext.selectFrom(Tables.LINK)
                .fetchInto(Link.class);
    }

    @Override
    public boolean removeLinkByUrlLike(String link) {
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
    public List<Link> findLinksByLastScrappedBefore(OffsetDateTime lastScrapped) {
        return dslContext.selectFrom(Tables.LINK)
                .where(Tables.LINK.LAST_UPDATED.le(lastScrapped.toLocalDateTime()))
                .fetchInto(Link.class);
    }
}
