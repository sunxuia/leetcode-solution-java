package q450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/strong-password-checker/
 *
 * A password is considered strong if below conditions are all met:
 *
 * It has at least 6 characters and at most 20 characters.
 * It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
 * It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong,
 * assuming other conditions are met).
 * Write a function strongPasswordChecker(s), that takes a string s as input, and return the MINIMUM change required
 * to make s a strong password. If s is already strong, return 0.
 *
 * Insertion, deletion or replace of any one character are all considered as one change.
 */
@RunWith(LeetCodeRunner.class)
public class Q420_StrongPasswordChecker {

    /**
     * 不会做, 解答也很复杂.
     * https://www.cnblogs.com/grandyang/p/5988792.html
     */
    @Answer
    public int strongPasswordChecker(String s) {
        int res = 0, a = 1, A = 1, d = 1;
        char[] carr = s.toCharArray();
        int[] arr = new int[carr.length];

        for (int i = 0; i < arr.length; ) {
            if (Character.isLowerCase(carr[i])) {
                a = 0;
            }
            if (Character.isUpperCase(carr[i])) {
                A = 0;
            }
            if (Character.isDigit(carr[i])) {
                d = 0;
            }

            int j = i;
            while (i < carr.length && carr[i] == carr[j]) {
                i++;
            }
            arr[j] = i - j;
        }

        int totalMissing = (a + A + d);

        if (arr.length < 6) {
            res += totalMissing + Math.max(0, 6 - (arr.length + totalMissing));

        } else {
            int overLength = Math.max(arr.length - 20, 0), leftOver = 0;
            res += overLength;

            for (int k = 1; k < 3; k++) {
                for (int i = 0; i < arr.length && overLength > 0; i++) {
                    if (arr[i] < 3 || arr[i] % 3 != (k - 1)) {
                        continue;
                    }
                    arr[i] -= Math.min(overLength, k);
                    overLength -= k;
                }
            }

            for (int i = 0; i < arr.length; i++) {
                if (arr[i] >= 3 && overLength > 0) {
                    int need = arr[i] - 2;
                    arr[i] -= overLength;
                    overLength -= need;
                }

                if (arr[i] >= 3) {
                    leftOver += arr[i] / 3;
                }
            }

            res += Math.max(totalMissing, leftOver);
        }

        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("").expect(6);

}
