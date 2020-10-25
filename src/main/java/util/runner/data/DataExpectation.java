package util.runner.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import util.asserthelper.AssertUtils;

/**
 * 表示方法类型的LeetCode 中的一个测试数据(测试用例).
 * 测试用例可以是方法返回值, 可以是类中的实例字段 (都需要是 public 类型).
 * 方法返回类型/ 字段类型可以是DataExpection, Collection<DataExpection>, DataExpection[], Map<String, DataExpectation> 等类型.
 */
public class DataExpectation {

    static final DefaultDataAssertMethod<?> DEFAULT_ASSERT_METHOD =
            new DefaultDataAssertMethod<>((e, a, o) -> AssertUtils.assertEquals(e, a), null);

    /**
     * 方法参数
     */
    private Object[] arguments = new Object[0];

    /**
     * 期望数据的值.
     * -1 表示是返回值, >=0 的表示期望的方法运行后的参数值.
     */
    private Map<Integer, Object> expects = new HashMap<>();

    /**
     * 期望数据的判定方法.
     * -1 表示是返回值, >=0 的表示期望的方法运行后的参数值.
     */
    private Map<Integer, DefaultDataAssertMethod<?>> assertMethods = new HashMap<>();

    public Object[] getArguments() {
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = unwrapValue(arguments[i]);
        }
        return arguments;
    }

    private Object unwrapValue(Object value) {
        while (value instanceof LazyDataProvider) {
            value = ((LazyDataProvider<?>) value).get();
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

    public void assertResult(Object actual) {
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

    public static DataExpectationAdaptor createWith(Object... args) {
        return new DataExpectationAdaptor(args);
    }

    public static DataExpectationAdaptor create(Object arg) {
        return new DataExpectationAdaptor(new Object[]{arg});
    }

}
