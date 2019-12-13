package q350;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/coin-change
 *
 * You are given coins of different denominations and a total amount of money amount. Write a function to compute the
 * fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any
 * combination of the coins, return -1.
 *
 * Example 1:
 *
 * Input: coins = [1, 2, 5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 *
 * Example 2:
 *
 * Input: coins = [2], amount = 3
 * Output: -1
 */
@RunWith(LeetCodeRunner.class)
public class Q322_CoinChange {

    // 暴力解法会超时, 所以这里用dp
    @Answer
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        int[] dp = new int[amount + 1];
        // 因为钱的最小单位是1, 所以最大次数不会超过 amount, 这里可以用 amount+1 (Integer.MAX_VALUE 则需要考虑溢出的情况)
        Arrays.fill(dp, 1, amount + 1, amount + 1);
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (i >= coins[j]) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.createWith(new int[]{1, 2, 5}, 11).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{2}, 3).expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{186, 419, 83, 408}, 6249).expect(20);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{470, 18, 66, 301, 403, 112, 360}, 8235).expect(20);

}
