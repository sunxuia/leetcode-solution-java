package util.asserthelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestDataFileHelper {

    private static final String DEFAULT_PREFIX = ".test.txt";

    public static int[] readIntegerArray(String fileName) {
        String content = readFileContent(fileName);
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

    private static String readFileContent(String fileName) {
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

}
