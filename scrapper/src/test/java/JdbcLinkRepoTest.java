import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dtos.Link;
import ru.tinkoff.edu.java.scrapper.repo.JdbcLinkRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringTestJdbcConfig.class)
@Transactional
public class JdbcLinkRepoTest extends IntegrationEnvironment {
    @Autowired
    private JdbcLinkRepo jdbcLinkRepo;

    @Test
    public void findAllAndPrint() {
        jdbcLinkRepo.findAll().forEach(System.out::println);
    }

    @Test
    public void addLink() {
        // Arrange
        Link link = new Link();
        link.setUrl("https://www.tinkoff.ru");
        link.setLastUpdated(null);

        // Act
        jdbcLinkRepo.add(link);

        // Assert
        List<Link> links = jdbcLinkRepo.findAll();
        assertTrue(links.stream().anyMatch(l -> l.getUrl().equals(link.getUrl())));
    }

    @Test
    public void removeLink() {
        // Arrange
        Link link = new Link();
        link.setUrl("https://www.tinkoff.ru");
        link.setLastUpdated(null);

        // Act
        jdbcLinkRepo.add(link);
        List<Link> linksBefore = jdbcLinkRepo.findAll();
        jdbcLinkRepo.remove(link.getUrl());
        List<Link> linksAfter = jdbcLinkRepo.findAll();

        // Assert
        assertTrue(linksAfter.stream().noneMatch(l -> l.getUrl().equals(link.getUrl())));
        assertEquals(linksAfter.size() + 1, linksBefore.size());
    }
}
