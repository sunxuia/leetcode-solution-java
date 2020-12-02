package util.runner.data;

import util.common.json.JsonParser;
import util.common.json.JsonTypeWrapper;

public class TestDataFileHelper {

    /**
     * 读取文件内容
     *
     * @param clazz 文件内容的类型
     * @param <T> 文件内容的类型
     * @return 指定类型
     */
    public static <T> LazyDataProvider<T> read(Class<T> clazz) {
        TestDataFile file = new TestDataFile();
        return () -> {
            String content = file.getLine(1);
            return JsonParser.parseJson(content, clazz);
        };
    }

    public static <T> LazyDataProvider<T> read(JsonTypeWrapper<T> typeWrapper) {
        TestDataFile file = new TestDataFile();
        return () -> {
            String content = file.getLine(1);
            return JsonParser.parseJson(content, typeWrapper);
        };
    }

    /**
     * 读取文件内容
     *
     * @param fileName 文件名
     * @param clazz 文件内容的类型
     * @param <T> 文件内容的类型
     * @return 指定类型
     */
    public static <T> LazyDataProvider<T> read(String fileName, Class<T> clazz) {
        TestDataFile file = new TestDataFile(fileName);
        return () -> {
            String content = file.getLine(1);
            return JsonParser.parseJson(content, clazz);
        };
    }

    public static <T> LazyDataProvider<T> read(String fileName, JsonTypeWrapper<T> typeWrapper) {
        TestDataFile file = new TestDataFile(fileName);
        return () -> {
            String content = file.getLine(1);
            return JsonParser.parseJson(content, typeWrapper);
        };
    }

    /**
     * 读取文件内容
     *
     * @param file 要读取的文件
     * @param line 行号 (从1 开始)
     * @param clazz 文件内容的类型
     * @param <T> 文件内容的类型
     * @return 指定类型
     */
    public static <T> LazyDataProvider<T> read(TestDataFile file, int line, Class<T> clazz) {
        return () -> {
            String content = file.getLine(line);
            return JsonParser.parseJson(content, clazz);
        };
    }

    public static <T> LazyDataProvider<T> read(TestDataFile file, int line, JsonTypeWrapper<T> typeWrapper) {
        return () -> {
            String content = file.getLine(line);
            return JsonParser.parseJson(content, typeWrapper);
        };
    }
}
