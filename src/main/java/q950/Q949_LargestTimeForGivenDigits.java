package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 949. Largest Time for Given Digits
 * https://leetcode.com/problems/largest-time-for-given-digits/
 *
 * Given an array of 4 digits, return the largest 24 hour time that can be made.
 *
 * The smallest 24 hour time is 00:00, and the largest is 23:59.  Starting from 00:00, a time is larger if more time has
 * elapsed since midnight.
 *
 * Return the answer as a string of length 5.  If no valid time can be made, return an empty string.
 *
 * Example 1:
 *
 * Input: [1,2,3,4]
 * Output: "23:41"
 *
 * Example 2:
 *
 * Input: [5,5,5,5]
 * Output: ""
 *
 * Note:
 *
 * A.length == 4
 * 0 <= A[i] <= 9
 */
@RunWith(LeetCodeRunner.class)
public class Q949_LargestTimeForGivenDigits {

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect("23:41");
    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{5, 5, 5, 5}).expect("");
    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0, 0, 0, 0}).expect("00:00");

    @Answer
    public String largestTimeFromDigits(int[] A) {
        char[] sb = new char[5];
        sb[2] = ':';
        if (getHour10(A, sb)) {
            return new String(sb);
        }
        return "";
    }

    private boolean getHour10(int[] arr, char[] sb) {
        for (int i = 2; i >= 0; i--) {
            int index = indexOf(arr, 4, i);
            if (index >= 0) {
                sb[0] = (char) ('0' + i);
                swap(arr, index, 3);
                if (getHour1(arr, i == 2 ? 4 : 10, sb)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean getHour1(int[] arr, int limit, char[] sb) {
        while (true) {
            int index = -1;
            while (limit > 0 && index < 0) {
                index = indexOf(arr, 3, --limit);
            }
            if (index == -1) {
                return false;
            }
            sb[1] = (char) ('0' + limit);
            swap(arr, index, 2);
            if (getMinute(arr, sb)) {
                return true;
            }
        }
    }

    private boolean getMinute(int[] arr, char[] sb) {
        int val1 = arr[0] * 10 + arr[1];
        int val2 = arr[1] * 10 + arr[0];
        val1 = val1 >= 60 ? -1 : val1;
        val2 = val2 >= 60 ? -1 : val2;
        int val = Math.max(val1, val2);
        if (val < 0) {
            return false;
        }
        sb[3] = (char) ('0' + val / 10);
        sb[4] = (char) ('0' + val % 10);
        return true;
    }

    private int indexOf(int[] arr, int len, int value) {
        for (int i = 0; i < len; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private void swap(int[] arr, int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }

    /**
     * 参考LeetCode 上的简单实现方式, 会慢一点.
     */
    @Answer
    public String largestTimeFromDigits2(int[] A) {
        int maxHour = -1, maxMinute = -1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i != j) {
                    for (int k = 0; k < 4; k++) {
                        if (i != k && j != k) {
                            for (int l = 0; l < 4; l++) {
                                if (i != l && j != l && k != l) {
                                    int hour = A[i] * 10 + A[j];
                                    int minute = A[k] * 10 + A[l];
                                    if (hour < 24 && minute < 60
                                            && (maxHour < hour || maxHour == hour && maxMinute < minute)) {
                                        maxHour = hour;
                                        maxMinute = minute;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (maxHour == -1) {
            return "";
        }
        return String.format("%02d:%02d", maxHour, maxMinute);
    }

}
