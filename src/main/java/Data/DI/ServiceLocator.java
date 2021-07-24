package Data.DI;

import java.util.Dictionary;
import java.util.Hashtable;

public class ServiceLocator implements IServiceLocator {
    Dictionary<String, Object> di = new Hashtable<>();

    @Override
    public <T> T getService(String name) {
        return (T) di.get(name);
    }

    public <T> void setService(String serviceName, T o) {
        di.put(serviceName,o);
    }
}
