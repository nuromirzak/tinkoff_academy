package test.jpa;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.configurations.databases.JpaAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;
import test.IntegrationEnvironment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ScrapperApplication.class, properties = {
        "app.database-access-type=jpa"
})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JpaChatRepoTest extends IntegrationEnvironment {
    @Autowired
    private ChatRepo chatRepo;

    @Test
    public void findAllAndPrint() {
        chatRepo.findAll().forEach(System.out::println);
    }

    @Test
    public void addChat() {
        // Arrange
        long chatId = 2688468954L;
        Chat chat = new Chat();
        chat.setChatId(chatId);

        // Act
        chatRepo.add(chatId);

        // Assert
        List<Chat> chats = chatRepo.findAll();
        assertTrue(chats.stream().anyMatch(subscription -> subscription.getChatId().equals(chatId)));
    }

    @Test
    public void removeChat() {
        // Arrange
        long chatId = 2688468954L;
        Chat chat = new Chat();
        chat.setChatId(chatId);

        // Act
        chatRepo.add(chatId);
        List<Chat> chatsBefore = chatRepo.findAll();
        chatRepo.remove(chatId);
        List<Chat> chatsAfter = chatRepo.findAll();

        // Assert
        assertTrue(chatsAfter.stream().noneMatch(c -> c.getChatId().equals(chatId)));
        assertEquals(chatsAfter.size() + 1, chatsBefore.size());
    }

    @Test
    public void removeAll() {
        // Arrange
        long chatId = 2688468954L;
        Chat chat = new Chat();
        chat.setChatId(chatId);

        // Act
        chatRepo.add(chatId);
        List<Chat> chatsBefore = chatRepo.findAll();
        chatRepo.removeAll();
        List<Chat> chatsAfter = chatRepo.findAll();

        // Assert
        assertFalse(chatsBefore.isEmpty());
        assertTrue(chatsAfter.isEmpty());
    }
}