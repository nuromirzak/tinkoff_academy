package ru.tinkoff.edu.java.bot.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.dtos.LinkResponse;
import ru.tinkoff.edu.java.bot.dtos.ListLinkResponse;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TextMessageHandlerTest {
    private static final Long USER_ID = 362037700L;
    private final ScrapperClient scrapperClient = new ScrapperClientStub();

    private TextMessageHandler textMessageHandler;
    private Update update;

    @BeforeEach
    void setUp() {
        textMessageHandler = new TextMessageHandler(scrapperClient);

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
        String response = textMessageHandler.handleTextMessage(update, "/list");
        String expected = TextMessageHandler.ERROR_MESSAGE_NO_LINKS;

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testAddLinkWhenRegistered() {
        // Arrange
        textMessageHandler.handleTextMessage(update, "/start");
        textMessageHandler.handleTextMessage(update, "/track https://www.google.com");

        // Act
        String response = textMessageHandler.handleTextMessage(update, "/list");
        LinkResponse response1 = new LinkResponse(0, "https://www.google.com");
        ListLinkResponse listLinkResponse = new ListLinkResponse(Collections.singletonList(response1), 1);
        String expected = String.format(TextMessageHandler.commands.get("/list"), listLinkResponse);

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testAddLinkWhenNotRegistered() {
        // Arrange
        textMessageHandler.handleTextMessage(update, "/track https://www.google.com");

        // Act
        String response = textMessageHandler.handleTextMessage(update, "/list");
        String expected = TextMessageHandler.ERROR_MESSAGE_NO_LINKS;

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testAddLinkWhenNotRegisteredAndRegistered() {
        // Arrange
        textMessageHandler.handleTextMessage(update, "/track https://www.google.com");
        textMessageHandler.handleTextMessage(update, "/start");
        textMessageHandler.handleTextMessage(update, "/track https://www.google.com");

        // Act
        String response = textMessageHandler.handleTextMessage(update, "/list");
        LinkResponse response1 = new LinkResponse(0, "https://www.google.com");
        ListLinkResponse listLinkResponse = new ListLinkResponse(Collections.singletonList(response1), 1);
        String expected = String.format(TextMessageHandler.commands.get("/list"), listLinkResponse);

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testListLinksMultiple() {
        // Arrange
        textMessageHandler.handleTextMessage(update, "/start");
        textMessageHandler.handleTextMessage(update, "/track https://www.google.com");
        textMessageHandler.handleTextMessage(update, "/track https://www.facebook.com");

        // Act
        String response = textMessageHandler.handleTextMessage(update, "/list");
        LinkResponse response1 = new LinkResponse(0, "https://www.google.com");
        LinkResponse response2 = new LinkResponse(1, "https://www.facebook.com");
        ListLinkResponse listLinkResponse = new ListLinkResponse(Arrays.asList(response1, response2), 2);
        String expected = String.format(TextMessageHandler.commands.get("/list"), listLinkResponse);

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testListLinksEmpty() {
        // Arrange
        textMessageHandler.handleTextMessage(update, "/start");

        // Act
        String response = textMessageHandler.handleTextMessage(update, "/list");

        // Assert
        assertEquals(TextMessageHandler.ERROR_MESSAGE_NO_LINKS, response);
    }
}