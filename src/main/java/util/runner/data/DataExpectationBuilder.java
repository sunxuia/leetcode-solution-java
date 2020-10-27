package util.runner.data;

import java.util.ArrayList;
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

    private Map<Integer, DefaultDataAssertMethod<?>> assertMethods = new HashMap<>();

    public DataExpectationBuilder addArgument(Object val) {
        arguments.add(val);
        return this;
    }

    public DataExpectationBuilder setArgument(int index, Object val) {
        while (index >= arguments.size()) {
            arguments.add(null);
        }
        arguments.set(index, val);
        return this;
    }

    public DataExpectationBuilder expect(Object val) {
        expects.put(-1, val);
        return this;
    }

    public DataExpectationBuilder expectDouble(double val, int precision) {
        expect(val);
        double delta = Math.pow(0.1, precision);
        assertMethods.put(-1, this.<Double>createAssertMethod((e, a, o) -> Assert.assertEquals(e, a, delta)));
        return this;
    }

    private <T> DefaultDataAssertMethod<T> createAssertMethod(DataAssertMethod<T> assertMethod) {
        return new DefaultDataAssertMethod<>(assertMethod, null);
    }

    public DataExpectationBuilder orExpect(Object val) {
        getOrList(-1).add(val);
        return this;
    }

    @SuppressWarnings("unchecked")
    private OrList<Object> getOrList(int idx) {
        if (!expects.containsKey(idx)) {
            throw new TestDataException("expect not exist");
        }
        Object exist = expects.get(idx);
        if (exist instanceof OrList) {
            return (OrList<Object>) exist;
        }
        OrList<Object> res = new OrList<>(exist);
        expects.put(idx, res);
        return res;
    }

    public DataExpectationBuilder expectArgument(int index, Object val) {
        checkRange(index);
        expects.put(index, val);
        return this;
    }

    private void checkRange(int index) {
        if (index < 0 || arguments.size() <= index) {
            throw new IllegalArgumentException(String.format(
                    "Argument %d is out of range of [0, %d]", index, arguments.size() - 1));
        }
    }

    public DataExpectationBuilder orExpectArgument(int index, Object val) {
        checkRange(index);
        getOrList(index).add(val);
        return this;
    }

    public <T> DataExpectationBuilder assertMethod(DataAssertMethod<T> assertMethod) {
        expects.putIfAbsent(-1, null);
        addAssertMethod(-1, assertMethod);
        return this;
    }

    private <T> void addAssertMethod(int idx, DataAssertMethod<T> assertMethod) {
        assertMethods.put(idx, new DefaultDataAssertMethod<>(assertMethod, getAssertMethod(idx)));
    }

    @SuppressWarnings("unchecked")
    private <T> DefaultDataAssertMethod<T> getAssertMethod(int idx) {
        return (DefaultDataAssertMethod<T>) assertMethods.getOrDefault(idx, DataExpectation.DEFAULT_ASSERT_METHOD);
    }

    public <T> DataExpectationBuilder assertMethod(BiConsumer<T, T> assertMethod) {
        expects.putIfAbsent(-1, null);
        assertMethods.put(-1, this.<T>createAssertMethod((e, a, o) -> assertMethod.accept(e, a)));
        return this;
    }

    public <T> DataExpectationBuilder assertMethod(Consumer<T> assertMethod) {
        expects.putIfAbsent(-1, null);
        assertMethods.put(-1, this.<T>createAssertMethod((e, a, o) -> assertMethod.accept(a)));
        return this;
    }

    public <T> DataExpectationBuilder assertMethod(Runnable assertMethod) {
        expects.putIfAbsent(-1, null);
        assertMethods.put(-1, this.<T>createAssertMethod((e, a, o) -> assertMethod.run()));
        return this;
    }

    public <T> DataExpectationBuilder argumentAssertMethod(int index, DataAssertMethod<T> assertMethod) {
        checkRange(index);
        expects.putIfAbsent(index, null);
        addAssertMethod(index, assertMethod);
        return this;
    }

    public <T> DataExpectationBuilder argumentAssertMethod(int index, BiConsumer<T, T> assertMethod) {
        checkRange(index);
        expects.putIfAbsent(index, null);
        assertMethods.put(index, this.<T>createAssertMethod((e, a, o) -> assertMethod.accept(e, a)));
        return this;
    }

    public DataExpectationBuilder unorderResult(String... pattern) {
        ObjectEqualsHelper helper = new ObjectEqualsHelper();
        if (pattern.length == 0) {
            helper.unorder("*");
        } else {
            for (String s : pattern) {
                helper.unorder(s);
            }
        }
        assertMethods.put(-1, this.createAssertMethod((expect, actual, originalAssertMethod) -> {
            AssertUtils.assertEquals(expect, actual, helper);
        }));
        return this;
    }

    public DataExpectation build() {
        DataExpectation res = new DataExpectation();
        res.setArguments(arguments.toArray());
        expects.forEach((index, expect) -> {
            if (expect instanceof OrList) {
                addAssertMethod(index, OrList.OR_EXPECT_ASSERT_METHOD);
            }
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

}
