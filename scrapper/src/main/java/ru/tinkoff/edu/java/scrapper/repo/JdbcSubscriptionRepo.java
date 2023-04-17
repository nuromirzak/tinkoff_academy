package ru.tinkoff.edu.java.scrapper.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dtos.Subscription;
import ru.tinkoff.edu.java.scrapper.dtos.mappers.SubscriptionMapper;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JdbcSubscriptionRepo {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_SUBSCRIPTION = "INSERT INTO subscription (chat_id, link_id) VALUES (?, ?)";
    private static final String SQL_DELETE_SUBSCRIPTION = "DELETE FROM subscription WHERE chat_id = ? AND link_id = ?";
    private static final String SQL_FIND_SUBSCRIPTION = "SELECT * FROM subscription";
    private static final String SQL_DELETE_ALL_SUBSCRIPTION = "DELETE FROM subscription";

    public boolean add(Subscription subscription) {
        int count = jdbcTemplate.update(SQL_INSERT_SUBSCRIPTION, subscription.getChatId(), subscription.getLinkId());
        return count > 0;
    }

    public boolean remove(Subscription subscription) {
        int count = jdbcTemplate.update(SQL_DELETE_SUBSCRIPTION, subscription.getChatId(), subscription.getLinkId());
        return count > 0;
    }

    public List<Subscription> findAll() {
        return jdbcTemplate.query(SQL_FIND_SUBSCRIPTION, new SubscriptionMapper());
    }

    public int removeAll() {
        return jdbcTemplate.update(SQL_DELETE_ALL_SUBSCRIPTION);
    }
}
