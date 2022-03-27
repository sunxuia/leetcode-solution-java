package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1808. Maximize Number of Nice Divisors
 * https://leetcode.com/problems/maximize-number-of-nice-divisors/
 *
 * You are given a positive integer primeFactors. You are asked to construct a positive integer n that satisfies the
 * following conditions:
 *
 * The number of prime factors of n (not necessarily distinct) is at most primeFactors.
 * The number of nice divisors of n is maximized. Note that a divisor of n is nice if it is divisible by every prime
 * factor of n. For example, if n = 12, then its prime factors are [2,2,3], then 6 and 12 are nice divisors, while 3 and
 * 4 are not.
 *
 * Return the number of nice divisors of n. Since that number can be too large, return it modulo 10^9 + 7.
 *
 * Note that a prime number is a natural number greater than 1 that is not a product of two smaller natural numbers. The
 * prime factors of a number n is a list of prime numbers such that their product equals n.
 *
 * Example 1:
 *
 * Input: primeFactors = 5
 * Output: 6
 * Explanation: 200 is a valid value of n.
 * It has 5 prime factors: [2,2,2,5,5], and it has 6 nice divisors: [10,20,40,50,100,200].
 * There is not other value of n that has at most 5 prime factors and more nice divisors.
 *
 * Example 2:
 *
 * Input: primeFactors = 8
 * Output: 18
 *
 * Constraints:
 *
 * 1 <= primeFactors <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1808_MaximizeNumberOfNiceDivisors {

    /**
     * 看不懂什么意思, 这题涉及到快速幂.
     * 参考文档 https://blog.csdn.net/zml66666/article/details/115313700
     */
    @Answer
    public int maxNiceDivisors(int primeFactors) {
        if (primeFactors == 1) {
            return 1;
        }
        // 对所给的 primeFactors 按 3 进行拆分
        // 只有当 primeFactors % 3 == 1 时, 将其中一组3 个质因数和剩余1 个一起考虑, 选取4 个或2 组2 个质因数
        if (primeFactors % 3 == 1) {
            return (int) (pow(3, primeFactors / 3 - 1) * 4 % MOD);
        } else if (primeFactors % 3 == 2) {
            return (int) (pow(3, primeFactors / 3) * 2 % MOD);
        } else {
            return (int) pow(3, primeFactors / 3);
        }
    }

    private static final int MOD = 10_0000_0007;

    /**
     * 快速幂计算
     */
    public long pow(long base, long exp) {
        long res = 1L;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                res = res * base % MOD;
            }
            exp >>>= 1;
            base = base * base % MOD;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(5).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(8).expect(18);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(84).expect(792294829);

}
