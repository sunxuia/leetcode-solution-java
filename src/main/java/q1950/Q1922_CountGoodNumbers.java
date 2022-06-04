package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1922. Count Good Numbers
 * https://leetcode.com/problems/count-good-numbers/
 *
 * A digit string is good if the digits (0-indexed) at even indices are even and the digits at odd indices are prime (2,
 * 3, 5, or 7).
 *
 * For example, "2582" is good because the digits (2 and 8) at even positions are even and the digits (5 and 2) at odd
 * positions are prime. However, "3245" is not good because 3 is at an even index but is not even.
 *
 * Given an integer n, return the total number of good digit strings of length n. Since the answer may be large, return
 * it modulo 10^9 + 7.
 *
 * A digit string is a string consisting of digits 0 through 9 that may contain leading zeros.
 *
 * Example 1:
 *
 * Input: n = 1
 * Output: 5
 * Explanation: The good numbers of length 1 are "0", "2", "4", "6", "8".
 *
 * Example 2:
 *
 * Input: n = 4
 * Output: 400
 *
 * Example 3:
 *
 * Input: n = 50
 * Output: 564908303
 *
 * Constraints:
 *
 * 1 <= n <= 10^15
 */
@RunWith(LeetCodeRunner.class)
public class Q1922_CountGoodNumbers {

    @Answer
    public int countGoodNumbers(long n) {
        final int mod = 10_0000_0007;
        long res = expMod(20, n / 2, mod);
        if (n % 2 == 1) {
            res = res * 5 % mod;
        }
        return (int) res;
    }

    /**
     * 快速幂取模运算. a^n%b
     * 参考文档 https://www.cnblogs.com/yinger/archive/2011/06/08/2075043.html
     */
    private long expMod(long a, long n, long mod) {
        if (n == 0) {
            return 1 % mod;
        }
        if (n == 1) {
            return a % mod;
        }
        long res = expMod(a, n / 2, mod);
        res = res * res % mod;
        if ((n & 1) == 1) {
            res = res * a % mod;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(4).expect(400);

    @TestData
    public DataExpectation example3 = DataExpectation.create(50).expect(564908303);

}
