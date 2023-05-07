package test;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class IntegrationEnvironment {
    public static PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;
    private static final String IMAGE_NAME = "postgres:15";

    private static final String CHANGELOG_PATH = "master.xml";
    private static final Path ROOT_DIRECTORY = Path.of(System.getProperty("user.dir"), "migrations");

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(IMAGE_NAME);
        POSTGRE_SQL_CONTAINER.start();
        startMigration();
    }

    static void startMigration() {
        try (java.sql.Connection connection = openConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(CHANGELOG_PATH, new DirectoryResourceAccessor(ROOT_DIRECTORY), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException | FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static Connection openConnection() {
        try {
            return DriverManager.getConnection(
                    POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                    POSTGRE_SQL_CONTAINER.getUsername(),
                    POSTGRE_SQL_CONTAINER.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("app.db_url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("app.db_username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("app.db_password", POSTGRE_SQL_CONTAINER::getPassword);
    }
}
