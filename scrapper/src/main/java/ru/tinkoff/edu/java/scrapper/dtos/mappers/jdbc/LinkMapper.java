package ru.tinkoff.edu.java.scrapper.dtos.mappers.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dtos.Link;

public class LinkMapper implements RowMapper<Link> {
    @Override
    public Link mapRow(ResultSet resultSet, int i) throws SQLException {
        Link link = new Link();

        link.setLinkId(resultSet.getLong("link_id"));

        link.setUrl(resultSet.getString("url"));

        OffsetDateTime lastUpdated =
            resultSet.getTimestamp("last_updated").toLocalDateTime().atOffset(OffsetDateTime.now().getOffset());

        link.setLastUpdated(lastUpdated);

        OffsetDateTime lastScrapped =
            resultSet.getTimestamp("last_scrapped").toLocalDateTime().atOffset(OffsetDateTime.now().getOffset());

        link.setLastScrapped(lastScrapped);

        link.setJsonProps(resultSet.getString("json_props"));

        return link;
    }
}
