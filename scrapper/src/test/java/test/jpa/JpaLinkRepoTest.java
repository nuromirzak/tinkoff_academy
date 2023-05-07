package test.jpa;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.LinkRepo;
import test.IntegrationEnvironment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ScrapperApplication.class, properties = {
    "app.database-access-type=jpa"
})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JpaLinkRepoTest extends IntegrationEnvironment {
    @Autowired
    private LinkRepo linkRepo;

    @Test
    public void findAllAndPrint() {
        linkRepo.findAll().forEach(System.out::println);
    }

    @Test
    public void addLink() {
        // Arrange
        Link link = new Link();
        link.setUrl("https://www.tinkoff.ru");
        link.setLastUpdated(null);

        // Act
        linkRepo.add(link);

        // Assert
        List<Link> links = linkRepo.findAll();
        assertTrue(links.stream().anyMatch(l -> l.getUrl().equals(link.getUrl())));
    }

    @Test
    public void removeLink() {
        // Arrange
        Link link = new Link();
        link.setUrl("https://www.tinkoff.ru");
        link.setLastUpdated(null);

        // Act
        linkRepo.add(link);
        List<Link> linksBefore = linkRepo.findAll();
        linkRepo.remove(link.getUrl());
        List<Link> linksAfter = linkRepo.findAll();

        // Assert
        assertTrue(linksAfter.stream().noneMatch(l -> l.getUrl().equals(link.getUrl())));
        assertEquals(linksAfter.size() + 1, linksBefore.size());
    }

    @Test
    public void getLinksByChatId() {
        // Arrange
        long chatId = 362037700L;

        // Act
        List<Link> subscriptionsByChatId = linkRepo.findLinksByChatId(chatId);

        // Assert
        assertEquals(2, subscriptionsByChatId.size());
    }

    @Test
    public void getLinksToScrap() {
        // Assert
        Duration fifteenMinutes = Duration.ofMinutes(15);

        // Act
        List<Link> linksToUpdate = linkRepo.findLinksToScrap(fifteenMinutes);

        // Assert
        assertEquals(4, linksToUpdate.size());
    }
}
