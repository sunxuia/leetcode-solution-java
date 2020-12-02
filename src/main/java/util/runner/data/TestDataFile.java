package util.runner.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.UtilPackageHelper;

/**
 * 测试文件对象.
 * 文件名默认是和测试类相同目录, 比如 "q001/Q001_TestData.test.txt", 可以自定义指定文件名.
 * 文件内容以行为分隔符, 1行或多行分隔1 个部分, 如果行以 "//" 开头则认为是注释跳过(对应行内容为"").
 */
public class TestDataFile {

    private static final String DEFAULT_SUFFIX = ".test.txt";

    private static final String DEFAULT_FILE_SUFFIX = "_TestData" + DEFAULT_SUFFIX;

    /**
     * 测试文件的绝对路径.
     */
    private final String fileName;

    /**
     * 测试文件内的行
     */
    private List<String> lines;

    /**
     * 测试文件内的部分
     */
    private List<List<String>> parts;

    /**
     * 默认测试文件
     */
    public TestDataFile() {
        this.fileName = wrapFileName(null);
    }

    /**
     * 传入指定测试文件
     */
    public TestDataFile(String fileName) {
        if (fileName != null && fileName.isEmpty()) {
            throw new TestDataException("Test data fileName is empty!");
        }
        this.fileName = wrapFileName(fileName);
    }

    /**
     * 处理文件名, 规则:
     * - / 开始的绝对路径: 不处理.
     * - null : 测试所在目录 + 测试类的编号 + {@value DEFAULT_FILE_SUFFIX} 组成文件名,
     * 比如 q001/Q001_TestData.test.txt.
     * - 相对路径: 测试类所在目录 + fileName 组成的文件名(比如 q001/CustomTestData),
     * 如果测试文件没有以指定后缀{@value DEFAULT_SUFFIX} 结尾, 则会加上这个后缀.
     * 注意null 和相对路径的情况需要在测试类内创建这个对象.
     *
     * @param fileName 文件名参数.
     * @return 文件的绝对路径.
     */
    private String wrapFileName(String fileName) {
        if (fileName == null) {
            String callerClassName = getCallerClassName();
            String packageName = callerClassName.substring(0, callerClassName.lastIndexOf('.'));
            String packagePath = packageName.replaceAll("\\.", "/");
            String className = callerClassName.substring(callerClassName.lastIndexOf('.') + 1);
            String questionNo;
            if (className.indexOf('_') == -1) {
                questionNo = className;
            } else {
                questionNo = className.substring(0, className.indexOf('_'));
            }
            fileName = packagePath + "/" + questionNo + DEFAULT_FILE_SUFFIX;
        } else if (fileName.startsWith("/")) {
            // do nothing
        } else {
            String callerClassName = getCallerClassName();
            String packageName = callerClassName.substring(0, callerClassName.lastIndexOf('.'));
            String packagePath = packageName.replaceAll("\\.", "/");
            fileName = packagePath + "/" + fileName;
            if (!fileName.endsWith(DEFAULT_SUFFIX)) {
                fileName += DEFAULT_SUFFIX;
            }
        }
        return fileName;
    }

    private String getCallerClassName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String callerClassName = "";
        for (int i = 1; i < stackTraceElements.length; i++) {
            if (!UtilPackageHelper.isInPackage(stackTraceElements[i].getClassName())) {
                callerClassName = stackTraceElements[i].getClassName();
                break;
            }
        }
        return callerClassName;
    }

    /**
     * 获取指定行的数据 (对应该文本文件的空行)
     *
     * @param line 行号(从1 开始)
     */
    public String getLine(int line) {
        readFile();
        if (lines.size() < line) {
            throw new TestDataException(
                    "Test data file %s line %d does not exist (total %d).",
                    fileName, line, lines.size());
        }
        return lines.get(line - 1);
    }

    private void readFile() {
        if (this.parts != null) {
            return;
        }
        synchronized (this) {
            if (this.parts != null) {
                return;
            }

            List<String> lines = new ArrayList<>();
            List<List<String>> parts = new ArrayList<>();

            URL resource = TestDataFileHelper.class.getClassLoader().getResource(fileName);
            if (resource == null) {
                throw new TestDataException("File %s not found!", fileName);
            }
            try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    // 注释行特殊处理
                    if (line.startsWith("//")) {
                        lines.add("//");
                    } else {
                        lines.add(line);
                    }
                }
            } catch (IOException err) {
                throw new TestDataException(err, "Error while read file [%s]", fileName);
            }

            List<String> list = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if ("//".equals(line)) {
                    lines.set(i, "");
                } else if (line.isEmpty()) {
                    if (!list.isEmpty()) {
                        parts.add(list);
                        list = new ArrayList<>();
                    }
                } else {
                    list.add(line);
                }
            }
            if (!list.isEmpty()) {
                parts.add(lines);
            }
            this.lines = Collections.unmodifiableList(lines);
            this.parts = Collections.unmodifiableList(parts);
        }
    }

    /**
     * 获取所有行数据 (包括空行)
     */
    public List<String> getAll() {
        readFile();
        return lines;
    }

    /**
     * 获取指定部分的数据(用1 个或者以上空行分隔的不同行)
     *
     * @param idx 编号(从1 开始)
     */
    public List<String> getPart(int idx) {
        readFile();
        if (parts.size() > idx) {
            throw new TestDataException(
                    "Test data file %s part %d does not exist (total %d).",
                    fileName, idx, parts.size());
        }
        return parts.get(idx - 1);
    }

}
