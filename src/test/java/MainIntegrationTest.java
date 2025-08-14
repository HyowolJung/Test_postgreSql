import org.testcontainers.containers.PostgreSQLContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.*;

public class MainIntegrationTest {
    static PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void start() { pg.start(); }
    @AfterAll
    static void stop()  { pg.stop(); }

    @Test
    void queryWorks() throws Exception {
        try (Connection c = DriverManager.getConnection(pg.getJdbcUrl(), pg.getUsername(), pg.getPassword());
             Statement st = c.createStatement()) {

            st.executeUpdate("CREATE TABLE table_test(id serial primary key, name text not null, joined timestamp default now())");
            st.executeUpdate("INSERT INTO table_test(name) VALUES ('Alice'),('Bob')");

            try (PreparedStatement ps = c.prepareStatement("SELECT id,name,joined FROM table_test ORDER BY id");
                 ResultSet rs = ps.executeQuery()) {
                int count = 0;
                while (rs.next()) count++;
                assertEquals(2, count);
            }
        }
    }
}
