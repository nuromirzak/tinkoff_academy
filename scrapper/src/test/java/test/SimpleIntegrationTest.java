package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleIntegrationTest extends IntegrationEnvironment {
    private static Connection connection;

    @BeforeAll
    static void setUp() {
        connection = openConnection();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    @DisplayName("Check that the database is up and running")
    void checkDatabaseIsUp() {
        assertTrue(POSTGRE_SQL_CONTAINER.isRunning());
    }

    @Test
    @DisplayName("Contains the expected tables")
    void checkTables() throws SQLException {
        // Arrange
        Set<String> actualTables = new HashSet<>();
        Set<String> expectedMyTables = Set.of("chat", "link", "link_chat");
        Set<String> expectedLiquibaseTables = Set.of("databasechangelog", "databasechangeloglock");

        // Act
        ResultSet rs = connection.getMetaData().getTables(null, null, "%", new String[] {"TABLE"});
        while (rs.next()) {
            actualTables.add(rs.getString("TABLE_NAME"));
        }

        // Assert
        assertTrue(actualTables.containsAll(expectedMyTables));
        assertTrue(actualTables.containsAll(expectedLiquibaseTables));
    }

    @Test
    @DisplayName("Liquibase has been successfully run")
    void checkLiquibase() throws SQLException {
        // Arrange
        Set<String> actualTables = new HashSet<>();
        Set<String> expectedLiquibaseTables = Set.of("databasechangelog", "databasechangeloglock");
        Set<String> execTypes = new HashSet<>();

        // Act
        ResultSet rs = connection.getMetaData().getTables(null, null, "%", new String[] {"TABLE"});
        while (rs.next()) {
            actualTables.add(rs.getString("TABLE_NAME"));
        }
        Statement statement = connection.createStatement();
        ResultSet rs2 = statement.executeQuery("SELECT exectype FROM databasechangelog");
        while (rs2.next()) {
            execTypes.add(rs2.getString("exectype"));
        }

        // Assert
        assertTrue(actualTables.containsAll(expectedLiquibaseTables));
        assertTrue(execTypes.stream().noneMatch("FAILED"::equals));
    }
}
