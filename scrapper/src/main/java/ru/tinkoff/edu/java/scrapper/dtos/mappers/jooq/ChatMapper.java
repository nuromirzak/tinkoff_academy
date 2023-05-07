package ru.tinkoff.edu.java.scrapper.dtos.mappers.jooq;

import java.time.ZoneOffset;
import org.jooq.RecordMapper;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;

public class ChatMapper implements RecordMapper<ChatRecord, Chat> {
    @Override
    public Chat map(ChatRecord chatRecord) {
        Chat chat = new Chat();
        chat.setChatId(chatRecord.getChatId());
        chat.setRegDate(chatRecord.getRegDate().atOffset(ZoneOffset.UTC));
        return chat;
    }
}
