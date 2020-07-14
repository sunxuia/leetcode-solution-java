package util.generator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JSON 解析工具
 */
public class JsonParser {

    public static Map<String, Object> parseJsonToMap(String json) {
        JsonParser parser = new JsonParser();
        List<JsonToken> tokens = parser.getTokens(json);
        JsonObject object = parser.parseTokens(tokens);
        if (object == null) {
            return null;
        }
        if (object.type != JsonObjectType.OBJECT) {
            throw new GeneratorException("Json root type should be object!");
        }
        return parser.convertToMap(object);
    }

    /**
     * 表示json 中的词
     */
    private static class JsonToken {

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

    /**
     * JSON 中的对象
     */
    private static class JsonObject {

        final JsonObjectType type;
        final JsonToken token;
        final List<JsonObjectChildItem> children = new ArrayList<>();

        public JsonObject(JsonObjectType type, JsonToken token) {
            this.type = type;
            this.token = token;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(type);
            if (token != null) {
                sb.append(" : ").append(token);
            }
            if (!children.isEmpty()) {
                sb.append(" : children size ").append(children.size());
            }
            return sb.toString();
        }
    }

    private static class JsonObjectChildItem {

        JsonObject key, value;
    }

    /**
     * JSON 中对象的类型
     */
    enum JsonObjectType {
        // {}
        OBJECT,
        // []
        ARRAY,
        // string, number, boolean, null
        VALUE,
    }

    private static final Map<Integer, JsonToken> STOP_TOKENS = new HashMap<>();

    private static final Map<String, JsonToken> LITERAL_TOKENS = new HashMap<>();

    private static final Map<Integer, Integer> ESCAPE_CHARACTERS = new HashMap<>();

    private static final Set<Integer> WHITESPACE_CHARACTERS = new HashSet<>();

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^-?(0|[1-9]\\d*)(\\.\\d+)?([Ee][+-]?\\d+)?$");

    static {
        STOP_TOKENS.put((int) '{', new JsonToken(JsonTokenType.LEFT_BRACE, "{"));
        STOP_TOKENS.put((int) '}', new JsonToken(JsonTokenType.RIGHT_BRACE, "}"));
        STOP_TOKENS.put((int) '[', new JsonToken(JsonTokenType.LEFT_BRACKET, "["));
        STOP_TOKENS.put((int) ']', new JsonToken(JsonTokenType.RIGHT_BRACKET, "]"));
        STOP_TOKENS.put((int) ',', new JsonToken(JsonTokenType.COMMA, ","));
        STOP_TOKENS.put((int) ':', new JsonToken(JsonTokenType.COLON, ":"));

        LITERAL_TOKENS.put("true", new JsonToken(JsonTokenType.TRUE, "true"));
        LITERAL_TOKENS.put("false", new JsonToken(JsonTokenType.FALSE, "false"));
        LITERAL_TOKENS.put("null", new JsonToken(JsonTokenType.NULL, "null"));

        ESCAPE_CHARACTERS.put((int) 'b', (int) '\b');
        ESCAPE_CHARACTERS.put((int) 'f', (int) '\f');
        ESCAPE_CHARACTERS.put((int) 'n', (int) '\n');
        ESCAPE_CHARACTERS.put((int) 'r', (int) '\r');
        ESCAPE_CHARACTERS.put((int) 't', (int) '\t');

        WHITESPACE_CHARACTERS.add((int) ' ');
        WHITESPACE_CHARACTERS.add((int) '\t');
        WHITESPACE_CHARACTERS.add((int) '\r');
        WHITESPACE_CHARACTERS.add((int) '\n');
    }

    private List<JsonToken> getTokens(String json) {
        List<JsonToken> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inString = false;
        int[] codePoints = json.codePoints().toArray();
        for (int i = 0; i < codePoints.length; i++) {
            final int cp = codePoints[i];
            if (inString) {
                if (cp == '\\' && i < codePoints.length - 1) {
                    int next = codePoints[++i];
                    if (next == 'u' && i < codePoints.length - 4) {
                        int value = 0;
                        for (int limit = i + 4; i < limit; i++) {
                            int c = codePoints[i + 1];
                            value <<= 4;
                            if ('0' <= c && c <= '9') {
                                value += c - '0';
                            } else if ('a' <= c && c <= 'f') {
                                value += c - 'a' + 10;
                            } else if ('A' <= c && c <= 'F') {
                                value += c - 'A' + 10;
                            } else {
                                i = limit - 4;
                                value = 'u';
                                break;
                            }
                        }
                        sb.appendCodePoint(value);
                    } else {
                        sb.appendCodePoint(ESCAPE_CHARACTERS.getOrDefault(next, next));
                    }
                } else if (cp == '"') {
                    inString = false;
                    res.add(new JsonToken(JsonTokenType.STRING, sb.toString()));
                    sb.setLength(0);
                } else {
                    sb.appendCodePoint(cp);
                }
            } else if (STOP_TOKENS.containsKey(cp)) {
                String str = toTrimString(sb);
                if (!str.isEmpty()) {
                    String lowerCase = str.toLowerCase();
                    if (LITERAL_TOKENS.containsKey(lowerCase)) {
                        res.add(LITERAL_TOKENS.get(lowerCase));
                    } else if (NUMBER_PATTERN.matcher(str).find()) {
                        res.add(new JsonToken(JsonTokenType.NUMBER, str));
                    } else {
                        throw new GeneratorException("Unknown token: " + str);
                    }
                }
                sb.setLength(0);
                res.add(STOP_TOKENS.get(cp));
            } else {
                if (cp == '"') {
                    sb.setLength(0);
                    inString = true;
                } else {
                    sb.appendCodePoint(cp);
                }
            }
        }
        if (!toTrimString(sb).isEmpty()) {
            throw new GeneratorException("Unexpected end of string: " + sb.toString());
        }
        return res;
    }

