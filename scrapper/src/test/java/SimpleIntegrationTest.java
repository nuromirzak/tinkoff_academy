import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleIntegrationTest extends IntegrationEnvironment {
    @Test
    @DisplayName("Check that the database is up and running")
    void checkDatabaseIsUp() {
        assertTrue(POSTGRE_SQL_CONTAINER.isRunning());
    }

    @Test
    @DisplayName("Contains the expected tables")
    void checkTables() {
        // Arrange
        Set<String> actualTables = new HashSet<>();
        Set<String> expectedTables = Set.of("chat", "link", "databasechangelog", "databasechangeloglock");

        // Act
        try (Connection connection = DriverManager.getConnection(
                POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                POSTGRE_SQL_CONTAINER.getUsername(),
                POSTGRE_SQL_CONTAINER.getPassword())) {
            ResultSet rs = connection.getMetaData().getTables(null, null, "%", new String[] { "TABLE" });
            while (rs.next()) {
                actualTables.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertTrue(actualTables.containsAll(expectedTables));
    }
}
