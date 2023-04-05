package ru.tinkoff.edu.java.bot.components.command_handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;

import static ru.tinkoff.edu.java.bot.components.BotCommands.ERROR_MESSAGE_COMMAND_NOT_FOUND;

public class NotFoundCommandHandler implements CommandHandler {

    public NotFoundCommandHandler(ScrapperClient scrapperClient) {
    }

    @Override
    public String handleCommand(Update update, String chatId, String[] commandArguments) {
        return ERROR_MESSAGE_COMMAND_NOT_FOUND;
    }
}
