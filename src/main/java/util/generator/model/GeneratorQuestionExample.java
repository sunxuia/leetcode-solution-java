package util.generator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * example
 */
public class GeneratorQuestionExample {

    private List<String> inputs = new ArrayList<>();

    private String output;

    public List<String> getInputs() {
        return inputs;
    }

    public void setInputs(List<String> inputs) {
        this.inputs = inputs;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
