package scraper;

@FunctionalInterface
public interface ThrowableHandler<T> {
  void handle(T t) throws Throwable;
}
