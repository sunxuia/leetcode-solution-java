package util.runner.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import util.runner.LeetCodeRunner;
import util.runner.data.TestDataException;

public abstract class AbstractRunner extends Runner implements Filterable {

    protected final Class<?> testedClass;

    private final List<TestInfo> testInfos = new ArrayList<>();

    public AbstractRunner(Class<?> testedClass) {
        this.testedClass = testedClass;
    }

    protected void addTestInfos(Collection<TestInfo> testInfos) {
        this.testInfos.addAll(testInfos);
    }

    @Override
    public Description getDescription() {
        Description desc = Description.createTestDescription(testedClass, testedClass.getCanonicalName());
        synchronized (testInfos) {
            for (TestInfo testInfo : testInfos) {
                desc.addChild(testInfo.getDescription());
            }
        }
        return desc;
    }

    @Override
    public void run(RunNotifier notifier) {
        for (TestInfo test : testInfos) {
            try {
                notifier.fireTestStarted(test.getDescription());
                int testTimes = invokeTest(test);
                if (testTimes > 0) {
                    notifier.fireTestFinished(test.getDescription());
                }
            } catch (TestRunnerException e) {
                notifier.fireTestFailure(new Failure(test.getDescription(), e));
            } catch (Throwable e) {
                for (Throwable cause = e; cause != null; cause = cause.getCause()) {
                    if (cause instanceof TestDataException) {
                        notifier.fireTestFailure(new Failure(test.getDescription(), cause));
                        return;
                    }
                }

                StackTraceElement[] elements = e.getStackTrace();
                int i = 0;
                while (i < elements.length &&
                        elements[i].getClassName().equals(LeetCodeRunner.class.getName())) {
                    i++;
                }
                elements = Arrays.copyOfRange(elements, i, elements.length);
                e.setStackTrace(elements);
                notifier.fireTestFailure(new Failure(test.getDescription(), e));
            }
        }
    }

    protected abstract int invokeTest(TestInfo testInfo) throws Throwable;

    @Override
    public void filter(Filter filter) throws NoTestsRemainException {
        synchronized (testInfos) {
            testInfos.removeIf(test -> !filter.shouldRun(test.getDescription()));
        }
        if (testInfos.isEmpty()) {
            throw new NoTestsRemainException();
        }
    }
}
