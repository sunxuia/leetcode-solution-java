package util.runner.runner;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.Description;
import util.asserthelper.ErrorStringBuilder;
import util.runner.Answer;
import util.runner.DebugWith;
import util.runner.data.ClassDataExpectation;
import util.runner.data.ClassDataExpectation.Command;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataProvider;

public class AnswerClassExecutor extends AbstractTestExecutor {

    private TestDataProvider<ClassDataExpectation> testDataProvider;

    private List<Class<?>> answerClasses;

    private Map<Class<?>, AnswerClassMetadata> answerClassMetadatas = new HashMap<>();

    public AnswerClassExecutor(Class<?> testedClass) {
        super(testedClass);
        testDataProvider = new TestDataProvider<>(testedClass);
        answerClasses = getElementWithAnnotation(Answer.class, Class::getClasses);
    }

    @Override
    public List<TestInfo> listTestInfo() {
        if (answerClasses.isEmpty()) {
            return Collections.emptyList();
        }

        List<TestInfo> res = getDebugTestInfos();
        if (res.isEmpty()) {
            res = getDefaultTestInfos();
        }
        return res;
    }

    private List<TestInfo> getDebugTestInfos() {
        List<TestInfo> res = new ArrayList<>();
        for (Method debugMethod : getElementWithAnnotation(DebugWith.class, Class::getMethods)) {
            TestInfo testInfo = new TestInfo();
            testInfo.setName(debugMethod.getName());
            testInfo.setDescription(Description.createTestDescription(testedClass, debugMethod.getName()));
            testInfo.addFilter(debugMethod.getAnnotation(DebugWith.class));
            res.add(testInfo);
        }
        return res;
    }

    private List<TestInfo> getDefaultTestInfos() {
        List<TestInfo> res = new ArrayList<>();
        for (Class<?> clazz : answerClasses) {
            String name = "test" + clazz.getSimpleName();
            TestInfo testInfo = new TestInfo();
            testInfo.setName(name);
            testInfo.setDescription(Description.createTestDescription(testedClass, name));
            testInfo.addFilter(new DebugWith() {

                @Override
                public Class<? extends Annotation> annotationType() {
                    return DebugWith.class;
                }

                @Override
                public String[] value() {
                    return new String[0];
                }

                @Override
                public Class<?>[] testFor() {
                    return new Class[]{clazz};
                }
            });
            res.add(testInfo);
        }
        return res;
    }

    @Override
    public int invokeTest(TestInfo testInfo) throws Throwable {
        int runTimes = 0;
        for (Class<?> clazz : answerClasses) {
            if (!testInfo.shouldTestClass(clazz)) {
                continue;
            }
            Object testedObject = createTestedObject();
            List<TestDataHolder<ClassDataExpectation>> testDatas = getTestDatas(testInfo, testedObject);

            for (TestDataHolder<ClassDataExpectation> testData : testDatas) {
                runTimes++;

                // before
                runBeforeMethod(testedObject);

                // test
                executeTest(testInfo, testData, clazz);

                // after
                runAfterMethod(testedObject);
            }
        }
        return runTimes;
    }

    private List<TestDataHolder<ClassDataExpectation>> getTestDatas(TestInfo testInfo, Object testedObject) {
        List<TestDataHolder<ClassDataExpectation>> testDatas =
                testDataProvider.getTestDatas(testInfo, testedObject);

        for (TestDataHolder<ClassDataExpectation> testData : testDatas) {
            try {
                ClassDataExpectation cde = testData.getTestData();
            } catch (ClassCastException err) {
                throw new TestRunnerException(String.format(
                        "Error while get test data [%s]: %s is not a valid test data type. Only "
                                + "ClassDataExpectation, ClassDataExpectation[], "
                                + "Collection<? extends ClassDataExpectation>, Map<String, ClassDataExpectation>"
                                + " is allowed.",
                        testData.getName(), testData.getTestData().getClass().getName()));
            }
        }

        return testDatas;
    }

