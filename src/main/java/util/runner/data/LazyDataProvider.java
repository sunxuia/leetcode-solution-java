package util.runner.data;

@FunctionalInterface
public interface LazyDataProvider<T> {

    T get();

    static <T> LazyDataProvider<T> create(LazyDataProvider<T> provider) {
        return provider;
    }

}
