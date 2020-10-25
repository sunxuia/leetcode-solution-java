package util.runner.data;

public class DataExpectationAdaptor extends DataExpectation {

    DataExpectationAdaptor(Object[] arguments) {
        setArguments(arguments);
    }

    public DataExpectation expect(Object e) {
        setExpect(e);
        return this;
    }
}
