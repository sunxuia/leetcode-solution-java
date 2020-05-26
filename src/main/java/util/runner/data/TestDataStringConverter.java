package util.runner.data;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import util.runner.data.TestDataString.TestDataStringType;

class TestDataStringConverter {

    private final TestDataString root;

    private Type type;

    private Map<TypeVariable<?>, Type> genericTypes;

    private Map<Type, Function<TestDataString, Object>> converters = Collections.emptyMap();


    public TestDataStringConverter(TestDataString root) {
        this.root = root;
    }

    public void addConverter(Type type, Function<TestDataString, Object> converter) {
        converters.put(type, converter);
    }

    public Object toObject(Type type) {
        this.genericTypes = new HashMap<>();
        return parseObject(type, root);
    }

    private Object parseObject(Type type, TestDataString data) {
        if (converters.containsKey(type)) {
            return converters.get(type).apply(data);
        }
        if (data.getType() == TestDataStringType.NULL) {
            return null;
        }
        if (DATA_CONVERTERS.containsKey(type)) {
            TypeConverter<?> converter = DATA_CONVERTERS.get(type);
            return converter.convert(data);
        }

        if (type instanceof TypeVariable) {
            return parseObject(genericTypes.get(type), data);
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return parseArray(componentType, data);
        } else if (type instanceof ParameterizedType) {
            return parseParameterizedType((ParameterizedType) type, data);
        } else if (type instanceof WildcardType) {
            throw new TestDataException("Wildcard type is not supported : " + type + ".");
        } else {
            Class<?> clazz = (Class<?>) type;
            if (clazz.isArray()) {
                // array
                return parseArray(clazz.getComponentType(), data);
            } else {
                // java object
                throw new TestDataException("Unparseable java object: " + clazz.getName());
            }
        }
    }

    private Object[] parseArray(Type componentType, TestDataString data) {
        if (!data.isArray()) {
            throw new TestDataException("Try to parse List with non-array type.");
        }
        List<TestDataString> children = data.getChildren();
        Object[] res = new Object[children.size()];
        for (int i = 0; i < children.size(); i++) {
            res[i] = parseObject(componentType, children.get(i));
        }
        return res;
    }

    private Object parseParameterizedType(ParameterizedType type, TestDataString data) {
        final Type rawType = type.getRawType();
        if (rawType.equals(List.class) || rawType.equals(Collections.class)) {
            return Arrays.asList(parseArray(rawType, data));
        } else if (rawType.equals(Set.class)) {
            return Arrays.stream(parseArray(rawType, data)).collect(Collectors.toSet());
        } else if (type.getOwnerType() instanceof Class) {
            addGenericType((Class<?>) rawType, type);
            return parseObject(rawType, data);
        } else {
            throw new TestDataException("Not supported ParameterizedType type convert : " + type + ".");
        }
    }

    private void addGenericType(Class<?> clazz, Type genericType) {
        if (!(genericType instanceof ParameterizedType)) {
            return;
        }
        ParameterizedType type = (ParameterizedType) genericType;
        int i = 0;
        for (TypeVariable<?> typeVariable : clazz.getTypeParameters()) {
            genericTypes.put(typeVariable, type.getActualTypeArguments()[i++]);
        }
    }

    public interface TypeConverter<T> {

        T convert(TestDataString testDataString);

    }

    private static final Map<Type, TypeConverter<?>> DATA_CONVERTERS = new HashMap<>();

    static {
        DATA_CONVERTERS.put(String.class, TestDataString::getValue);
        DATA_CONVERTERS.put(Integer.class, d -> Integer.parseInt(d.getValue()));
        DATA_CONVERTERS.put(int.class, DATA_CONVERTERS.get(Integer.class));
        DATA_CONVERTERS.put(Long.class, d -> Long.parseLong(d.getValue()));
        DATA_CONVERTERS.put(long.class, DATA_CONVERTERS.get(Long.class));
        DATA_CONVERTERS.put(Double.class, d -> Double.parseDouble(d.getValue()));
        DATA_CONVERTERS.put(double.class, DATA_CONVERTERS.get(Double.class));
        DATA_CONVERTERS.put(Boolean.class, d -> {
            if (TestDataString.TRUE.equals(d)) {
                return true;
            }
            if (TestDataString.FALSE.equals(d)) {
                return false;
            }
            throw new TestDataException("Unkown boolean value: " + d.getValue());
        });
        DATA_CONVERTERS.put(boolean.class, DATA_CONVERTERS.get(Boolean.class));
        DATA_CONVERTERS.put(Character.class, d -> d.getValue().charAt(0));
        DATA_CONVERTERS.put(char.class, DATA_CONVERTERS.get(Character.class));
    }

}
