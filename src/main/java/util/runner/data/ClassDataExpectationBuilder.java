package util.runner.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import util.runner.data.ClassDataExpectation.Command;
import util.runner.data.TestDataString.TestDataStringType;

public class ClassDataExpectationBuilder {

    /**
     * 从文件读取测试用例, 不同测试用例之间使用两个换行分隔.
     */
    public static Map<String, ClassDataExpectation> readFromFile(Class<?> example, String fileName) {
        Map<Integer, Constructor<?>> constructors = new HashMap<>();
        for (Constructor<?> constructor : example.getConstructors()) {
            if (constructors.containsKey(constructor.getParameterCount())) {
                throw newException(fileName,
                        "Test Class should not have public constructors with same arguments counts.");
            }
            constructors.put(constructor.getParameterCount(), constructor);
        }
        Map<String, Map<Integer, Method>> methods = new HashMap<>();
        for (Method method : example.getMethods()) {
            Map<Integer, Method> map = methods.computeIfAbsent(method.getName(), k -> new HashMap<>());
            if (map.containsKey(method.getParameterCount())) {
                throw newException(fileName,
                        "Test Class should not have public methods with same name and arguments counts.");
            }
            map.put(method.getParameterCount(), method);
        }

        Map<String, ClassDataExpectation> res = new LinkedHashMap<>();
        final String constructorName = example.getSimpleName();

        List<String> texts = new TestDataFile(fileName).getAll();
        StringJoiner joiner = new StringJoiner("\n");
        for (String text : texts) {
            joiner.add(text);
        }
        String[] testCases = joiner.toString().split("\n\n");
        for (int i = 0; i < testCases.length; i++) {
            String[] testCase = testCases[i].split("\n");
            String name;
            if (testCase.length == 4) {
                name = testCase[0];
            } else if (testCase.length != 3) {
                if (i == testCases.length - 1) {
                    continue;
                }
                throw newException(fileName,
                        ": A test data should have at least 3 lines: commands, data, answers.");
            } else {
                name = String.valueOf(i);
            }

            ClassDataExpectation expectation = new ClassDataExpectation();
            expectation.setConstructorName(constructorName);
            expectation.setCommands(new ArrayList<>());

            TestDataString commandStrings = TestDataStringBuilder.parseString(testCase[testCase.length - 3]);
            TestDataString argumentStrings = TestDataStringBuilder.parseString(testCase[testCase.length - 2]);
            TestDataString expectStrings = TestDataStringBuilder.parseString(testCase[testCase.length - 1]);

            if (!commandStrings.isArray() || !argumentStrings.isArray() || !expectStrings.isArray()) {
                throw newException(name, "Test case should be in the form of array.");
            }
            final int n = commandStrings.getChildren().size();
            if (argumentStrings.getChildren().size() != n || expectStrings.getChildren().size() != n) {
                throw newException(name, "Commands, data, and answers' counts not equal.");
            }

            for (int j = 0; j < n; j++) {
                TestDataString commandString = commandStrings.getChildren().get(j);
                TestDataString argumentString = argumentStrings.getChildren().get(j);
                TestDataString expectString = expectStrings.getChildren().get(j);
                final String commandName = commandString.getValue();
                DataExpectationBuilder builder = DataExpectation.builder();

                if (commandString.getType() != TestDataStringType.STRING) {
                    throw newException(name, "Command %d should be string.", j);
                }
                if (!argumentString.isArray()) {
                    throw newException(name, "Arguments %d (%s) should be array.", j, commandName);
                }

                List<TestDataString> arguments = argumentString.getChildren();
                Class<?>[] argumentTypes;
                Class<?> returnType = null;
                if (commandName.equals(constructorName)) {
                    Constructor<?> constructor = constructors.get(arguments.size());
                    if (constructor == null) {
                        throw newException(name, "Arguments %d (%s) constructor not found.", j, commandName);
                    }
                    argumentTypes = constructor.getParameterTypes();
                } else {
                    Map<Integer, Method> map = methods.get(commandName);
                    Method method = map == null ? null : map.get(arguments.size());
                    if (method == null) {
                        throw newException(name, "Arguments %d (%s) method not found.", j, commandName);
                    }
                    argumentTypes = method.getParameterTypes();
                    returnType = method.getReturnType();
                }
                for (int k = 0; k < argumentTypes.length; k++) {
                    Object arg = arguments.get(k).toObject(argumentTypes[k]);
                    builder.addArgument(arg);
                }

                Object returnValue = expectString.toObject(returnType);
                builder.expect(returnValue);

                Command cmd = new Command();
                cmd.setMethodName(commandName);
                cmd.setDataExpectation(builder.build());
                expectation.getCommands().add(cmd);
            }
            res.put(name, expectation);
        }
        return res;
    }

    private static TestDataException newException(String name, String reason, Object... params) {
        if (params.length > 0) {
            reason = String.format(reason, params);
        }
        return new TestDataException("Error while parse test data " + name + " error: " + reason);
    }

}
