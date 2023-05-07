package ru.tinkoff.edu.java.scrapper.dtos.mappers.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;

public class ChatMapper implements RowMapper<Chat> {
    @Override
    public Chat mapRow(ResultSet resultSet, int i) throws SQLException {
        Chat chat = new Chat();
        chat.setChatId(resultSet.getLong("chat_id"));

        OffsetDateTime offsetDateTime =
            resultSet.getTimestamp("reg_date").toLocalDateTime().atOffset(OffsetDateTime.now().getOffset());
        chat.setRegDate(offsetDateTime);

        return chat;
    }
}
