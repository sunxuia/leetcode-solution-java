package util.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import util.asserthelper.ErrorStringBuilder;

public class LeetCodeRunner extends Runner implements Filterable {

    private Class testedClass;

    private List<AnswerMethod> testMethods;

    private Map<String, Object> cache = new HashMap<>();

    public LeetCodeRunner(Class testedClass) {
        super();
        this.testedClass = testedClass;
        this.testMethods = createTestMethods();
    }

    private List<AnswerMethod> createTestMethods() {
        List<AnswerMethod> res = new ArrayList<>();
        for (Method method : getMethodsWithAnnotation(Answer.class)) {
            AnswerMethod answerMethod = new AnswerMethod();
            answerMethod.name = method.getName();
            answerMethod.description = Description.createTestDescription(testedClass, answerMethod.name);
            answerMethod.method = method;
            answerMethod.usingTestData = Optional.ofNullable(method.getAnnotation(UsingTestData.class))
                    .map(a -> Stream.of(a.value()).collect(Collectors.toSet()))
                    .orElse(Collections.emptySet());
            res.add(answerMethod);
        }
        return res;
    }

    private List<Method> getMethodsWithAnnotation(Class<? extends Annotation> annotation) {
        return getCahceValue("method-" + annotation.getName(), key -> {
            List<Method> res = new ArrayList<>();
            for (Method method : testedClass.getMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    res.add(method);
                }
            }
            return res;
        });
    }

    @SuppressWarnings("unchecked")
    private <T> T getCahceValue(String key, Function<String, T> provider) {
        return (T) cache.computeIfAbsent(key, provider);
    }

    private List<Field> getFieldsWithAnnotation(Class<? extends Annotation> annotation) {
        return getCahceValue("field-" + annotation.getName(), key -> {
            List<Field> res = new ArrayList<>();
            for (Field method : testedClass.getFields()) {
                if (method.isAnnotationPresent(annotation)) {
                    res.add(method);
                }
            }
            return res;
        });
    }

    @Override
    public void filter(Filter filter) throws NoTestsRemainException {
        synchronized (testMethods) {
            testMethods.removeIf(answer -> !filter.shouldRun(answer.description));
        }
        if (testMethods.isEmpty()) {
            throw new NoTestsRemainException();
        }
    }

    @Override
    public Description getDescription() {
        Description desc = Description.createTestDescription(testedClass, testedClass.getCanonicalName());
        synchronized (testMethods) {
            for (AnswerMethod testMethod : testMethods) {
                desc.addChild(testMethod.description);
            }
        }
        return desc;
    }

    @Override
    public void run(RunNotifier notifier) {
        for (AnswerMethod answer : this.testMethods) {
            Description desc = answer.description;
            notifier.fireTestStarted(desc);
            try {
                int count = invokeTestMethod(answer);
                if (count > 0) {
                    notifier.fireTestFinished(desc);
                }
            } catch (Throwable e) {
                StackTraceElement[] elements = e.getStackTrace();
                int i = 0;
                while (i < elements.length &&
                        elements[i].getClassName().equals(LeetCodeRunner.class.getName())) {
                    i++;
                }
                elements = Arrays.copyOfRange(elements, i, elements.length);
                e.setStackTrace(elements);
                notifier.fireTestFailure(new Failure(desc, e));
            }
        }
    }

    private int invokeTestMethod(AnswerMethod answer) throws Throwable {
        Object testedObject = testedClass.getConstructor().newInstance();
        List<TestDataHolder> holders = getTestDataHolders(testedObject);
        int runTimes = 0;
        for (TestDataHolder holder : holders) {
            if (answer.shouldTest(holder)) {
                runTimes++;
                testedObject = testedClass.getConstructor().newInstance();

                // before
                for (Method method : getMethodsWithAnnotation(Before.class)) {
                    invokeMethod(testedObject, method, null,
                            "Error while invoke @Before method %s", method.getName());
                }

                // test method
                Object res = invokeMethod(testedObject, answer.method, holder.testData.getArguments(),
                        "Error while calculate for input %s", holder.name);

                // after
                for (Method method : getMethodsWithAnnotation(After.class)) {
                    invokeMethod(testedObject, method, null,
                            "Error while invoke @After method %s", method.getName());
                }

                // validate
                try {
                    holder.testData.assertEquals(res);
                } catch (Throwable err) {
                    ErrorStringBuilder esb = new ErrorStringBuilder();
                    esb.append("Error while validate ").append(holder.name).append(":\n");
                    esb.append("    expect: ").append(holder.testData.getExpect()).newLine();
                    esb.append("    actual: ").append(res);
                    System.err.println(esb.toString());
                    throw err;
                }
            }
        }
        return runTimes;
    }

    private List<TestDataHolder> getTestDataHolders(Object testedObject)
            throws IllegalAccessException, InvocationTargetException {
        List<TestDataHolder> res = new ArrayList<>();
        for (Field field : getFieldsWithAnnotation(TestData.class)) {
            TestDataHolder holder = new TestDataHolder();
            holder.name = field.getName();
            field.setAccessible(true);
            holder.testData = (DataExpectation) field.get(testedObject);
            res.add(holder);
        }
        for (Method method : getMethodsWithAnnotation(TestData.class)) {
            DataExpectation data = (DataExpectation) method.invoke(testedObject);
            TestDataHolder holder = new TestDataHolder();
            holder.name = method.getName();
            holder.testData = data;
            res.add(holder);
        }
        return res;
    }

    private Object invokeMethod(Object testObject, Method method, Object[] args,
            String errorMessage, Object... errorMessageParas) throws Throwable {
        try {
            return method.invoke(testObject, args);
        } catch (Throwable err) {
            if (errorMessageParas.length > 0) {
                errorMessage = String.format(errorMessage, errorMessageParas);
            }
            System.err.println(errorMessage);
            throw err.getCause();
        }
    }


    private static class AnswerMethod {

        private String name;

        private Description description;

        private Method method;

        private Set<String> usingTestData;

        private boolean shouldTest(TestDataHolder holder) {
            return usingTestData.isEmpty() || usingTestData.contains(holder.name);
        }

    }

    private static class TestDataHolder {

        private DataExpectation testData;

        private String name;

    }
}