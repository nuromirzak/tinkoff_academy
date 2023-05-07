package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.dtos.requests.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.requests.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dtos.responses.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dtos.responses.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaLinkRepo;
import ru.tinkoff.edu.java.scrapper.services.impls.TelegramChatServiceImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ScrapperApplication.class, properties = {
        "app.database-access-type=jpa"
})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class TelegramChatServiceImplTest {
    @Autowired
    private TelegramChatServiceImpl telegramChatService;
    @Autowired
    private JpaChatRepo chatRepo;
    @Autowired
    private JpaLinkRepo linkRepo;

    @Test
    public void registerChat() {
        // Arrange
        String chatId = "362037702";

        // Act
        boolean result = telegramChatService.registerChat(chatId);

        // Assert
        assertTrue(result);
        assertNotNull(chatRepo.findChatByChatId(Long.parseLong(chatId)));
    }

    @Test
    public void deleteChat() {
        // Arrange
        String chatId = "362037701";

        // Act
        boolean result = telegramChatService.deleteChat(chatId);

        // Assert
        assertTrue(result);
        Assertions.assertNull(chatRepo.findChatByChatId(Long.parseLong(chatId)));
    }

    @Test
    public void getLinks() {
        // Arrange
        String chatId = "362037700";

        // Act
        ListLinkResponse listLinkResponse = telegramChatService.getLinks(chatId);

        // Assert
        assertEquals(2, listLinkResponse.size());
        assertTrue(listLinkResponse.links().stream().anyMatch(lr -> lr.url().equals("https://github.com/nuromirzak/tinkoff_academy/")));
        assertTrue(listLinkResponse.links().stream().anyMatch(lr -> lr.url().equals("https://stackoverflow.com/questions/5585779/")));
    }

    @Test
    public void addLink() {
        // Arrange
        String chatId = "362037700";
        AddLinkRequest addLinkRequest = new AddLinkRequest("https://github.com/neo4j/neo4j");

        // Act
        LinkResponse linkResponse = telegramChatService.addLink(chatId, addLinkRequest);

        // Assert
        assertNotNull(linkResponse);
        assertEquals(addLinkRequest.link(), linkResponse.url());
        assertTrue(
                linkRepo.findLinksByChatsChatId(Long.parseLong(chatId)).stream().anyMatch(l -> l.getUrl().equals(addLinkRequest.link())));
    }

    @Test
    public void deleteLink() {
        // Arrange
        String chatId = "362037700";
        String url = "https://stackoverflow.com/questions/5585779/";
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(url);

        // Act
        LinkResponse linkResponse = telegramChatService.deleteLink(chatId, removeLinkRequest);

        // Assert
        assertNotNull(linkResponse);
        assertEquals(0, linkResponse.id());
        assertEquals(url, linkResponse.url());
        assertFalse(linkRepo.findLinksByChatsChatId(Long.parseLong(chatId)).stream().anyMatch(l -> l.getUrl().equals(url)));
    }

    @Test
    public void addLinkToNonExistentChat() {
        // Arrange
        String chatId = "362037703";
        AddLinkRequest addLinkRequest = new AddLinkRequest("https://github.com/neo4j/neo4j");

        // Act
        LinkResponse linkResponse = telegramChatService.addLink(chatId, addLinkRequest);

        // Assert
        assertNotNull(linkResponse);
        assertEquals(addLinkRequest.link(), linkResponse.url());
        assertTrue(
                linkRepo.findLinksByChatsChatId(Long.parseLong(chatId)).stream().anyMatch(l -> l.getUrl().equals(addLinkRequest.link())));
        assertTrue(
                chatRepo.existsById(Long.parseLong(chatId)));
    }

    @Test
    public void getLinksForNonExistentChat() {
        // Arrange
        String chatId = "362037703";

        // Act
        ListLinkResponse listLinkResponse = telegramChatService.getLinks(chatId);

        // Assert
        assertEquals(0, listLinkResponse.size());
    }

    @Test
    public void deleteNonExistentLink() {
        // Arrange
        String chatId = "362037700";
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest("https://github.com/non/existent");

        // Act
        assertThrows(RuntimeException.class, () -> telegramChatService.deleteLink(chatId, removeLinkRequest));
    }

    @Test
    public void deleteNonExistentChat() {
        // Arrange
        String chatId = "362037703";

        // Act
        boolean result = telegramChatService.deleteChat(chatId);

        // Assert
        assertFalse(result);
    }
}

