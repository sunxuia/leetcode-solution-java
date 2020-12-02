package util.common.json;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import util.common.json.JsonObject.JsonObjectChildItem;

/**
 * 将JSONObject 转换为对应类型.
 */
class JsonConverter {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^-?(0|[1-9]\\d*)(\\.\\d+)?([Ee][+-]?\\d+)?$");

    private Map<TypeVariable<?>, Type> genericTypes = new HashMap<>();

    void addGenericTypes(Class<?> clazz) {
        for (; clazz != null; clazz = clazz.getSuperclass()) {
            Type genericType = clazz.getGenericSuperclass();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) genericType;
                int i = 0;
                for (TypeVariable<?> typeVariable : clazz.getSuperclass().getTypeParameters()) {
                    genericTypes.put(typeVariable, type.getActualTypeArguments()[i++]);
                }
            }
        }
    }

    Object convertType(JsonObject object, Type type) {
        while (type instanceof TypeVariable) {
            type = genericTypes.get(type);
        }
        switch (object.type) {
            case OBJECT:
                return convertObject(object, type);
            case ARRAY:
                return convertList(object, type);
            case VALUE:
                return convertValue(object, type);
            default:
                throw new JsonException("Should not run to here");
        }
    }

    Object convertObject(JsonObject map, Type type) {
        Class<?> clazz = getBaseClass(type);
        Type keyType, valueType;
        Map<Object, Object> res;
        if (isDefault(clazz)) {
            keyType = String.class;
            valueType = Object.class;
            res = new LinkedHashMap<>();
        } else if (Map.class.isAssignableFrom(clazz)) {
            if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                Type[] typeArgs = pt.getActualTypeArguments();
                keyType = typeArgs[0];
                valueType = typeArgs[1];
            } else if (type instanceof Class) {
                keyType = String.class;
                valueType = Object.class;
            } else {
                throw new JsonException("Unsupported type in object: %s", type);
            }
            if (Map.class.equals(clazz)) {
                res = new LinkedHashMap<>();
            } else {
                res = createObject(clazz);
            }
        } else {
            throw new JsonException("Class [%s] not supported!", clazz);
        }

        for (JsonObjectChildItem child : map.children) {
            Object key = convertType(child.key, keyType);
            Object value = convertType(child.value, valueType);
            res.put(key, value);
        }
        return res;
    }

    private Class<?> getBaseClass(Type type) {
        if (type == null) {
            throw new JsonException("Type is null!");
        }
        if (type instanceof Class) {
            return (Class<?>) type;
        }
        if (type instanceof TypeVariable) {
            return getBaseClass(genericTypes.get(type));
        }
        if (type instanceof GenericArrayType) {
            Type comp = ((GenericArrayType) type).getGenericComponentType();
            return getBaseClass(comp);
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            if (pt.getOwnerType() instanceof Class) {
                addGenericTypes((Class<?>) pt.getOwnerType());
            }
            return getBaseClass(pt.getRawType());
        }
        if (type instanceof WildcardType) {
            throw new JsonException("Wildcard type not supported!");
        }
        throw new JsonException("Type not supported!");
    }

    private boolean isDefault(Class<?> clazz) {
        return Object.class.equals(clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> T createObject(Class<?> clazz) {
        try {
            Object res = clazz.getConstructor().newInstance();
            return (T) res;
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    Object convertList(JsonObject array, Type type) {
        final int len = array.children.size();
        Class<?> clazz = getBaseClass(type);
        if (clazz.isArray()) {
            Class<?> compClass = clazz.getComponentType();
            Object res = Array.newInstance(compClass, len);
            for (int i = 0; i < len; i++) {
                JsonObject child = array.children.get(i).value;
                Object item = convertType(child, compClass);
                Array.set(res, i, item);
            }
            return res;
        }

        Collection<Object> res;
        if (isDefault(clazz) || Collection.class.equals(clazz)
                || List.class.equals(clazz)) {
            res = new ArrayList<>(len);
        } else if (Set.class.equals(clazz)) {
            res = new HashSet<>(len);
        } else {
            res = createObject(clazz);
        }

        Type elementType = Object.class;
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            elementType = pt.getActualTypeArguments()[0];
        }
        for (JsonObjectChildItem child : array.children) {
            Object item = convertType(child.value, elementType);
            res.add(item);
        }
        return res;
    }

    Object convertValue(JsonObject object, Type type) {
        Class<?> clazz = getBaseClass(type);
        switch (object.token.type) {
            case NUMBER:
                return parseNumber(object.token.word, clazz);
            case STRING:
                return parseString(object.token.word, clazz);
            case TRUE:
                return parseBoolean(true, clazz);
            case FALSE:
                return parseBoolean(false, clazz);
            case NULL:
                return null;
            default:
                throw new JsonException("Should not run to here.");
        }
    }

    Object parseNumber(String str, Class<?> clazz) {
        if (isDefault(clazz)) {
            Matcher matcher = NUMBER_PATTERN.matcher(str);
            matcher.find();
            if (matcher.group(2) == null && matcher.group(3) == null) {
                return Long.parseLong(str);
            }
            return Double.parseDouble(str);
        }
        if (Double.class.equals(clazz) || double.class.equals(clazz)) {
            return Double.parseDouble(str);
        }
        if (Integer.class.equals(clazz) || int.class.equals(clazz)) {
            return Integer.parseInt(str);
        }
        if (Long.class.equals(clazz) || long.class.equals(clazz)) {
            return Long.parseLong(str);
        }
        if (Byte.class.equals(clazz) || byte.class.equals(clazz)) {
            return Byte.parseByte(str);
        }
        if (Short.class.equals(clazz) || short.class.equals(clazz)) {
            return Short.parseShort(str);
        }
        if (Boolean.class.equals(clazz) || boolean.class.equals(clazz)) {
            return !"0".equals(str);
        }
        if (String.class.equals(clazz)) {
            return str;
        }
        throw new JsonException("Unknown number type %s.", clazz);
    }

    Object parseString(String str, Class<?> clazz) {
        if (isDefault(clazz) || String.class.equals(clazz)) {
            return str;
        }
        if (Character.class.equals(clazz) || char.class.equals(clazz)) {
            return str.charAt(0);
        }
        if (Number.class.isAssignableFrom(clazz)) {
            return parseNumber(str, clazz);
        }
        throw new JsonException("Unknown string type %s.", clazz);
    }

    Object parseBoolean(boolean value, Class<?> clazz) {
        return parseNumber(value ? "1" : "0", clazz);
    }
}
