package test.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaChatRepo;
import ru.tinkoff.edu.java.scrapper.services.impls.jpa.JpaTgChatService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ScrapperApplication.class, properties = {
        "app.database-access-type=jpa"
})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JpaTgChatServiceTest {

    @Autowired
    private JpaTgChatService tgChatService;

    @Autowired
    private JpaChatRepo chatRepo;

    @Test
    public void registerNewChat() {
        // Arrange
        long newTgChatId = 100000000L;

        // Act
        boolean isRegistered = tgChatService.register(newTgChatId);

        // Assert
        assertTrue(isRegistered);
        assertNotNull(chatRepo.findChatByChatId(newTgChatId));
    }

    @Test
    public void registerExistingChat() {
        // Arrange
        long existingTgChatId = 362037700L;

        // Act
        boolean isRegistered = tgChatService.register(existingTgChatId);

        // Assert
        assertTrue(isRegistered);
        assertNotNull(chatRepo.findChatByChatId(existingTgChatId));
    }

    @Test
    public void unregisterExistingChat() {
        // Arrange
        long existingTgChatId = 362037700L;

        // Act
        boolean isUnregistered = tgChatService.unregister(existingTgChatId);

        // Assert
        assertTrue(isUnregistered);
        assertNull(chatRepo.findChatByChatId(existingTgChatId));
    }

    @Test
    public void unregisterNonExistingChat() {
        // Arrange
        long nonExistingTgChatId = 100000000L;

        // Act
        boolean isUnregistered = tgChatService.unregister(nonExistingTgChatId);

        // Assert
        assertFalse(isUnregistered);
    }
}
