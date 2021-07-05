package Data.utils;

public interface IDatabaseManager {
    ConnectionInfo acquire();
    void release(ConnectionInfo connectionInfo);
}
