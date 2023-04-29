package test.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.ChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import test.IntegrationEnvironment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ScrapperApplication.class, properties = {
        "app.database-access-type=jpa"
})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JpaChatLinkRepoTest extends IntegrationEnvironment {
    @Autowired
    private ChatLinkRepo chatLinkRepo;
    @Autowired
    private LinkRepo linkRepo;

    @Test
    public void removeLinkFromChat() {
        // Arrange
        long chatId = 362037700L;
        long linkId = 20L;

        // Act
        List<Link> linksByChatIdBefore = linkRepo.findLinksByChatId(chatId);
        chatLinkRepo.removeLinkFromChat(chatId, linkId);
        List<Link> linksByChatIdAfter = linkRepo.findLinksByChatId(chatId);

        // Assert
        assertEquals(linksByChatIdBefore.size() - 1, linksByChatIdAfter.size());
        assertTrue(linksByChatIdAfter.stream().noneMatch(link -> link.getLinkId().equals(linkId)));
    }

    @Test
    public void addLinkToChat() {
        // Arrange
        long chatId = 362037700L;
        long linkId = 40L;

        // Act
        List<Link> linksByChatIdBefore = linkRepo.findLinksByChatId(chatId);
        chatLinkRepo.addLinkToChat(chatId, linkId);
        List<Link> linksByChatIdAfter = linkRepo.findLinksByChatId(chatId);

        // Assert
        assertEquals(linksByChatIdBefore.size() + 1, linksByChatIdAfter.size());
    }
}
