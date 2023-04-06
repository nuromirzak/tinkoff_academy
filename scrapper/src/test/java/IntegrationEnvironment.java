import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class IntegrationEnvironment {
    protected static PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;
    private static final String IMAGE_NAME = "postgres:15";
    private static final String DB_NAME = "test";
    private static final String USERNAME = "duke";
    private static final String PASSWORD = "s3cret";

    private static final String CHANGELOG_PATH = "master.xml";
    private static final Path ROOT_DIRECTORY = Path.of(System.getProperty("user.dir")).getParent().resolve("migrations");

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(IMAGE_NAME);
        POSTGRE_SQL_CONTAINER
                .withDatabaseName(DB_NAME)
                .withUsername(USERNAME)
                .withPassword(PASSWORD);
        POSTGRE_SQL_CONTAINER.start();
        startMigration();
    }

    static void startMigration() {
        try (java.sql.Connection connection = DriverManager.getConnection(
                POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                POSTGRE_SQL_CONTAINER.getUsername(),
                POSTGRE_SQL_CONTAINER.getPassword())) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(CHANGELOG_PATH, new DirectoryResourceAccessor(ROOT_DIRECTORY), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException | FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
