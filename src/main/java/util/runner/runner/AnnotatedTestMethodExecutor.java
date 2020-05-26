package util.runner.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.Description;

public class AnnotatedTestMethodExecutor extends AbstractTestExecutor {

    private Map<TestInfo, Method> testMethods = new LinkedHashMap<>();

    private Object testedObject;

    public AnnotatedTestMethodExecutor(Class<?> testedClass) {
        super(testedClass);

        for (Method method : getElementWithAnnotation(Test.class, Class::getMethods)) {
            TestInfo testInfo = new TestInfo();
            testInfo.setName(method.getName());
            testInfo.setDescription(Description.createTestDescription(testedClass, method.getName()));
            testMethods.put(testInfo, method);
        }
    }

    @Override
    public List<TestInfo> listTestInfo() {
        return new ArrayList<>(testMethods.keySet());
    }

    @Override
    public int invokeTest(TestInfo testInfo) throws Throwable {
        if (testedObject == null) {
            testedObject = createTestedObject();
        }

        runBeforeMethod(testedObject);

        Method method = testMethods.get(testInfo);
        invokeMethod(testedObject, method, new Object[0],
                "Error while run test %s", testInfo.getName());

        runAfterMethod(testedObject);
        return 1;
    }


}
