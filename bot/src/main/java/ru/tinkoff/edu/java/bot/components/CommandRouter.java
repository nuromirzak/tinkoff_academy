package ru.tinkoff.edu.java.bot.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.components.command_handlers.CommandHandler;
import static ru.tinkoff.edu.java.bot.components.BotCommands.ERROR_MESSAGE_COMMAND_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CommandRouter {
    private final CommandHandlerFactory handlerFactory;

    public String routeCommand(Update update, String message) {
        String[] commandArgs = getFirstAndRemainingWords(message);
        String chatId = update.getMessage().getChatId().toString();

        if (commandArgs.length < 1) {
            return ERROR_MESSAGE_COMMAND_NOT_FOUND;
        }

        CommandHandler handler = handlerFactory.createCommandHandler(commandArgs[0]);
        return handler.handleCommand(update, chatId, commandArgs);
    }

    private String[] getFirstAndRemainingWords(String text) {
        if (text == null) {
            return new String[0];
        }

        String[] words = text.split(" ", 2);
        if (words.length > 1) {
            return words;
        } else {
            return new String[] {words[0], null};
        }
    }
}
