package util.runner.data;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 表示类类型中的一个测试数据(测试用例)
 */
public class ClassDataExpectation {

    private List<Command> commands;

    private String constructorName;

    private BiConsumer<List<Object>, Map<String, List<Object>>> afterAssertMethod;

    public static class Command {

        private String methodName;

        private DataExpectation dataExpectation;

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public DataExpectation getDataExpectation() {
            return dataExpectation;
        }

        public void setDataExpectation(DataExpectation dataExpectation) {
            this.dataExpectation = dataExpectation;
        }
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public String getConstructorName() {
        return constructorName;
    }

    public void setConstructorName(String constructorName) {
        this.constructorName = constructorName;
    }

    public BiConsumer<List<Object>, Map<String, List<Object>>> getAfterAssertMethod() {
        return afterAssertMethod;
    }

    public void setAfterAssertMethod(
            BiConsumer<List<Object>, Map<String, List<Object>>> afterAssertMethod) {
        this.afterAssertMethod = afterAssertMethod;
    }

    public void afterAssert(List<Object> allResult, Map<String, List<Object>> methodResult) {
        if (this.afterAssertMethod != null) {
            this.afterAssertMethod.accept(allResult, methodResult);
        }
    }
}
