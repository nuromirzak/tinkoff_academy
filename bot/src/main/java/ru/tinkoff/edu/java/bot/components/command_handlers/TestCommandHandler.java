package ru.tinkoff.edu.java.bot.components.command_handlers;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;

import java.util.List;

import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMAND_TEST_RESPONSE;
import static ru.tinkoff.edu.java.bot.components.BotCommands.SECRET_COMMAND_USERNAME;

public class TestCommandHandler implements CommandHandler {

    public TestCommandHandler(ScrapperClient scrapperClient) {
    }

    @Override
    public String handleCommand(Update update, String chatId, String[] commandArguments) {
        String username = update.getMessage().getFrom().getUserName();
        if (!username.equals(SECRET_COMMAND_USERNAME)) {
            return COMMAND_TEST_RESPONSE;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Получаем информацию о сообщении:\n");
        String userId = update.getMessage().getFrom().getId().toString();
        sb.append("UserId: ").append(userId).append("\n");
        sb.append("ChatId: ").append(chatId).append("\n");
        sb.append("MessageId: ").append(update.getMessage().getMessageId()).append("\n");
        if (update.getMessage().hasPhoto()) {
            List<String> photoIds = update.getMessage().getPhoto().stream().map(PhotoSize::getFileId).toList();
            sb.append("PhotoIds: ").append(photoIds).append("\n");
        }
        if (update.getMessage().hasDocument()) {
            String documentId = update.getMessage().getDocument().getFileId();
            sb.append("DocumentId: ").append(documentId).append("\n");
        }
        return sb.toString();
    }
}
