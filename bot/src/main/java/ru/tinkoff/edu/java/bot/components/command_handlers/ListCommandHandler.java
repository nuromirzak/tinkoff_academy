package ru.tinkoff.edu.java.bot.components.command_handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.dtos.ListLinkResponse;
import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMANDS;
import static ru.tinkoff.edu.java.bot.components.BotCommands.ERROR_MESSAGE_NO_LINKS;

public class ListCommandHandler implements CommandHandler {
    private final ScrapperClient scrapperClient;

    public ListCommandHandler(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public String handleCommand(Update update, String chatId, String[] commandArguments) {
        ListLinkResponse linkUpdateRequest = scrapperClient.getTrackedLinks(chatId);
        if (linkUpdateRequest.size() == 0) {
            return ERROR_MESSAGE_NO_LINKS;
        }
        return String.format(COMMANDS.get(commandArguments[0]), linkUpdateRequest);
    }
}
