package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1680. Concatenation of Consecutive Binary Numbers
 * https://leetcode.com/problems/concatenation-of-consecutive-binary-numbers/
 *
 * Given an integer n, return the decimal value of the binary string formed by concatenating the binary representations
 * of 1 to n in order, modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: n = 1
 * Output: 1
 * Explanation: "1" in binary corresponds to the decimal value 1.
 *
 * Example 2:
 *
 * Input: n = 3
 * Output: 27
 * Explanation: In binary, 1, 2, and 3 corresponds to "1", "10", and "11".
 * After concatenating them, we have "11011", which corresponds to the decimal value 27.
 *
 * Example 3:
 *
 * Input: n = 12
 * Output: 505379714
 * Explanation: The concatenation results in "1101110010111011110001001101010111100".
 * The decimal value of that is 118505380540.
 * After modulo 10^9 + 7, the result is 505379714.
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1680_ConcatenationOfConsecutiveBinaryNumbers {

    @Answer
    public int concatenatedBinary(int n) {
        final int mod = 10_0000_0007;
        long res = 0;
        for (int i = 1, len = 0; i <= n; i++) {
            if ((i >> len & 1) != 0) {
                len++;
            }
            res = (res << len | i) % mod;
        }
        return (int) res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(3).expect(27);

    @TestData
    public DataExpectation example3 = DataExpectation.create(12).expect(505379714);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(100000).expect(757631812);

}
