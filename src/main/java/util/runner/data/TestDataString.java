package util.runner.data;

import java.util.ArrayList;
import java.util.List;

public class TestDataString {

    static final TestDataString NULL = new TestDataString(TestDataStringType.NULL, "null");
    static final TestDataString TRUE = new TestDataString(TestDataStringType.BOOLEAN, "true");
    static final TestDataString FALSE = new TestDataString(TestDataStringType.BOOLEAN, "false");

    TestDataString(TestDataStringType type, String value) {
        this.type = type;
        this.value = value;
    }

    TestDataString(List<TestDataString> children) {
        this.type = TestDataStringType.ARRAY;
        this.children.addAll(children);
    }

    private String value;

    private TestDataStringType type;

    private List<TestDataString> children = new ArrayList<>();

    public String getValue() {
        return value;
    }

    public TestDataStringType getType() {
        return type;
    }

    public boolean isArray() {
        return type == TestDataStringType.ARRAY;
    }

    public List<TestDataString> getChildren() {
        return children;
    }

    public Object toObject(Class<?> clazz) {
        return new TestDataStringConverter(this).toObject(clazz);
    }

    @Override
    public int hashCode() {
        return value.hashCode() + type.hashCode() + children.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TestDataString)) {
            return false;
        }
        TestDataString other = (TestDataString) obj;
        return this == obj
                || type.equals(other.type)
                && value.equals(other.value)
                && children.equals(other.children);
    }

    /**
     * type of {@link TestDataString}
     */
    public enum TestDataStringType {
        STRING,
        NUMBER,
        BOOLEAN,
        NULL,
        ARRAY
    }

}