    private String toTrimString(StringBuilder sb) {
        int end = sb.length();
        while (end > 0 &&
                WHITESPACE_CHARACTERS.contains(sb.codePointBefore(end))) {
            end--;
        }
        int start = 0;
        while (start < end &&
                WHITESPACE_CHARACTERS.contains(sb.codePointAt(start))) {
            start++;
        }
        return sb.substring(start, end);
    }

    private JsonObject parseTokens(List<JsonToken> tokens) {
        Deque<JsonObject> stack = new ArrayDeque<>();
        stack.push(new JsonObject(JsonObjectType.ARRAY, null));
        for (JsonToken token : tokens) {
            JsonObject parent = stack.peek();
            JsonObject curr = null;
            switch (token.type) {
                case LEFT_BRACE:
                    curr = new JsonObject(JsonObjectType.OBJECT, null);
                    stack.push(curr);
                    break;
                case LEFT_BRACKET:
                    curr = new JsonObject(JsonObjectType.ARRAY, null);
                    stack.push(curr);
                    break;
                case RIGHT_BRACE:
                case RIGHT_BRACKET:
                    stack.pop();
                    break;
                case COMMA:
                case COLON:
                    continue;
                default:
                    curr = new JsonObject(JsonObjectType.VALUE, token);
            }

            if (curr != null) {
                JsonObjectChildItem child;
                switch (parent.type) {
                    case OBJECT:
                        if (!parent.children.isEmpty()) {
                            child = parent.children.get(parent.children.size() - 1);
                            if (child.value == null) {
                                child.value = curr;
                                break;
                            }
                        }
                        if (curr.type != JsonObjectType.VALUE) {
                            throw new GeneratorException("Json object's key should be value!");
                        }
                        child = new JsonObjectChildItem();
                        child.key = curr;
                        parent.children.add(child);
                        break;
                    case ARRAY:
                        child = new JsonObjectChildItem();
                        child.value = curr;
                        parent.children.add(child);
                        break;
                    default:
                }
            }
        }

        List<JsonObjectChildItem> items = stack.getFirst().children;
        return items.isEmpty() ? null : items.get(0).value;
    }

    private Object convertToObject(JsonObject object) {
        switch (object.type) {
            case OBJECT:
                return convertToMap(object);
            case ARRAY:
                return convertToList(object);
            case VALUE:
                return convertToValue(object);
            default:
                throw new GeneratorException("Should not run to here");
        }
    }

    private Map<String, Object> convertToMap(JsonObject map) {
        Map<String, Object> res = new LinkedHashMap<>();
        for (JsonObjectChildItem child : map.children) {
            Object key = convertToObject(child.key);
            String keyStr = key == null ? "null" : key.toString();
            res.put(keyStr, convertToObject(child.value));
        }
        return res;
    }

    private List<Object> convertToList(JsonObject array) {
        List<Object> res = new ArrayList<>(array.children.size());
        for (JsonObjectChildItem child : array.children) {
            res.add(convertToObject(child.value));
        }
        return res;
    }

    private Object convertToValue(JsonObject object) {
        switch (object.token.type) {
            case NUMBER:
                return parseNumber(object.token.word);
            case STRING:
                return object.token.word;
            case TRUE:
                return Boolean.TRUE;
            case FALSE:
                return Boolean.FALSE;
            case NULL:
                return null;
            default:
                throw new GeneratorException("Should not run to here.");
        }
    }

    private Object parseNumber(String str) {
        Matcher matcher = NUMBER_PATTERN.matcher(str);
        matcher.find();
        if (matcher.group(2) == null && matcher.group(3) == null) {
            return Long.parseLong(str);
        }
        return Double.parseDouble(str);
    }
}
