package ru.tinkoff.edu.java.bot.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.components.command_handlers.CommandHandler;
import ru.tinkoff.edu.java.bot.components.command_handlers.DeleteCommandHandler;
import ru.tinkoff.edu.java.bot.components.command_handlers.HelpCommandHandler;
import ru.tinkoff.edu.java.bot.components.command_handlers.ListCommandHandler;
import ru.tinkoff.edu.java.bot.components.command_handlers.NotFoundCommandHandler;
import ru.tinkoff.edu.java.bot.components.command_handlers.StartCommandHandler;
import ru.tinkoff.edu.java.bot.components.command_handlers.TestCommandHandler;
import ru.tinkoff.edu.java.bot.components.command_handlers.TrackCommandHandler;
import ru.tinkoff.edu.java.bot.components.command_handlers.UntrackCommandHandler;
import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMAND_DELETE;
import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMAND_HELP;
import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMAND_LIST;
import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMAND_START;
import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMAND_TEST;
import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMAND_TRACK;
import static ru.tinkoff.edu.java.bot.components.BotCommands.COMMAND_UNTRACK;

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

