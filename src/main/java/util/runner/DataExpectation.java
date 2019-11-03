package util.runner;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import util.asserthelper.AssertUtils;

public class DataExpectation {

    private static final BiConsumer defaultAssertMethod = AssertUtils::assertEquals;

    private Object[] arguments;

    private Map<Integer, Object> expects = new HashMap<>();

    private Map<Integer, BiConsumer> assertMethods = new HashMap<>();

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Object getExpect() {
        return expects.get(-1);
    }

    public void setExpect(Object expect) {
        this.expects.put(-1, expect);
    }

    public void setExpectArgument(int i, Object expect) {
        if (i < 0) {
            throw new IllegalArgumentException("Argument index can not be empty.");
        }
        this.expects.put(i, expect);
    }

    public void setExpectAssertMethod(BiConsumer assertMethod) {
        assertMethods.put(-1, assertMethod);
    }

    public void setArgumentAssertMethod(int i, BiConsumer assertMethod) {
        if (i < 0) {
            throw new IllegalArgumentException("Argument index can not be empty.");
        }
        assertMethods.put(i, assertMethod);
    }

    public void assertEquals(Object actual) {
        expects.forEach((index, expected) -> {
            Object act = index == -1 ? actual : arguments[index];
            BiConsumer assertMethod = assertMethods.getOrDefault(index, defaultAssertMethod);
            assertMethod.accept(expected, act);
        });
    }

    public static DataExpectationBuilder builder() {
        return new DataExpectationBuilder();
    }

    public static SimpleDataExpectationBuilder createWith(Object... arguments) {
        return new SimpleDataExpectationBuilder(arguments);
    }

    public static SimpleDataExpectationBuilder create(Object singleArgument) {
        return new SimpleDataExpectationBuilder(new Object[]{singleArgument});
    }

}
