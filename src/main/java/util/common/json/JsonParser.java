package util.common.json;


/**
 * JSON 解析工具
 */
public class JsonParser {

    private static final JsonBuilder BUILDER = new JsonBuilder();

    /**
     * 根据指定类型解析数据
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseJson(String json, Class<T> clazz) {
        JsonObject object = BUILDER.parse(json);
        if (object == null) {
            return null;
        }

        JsonConverter converter = new JsonConverter();
        converter.addGenericTypes(clazz);
        return (T) converter.convertType(object, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T parseJson(String json, JsonTypeWrapper<T> type) {
        JsonObject object = BUILDER.parse(json);
        if (object == null) {
            return null;
        }

        Class<?> clazz = type.getClass();
        JsonConverter converter = new JsonConverter();
        converter.addGenericTypes(clazz);
        while (!JsonTypeWrapper.class.equals(clazz)) {
            clazz = clazz.getSuperclass();
        }
        return (T) converter.convertType(object, clazz.getTypeParameters()[0]);
    }

}
