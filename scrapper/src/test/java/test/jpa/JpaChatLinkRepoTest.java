package test.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaChatRepo;
import ru.tinkoff.edu.java.scrapper.repo.jpa.JpaLinkRepo;
import test.IntegrationEnvironment;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ScrapperApplication.class, properties = {
    "app.database-access-type=jpa"
})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JpaChatLinkRepoTest extends IntegrationEnvironment {
    private static final int START_INDEX = 100000;
    @Autowired
    private JpaChatRepo chatRepo;
    @Autowired
    private JpaLinkRepo linkRepo;

    @Test
    public void removeLinkFromChat() {
        // Arrange
        long chatId = 362037700L;
        long linkId = START_INDEX + 1;

        // Act
        Chat chatBefore = chatRepo.findChatByChatId(chatId);
        List<Link> linksByChatIdBefore = new ArrayList<>(chatBefore.getLinks());

        chatBefore.getLinks().removeIf(l -> l.getLinkId() == linkId);
        chatRepo.save(chatBefore);

        Chat chatAfter = chatRepo.findChatByChatId(chatId);
        List<Link> linksByChatIdAfter = new ArrayList<>(chatAfter.getLinks());

        // Assert
        assertEquals(linksByChatIdBefore.size() - 1, linksByChatIdAfter.size());
        assertTrue(linksByChatIdAfter.stream().noneMatch(link -> link.getLinkId().equals(linkId)));
    }

    @Test
    public void addLinkToChat() {
        // Arrange
        long chatId = 362037700L;
        long linkId = START_INDEX + 3;
        Link linkToAdd = linkRepo.findByLinkId(linkId);

        // Act
        Chat chatBefore = chatRepo.findChatByChatId(chatId);
        List<Link> linksByChatIdBefore = new ArrayList<>(chatBefore.getLinks());

        chatBefore.getLinks().add(linkToAdd);
        chatRepo.save(chatBefore);

        Chat chatAfter = chatRepo.findChatByChatId(chatId);
        List<Link> linksByChatIdAfter = new ArrayList<>(chatAfter.getLinks());

        // Assert
        assertEquals(linksByChatIdBefore.size() + 1, linksByChatIdAfter.size());
    }
}
