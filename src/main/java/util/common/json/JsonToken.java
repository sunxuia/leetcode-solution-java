package util.common.json;

/**
 * 表示json 中的词
 */
class JsonToken {

    final JsonTokenType type;

    final String word;

    public JsonToken(JsonTokenType type, String word) {
        this.type = type;
        this.word = word;
    }

    @Override
    public String toString() {
        return "[ " + type + "] " + word;
    }

    /**
     * json token 的类型
     */
    enum JsonTokenType {
        // {
        LEFT_BRACE,
        // }
        RIGHT_BRACE,
        // [
        LEFT_BRACKET,
        // ]
        RIGHT_BRACKET,
        // ,
        COMMA,
        // :
        COLON,
        // number
        NUMBER,
        // string
        STRING,
        // boolean true
        TRUE,
        // boolean false
        FALSE,
        // null
        NULL,
    }
}
