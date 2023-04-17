package ru.tinkoff.edu.java.scrapper.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.mappers.LinkMapper;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JdbcLinkRepo {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_LINK = "INSERT INTO link (url, last_updated) VALUES (?, ?)";
    private static final String SQL_DELETE_LINK = "DELETE FROM link WHERE url = ?";
    private static final String SQL_FIND_LINK = "SELECT * FROM link";
    private static final String SQL_DELETE_ALL_LINKS = "DELETE FROM link";

    public boolean add(Link link) {
        if (link.getLastUpdated() == null)
            link.setLastUpdated(OffsetDateTime.now());

        int count = jdbcTemplate.update(SQL_INSERT_LINK, link.getUrl(), link.getLastUpdated());
        return count > 0;
    }

    public List<Link> findAll() {
        return jdbcTemplate.query(SQL_FIND_LINK, new LinkMapper());
    }

    public boolean remove(String link) {
        int count = jdbcTemplate.update(SQL_DELETE_LINK, link);
        return count > 0;
    }

    public int removeAll() {
        return jdbcTemplate.update(SQL_DELETE_ALL_LINKS);
    }
}
