package util.runner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.junit.Assert;
import util.asserthelper.AssertUtils;
import util.asserthelper.ObjectEqualsHelper;

public class DataExpectationBuilder {

    private List<Object> arguments = new ArrayList<>();

    private Map<Integer, Object> expects = new HashMap<>();

    private Map<Integer, BiConsumer> assertMethods = new HashMap<>();


    public DataExpectationBuilder addArgument(Object val) {
        arguments.add(val);
        return this;
    }

    public DataExpectationBuilder addArgument(LazyDataProvider provider) {
        arguments.add(provider);
        return this;
    }

    public DataExpectationBuilder expect(Object val) {
        expects.put(-1, val);
        return this;
    }

    public DataExpectationBuilder expect(LazyDataProvider provider) {
        expects.put(-1, provider);
        return this;
    }

    public <T> DataExpectationBuilder expectDouble(double val, int precision) {
        expect(val);
        assertMethod((e, a) -> Assert.assertEquals((Double) e, (Double) a, precision));
        return this;
    }

    public DataExpectationBuilder expectArgument(int index, Object val) {
        checkRange(index);
        expects.put(index, val);
        return this;
    }

    public DataExpectationBuilder expectArgument(int index, LazyDataProvider provider) {
        checkRange(index);
        expects.put(index, provider);
        return this;
    }

    private void checkRange(int index) {
        if (index < 0 || arguments.size() <= index) {
            throw new IllegalArgumentException(String.format(
                    "Argument %d is out of range of [0, %d]", index, arguments.size() - 1));
        }
    }

    public <T> DataExpectationBuilder assertMethod(BiConsumer<T, T> assertMethod) {
        if (!expects.containsKey(-1)) {
            expects.put(-1, null);
        }
        assertMethods.put(-1, assertMethod);
        return this;
    }

    public <T> DataExpectationBuilder assertMethod(Consumer<T> assertMethod) {
        if (!expects.containsKey(-1)) {
            expects.put(-1, null);
        }
        assertMethods.put(-1, (BiConsumer<T, T>) (expect, actual) -> assertMethod.accept(actual));
        return this;
    }

    public <T> DataExpectationBuilder assertMethod(Runnable assertMethod) {
        if (!expects.containsKey(-1)) {
            expects.put(-1, null);
        }
        assertMethods.put(-1, (BiConsumer<T, T>) (expect, actual) -> assertMethod.run());
        return this;
    }

    public <T> DataExpectationBuilder argumentAssertMethod(int index, BiConsumer<T, T> assertMethod) {
        checkRange(index);
        assertMethods.put(index, assertMethod);
        return this;
    }

    public <T> DataExpectationBuilder unorderResult(String... pattern) {
        ObjectEqualsHelper helper = new ObjectEqualsHelper();
        if (pattern.length == 0) {
            helper.unorder("*");
        } else {
            for (String s : pattern) {
                helper.unorder(s);
            }
        }
        return assertMethod((expect, actual) -> {
            AssertUtils.assertEquals(expect, actual, helper);
        });
    }

    public DataExpectation build() {
        DataExpectation res = new DataExpectation();
        res.setArguments(arguments.toArray());
        expects.forEach((index, expect) -> {
            if (index == -1) {
                res.setExpect(expect);
            } else {
                res.setExpectArgument(index, expect);
            }
        });
        assertMethods.forEach((index, assertMethod) -> {
            if (index == -1) {
                res.setExpectAssertMethod(assertMethod);
            } else {
                res.setArgumentAssertMethod(index, assertMethod);
            }
        });
        return res;
    }

    public static BiConsumer orExpectAssertMethod = (expects, actual) -> {
        Class<?> type = expects.getClass();
        Object[] expectArray;
        if (type.isArray()) {
            int length = Array.getLength(expects);
            expectArray = new Object[length];
            for (int i = 0; i < length; i++) {
                expectArray[i] = Array.get(expects, i);
            }
        } else if (Collection.class.isAssignableFrom(type)) {
            expectArray = new Object[((Collection) expects).size()];
            int i = 0;
            for (Object expect : ((Collection) expects)) {
                expectArray[i++] = expect;
            }
        } else {
            throw new RuntimeException("Expect value must be an Array or Collection.");
        }

        StringBuilder sb = new StringBuilder();
        for (Object expect : expectArray) {
            try {
                AssertUtils.assertEquals(expect, actual);
                return;
            } catch (Throwable err) {
                sb.append("\n    expect: ").append(expect);
            }
        }
        System.err.println("OrExpects not match with \n    actual: " + actual + sb.toString());
        throw new RuntimeException("OrExpects found no match for actual");
    };
}
