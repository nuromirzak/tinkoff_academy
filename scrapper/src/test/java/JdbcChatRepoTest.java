import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.JdbcChatRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringTestJdbcConfig.class)
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JdbcChatRepoTest extends IntegrationEnvironment {
    @Autowired
    private JdbcChatRepo jdbcChatRepo;

    @Before
    public void setUp() {
        jdbcChatRepo.removeAll();
    }

    @Test
    public void findAllAndPrint() {
        jdbcChatRepo.findAll().forEach(System.out::println);
    }

    @Test
    public void addChat() {
        // Arrange
        long chatId = 2688468954L;
        Chat chat = new Chat();
        chat.setChatId(chatId);

        // Act
        jdbcChatRepo.add(chatId);

        // Assert
        List<Chat> chats = jdbcChatRepo.findAll();
        assertTrue(chats.stream().anyMatch(subscription -> subscription.getChatId().equals(chatId)));
    }

    @Test
    public void removeChat() {
        // Arrange
        long chatId = 2688468954L;
        Chat chat = new Chat();
        chat.setChatId(chatId);

        // Act
        jdbcChatRepo.add(chatId);
        List<Chat> chatsBefore = jdbcChatRepo.findAll();
        jdbcChatRepo.remove(chatId);
        List<Chat> chatsAfter = jdbcChatRepo.findAll();

        // Assert
        assertTrue(chatsAfter.stream().noneMatch(c -> c.getChatId().equals(chatId)));
        assertEquals(chatsAfter.size() + 1, chatsBefore.size());
    }

    @Test
    public void getLinksByChatId() {
        // Arrange
        long chatId = 362037700L;

        // Act
        List<Link> subscriptionsByChatId = jdbcChatRepo.findLinksByChatId(chatId);

        // Assert
        assertEquals(2, subscriptionsByChatId.size());
    }

    @Test
    public void addLinkToChat() {
        // Arrange
        long chatId = 2688468954L;
        long linkId = 20L;
        Chat chat = new Chat();
        chat.setChatId(chatId);
        Link link = new Link();
        link.setLinkId(linkId);

        // Act
        jdbcChatRepo.add(chatId);
        jdbcChatRepo.addLinkToChat(chatId, linkId);
        List<Link> linksByChatId = jdbcChatRepo.findLinksByChatId(chatId);

        // Assert
        assertEquals(1, linksByChatId.size());
    }

    @Test
    public void removeLinkFromChat() {
        // Arrange
        long chatId = 2688468954L;
        long linkId = 20L;
        Chat chat = new Chat();
        chat.setChatId(chatId);
        Link link = new Link();
        link.setLinkId(linkId);

        // Act
        jdbcChatRepo.add(chatId);
        jdbcChatRepo.addLinkToChat(chatId, linkId);
        List<Link> linksByChatIdBefore = jdbcChatRepo.findLinksByChatId(chatId);
        jdbcChatRepo.removeLinkFromChat(chatId, linkId);
        List<Link> linksByChatIdAfter = jdbcChatRepo.findLinksByChatId(chatId);

        // Assert
        assertEquals(1, linksByChatIdBefore.size());
        assertTrue(linksByChatIdAfter.isEmpty());
    }


    @Test
    public void removeAll() {
        // Arrange
        long chatId = 2688468954L;
        Chat chat = new Chat();
        chat.setChatId(chatId);

        // Act
        jdbcChatRepo.add(chatId);
        List<Chat> chatsBefore = jdbcChatRepo.findAll();
        jdbcChatRepo.removeAll();
        List<Chat> chatsAfter = jdbcChatRepo.findAll();

        // Assert
        assertFalse(chatsBefore.isEmpty());
        assertTrue(chatsAfter.isEmpty());
    }
}
