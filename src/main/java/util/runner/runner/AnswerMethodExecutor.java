package util.runner.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.Description;
import util.asserthelper.ErrorStringBuilder;
import util.runner.Answer;
import util.runner.DebugWith;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataProvider;

public class AnswerMethodExecutor extends AbstractTestExecutor {

    private Map<TestInfo, Method> testMethods = new LinkedHashMap<>();

    private TestDataProvider<DataExpectation> testDataProvider;

    public AnswerMethodExecutor(Class<?> testedClass) {
        super(testedClass);
        testDataProvider = new TestDataProvider<>(testedClass);

        for (Method method : getElementWithAnnotation(Answer.class, Class::getMethods)) {
            TestInfo testInfo = new TestInfo();
            testInfo.setName(method.getName());
            testInfo.setDescription(Description.createTestDescription(testedClass, method.getName()));
            testInfo.addFilter(method.getAnnotation(DebugWith.class));
            testMethods.put(testInfo, method);
        }
    }

    @Override
    public List<TestInfo> listTestInfo() {
        return new ArrayList<>(testMethods.keySet());
    }

    @Override
    public int invokeTest(TestInfo testInfo) throws Throwable {
        Object testedObject = createTestedObject();
        List<TestDataHolder<DataExpectation>> testDatas = getTestDatas(testInfo, testedObject);

        for (TestDataHolder<DataExpectation> testData : testDatas) {
            DataExpectation dataExpectation = testData.getTestData();
            Method method = testMethods.get(testInfo);
            // before
            runBeforeMethod(testedObject);

            // test method
            Object res = invokeMethod(testedObject, method, dataExpectation.getArguments(),
                    "Error while calculate for input %s", testData.getName());

            // validate
            try {
                dataExpectation.assertResult(res);
            } catch (AssertionError err) {
                ErrorStringBuilder esb = new ErrorStringBuilder();
                esb.append("Error while validate ").append(testInfo.getName()).append(":\n");
                esb.append("    expect: ").append(dataExpectation.getExpect()).newLine();
                esb.append("    actual: ").append(res);
                logError(esb.toString());
                throw err;
            }

            // after
            runAfterMethod(testedObject);
        }

        return testDatas.size();
    }

    private List<TestDataHolder<DataExpectation>> getTestDatas(TestInfo testInfo, Object testedObject) {
        List<TestDataHolder<DataExpectation>> testDatas = testDataProvider.getTestDatas(testInfo, testedObject);

        for (TestDataHolder<DataExpectation> testData : testDatas) {
            try {
                DataExpectation dataExpectation = testData.getTestData();
            } catch (ClassCastException err) {
                throw new TestRunnerException(String.format(
                        "Error while get test data [%s]: %s is not a valid test data type. Only DataExpection, "
                                + "DataExpection[], Collection<? extends DataExpection>, Map<String, DataExpection>"
                                + " is allowed.",
                        testData.getName(), testData.getTestData().getClass().getName()));
            }
        }

        return testDatas;
    }

}
