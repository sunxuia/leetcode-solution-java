package q750;

import org.junit.runner.RunWith;
import q150.Q122_BestTimeToBuyAndSellStockII;
import q350.Q309_BestTimeToBuyAndSellStockWithCooldown;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 714. Best Time to Buy and Sell Stock with Transaction Fee
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
 *
 * Your are given an array of integers prices, for which the i-th element is the price of a given stock on day i; and
 * a non-negative integer fee representing a transaction fee.
 *
 * You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction.
 * You may not buy more than 1 share of a stock at a time (ie. you must sell the stock share before you buy again.)
 *
 * Return the maximum profit you can make.
 *
 * Example 1:
 *
 * Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
 * Output: 8
 * Explanation: The maximum profit can be achieved by:
 * Buying at prices[0] = 1
 * Selling at prices[3] = 8
 * Buying at prices[4] = 4
 * Selling at prices[5] = 9
 * The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
 *
 * Note:
 * 0 < prices.length <= 50000.
 * 0 < prices[i] < 50000.
 * 0 <= fee < 50000.
 *
 * 相关题目:
 * 上一题 {@link Q309_BestTimeToBuyAndSellStockWithCooldown}
 *
 * 题解:
 * 这题相比 {@link Q122_BestTimeToBuyAndSellStockII} 多了1 个限制条件: 每次卖出后都手续费 fee.
 */
@RunWith(LeetCodeRunner.class)
public class Q714_BestTimeToBuyAndSellStockWithTransactionFee {

    /**
     * 同第122 题, 定义 dp[i][j] 表示第 i 天持有 j 个股票的总收益.
     * 则 dp[i][0] = dp[i - 1][0] 或 dp[0~i-1][1] + prices[i] - fee 的最大值 (买入后在今天卖出)
     * dp[i][1] = dp[i-1][1] 或 dp[i-1][0] - prices[i], 这个和之前一样
     */
    @Answer
    public int maxProfit(int[] prices, int fee) {
        final int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][1] = -prices[0];
        // max 表示 dp[0~i-1][1] 的最大值
        int max = dp[0][1];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], max + prices[i] - fee);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
            max = Math.max(max, dp[i][1]);
        }
        return dp[n - 1][0];
    }

    /**
     * 网上的其他解答, 与上面方式类似, 优化了上面dp 的空间调用.
     * https://www.cnblogs.com/grandyang/p/7776979.html
     */
    @Answer
    public int maxProfit2(int[] prices, int fee) {
        int res = 0, max = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            max = Math.max(max, res - prices[i]);
            res = Math.max(res, max + prices[i] - fee);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{1, 3, 2, 8, 4, 9}, 2).expect(8);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{1, 3, 7, 5, 10, 3}, 3).expect(6);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 6806)
            .expect(309432285);

}
