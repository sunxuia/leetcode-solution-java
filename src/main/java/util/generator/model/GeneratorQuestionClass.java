package util.generator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A (processed) question class.
 */
public class GeneratorQuestionClass {

    private String packageName;
    private String className;
    private String directory;
    private boolean isMethodQuestion;
    private String decodedContent;
    private String decodedCode;
    private List<GeneratorQuestionMethod> methods = new ArrayList<>();

    private String testedClassName;
    private int methodArgumentCount;
    private int exampleCount;
    private String methodCode;
    private List<GeneratorQuestionExample> examples = new ArrayList<>();

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public boolean isMethodQuestion() {
        return isMethodQuestion;
    }

    public void setMethodQuestion(boolean methodQuestion) {
        isMethodQuestion = methodQuestion;
    }

    public String getDecodedContent() {
        return decodedContent;
    }

    public void setDecodedContent(String decodedContent) {
        this.decodedContent = decodedContent;
    }

    public String getDecodedCode() {
        return decodedCode;
    }

    public void setDecodedCode(String decodedCode) {
        this.decodedCode = decodedCode;
    }

    public List<GeneratorQuestionMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<GeneratorQuestionMethod> methods) {
        this.methods = methods;
    }

    public String getTestedClassName() {
        return testedClassName;
    }

    public void setTestedClassName(String testedClassName) {
        this.testedClassName = testedClassName;
    }

    public int getMethodArgumentCount() {
        return methodArgumentCount;
    }

    public void setMethodArgumentCount(int methodArgumentCount) {
        this.methodArgumentCount = methodArgumentCount;
    }

    public int getExampleCount() {
        return exampleCount;
    }

    public void setExampleCount(int exampleCount) {
        this.exampleCount = exampleCount;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    public List<GeneratorQuestionExample> getExamples() {
        return examples;
    }

    public void setExamples(List<GeneratorQuestionExample> examples) {
        this.examples = examples;
    }
}
