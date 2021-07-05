package Data.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class can be used to initialize the database connection
public class DatabaseConnection implements IDatabaseManager {
    private Connection connection;

    @Override
    public ConnectionInfo acquire() {
        try {
            if (connection == null) {
                connection = initializeDatabase();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return new ConnectionInfo(1, connection);
    }

    @Override
    public void release(ConnectionInfo connectionInfo) {

    }

    private static Connection initializeDatabase()
            throws SQLException, ClassNotFoundException {

        String url = "jdbc:sqlite:QuestLog.sqlite";

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
