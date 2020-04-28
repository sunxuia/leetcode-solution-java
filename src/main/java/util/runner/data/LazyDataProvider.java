package util.runner.data;

import java.util.function.Function;

@FunctionalInterface
public interface LazyDataProvider<T> {

    T get();

    static <T> LazyDataProvider<T> create(LazyDataProvider<T> provider) {
        return provider;
    }

    default <R> LazyDataProvider<R> then(Function<T, R> converter) {
        return () -> {
            T val = get();
            return converter.apply(val);
        };
    }

}
