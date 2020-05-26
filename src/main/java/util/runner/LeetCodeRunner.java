package util.runner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.runner.runner.AbstractRunner;
import util.runner.runner.AbstractTestExecutor;
import util.runner.runner.AnnotatedTestMethodExecutor;
import util.runner.runner.AnswerClassExecutor;
import util.runner.runner.AnswerMethodExecutor;
import util.runner.runner.TestInfo;
import util.runner.runner.TestRunnerException;

/**
 * LeetCode 方法类型解答的Junit runner
 */
public class LeetCodeRunner extends AbstractRunner {

    private AnswerClassExecutor answerClassExecutor;

    private AnswerMethodExecutor answerMethodExecutor;

    private AnnotatedTestMethodExecutor annotatedTestMethodExecutor;

    private Map<TestInfo, AbstractTestExecutor> testExecutors = new HashMap<>();

    public LeetCodeRunner(Class<?> testedClass) {
        super(testedClass);
        answerClassExecutor = new AnswerClassExecutor(testedClass);
        answerMethodExecutor = new AnswerMethodExecutor(testedClass);
        annotatedTestMethodExecutor = new AnnotatedTestMethodExecutor(testedClass);
        initialTestInfos();
    }

    private void initialTestInfos() {
        List<TestInfo> testMethodInfos = annotatedTestMethodExecutor.listTestInfo();
        testMethodInfos.forEach(t -> testExecutors.put(t, annotatedTestMethodExecutor));
        addTestInfos(testMethodInfos);

        List<TestInfo> classTestInfos = answerClassExecutor.listTestInfo();
        classTestInfos.forEach(t -> testExecutors.put(t, answerClassExecutor));
        addTestInfos(classTestInfos);

        List<TestInfo> answerMethodTestInfos = answerMethodExecutor.listTestInfo();
        if (!classTestInfos.isEmpty() && !answerMethodTestInfos.isEmpty()) {
            throw new TestRunnerException("A LeetCodeRunner can either run a test method or a test class.");
        }
        answerMethodTestInfos.forEach(t -> testExecutors.put(t, answerMethodExecutor));
        addTestInfos(answerMethodTestInfos);
    }

    @Override
    protected int invokeTest(TestInfo testInfo) throws Throwable {
        AbstractTestExecutor testExecutor = testExecutors.get(testInfo);
        return testExecutor.invokeTest(testInfo);
    }

}