package util.common.json;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON 中的对象
 */
class JsonObject {

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

    static class JsonObjectChildItem {

        JsonObject key, value;
    }
}
