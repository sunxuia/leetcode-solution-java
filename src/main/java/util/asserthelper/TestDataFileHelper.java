package util.asserthelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDataFileHelper {

    private static final String DEFAULT_PREFIX = ".test.txt";

    // 单行文本, 数组以逗号分隔
    public static int[] readIntegerArray(String fileName) {
        String content = readString(fileName);
        String[] strs = content.split(",");
        int[] res = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            try {
                res[i] = Integer.parseInt(strs[i].trim());
            } catch (NumberFormatException err) {
                throw new RuntimeException(String.format("Error while parse %dth number: %s.", i, strs[i]), err);
            }
        }
        return res;
    }

    // 读取文件所有的文本
    public static String readString(String fileName) {
        fileName = wrapFileName(fileName);
        StringBuilder sb = new StringBuilder();
        String filePath = TestDataFileHelper.class.getClassLoader().getResource(fileName).getFile();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line).append("\n");
            }
        } catch (IOException err) {
            throw new RuntimeException("Error while read file " + fileName, err);
        }
        return sb.toString();
    }

    private static String wrapFileName(String fileName) {
        if (!fileName.startsWith("/")) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            String callerClassName = "";
            for (int i = 1; i < stackTraceElements.length; i++) {
                if (!stackTraceElements[i].getClassName().equals(TestDataFileHelper.class.getName())) {
                    callerClassName = stackTraceElements[i].getClassName();
                    break;
                }
            }
            String packagePath = callerClassName.substring(0, callerClassName.lastIndexOf('.'))
                    .replaceAll("\\.", "/");
            fileName = packagePath + "/" + fileName;
        }

        if (!fileName.endsWith(DEFAULT_PREFIX)) {
            fileName += DEFAULT_PREFIX;
        }

        return fileName;
    }

    // 单行文本, 数组使用"[]" 来标记数组边界, 使用"," 来分隔.
    public static int[][] read2DArray(String fileName) {
        String content = readString(fileName);
        List<int[]> arrayBuffer = new ArrayList<>();
        List<Integer> elementBuffer = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '[' || c == ' ') {
                // do nothing
            } else if (c == ']') {
                if (content.charAt(i - 1) == ']') {
                    continue;
                }
                if (sb.length() > 0) {
                    elementBuffer.add(Integer.parseInt(sb.toString()));
                    sb.setLength(0);
                }
                int[] arr = new int[elementBuffer.size()];
                for (int j = 0; j < elementBuffer.size(); j++) {
                    arr[j] = elementBuffer.get(j);
                }
                arrayBuffer.add(arr);
                elementBuffer.clear();
            } else if (c == ',') {
                if (sb.length() > 0) {
                    elementBuffer.add(Integer.parseInt(sb.toString()));
                    sb.setLength(0);
                }
            } else {
                sb.append(c);
            }
        }
        int[][] res = new int[arrayBuffer.size()][];
        for (int i = 0; i < arrayBuffer.size(); i++) {
            res[i] = arrayBuffer.get(i);
        }
        return res;
    }

}
