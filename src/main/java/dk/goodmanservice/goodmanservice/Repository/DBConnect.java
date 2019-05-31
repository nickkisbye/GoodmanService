package dk.goodmanservice.goodmanservice.Repository;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lavet af Nick
 */

/**
 * Denne klasse skaber connection til alle repository klasserne.
 */

@Repository
public class DBConnect {

    private Connection connection;

    public DBConnect() throws SQLException {

            this.connection = DriverManager.getConnection(
                    "");
    }

    public Connection getConnection() {
        return connection;
    }

}
