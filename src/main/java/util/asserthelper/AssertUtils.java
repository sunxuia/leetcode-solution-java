package util.asserthelper;

import java.util.Arrays;
import util.UtilPackageHelper;

public class AssertUtils {

    private AssertUtils() {}

    /**
     * Fail with AssertionError thrown.
     *
     * @param message fail message, might be format string.
     * @param paras format string parameters.
     */
    public static void fail(String message, Object... paras) {
        if (paras.length > 0) {
            message = String.format(message, paras);
        }
        AssertionError assertionError = new AssertionError(message);
        StackTraceElement[] stackTraces = assertionError.getStackTrace();
        int subIndex = 1;
        while (UtilPackageHelper.isInPackage(stackTraces[subIndex].getClassName())) {
            subIndex++;
        }
        assertionError.setStackTrace(Arrays.copyOfRange(stackTraces, subIndex, stackTraces.length));
        throw assertionError;
    }

    /**
     * assert actual is in expects
     *
     * @param actual actual value
     * @param expects expect values
     */
    public static <T> void assertIn(T actual, T... expects) {
        ObjectEqualsHelper helper = new ObjectEqualsHelper();
        for (T expect : expects) {
            if (helper.isEquals(expect, actual).isEmpty()) {
                return;
            }
        }
        fail(new ErrorStringBuilder(actual)
                .append(" not in range ").append(expects)
                .toString());
    }

    public static <T> void assertEquals(T expected, T actual) {
        assertEquals(expected, actual, new ObjectEqualsHelper());
    }

    public static <T> void assertEquals(T expected, T actual, ObjectEqualsHelper helper) {
        String res = helper.isEquals(expected, actual);
        if (!res.isEmpty()) {
            fail(new ErrorStringBuilder()
                    .append("expect [").append(expected).append("]")
                    .append(" not equal to ")
                    .append("actual [").append(actual).append("]")
                    .append(": ").append(res)
                    .toString());
        }
    }

    public static <T> void assertEquals(int[] expected,
            int[] actual, int startInclusive, int endExclusive,
            ObjectEqualsHelper helper) {
        int[] realActual = new int[endExclusive - startInclusive];
        int[] realExpect = new int[endExclusive - startInclusive];
        for (int i = 0; i < realActual.length; i++) {
            realActual[i] = actual[startInclusive + i];
            realExpect[i] = expected[startInclusive + i];
        }
        assertEquals(realExpect, realActual, helper);
    }

}
