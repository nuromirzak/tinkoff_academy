package ru.tinkoff.edu.java.scrapper.dtos.mappers.jooq;

import org.jooq.RecordMapper;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import java.time.ZoneOffset;

public class ChatMapper implements RecordMapper<ChatRecord, Chat> {
    @Override
    public Chat map(ChatRecord record) {
        Chat chat = new Chat();
        chat.setChatId(record.getChatId());
        chat.setRegDate(record.getRegDate().atOffset(ZoneOffset.UTC));
        return chat;
    }
}