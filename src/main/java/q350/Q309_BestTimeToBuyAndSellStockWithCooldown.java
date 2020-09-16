package q350;

import org.junit.runner.RunWith;
import q150.Q122_BestTimeToBuyAndSellStockII;
import q200.Q188_BestTimeToBuyAndSellStockIV;
import q750.Q714_BestTimeToBuyAndSellStockWithTransactionFee;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 309. Best Time to Buy and Sell Stock with Cooldown
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and
 * sell one share of the stock multiple times) with the following restrictions:
 *
 * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 * After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
 *
 * Example:
 *
 * Input: [1,2,3,0,2]
 * Output: 3
 * Explanation: transactions = [buy, sell, cooldown, buy, sell]
 *
 * 相关题目:
 * 上一题 {@link Q188_BestTimeToBuyAndSellStockIV}
 * 下一题 {@link Q714_BestTimeToBuyAndSellStockWithTransactionFee}
 *
 * 题解:
 * 这题相比 {@link Q122_BestTimeToBuyAndSellStockII} 多了1 个限制条件: 卖出后的1 天不能买入.
 */
@RunWith(LeetCodeRunner.class)
public class Q309_BestTimeToBuyAndSellStockWithCooldown {

    /**
     * 类似 122题, 用 dp[i][j] 表示第 i 天持有j 股股票.
     * 则 dp[i][0] 的值为 dp[i-1][0] (不卖出) 或 dp[i-1][1] + prices[i] (今天卖出), 这个和之前一样
     * dp[i][1] 的值为 dp[i-1][1] (不买入) 或 dp[i-2][0] - prices[i] (2 天前卖出今天再买入)
     */
    @Answer
    public int maxProfit(int[] prices) {
        final int n = prices.length;
        if (n < 2) {
            return 0;
        }
        int[][] dp = new int[n][2];
        // 起始的边界条件.
        dp[0][1] = -prices[0];
        dp[1][0] = Math.max(0, prices[1] - prices[0]);
        dp[1][1] = Math.max(-prices[0], -prices[1]);
        for (int i = 2; i < n; i++) {
            // 不卖出或卖出
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 不买入或 2 天前卖出后再买入
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    /**
     * 网上的其他解答, 与上面的解法类似.
     * https://soulmachine.gitbooks.io/algorithm-essentials/java/dp/best-time-to-buy-and-sell-stock-with-cooldown.html
     */
    @Answer
    public int maxProfit2(int[] prices) {
        final int n = prices.length;
        if (n < 2) {
            return 0;
        }
        // 表示当天卖出获得的最大利润
        int[] sell = new int[n];
        // 表示当天买入获得的最大利润
        int[] buy = new int[n];
        sell[0] = 0;
        buy[0] = -prices[0];

        for (int i = 1; i < n; ++i) {
            // 今天卖出, 最大利润有两种可能: 一是今天没动作跟昨天未持股状态一样, 二是今天卖了股票
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
            // 今天买入, 最大利润有两种可能: 一是今天没动作跟昨天持股状态一样,
            // 二是前天卖了股票, 今天买了股票, 因为 cooldown 只能隔天交易, 所以今天买股票要追溯到前天的状态
            buy[i] = Math.max(buy[i - 1], (i > 1 ? sell[i - 2] : 0) - prices[i]);
        }
        // 最后一天卖出即最大的利润
        return sell[n - 1];
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 2, 3, 0, 2}).expect(3);

}
