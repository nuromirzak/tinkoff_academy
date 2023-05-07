package ru.tinkoff.edu.java.bot.components;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.dtos.LinkResponse;
import ru.tinkoff.edu.java.bot.dtos.ListLinkResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TextMessageHandlerTest {
    private static final Long USER_ID = 362037700L;
    private final ScrapperClient scrapperClient = new ScrapperClientStub();

    private CommandRouter commandRouter;
    private Update update;

    @BeforeEach
    void setUp() {
        CommandHandlerFactory commandHandlerFactory = new CommandHandlerFactory(scrapperClient);
        commandRouter = new CommandRouter(commandHandlerFactory);

        User user = new User();
        user.setId(USER_ID);

        Chat chat = new Chat();
        chat.setId(USER_ID);

        Message message = new Message();
        message.setFrom(user);
        message.setChat(chat);

        update = new Update();
        update.setMessage(message);

        scrapperClient.removeChat(String.valueOf(USER_ID));
    }

    @Test
    void testWhenNoLinks() {
        // Act
        String response = commandRouter.routeCommand(update, "/list");
        String expected = BotCommands.ERROR_MESSAGE_NO_LINKS;

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testAddLinkWhenRegistered() {
        // Arrange
        commandRouter.routeCommand(update, "/start");
        commandRouter.routeCommand(update, "/track https://www.google.com");

        // Act
        String response = commandRouter.routeCommand(update, "/list");
        LinkResponse response1 = new LinkResponse(0, "https://www.google.com");
        ListLinkResponse listLinkResponse = new ListLinkResponse(Collections.singletonList(response1), 1);
        String expected = String.format(BotCommands.COMMANDS.get("/list"), listLinkResponse);

        // AssertBotCommands
        assertEquals(expected, response);
    }

    @Test
    void testAddLinkWhenNotRegistered() {
        // Arrange
        commandRouter.routeCommand(update, "/track https://www.google.com");

        // Act
        String response = commandRouter.routeCommand(update, "/list");
        String expected = BotCommands.ERROR_MESSAGE_NO_LINKS;

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testAddLinkWhenNotRegisteredAndRegistered() {
        // Arrange
        commandRouter.routeCommand(update, "/track https://www.google.com");
        commandRouter.routeCommand(update, "/start");
        commandRouter.routeCommand(update, "/track https://www.google.com");

        // Act
        String response = commandRouter.routeCommand(update, "/list");
        LinkResponse response1 = new LinkResponse(0, "https://www.google.com");
        ListLinkResponse listLinkResponse = new ListLinkResponse(Collections.singletonList(response1), 1);
        String expected = String.format(BotCommands.COMMANDS.get("/list"), listLinkResponse);

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testListLinksMultiple() {
        // Arrange
        commandRouter.routeCommand(update, "/start");
        commandRouter.routeCommand(update, "/track https://www.google.com");
        commandRouter.routeCommand(update, "/track https://www.facebook.com");

        // Act
        String response = commandRouter.routeCommand(update, "/list");
        LinkResponse response1 = new LinkResponse(0, "https://www.google.com");
        LinkResponse response2 = new LinkResponse(1, "https://www.facebook.com");
        ListLinkResponse listLinkResponse = new ListLinkResponse(Arrays.asList(response1, response2), 2);
        String expected = String.format(BotCommands.COMMANDS.get("/list"), listLinkResponse);

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testListLinksEmpty() {
        // Arrange
        commandRouter.routeCommand(update, "/start");

        // Act
        String response = commandRouter.routeCommand(update, "/list");

        // Assert
        assertEquals(BotCommands.ERROR_MESSAGE_NO_LINKS, response);
    }
}
