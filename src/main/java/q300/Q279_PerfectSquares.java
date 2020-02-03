package q300;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/perfect-squares/
 *
 * Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which
 * sum to n.
 *
 * Example 1:
 *
 * Input: n = 12
 * Output: 3
 * Explanation: 12 = 4 + 4 + 4.
 *
 * Example 2:
 *
 * Input: n = 13
 * Output: 2
 * Explanation: 13 = 4 + 9.
 */
@RunWith(LeetCodeRunner.class)
public class Q279_PerfectSquares {

    /**
     * 这题利用到了四平方和定理(https://zh.wikipedia.org/wiki/%E5%9B%9B%E5%B9%B3%E6%96%B9%E5%92%8C%E5%AE%9A%E7%90%86)
     * 定理证明了每个正整数均可表示为4个(或小于4个)整数的平方和, 这道题目目前就是要找出这些整数平方和的数字.
     * 相关的解题思路可以参阅 https://www.cnblogs.com/grandyang/p/4800552.html
     */
    @Answer
    public int numSquares(int n) {
        while (n % 4 == 0) {
            n /= 4;
        }
        if (n % 8 == 7) {
            return 4;
        }
        for (int a = 0; a * a <= n; ++a) {
            int b = (int) Math.sqrt(n - a * a);
            if (a * a + b * b == n) {
                return (a == 0 ? 0 : 1) + (b == 0 ? 0 : 1);
            }
        }
        return 3;
    }

    /**
     * dp 的解法, 这个相对上面的要好理解一点.
     */
    @Answer
    public int numSquares_dp(int n) {
        // dp[i] 表示正整数i 能由多个完全平方数组成
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        // i 从0 开始循环, 那么  i + j * j 这个数字的次数就可以更新为 i 的次数 + j*j 的这一个次数 (dp[i] + 1).
        for (int i = 0; i <= n; i++) {
            for (int j = 1; i + j * j <= n; j++) {
                dp[i + j * j] = Math.min(dp[i + j * j], dp[i] + 1);
            }
        }
        return dp[n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(12).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(13).expect(2);

}
