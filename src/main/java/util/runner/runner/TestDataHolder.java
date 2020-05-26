package util.runner.runner;

public class TestDataHolder<T> {

    private String name;

    private T testData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getTestData() {
        return testData;
    }

    public void setTestData(T testData) {
        this.testData = testData;
    }
}
