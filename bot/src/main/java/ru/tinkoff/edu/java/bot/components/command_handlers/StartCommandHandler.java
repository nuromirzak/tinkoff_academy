package ru.tinkoff.edu.java.bot.components.command_handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;

import static ru.tinkoff.edu.java.bot.components.BotCommands.*;

public class StartCommandHandler implements CommandHandler {
    private final ScrapperClient scrapperClient;

    public StartCommandHandler(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String handleCommand(Update update, String chatId, String[] commandArguments) {
        String text = commands.get(commandArguments[0]);

        if (scrapperClient.registerChat(chatId)) {
            return String.format(text, update.getMessage().getFrom().getFirstName());
        } else {
            return ERROR_MESSAGE_UNHANDLED;
        }
    }
}
