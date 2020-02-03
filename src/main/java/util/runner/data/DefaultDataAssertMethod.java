package util.runner.data;

import java.util.function.BiConsumer;

public class DefaultDataAssertMethod<T> implements DataAssertMethod<T> {

    private final DataAssertMethod<T> assertMethod;

    private final DefaultDataAssertMethod<T> originalAssertMethod;

    public DefaultDataAssertMethod(DataAssertMethod<T> assertMethod, DefaultDataAssertMethod<T> originalAssertMethod) {
        this.assertMethod = assertMethod;
        this.originalAssertMethod = originalAssertMethod;
    }

    @Override
    public void doAssert(T expect, T actual, BiConsumer<T, T> originAssertMethod) {
        assertMethod.doAssert(expect, actual, originAssertMethod);
    }

    void doAssert(T expect, T actual) {
        assertMethod.doAssert(expect, actual, originalAssertMethod == null ? null : originalAssertMethod::doAssert);
    }

}
