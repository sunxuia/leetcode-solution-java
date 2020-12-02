package util.common.json;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import util.common.json.JsonObject.JsonObjectChildItem;

/**
 * 构建JSON 对象.
 */
class JsonBuilder {

    private static final Map<Integer, JsonToken> STOP_TOKENS = new HashMap<>();

    private static final Map<String, JsonToken> LITERAL_TOKENS = new HashMap<>();

    private static final Map<Integer, Integer> ESCAPE_CHARACTERS = new HashMap<>();

    private static final Set<Integer> WHITESPACE_CHARACTERS = new HashSet<>();

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^-?(0|[1-9]\\d*)(\\.\\d+)?([Ee][+-]?\\d+)?$");

    static {
        STOP_TOKENS.put((int) '{', new JsonToken(JsonToken.JsonTokenType.LEFT_BRACE, "{"));
        STOP_TOKENS.put((int) '}', new JsonToken(JsonToken.JsonTokenType.RIGHT_BRACE, "}"));
        STOP_TOKENS.put((int) '[', new JsonToken(JsonToken.JsonTokenType.LEFT_BRACKET, "["));
        STOP_TOKENS.put((int) ']', new JsonToken(JsonToken.JsonTokenType.RIGHT_BRACKET, "]"));
        STOP_TOKENS.put((int) ',', new JsonToken(JsonToken.JsonTokenType.COMMA, ","));
        STOP_TOKENS.put((int) ':', new JsonToken(JsonToken.JsonTokenType.COLON, ":"));

        LITERAL_TOKENS.put("true", new JsonToken(JsonToken.JsonTokenType.TRUE, "true"));
        LITERAL_TOKENS.put("false", new JsonToken(JsonToken.JsonTokenType.FALSE, "false"));
        LITERAL_TOKENS.put("null", new JsonToken(JsonToken.JsonTokenType.NULL, "null"));

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

    JsonObject parse(String json) {
        List<JsonToken> tokens = getTokens(json);
        return parseTokens(tokens);
    }
    
    private List<JsonToken> getTokens(String json) {
        List<JsonToken> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inString = false;
        int[] codePoints = json.codePoints().toArray();
        final int len = codePoints.length;
        for (int i = 0; i <= len; i++) {
            final int cp = i == len ? ']' : codePoints[i];
            if (inString) {
                if (cp == '\\' && i < len - 1) {
                    int next = codePoints[++i];
                    if (next == 'u' && i < len - 4) {
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
                    res.add(new JsonToken(JsonToken.JsonTokenType.STRING, sb.toString()));
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
                        res.add(new JsonToken(JsonToken.JsonTokenType.NUMBER, str));
                    } else {
                        throw new JsonException("Unknown token: " + str);
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
            if (inString) {
                throw new JsonException("Unexpected end of string: " + sb.toString());
            }
        }
        if (!res.isEmpty()) {
            res.remove(res.size() - 1);
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
        stack.push(new JsonObject(JsonObject.JsonObjectType.ARRAY, null));
        for (JsonToken token : tokens) {
            JsonObject parent = stack.peek();
            JsonObject curr = null;
            switch (token.type) {
                case LEFT_BRACE:
                    curr = new JsonObject(JsonObject.JsonObjectType.OBJECT, null);
                    stack.push(curr);
                    break;
                case LEFT_BRACKET:
                    curr = new JsonObject(JsonObject.JsonObjectType.ARRAY, null);
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
                    curr = new JsonObject(JsonObject.JsonObjectType.VALUE, token);
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
                        if (curr.type != JsonObject.JsonObjectType.VALUE) {
                            throw new JsonException("Json object's key should be value!");
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
}
