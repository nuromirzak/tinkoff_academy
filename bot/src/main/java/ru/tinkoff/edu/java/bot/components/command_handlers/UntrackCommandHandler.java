package ru.tinkoff.edu.java.bot.components.command_handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMANDS;
import static ru.tinkoff.edu.java.bot.components.BotCommands.ERROR_MESSAGE_INVALID_COMMAND_ARGUMENTS;
import static ru.tinkoff.edu.java.bot.components.BotCommands.ERROR_MESSAGE_LINK_NOT_REMOVED;
import static ru.tinkoff.edu.java.bot.components.BotCommands.ERROR_MESSAGE_UNHANDLED;

public class UntrackCommandHandler implements CommandHandler {
    private final ScrapperClient scrapperClient;

    public UntrackCommandHandler(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String handleCommand(Update update, String chatId, String[] commandArguments) {
        String text = COMMANDS.get(commandArguments[0]);
        if (commandArguments[1] == null) {
            return String.format(ERROR_MESSAGE_INVALID_COMMAND_ARGUMENTS, commandArguments[0]);
        } else {
            Long longId = scrapperClient.removeLinkFromTrack(chatId, commandArguments[1]);

            if (longId == null) {
                return ERROR_MESSAGE_LINK_NOT_REMOVED;
            } else if (longId == -1) {
                return ERROR_MESSAGE_UNHANDLED;
            } else {
                return String.format(text, commandArguments[1]);
            }
        }
    }
}
