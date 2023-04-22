package ru.tinkoff.edu.java.scrapper.repo.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Tables;

import java.util.List;

@RequiredArgsConstructor
public class JooqLinkRepo implements LinkRepo {
    private final DSLContext dslContext;

    @Override
    public long add(Link link) {
        return dslContext.insertInto(Tables.LINK)
                .set(Tables.LINK.URL, link.getUrl())
                .set(Tables.LINK.LAST_UPDATED, link.getLastUpdated().toLocalDateTime())
                .set(Tables.LINK.JSON_PROPS, JSON.json(link.getJsonProps().toString()))
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
    public int removeAll() {
        return dslContext.deleteFrom(Tables.LINK)
                .execute();
    }
}
