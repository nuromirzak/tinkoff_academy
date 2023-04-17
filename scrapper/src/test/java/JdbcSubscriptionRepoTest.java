import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dtos.Subscription;
import ru.tinkoff.edu.java.scrapper.repo.JdbcSubscriptionRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringTestJdbcConfig.class)
@Transactional
@Sql(scripts = "classpath:populateDB.sql")
public class JdbcSubscriptionRepoTest extends IntegrationEnvironment {
    @Autowired
    private JdbcSubscriptionRepo jdbcSubscriptionRepo;

    @Before
    public void setUp() {
        jdbcSubscriptionRepo.removeAll();
    }

    @Test
    public void findAllAndPrint() {
        jdbcSubscriptionRepo.findAll().forEach(System.out::println);
    }

    @Test
    public void addSubscription() {
        // Arrange
        Long chatId = 2688468954L;
        Long LINK_ID = 20L;
        Subscription subscription = new Subscription();
        subscription.setChatId(chatId);
        subscription.setLinkId(LINK_ID);

        // Act
        jdbcSubscriptionRepo.add(subscription);

        // Assert
        List<Subscription> subscriptions = jdbcSubscriptionRepo.findAll();
        assertTrue(subscriptions.stream().anyMatch(subscription::equals));
    }

    @Test
    public void removeSubscription() {
        // Arrange
        Long chatId = 2688468954L;
        Long LINK_ID = 20L;
        Subscription subscription = new Subscription();
        subscription.setChatId(chatId);
        subscription.setLinkId(LINK_ID);

        // Act
        jdbcSubscriptionRepo.add(subscription);
        List<Subscription> subscriptionsBefore = jdbcSubscriptionRepo.findAll();
        jdbcSubscriptionRepo.remove(subscription);
        List<Subscription> subscriptionsAfter = jdbcSubscriptionRepo.findAll();

        // Assert
        assertTrue(subscriptionsAfter.stream().noneMatch(subscription::equals));
        assertEquals(subscriptionsAfter.size() + 1, subscriptionsBefore.size());
    }
}
