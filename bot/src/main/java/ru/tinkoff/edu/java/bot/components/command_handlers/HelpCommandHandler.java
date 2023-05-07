package ru.tinkoff.edu.java.bot.components.command_handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMANDS;

public class HelpCommandHandler implements CommandHandler {

    public HelpCommandHandler(ScrapperClient scrapperClient) {
    }

    @Override
    public String handleCommand(Update update, String chatId, String[] commandArguments) {
        return COMMANDS.get(commandArguments[0]);
    }
}
