package util.generator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * question method
 */
public class GeneratorQuestionMethod {

    String raw;
    String methodName;
    String returnType;
    String callerType;
    List<GeneratorQuestionArgument> arguments = new ArrayList<>();

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getCallerType() {
        return callerType;
    }

    public void setCallerType(String callerType) {
        this.callerType = callerType;
    }

    public List<GeneratorQuestionArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<GeneratorQuestionArgument> arguments) {
        this.arguments = arguments;
    }
}
