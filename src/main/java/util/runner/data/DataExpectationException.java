package util.runner.data;

public class DataExpectationException extends RuntimeException {

    DataExpectationException(String msg, Object... paras) {
        super(format(msg, paras));
    }

    DataExpectationException(Throwable cause, String msg, Object... paras) {
        super(format(msg, paras), cause);
    }

    private static String format(String msg, Object... paras) {
        return paras.length == 0 ? msg : String.format(msg, paras);
    }

}
