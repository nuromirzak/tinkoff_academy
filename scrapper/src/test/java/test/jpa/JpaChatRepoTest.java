package test.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaChatRepo;
import test.IntegrationEnvironment;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ScrapperApplication.class, properties = {
        "app.database-access-type=jpa"
})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JpaChatRepoTest extends IntegrationEnvironment {
    @Autowired
    private JpaChatRepo chatRepo;

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
        chatRepo.save(chat);

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
        chatRepo.save(chat);
        List<Chat> chatsBefore = chatRepo.findAll();
        chatRepo.delete(chat);
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
        chatRepo.save(chat);
        List<Chat> chatsBefore = chatRepo.findAll();
        chatRepo.removeAllBy();
        List<Chat> chatsAfter = chatRepo.findAll();

        // Assert
        assertFalse(chatsBefore.isEmpty());
        assertTrue(chatsAfter.isEmpty());
    }
}
