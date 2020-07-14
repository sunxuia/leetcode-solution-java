package util.generator;

public class GeneratorException extends RuntimeException {

    public GeneratorException(String message, Object... params) {
        super(format(message, params));
    }

    private static String format(String msg, Object... params) {
        if (params.length > 0) {
            msg = String.format(msg, params);
        }
        return msg;
    }

}
