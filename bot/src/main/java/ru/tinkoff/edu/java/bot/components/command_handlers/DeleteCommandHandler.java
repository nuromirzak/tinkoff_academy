package ru.tinkoff.edu.java.bot.components.command_handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;

import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMAND_DELETE_RESPONSE;
import static ru.tinkoff.edu.java.bot.components.BotCommands.ERROR_MESSAGE_NOT_REGISTERED;

public class DeleteCommandHandler implements CommandHandler {
    private final ScrapperClient scrapperClient;

    public DeleteCommandHandler(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String handleCommand(Update update, String chatId, String[] commandArguments) {
        if (scrapperClient.removeChat(update.getMessage().getChatId().toString())) {
            return COMMAND_DELETE_RESPONSE;
        } else {
            return ERROR_MESSAGE_NOT_REGISTERED;
        }
    }
}