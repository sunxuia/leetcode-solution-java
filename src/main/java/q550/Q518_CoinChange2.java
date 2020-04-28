package q550;

import org.junit.runner.RunWith;
import q350.Q322_CoinChange;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/coin-change-2/
 *
 * You are given coins of different denominations and a total amount of money. Write a function to compute the number
 * of combinations that make up that amount. You may assume that you have infinite number of each kind of coin.
 *
 *
 *
 * Example 1:
 *
 * Input: amount = 5, coins = [1, 2, 5]
 * Output: 4
 * Explanation: there are four ways to make up the amount:
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 *
 * Example 2:
 *
 * Input: amount = 3, coins = [2]
 * Output: 0
 * Explanation: the amount of 3 cannot be made up just with coins of 2.
 *
 * Example 3:
 *
 * Input: amount = 10, coins = [10]
 * Output: 1
 *
 *
 *
 * Note:
 *
 * You can assume that
 *
 * 0 <= amount <= 5000
 * 1 <= coin <= 5000
 * the number of coins is less than 500
 * the answer is guaranteed to fit into signed 32-bit integer
 *
 * 上一题 {@link Q322_CoinChange}
 */
@RunWith(LeetCodeRunner.class)
public class Q518_CoinChange2 {

    /**
     * 这题相比上一题在于组合是没有顺序的, 1 + 2 + 2 和 2 + 1 + 2 应该被算作是一个.
     * 使用暴力dfs 会出现超时, 那么可以计算1 的数量和2 的数量和 5 的数量这样得出dp
     * dp[i][j] 表示在使用到第 i 个硬币(i 之前的硬币也用到了) 时可以得出的金额数.
     */
    @Answer
    public int change(int amount, int[] coins) {
        final int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= amount; j++) {
                // 这个金额是可以达到的
                if (dp[i][j] > 0) {
                    // 计算使用 coins[i] 可以累加出来的金额
                    for (int v = j; v <= amount; v += coins[i]) {
                        dp[i + 1][v] += dp[i][j];
                    }
                }
            }
        }
        return dp[n][amount];
    }

    /**
     * LeetCode 上最快的解法, 是针对上面dp 方式的改进.
     * dp[i] 表示在金额为i 的时候可能的组合数量.
     */
    @Answer
    public int change2(int amount, int[] coins) {
        int dp[] = new int[amount + 1];
        dp[0] = 1;
        // 每次计算一种货币
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(5, new int[]{1, 2, 5}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(3, new int[]{2}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(10, new int[]{10}).expect(1);

}
