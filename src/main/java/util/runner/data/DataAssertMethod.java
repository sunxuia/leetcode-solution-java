package util.runner.data;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface DataAssertMethod<T> {

    void doAssert(T expect, T actual, BiConsumer<T, T> originAssertMethod);

}
