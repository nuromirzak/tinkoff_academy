package ru.tinkoff.edu.java.scrapper.dtos.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dtos.Link;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class LinkMapper implements RowMapper<Link> {
    @Override
    public Link mapRow(ResultSet resultSet, int i) throws SQLException {
        Link link = new Link();

        link.setLinkId(resultSet.getLong("link_id"));

        link.setUrl(resultSet.getString("url"));

        OffsetDateTime offsetDateTime = resultSet.getTimestamp("last_updated").toLocalDateTime().atOffset(OffsetDateTime.now().getOffset());
        link.setLastUpdated(offsetDateTime);

        return link;
    }
}
