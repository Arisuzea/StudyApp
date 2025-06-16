package studyapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:studyapp.db";

    // Establishes and returns a connection to the SQLite database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
