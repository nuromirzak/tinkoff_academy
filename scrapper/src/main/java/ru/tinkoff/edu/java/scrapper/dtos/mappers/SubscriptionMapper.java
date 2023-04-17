package ru.tinkoff.edu.java.scrapper.dtos.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dtos.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionMapper implements RowMapper<Subscription> {
    @Override
    public Subscription mapRow(ResultSet resultSet, int i) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setChatId(resultSet.getLong("chat_id"));
        subscription.setLinkId(resultSet.getLong("link_id"));

        return subscription;
    }
}
