package util.runner.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import util.asserthelper.AssertUtils;

public class DataExpectation {

    static final DefaultDataAssertMethod<?> DEFAULT_ASSERT_METHOD = new DefaultDataAssertMethod<>((
            e, a, o) -> AssertUtils.assertEquals(e, a), null);

    private Object[] arguments;

    private Map<Integer, Object> expects = new HashMap<>();

    private Map<Integer, DefaultDataAssertMethod<?>> assertMethods = new HashMap<>();

    public Object[] getArguments() {
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = unwrapValue(arguments[i]);
        }
        return arguments;
    }

    private Object unwrapValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LazyDataProvider) {
            return ((LazyDataProvider) value).get();
        }
        return value;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Object getExpect() {
        Object res = unwrapValue(expects.get(-1));
        expects.put(-1, res);
        return res;
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

    public void setExpectAssertMethod(DataAssertMethod<?> assertMethod) {
        assertMethods.put(-1, wrapDefaultAssertMethod(assertMethod));
    }

    private <T> DefaultDataAssertMethod<T> wrapDefaultAssertMethod(DataAssertMethod<T> assertMethod) {
        if (assertMethod instanceof DefaultDataAssertMethod) {
            return (DefaultDataAssertMethod<T>) assertMethod;
        }
        return new DefaultDataAssertMethod<>(assertMethod, null);
    }

    public void setArgumentAssertMethod(int i, DataAssertMethod<?> assertMethod) {
        if (i < 0) {
            throw new IllegalArgumentException("Argument index can not be empty.");
        }
        assertMethods.put(i, wrapDefaultAssertMethod(assertMethod));
    }

    public void assertEquals(Object actual) {
        for (Entry<Integer, Object> entry : expects.entrySet()) {
            int index = entry.getKey();
            Object expected = entry.getValue();
            expected = unwrapValue(expected);
            entry.setValue(expected);

            Object act = index == -1 ? actual : arguments[index];
            getAssertMethod(index).doAssert(expected, act);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> DefaultDataAssertMethod<T> getAssertMethod(int idx) {
        return (DefaultDataAssertMethod<T>) assertMethods.getOrDefault(idx, DEFAULT_ASSERT_METHOD);
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
