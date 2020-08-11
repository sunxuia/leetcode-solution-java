package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 926. Flip String to Monotone Increasing
 * https://leetcode.com/problems/flip-string-to-monotone-increasing/
 *
 * A string of '0's and '1's is monotone increasing if it consists of some number of '0's (possibly 0), followed by some
 * number of '1's (also possibly 0.)
 *
 * We are given a string S of '0's and '1's, and we may flip any '0' to a '1' or a '1' to a '0'.
 *
 * Return the minimum number of flips to make S monotone increasing.
 *
 * Example 1:
 *
 * Input: "00110"
 * Output: 1
 * Explanation: We flip the last digit to get 00111.
 *
 * Example 2:
 *
 * Input: "010110"
 * Output: 2
 * Explanation: We flip to get 011111, or alternatively 000111.
 *
 * Example 3:
 *
 * Input: "00011000"
 * Output: 2
 * Explanation: We flip to get 00000000.
 *
 * Note:
 *
 * 1 <= S.length <= 20000
 * S only consists of '0' and '1' characters.
 */
@RunWith(LeetCodeRunner.class)
public class Q926_FlipStringToMonotoneIncreasing {

    @Answer
    public int minFlipsMonoIncr(String S) {
        final int n = S.length();
        char[] sc = S.toCharArray();

        int[] leftOne = new int[n];
        leftOne[0] = sc[0] == '0' ? 0 : 1;
        for (int i = 1; i < n; i++) {
            leftOne[i] = leftOne[i - 1] + (sc[i] == '0' ? 0 : 1);
        }
        int[] rightZero = new int[n];
        rightZero[n - 1] = sc[n - 1] == '0' ? 1 : 0;
        for (int i = n - 2; i >= 0; i--) {
            rightZero[i] = rightZero[i + 1] + (sc[i] == '0' ? 1 : 0);
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            res = Math.min(res, leftOne[i] + rightZero[i] - 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("00110").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("010110").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("00011000").expect(2);

}