    private void executeTest(TestInfo testInfo, TestDataHolder<ClassDataExpectation> testData,
            Class<?> clazz) throws Throwable {
        ClassDataExpectation cde = testData.getTestData();
        if (cde.getConstructorName() == null || cde.getConstructorName().isEmpty()) {
            throw new TestRunnerException(String.format(
                    "Error while run test %s of type %s: constructor name not specified.",
                    testData.getName(), clazz.getSimpleName()));
        }

        AnswerClassMetadata metadata = getAnswerClassMetadata(clazz);

        Map<String, List<Object>> methodResults = new HashMap<>();
        List<Object> allResults = new ArrayList<>();
        Object answerObject = null;
        int seq = 0;
        for (Command command : cde.getCommands()) {
            seq++;
            DataExpectation dataExpectation = command.getDataExpectation();
            Object[] arguments = dataExpectation.getArguments();
            if (command.getMethodName().equals(cde.getConstructorName())) {
                Constructor<?> constructor = metadata.constructors.get(arguments.length);
                if (constructor == null) {
                    throw new TestRunnerException(String.format(
                            "Error while run test %s of type %s: constructor with %d arguments not exist.",
                            testData.getName(), clazz.getSimpleName(), arguments.length));
                }

                answerObject = constructor.newInstance(arguments);
                allResults.add(null);
            } else {
                if (answerObject == null) {
                    throw new TestRunnerException(String.format(
                            "Error while run test %s of type %s: methods ran before constructor called.",
                            testData.getName(), clazz.getSimpleName()));
                }
                Map<Integer, MethodMetadata> methodMap = metadata.methods.get(command.getMethodName());
                MethodMetadata methodMetadata = methodMap == null ? null : methodMap.get(arguments.length);
                if (methodMetadata == null) {
                    throw new TestRunnerException(String.format(
                            "Error while run test %s of type %s: method %s with %d arguments not exist.",
                            testData.getName(), clazz.getSimpleName(), command.getMethodName(), arguments.length));
                }

                Object[] invokeArgs = new Object[arguments.length + 1];
                invokeArgs[0] = answerObject;
                System.arraycopy(arguments, 0, invokeArgs, 1, arguments.length);

                // test method
                Object res = methodMetadata.methodHandle.invokeWithArguments(invokeArgs);

                // validate
                try {
                    dataExpectation.assertResult(res);
                } catch (AssertionError err) {
                    ErrorStringBuilder esb = new ErrorStringBuilder();
                    esb.append("Error while validate %s with test class %s and test data %s :\n",
                            testInfo.getName(), clazz.getSimpleName(), testData.getName());
                    esb.append("  Command %d assert failed:\n", seq);
                    esb.append("    expect: ").append(dataExpectation.getExpect()).newLine();
                    esb.append("    actual: ").append(res);
                    logError(esb.toString());
                    throw err;
                }

                allResults.add(res);
                methodResults.computeIfAbsent(command.getMethodName(), k -> new ArrayList<>()).add(res);
            }
        }

        cde.afterAssert(allResults, methodResults);
    }

    private AnswerClassMetadata getAnswerClassMetadata(Class<?> answerClass) {
        if (answerClassMetadatas.containsKey(answerClass)) {
            return answerClassMetadatas.get(answerClass);
        }

        AnswerClassMetadata metadata = new AnswerClassMetadata();
        for (Constructor<?> constructor : answerClass.getConstructors()) {
            if (metadata.constructors.containsKey(constructor.getParameterCount())) {
                throw new TestRunnerException(
                        "Test Class should not have public constructors with same arguments counts.");
            }
            metadata.constructors.put(constructor.getParameterCount(), constructor);
        }

        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        for (Method method : answerClass.getMethods()) {
            MethodHandle methodHandle;
            try {
                methodHandle = publicLookup.findVirtual(answerClass, method.getName(),
                        MethodType.methodType(method.getReturnType(), method.getParameterTypes()));
            } catch (Exception e) {
                throw new TestRunnerException("Error while get metadata of " + answerClass.getSimpleName() + ".", e);
            }
            Map<Integer, MethodMetadata> sameNameMethods = metadata.methods
                    .computeIfAbsent(method.getName(), k -> new HashMap<>());
            if (sameNameMethods.containsKey(method.getParameterCount())) {
                throw new TestRunnerException(
                        "Test Class should not have public methods with same name and arguments counts.");
            }
            MethodMetadata methodMetadata = new MethodMetadata();
            methodMetadata.method = method;
            methodMetadata.methodHandle = methodHandle;
            sameNameMethods.put(method.getParameterCount(), methodMetadata);
        }

        answerClassMetadatas.put(answerClass, metadata);
        return metadata;
    }

    private static class AnswerClassMetadata {

        HashMap<Integer, Constructor<?>> constructors = new HashMap<>();

        Map<String, Map<Integer, MethodMetadata>> methods = new HashMap<>();
    }

    private static class MethodMetadata {

        MethodHandle methodHandle;

        Method method;

    }

}
