package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/powx-n/
 *
 * Implement pow(x, n), which calculates x raised to the power n (xn).
 *
 * Example 1:
 *
 * Input: 2.00000, 10
 * Output: 1024.00000
 *
 * Example 2:
 *
 * Input: 2.10000, 3
 * Output: 9.26100
 *
 * Example 3:
 *
 * Input: 2.00000, -2
 * Output: 0.25000
 * Explanation: 2-2 = 1/22 = 1/4 = 0.25
 *
 * Note:
 *
 * -100.0 < x < 100.0
 * n is a 32-bit signed integer, within the range [−231, 231 − 1]
 *
 * 题解:
 * 自行实现 Math.pow(x, n)
 */
@RunWith(LeetCodeRunner.class)
public class Q050_Pow {

    /**
     * 位移的方式. 这种方式不如下面的递归方式
     */
    @Answer
    public double myPow(double x, int n) {
        boolean isNegative = n < 0;
        double res = 1;
        // 转换为整数前对特殊值进行处理
        if (n == Integer.MIN_VALUE) {
            res *= x;
            n++;
        }
        n = isNegative ? -n : n;
        while (n > 0) {
            if ((n & 0x01) == 1) {
                res *= x;
            }
            x *= x;
            n = n >> 1;
        }
        return isNegative ? 1 / res : res;
    }

    /**
     * 递归的方式
     */
    @Answer
    public double myPowRecursion(double x, int n) {
        if (n < 0) {
            return 1 / recursion(x, -n);
        } else {
            return recursion(x, n);
        }
    }

    public double recursion(double x, int n) {
        if (n == 0) {
            return 1;
        }
        double pow = recursion(x, n / 2);
        return n % 2 == 0 ? pow * pow : x * pow * pow;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(2.0)
            .addArgument(10)
            .expectDouble(1024.0, 4)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(2.1)
            .addArgument(3)
            .expectDouble(9.261, 5)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(2.0)
            .addArgument(-2)
            .expectDouble(0.25, 5)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(0.00001)
            .addArgument(2147483647)
            .expectDouble(0.25, 5)
            .build();

}
