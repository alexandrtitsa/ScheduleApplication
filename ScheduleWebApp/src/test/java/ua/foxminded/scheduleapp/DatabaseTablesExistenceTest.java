package ua.foxminded.scheduleapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
public class DatabaseTablesExistenceTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("schedule_test_db")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private DataSource dataSource;

    @Test
    void testTablesAreCreated() throws SQLException {
        List<String> expectedTables = Arrays.asList("users", "students", "teachers", "groups", "courses", "schedules", "teacher_courses", "student_schedules");

        try (Connection connection = dataSource.getConnection()) {
            for (String tableName : expectedTables) {
                ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);
                assertTrue(resultSet.next(), "Table " + tableName + " should exist");
            }
        }
    }
}
