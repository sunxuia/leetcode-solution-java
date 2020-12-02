package util.common.json;

public class JsonException extends RuntimeException {

    public JsonException(Throwable cause) {
        super(cause);
    }

    public JsonException(String message, Object... params) {
        super(format(message, params));
    }

    private static String format(String msg, Object... params) {
        if (params.length > 0) {
            msg = String.format(msg, params);
        }
        return msg;
    }

}
