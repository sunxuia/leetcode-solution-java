package util.runner.data;

public class SimpleDataExpectationBuilder {

    private DataExpectation expectation = new DataExpectation();

    SimpleDataExpectationBuilder(Object[] arguments) {
        expectation.setArguments(arguments);
    }

    public DataExpectation expect(Object e) {
        expectation.setExpect(e);
        return expectation;
    }
}
