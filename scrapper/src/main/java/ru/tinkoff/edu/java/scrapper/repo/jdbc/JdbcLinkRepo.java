package ru.tinkoff.edu.java.scrapper.repo.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.mappers.jdbc.LinkMapper;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class JdbcLinkRepo implements LinkRepo {
    private static final String SQL_INSERT_LINK =
            "INSERT INTO link (url, last_updated, last_scrapped, json_props) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_LINK_BY_URL = "SELECT * FROM link WHERE url LIKE ?";
    private static final String SQL_DELETE_LINK = "DELETE FROM link WHERE url = ?";
    private static final String SQL_FIND_LINK = "SELECT * FROM link";
    private static final String SQL_DELETE_ALL_LINKS = "DELETE FROM link";
    private static final String SQL_FIND_LINKS_BY_CHAT_ID =
            "SELECT * FROM link WHERE link_id IN (SELECT link_id FROM link_chat WHERE chat_id = ?)";
    private static final String SQL_FIND_LINKS_TO_SCRAP = "SELECT * FROM link WHERE link.last_scrapped <= ?";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Link saveIfAbsentOrReturnExisting(Link link) {
        if (link.getLastUpdated() == null) {
            link.setLastUpdated(OffsetDateTime.now());
        }
        if (link.getLastScrapped() == null) {
            link.setLastScrapped(OffsetDateTime.now());
        }

        List<Link> existingLinks = jdbcTemplate.query(SQL_FIND_LINK_BY_URL, new LinkMapper(), link.getUrl());
        if (!existingLinks.isEmpty()) {
            return existingLinks.get(0);
        }

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            var statement = con.prepareStatement(SQL_INSERT_LINK, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, link.getUrl());
            statement.setObject(2, link.getLastUpdated());
            statement.setObject(3, link.getLastScrapped());
            statement.setObject(4, link.getJsonProps());
            return statement;
        }, holder);

        Map<String, Object> keys = holder.getKeys();

        long linkId = keys.get("link_id") == null ? -1L : ((Number) keys.get("link_id")).longValue();
        link.setLinkId(linkId);
        return link;
    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query(SQL_FIND_LINK, new LinkMapper());
    }

    @Override
    public boolean removeLinkByUrlLike(String link) {
        int count = jdbcTemplate.update(SQL_DELETE_LINK, link);
        return count > 0;
    }

    @Override
    public int removeAll() {
        return jdbcTemplate.update(SQL_DELETE_ALL_LINKS);
    }

    @Override
    public List<Link> findLinksByChatId(long chatId) {
        return jdbcTemplate.query(SQL_FIND_LINKS_BY_CHAT_ID, new LinkMapper(), chatId);
    }

    @Override
    public List<Link> findLinksByLastScrappedBefore(OffsetDateTime lastScrapped) {
        return jdbcTemplate.query(SQL_FIND_LINKS_TO_SCRAP, new LinkMapper(), lastScrapped);
    }
}
