package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/integer-break/
 *
 * Given a positive integer n, break it into the sum of at least two positive integers and maximize the product of
 * those integers. Return the maximum product you can get.
 *
 * Example 1:
 *
 * Input: 2
 * Output: 1
 * Explanation: 2 = 1 + 1, 1 × 1 = 1.
 *
 * Example 2:
 *
 * Input: 10
 * Output: 36
 * Explanation: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36.
 *
 * Note: You may assume that n is not less than 2 and not larger than 58.
 */
@RunWith(LeetCodeRunner.class)
public class Q343_IntegerBreak {

    // dp 的算法, dp 表示当前数字得到的乘积的最大值. 时间复杂度 O(N^2)
    // https://www.cnblogs.com/grandyang/p/5411919.html
    @Answer
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                // 确定第一个数为j, 计算第二个数为 i-j 或更多数字情况下的最大值
                int divideMax = Math.max(j * (i - j), j * dp[i - j]);
                dp[i] = Math.max(dp[i], divideMax);
            }
        }
        return dp[n];
    }

    // 通过题目提示得到的算法, 根据上面得出最大值的数字组合的规律计算结果, 时间复杂度 O(N)
    // https://www.cnblogs.com/grandyang/p/5411919.html
    @Answer
    public int integerBreak2(int n) {
        int[] dp = new int[Math.max(7, n + 1)];
        dp[2] = 1; // 1 * 1
        dp[3] = 2; // 1 * 2
        dp[4] = 4; // 2 * 2

        // 从5 开始的数字, 最大乘积都可以拆分为 3 * 其它数字
        dp[5] = 6; // 2 * 3
        dp[6] = 9; // 3 * 3

        // 从7 开始的数字都可以拆分为3 个数字的乘积, 且最大乘积都是 3 * (剩余数的最大数)
        // dp[7] = 3 * 2 * 2 = 3 * dp[4]
        for (int i = 7; i <= n; ++i) {
            dp[i] = 3 * dp[i - 3];
        }
        return dp[n];
    }

    // leetcode 上最快的解法
    // 根据上面2 中 >=5 的最大乘积都是 3的次方数 * 剩余数, 这个规律改进而来.
    // 时间复杂度 O(N)
    @Answer
    public int integerBreak3(int n) {
        if (n < 5) {
            return n == 4 ? 4 : n - 1;
        }
        n -= 5;
        return (int) Math.pow(3, n / 3 + 1) * (n % 3 + 2);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(2).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(10).expect(36);

    @TestData
    public DataExpectation value3 = DataExpectation.create(3).expect(2);

}
