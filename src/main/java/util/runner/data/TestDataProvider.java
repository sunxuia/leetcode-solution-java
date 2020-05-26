package util.runner.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import util.runner.TestData;
import util.runner.runner.TestDataHolder;
import util.runner.runner.TestInfo;
import util.runner.runner.TestRunnerException;

public class TestDataProvider<T> {

    private Class<?> testedClass;

    private List<Field> dataFields;

    private List<Method> dataMethods;

    public TestDataProvider(Class<?> testedClass) {
        this.testedClass = testedClass;
        this.dataFields = getElementWithAnnotation(TestData.class, Class::getFields);
        this.dataMethods = getElementWithAnnotation(TestData.class, Class::getMethods);
    }

    private <T extends AnnotatedElement> List<T> getElementWithAnnotation(
            Class<? extends Annotation> annotation, Function<Class<?>, T[]> provider) {
        List<T> res = new ArrayList<>();
        for (T ele : provider.apply(testedClass)) {
            if (ele.isAnnotationPresent(annotation)) {
                res.add(ele);
            }
        }
        return res;
    }

    public List<TestDataHolder<T>> getTestDatas(TestInfo testInfo, Object testedObject) {
        List<TestDataHolder<T>> res = new ArrayList<>();
        for (Field field : dataFields) {
            String name = field.getName();
            if (testInfo.shouldTestData(name, "")) {
                try {
                    Object data = field.get(testedObject);
                    res.addAll(unwrapTestDataList(testInfo, name, data));
                } catch (Exception err) {
                    throw new TestDataException("Error while getting test data.", err);
                }
            }
        }
        for (Method method : dataMethods) {
            String name = method.getName();
            if (testInfo.shouldTestData(name, "")) {
                try {
                    Object data = method.invoke(testedObject);
                    res.addAll(unwrapTestDataList(testInfo, name, data));
                } catch (Exception err) {
                    throw new TestDataException("Error while getting test data.", err);
                }
            }
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    private Collection<TestDataHolder<T>> unwrapTestDataList(TestInfo testInfo, String name, Object data) {
        Collection<? extends T> list = Collections.emptyList();
        Map<String, T> map = Collections.emptyMap();
        while (data instanceof LazyDataProvider) {
            data = ((LazyDataProvider<?>) data).get();
        }
        if (data == null) {
            throw new TestRunnerException("TestData " + name + " is null.");
        } else if (data.getClass().isArray()) {
            list = Arrays.asList((T[]) data);
        } else if (data instanceof Map) {
            map = (Map<String, T>) data;
        } else if (data instanceof Collection) {
            list = (Collection<T>) data;
        } else {
            return Collections.singleton(createTestHolder(name, data));
        }

        if (list.isEmpty() && map.isEmpty()) {
            throw new TestDataException("TestData " + name + " is empty.");
        }

        List<TestDataHolder<T>> res = new ArrayList<>();
        for (Entry<String, T> entry : map.entrySet()) {
            if (testInfo.shouldTestData(name, entry.getKey())) {
                res.add(createTestHolder("[" + entry.getKey() + "]", entry.getValue()));
            }
        }

        int i = 0;
        for (T testData : list) {
            if (testInfo.shouldTestData(name, String.valueOf(i))) {
                res.add(createTestHolder(name + "[" + i + "]", testData));
            }
            i++;
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    private TestDataHolder<T> createTestHolder(String name, Object testData) {
        TestDataHolder<T> holder = new TestDataHolder<>();
        holder.setName(name);
        holder.setTestData((T) testData);
        return holder;
    }
}
