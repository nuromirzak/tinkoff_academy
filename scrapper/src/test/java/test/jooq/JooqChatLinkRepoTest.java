package test.jooq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.ChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.jooq.JooqChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.jooq.JooqLinkRepo;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ScrapperApplication.class, properties = {
        "app.database-access-type=jooq"
})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JooqChatLinkRepoTest {
    @Autowired
    private JooqChatLinkRepo chatLinkRepo;
    @Autowired
    private JooqLinkRepo linkRepo;
    private static final int START_INDEX = 100000;

    @Test
    public void removeLinkFromChat() {
        // Arrange
        long chatId = 362037700L;
        long linkId = START_INDEX + 1;

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
        long linkId = START_INDEX + 3;

        // Act
        List<Link> linksByChatIdBefore = linkRepo.findLinksByChatId(chatId);
        chatLinkRepo.addLinkToChat(chatId, linkId);
        List<Link> linksByChatIdAfter = linkRepo.findLinksByChatId(chatId);

        // Assert
        assertEquals(linksByChatIdBefore.size() + 1, linksByChatIdAfter.size());
    }
}
