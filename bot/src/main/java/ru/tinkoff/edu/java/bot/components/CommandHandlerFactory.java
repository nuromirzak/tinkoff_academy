package ru.tinkoff.edu.java.bot.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.components.command_handlers.*;

import static ru.tinkoff.edu.java.bot.components.BotCommands.*;

@Component
@RequiredArgsConstructor
public class CommandHandlerFactory {
    private final ScrapperClient scrapperClient;

    public CommandHandler createCommandHandler(String commandName) {
        return switch (commandName) {
            case COMMAND_START -> new StartCommandHandler(scrapperClient);
            case COMMAND_LIST -> new ListCommandHandler(scrapperClient);
            case COMMAND_HELP -> new HelpCommandHandler(scrapperClient);
            case COMMAND_TRACK -> new TrackCommandHandler(scrapperClient);
            case COMMAND_UNTRACK -> new UntrackCommandHandler(scrapperClient);
            case COMMAND_DELETE -> new DeleteCommandHandler(scrapperClient);
            case COMMAND_TEST -> new TestCommandHandler(scrapperClient);
            default -> new NotFoundCommandHandler(scrapperClient);
        };
    }
}

