package test.jdbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.JdbcChatLinkRepo;
import ru.tinkoff.edu.java.scrapper.repo.JdbcLinkRepo;
import test.IntegrationEnvironment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringTestJdbcConfig.class)
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JdbcChatLinkRepoTest extends IntegrationEnvironment {
    @Autowired
    private JdbcChatLinkRepo jdbcChatLinkRepo;
    @Autowired
    private JdbcLinkRepo jdbcLinkRepo;

    @Test
    public void removeLinkFromChat() {
        // Arrange
        long chatId = 362037700L;
        long linkId = 20L;

        // Act
        List<Link> linksByChatIdBefore = jdbcLinkRepo.findLinksByChatId(chatId);
        jdbcChatLinkRepo.removeLinkFromChat(chatId, linkId);
        List<Link> linksByChatIdAfter = jdbcLinkRepo.findLinksByChatId(chatId);

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
        List<Link> linksByChatIdBefore = jdbcLinkRepo.findLinksByChatId(chatId);
        jdbcChatLinkRepo.addLinkToChat(chatId, linkId);
        List<Link> linksByChatIdAfter = jdbcLinkRepo.findLinksByChatId(chatId);

        // Assert
        assertEquals(linksByChatIdBefore.size() + 1, linksByChatIdAfter.size());
    }
}
