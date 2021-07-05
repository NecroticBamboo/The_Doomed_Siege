package Data.utils;

import java.sql.Connection;

public class ConnectionInfo {
    private int connectionId;
    private Connection connection;

    public ConnectionInfo(int connectionIdIn,Connection connectionIn){
        connectionId=connectionIdIn;
        connection=connectionIn;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public Connection getConnection() {
        return connection;
    }
}
