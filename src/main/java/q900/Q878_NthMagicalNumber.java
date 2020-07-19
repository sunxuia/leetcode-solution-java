package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 878. Nth Magical Number
 * https://leetcode.com/problems/nth-magical-number/
 *
 * A positive integer is magical if it is divisible by either A or B.
 *
 * Return the N-th magical number.  Since the answer may be very large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: N = 1, A = 2, B = 3
 * Output: 2
 *
 * Example 2:
 *
 * Input: N = 4, A = 2, B = 3
 * Output: 6
 *
 * Example 3:
 *
 * Input: N = 5, A = 2, B = 4
 * Output: 10
 *
 * Example 4:
 *
 * Input: N = 3, A = 6, B = 4
 * Output: 8
 *
 * Note:
 *
 * 1 <= N <= 10^9
 * 2 <= A <= 40000
 * 2 <= B <= 40000
 */
@RunWith(LeetCodeRunner.class)
public class Q878_NthMagicalNumber {

    // 暴力求解会超时
//    @Answer
    public int nthMagicalNumber_bruteForce(int N, int A, int B) {
        long res = 0;
        while (N-- > 0) {
            do {
                res++;
            } while (res % A != 0 && res % B != 0);
        }
        return (int) (res % 10_0000_0007);
    }

    /**
     * 二分查找, 求A的倍数和B 的倍数都多少个, 并扣掉其中 A 和B 的公倍数.
     */
    @Answer
    public int nthMagicalNumber(int N, int A, int B) {
        long gcd = gcd(A, B), lcm = A * B / gcd;
        long start = 1, end = Long.MAX_VALUE / gcd;
        while (start < end) {
            long mid = start + (end - start) / 2;
            long count = mid * gcd / A + mid * gcd / B - mid * gcd / lcm;
            if (count < N) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return (int) (end * gcd % 10_0000_0007);
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 2, 3).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(4, 2, 3).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(5, 2, 4).expect(10);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(3, 6, 4).expect(8);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(1000000000, 40000, 40000).expect(999720007);

}
