package test.jpa;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.dtos.Chat;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.services.impls.jpa.JpaLinkService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ScrapperApplication.class, properties = {
    "app.database-access-type=jpa"
})
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JpaLinkServiceTest {
    @Autowired
    private JpaLinkService linkService;

    @Test
    public void linkAddedSuccessfully() {
        // Arrange
        String url = "https://github.com/facebook/react";
        long tgChatId = 362037700L;

        // Act
        Optional<Link> link = linkService.add(tgChatId, url);

        // Assert
        assertTrue(link.isPresent());
    }

    @Test
    public void duplicateLinkAdded() {
        // Arrange
        String url = "https://github.com/nuromirzak/tinkoff_academy/";
        long tgChatId = 362037700L;

        // Act
        Optional<Link> link = linkService.add(tgChatId, url);

        // Assert
        assertTrue(link.isPresent());
    }

    @Test
    public void listAllLinks() {
        // Arrange
        long tgChatId = 362037700L;

        // Act
        Collection<Link> links = linkService.listAll(tgChatId);

        // Assert
        assertEquals(2, links.size());
    }

    @Test
    public void findAllLinks() {
        // Arrange

        // Act
        Collection<Link> links = linkService.findAll();

        // Assert
        assertEquals(5, links.size());
    }

    @Test
    public void findFollowersOfLink() {
        // Arrange
        String url = "https://github.com/nuromirzak/tinkoff_academy/";

        // Act
        Collection<Chat> followers = linkService.findFollowers(url);

        // Assert
        assertEquals(1, followers.size());
        assertTrue(followers.stream().anyMatch(f -> f.getChatId() == 362037700L));
    }

    @Test
    public void cancelSubscription() {
        // Arrange
        String url = "https://github.com/nuromirzak/tinkoff_academy/";
        long tgChatId = 362037700L;

        // Act
        Collection<Link> before = linkService.listAll(tgChatId);
        linkService.remove(tgChatId, url);
        Collection<Link> after = linkService.listAll(tgChatId);

        // Assert
        assertEquals(before.size() - 1, after.size());
    }

    @Test
    public void addValidStackOverflowLink() {
        // Arrange
        String url = "https://stackoverflow.com/questions/38480582";
        long tgChatId = 362037700L;

        // Act
        Optional<Link> link = linkService.add(tgChatId, url);

        // Assert
        assertTrue(link.isPresent());
    }

    @Test
    public void addInvalidLink() {
        // Arrange
        String url = "https://invalid.url";
        long tgChatId = 362037700L;

        // Act
        Optional<Link> link = linkService.add(tgChatId, url);

        // Assert
        assertFalse(link.isPresent());
    }

    @Test
    public void removeLinkNotInChat() {
        // Arrange
        String url = "https://github.com/facebook/react";
        long tgChatId = 362037701L; // Chat ID not containing the link

        // Act
        boolean result = linkService.remove(tgChatId, url);

        // Assert
        assertFalse(result);
    }

    @Test
    public void listAllLinksWhenNoLinks() {
        // Arrange
        long tgChatId = 362037702L; // Chat ID with no links

        // Act
        Collection<Link> links = linkService.listAll(tgChatId);

        // Assert
        assertEquals(0, links.size());
    }

    @Test
    public void findFollowersForLinkWithNoFollowers() {
        // Arrange
        String url = "https://github.com/no-followers/link";

        // Act
        Collection<Chat> followers = linkService.findFollowers(url);

        // Assert
        assertEquals(0, followers.size());
    }

    @Test
    public void findLinksToScrapSinceDuration() {
        // Arrange
        Duration checkInterval = Duration.ofHours(1);

        // Act
        List<Link> linksToScrap = linkService.findLinksToScrapSince(checkInterval);

        // Assert
        assertNotNull(linksToScrap);
        // Add specific assertions based on the data in the 'populateDB.sql' script
    }

    @Test
    public void addLinkWithUnsupportedHost() {
        // Arrange
        String url = "https://unsupported.host/";
        long tgChatId = 362037700L;

        // Act
        Optional<Link> link = linkService.add(tgChatId, url);

        // Assert
        assertFalse(link.isPresent());
    }

    @Test
    public void removeLinkNotExists() {
        // Arrange
        String url = "https://github.com/not/exist";
        long tgChatId = 362037700L;

        // Act
        boolean result = linkService.remove(tgChatId, url);

        // Assert
        assertFalse(result);
    }

    @Test
    public void listAllLinksWhenMultipleChats() {
        // Arrange
        long tgChatId1 = 362037700L;
        long tgChatId2 = 362037701L;

        // Act
        Collection<Link> links1 = linkService.listAll(tgChatId1);
        Collection<Link> links2 = linkService.listAll(tgChatId2);

        // Assert
        assertNotEquals(links1.size(), links2.size());
    }

    @Test
    public void findLinksToScrapSinceWithDurationZero() {
        // Arrange
        Duration checkInterval = Duration.ZERO;

        // Act
        List<Link> linksToScrap = linkService.findLinksToScrapSince(checkInterval);

        // Assert
        assertNotNull(linksToScrap);
        // Add specific assertions based on the data in the 'populateDB.sql' script
    }
}
