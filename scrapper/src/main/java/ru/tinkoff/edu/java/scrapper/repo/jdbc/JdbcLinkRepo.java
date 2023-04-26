package ru.tinkoff.edu.java.scrapper.repo.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.dtos.mappers.LinkMapper;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;

import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class JdbcLinkRepo implements LinkRepo {
    private final JdbcTemplate jdbcTemplate;

    // TODO: Duplicate URLs cannot be added, change SQL Query
    private static final String SQL_INSERT_LINK = "INSERT INTO link (url, last_updated, json_props) VALUES (?, ?, ?)";
    private static final String SQL_CHECK_LINK_EXISTS = "SELECT * FROM link WHERE url = ?";
    private static final String SQL_DELETE_LINK = "DELETE FROM link WHERE url = ?";
    private static final String SQL_FIND_LINK = "SELECT * FROM link";
    private static final String SQL_DELETE_ALL_LINKS = "DELETE FROM link";

    @Override
    public int add(Link link) {
        if (link.getLastUpdated() == null)
            link.setLastUpdated(OffsetDateTime.now());

        List<Link> existingLinks = jdbcTemplate.query(SQL_CHECK_LINK_EXISTS, new LinkMapper(), link.getUrl());
        if (!existingLinks.isEmpty()) {
            System.out.println("LINK ALREADY EXISTS");
            System.out.println(existingLinks);
            return Math.toIntExact(existingLinks.get(0).getLinkId());
        }

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            var statement = con.prepareStatement(SQL_INSERT_LINK, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, link.getUrl());
            statement.setObject(2, link.getLastUpdated());
            statement.setObject(3, link.getJsonProps());
            return statement;
        }, holder);

        System.out.println("KEYS" + holder.getKeys());

        return (int) ((Number) Objects.requireNonNull(holder.getKeys()).get("link_id")).longValue();
    }

    @Override
    public List<Link> findAll() {
        return jdbcTemplate.query(SQL_FIND_LINK, new LinkMapper());
    }

    @Override
    public boolean remove(String link) {
        int count = jdbcTemplate.update(SQL_DELETE_LINK, link);
        return count > 0;
    }

    @Override
    public int removeAll() {
        return jdbcTemplate.update(SQL_DELETE_ALL_LINKS);
    }
}
