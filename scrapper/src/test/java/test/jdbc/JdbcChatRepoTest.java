package test.jdbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;
import test.DataSourceConfig;
import test.IntegrationEnvironment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestJdbcConfig.class, DataSourceConfig.class})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JdbcChatRepoTest extends IntegrationEnvironment {
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
