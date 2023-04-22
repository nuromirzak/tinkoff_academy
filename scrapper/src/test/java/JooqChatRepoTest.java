import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.ChatRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestJooqConfig.class, DataSourceConfig.class})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JooqChatRepoTest extends IntegrationEnvironment {
    @Autowired
    private ChatRepo chatRepo;

    @Before
    public void setUp() {
        chatRepo.removeAll();
    }

    @Test
    public void findAllAndPrint() {
        System.out.println("---Start---");
        chatRepo.findAll().forEach(System.out::println);
        System.out.println("---End---");
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
    public void getLinksByChatId() {
        // Arrange
        long chatId = 362037700L;

        // Act
        List<Link> subscriptionsByChatId = chatRepo.findLinksByChatId(chatId);

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
        chatRepo.add(chatId);
        chatRepo.addLinkToChat(chatId, linkId);
        List<Link> linksByChatId = chatRepo.findLinksByChatId(chatId);

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
        boolean chatAdded = chatRepo.add(chatId);
        System.out.println("chatAdded = " + chatAdded);
        boolean linkAdded = chatRepo.addLinkToChat(chatId, linkId);
        System.out.println("linkAdded = " + linkAdded);
        List<Link> linksByChatIdBefore = chatRepo.findLinksByChatId(chatId);
        System.out.println("linksByChatIdBefore = " + linksByChatIdBefore);
        chatRepo.removeLinkFromChat(chatId, linkId);
        List<Link> linksByChatIdAfter = chatRepo.findLinksByChatId(chatId);
        System.out.println("linksByChatIdAfter = " + linksByChatIdAfter);

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
        chatRepo.add(chatId);
        List<Chat> chatsBefore = chatRepo.findAll();
        chatRepo.removeAll();
        List<Chat> chatsAfter = chatRepo.findAll();

        // Assert
        assertFalse(chatsBefore.isEmpty());
        assertTrue(chatsAfter.isEmpty());
    }
}
