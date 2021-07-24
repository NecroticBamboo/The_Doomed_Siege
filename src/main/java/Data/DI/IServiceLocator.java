package Data.DI;

public interface IServiceLocator {
    <T> T getService(String name);
}
