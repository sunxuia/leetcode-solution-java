package util.runner.runner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.runner.Description;
import util.runner.DebugWith;

public class TestInfo {

    private static final Pattern USING_TEST_DATA_PATTERN = Pattern.compile("^([^\\[]+)(?:\\[(.+)])?$");

    private String name;

    private Description description;

    private Map<String, Set<String>> shouldTestDatas = Collections.emptyMap();

    private Set<Class<?>> shouldTestClasses = Collections.emptySet();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Description getDescription() {
        return description;
    }

    public void addFilter(DebugWith annotation) {
        if (annotation == null) {
            return;
        }
        if (annotation.value().length > 0) {
            shouldTestDatas = new HashMap<>(annotation.value().length);
            for (String testDataName : annotation.value()) {
                Matcher matcher = USING_TEST_DATA_PATTERN.matcher(testDataName);
                if (!matcher.find()) {
                    throw new TestRunnerException("Cannot parse @DebugWith " + testDataName + ".");
                }
                String name = matcher.group(1);
                Set<String> exist = shouldTestDatas.computeIfAbsent(name, k -> new HashSet<>());
                if (!isNullOrEmpty(matcher.group(2))) {
                    exist.add(matcher.group(2));
                }
            }
        }
        if (annotation.testFor().length > 0) {
            shouldTestClasses = new HashSet<>(annotation.testFor().length);
            shouldTestClasses.addAll(Arrays.asList(annotation.testFor()));
        }
    }

    public boolean shouldTestData(String testDataName, String subName) {
        if (shouldTestDatas == null || shouldTestDatas.isEmpty()) {
            return true;
        }
        Set<String> set = shouldTestDatas.get(testDataName);
        if (set == null) {
            return false;
        }
        return isNullOrEmpty(subName)
                || set.isEmpty()
                || set.contains(subName);
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public boolean shouldTestClass(Class<?> clazz) {
        return shouldTestClasses.isEmpty()
                || shouldTestClasses.contains(clazz);
    }

}
