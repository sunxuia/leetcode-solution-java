package q1250;

import org.junit.runner.RunWith;
import q300.Q264_UglyNumberII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1201. Ugly Number III
 * https://leetcode.com/problems/ugly-number-iii/
 *
 * Write a program to find the n-th ugly number.
 *
 * Ugly numbers are positive integers which are divisible by a or b or c.
 *
 * Example 1:
 *
 * Input: n = 3, a = 2, b = 3, c = 5
 * Output: 4
 * Explanation: The ugly numbers are 2, 3, 4, 5, 6, 8, 9, 10... The 3rd is 4.
 *
 * Example 2:
 *
 * Input: n = 4, a = 2, b = 3, c = 4
 * Output: 6
 * Explanation: The ugly numbers are 2, 3, 4, 6, 8, 9, 10, 12... The 4th is 6.
 *
 * Example 3:
 *
 * Input: n = 5, a = 2, b = 11, c = 13
 * Output: 10
 * Explanation: The ugly numbers are 2, 4, 6, 8, 10, 11, 12, 13... The 5th is 10.
 *
 * Example 4:
 *
 * Input: n = 1000000000, a = 2, b = 217983653, c = 336916467
 * Output: 1999999984
 *
 * Constraints:
 *
 * 1 <= n, a, b, c <= 10^9
 * 1 <= a * b * c <= 10^18
 * It's guaranteed that the result will be in range [1, 2 * 10^9]
 *
 * 上一题 {@link Q264_UglyNumberII}
 */
@RunWith(LeetCodeRunner.class)
public class Q1201_UglyNumberIII {

    /**
     * 这题可以通过最小公倍数的方式来做,
     * 参考文档 https://zhuanlan.zhihu.com/p/104567808
     */
    @Answer
    public int nthUglyNumber(int n, int a, int b, int c) {
        long start = 1, end = 20_0000_0000;
        long ab = lcm(a, b), ac = lcm(a, c), bc = lcm(b, c), abc = lcm(a, bc);
        while (start < end) {
            long mid = start + (end - start) / 2;
            long pos = mid / a + mid / b + mid / c - mid / ab - mid / ac - mid / bc + mid / abc;
            if (pos < n) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return (int) start;
    }

    private long lcm(long x, long y) {
        return x * y / gcd(x, y);
    }

    private long gcd(long x, long y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 2, 3, 5).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(4, 2, 3, 4).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(5, 2, 11, 13).expect(10);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(1000000000, 2, 217983653, 336916467)
            .expect(1999999984);

}
