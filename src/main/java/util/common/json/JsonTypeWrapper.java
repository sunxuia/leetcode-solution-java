package util.common.json;

import java.util.List;
import java.util.Map;

/**
 * 解析json 对象时候传递泛型参数
 * @param <T> 要解析的泛型参数
 */
public abstract class JsonTypeWrapper<T> {

    public static final JsonTypeWrapper<Map<String, Object>> DEFAULT_MAP = new JsonTypeWrapper<>() {};

    public static final JsonTypeWrapper<List<String>> STRING_LIST = new JsonTypeWrapper<>() {};

    public static final JsonTypeWrapper<List<Integer>> INTEGER_LIST = new JsonTypeWrapper<>() {};

}
