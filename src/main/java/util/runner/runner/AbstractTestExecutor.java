package util.runner.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractTestExecutor {

    protected final Class<?> testedClass;

    private List<Method> beforeMethods, afterMethods;

    public AbstractTestExecutor(Class<?> testedClass) {
        this.testedClass = testedClass;
        beforeMethods = getElementWithAnnotation(Before.class, Class::getMethods);
        afterMethods = getElementWithAnnotation(After.class, Class::getMethods);
    }

    public abstract List<TestInfo> listTestInfo();

    protected <T extends AnnotatedElement> List<T> getElementWithAnnotation(
            Class<? extends Annotation> annotation, Function<Class<?>, T[]> provider) {
        List<T> res = new ArrayList<>();
        for (T ele : provider.apply(testedClass)) {
            if (ele.isAnnotationPresent(annotation)) {
                res.add(ele);
            }
        }
        return res;
    }

    public abstract int invokeTest(TestInfo testInfo) throws Throwable;

    protected Object createTestedObject() {
        try {
            return testedClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new TestRunnerException("Cannot instantiate tested data.", e);
        }
    }

    protected void runBeforeMethod(Object testedObject) throws Throwable {
        for (Method method : beforeMethods) {
            invokeMethod(testedObject, method, null,
                    "Error while invoke @Before method %s", method.getName());
        }
    }

    protected Object invokeMethod(Object testedObject, Method method, Object[] args,
            String errorMessage, Object... errorMessageParas) throws Throwable {
        try {
            return method.invoke(testedObject, args);
        } catch (Throwable err) {
            if (errorMessageParas.length > 0) {
                errorMessage = String.format(errorMessage, errorMessageParas);
            }
            logError(errorMessage);
            throw err.getCause();
        }
    }

    protected void logError(String msg) {
        System.err.println(msg);
    }

    protected void runAfterMethod(Object testedObject) throws Throwable {
        for (Method method : afterMethods) {
            invokeMethod(testedObject, method, null,
                    "Error while invoke @Before method %s", method.getName());
        }
    }

}
