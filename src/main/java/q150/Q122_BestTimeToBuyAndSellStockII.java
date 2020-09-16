package q150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 122. Best Time to Buy and Sell Stock II
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit. You may complete as many transactions as you like (i.e., buy one
 * and sell one share of the stock multiple times).
 *
 * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy
 * again).
 *
 * Example 1:
 *
 * Input: [7,1,5,3,6,4]
 * Output: 7
 * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
 * Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
 *
 * Example 2:
 *
 * Input: [1,2,3,4,5]
 * Output: 4
 * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 * Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
 * engaging multiple transactions at the same time. You must sell before buying again.
 *
 * Example 3:
 *
 * Input: [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transaction is done, i.e. max profit = 0.
 *
 * 题解: 题目不限制买卖次数, (但是买入前必须卖出, 手上的股票数要 <= 1)
 */
@RunWith(LeetCodeRunner.class)
public class Q122_BestTimeToBuyAndSellStockII {

    /**
     * 可以逢低买入, 逢高卖出, 取低 -> 高 之间的差值.
     */
    @Answer
    public int maxProfit(int[] prices) {
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            // 如果当前价格高于前一天, 则可以前一天买入, 这一天卖出 (手上的股票是 0)
            res += Math.max(0, prices[i] - prices[i - 1]);
        }
        return res;
    }


    /**
     * dp 解法 :
     * 用 dp[i][j] 表示第i 天手上有j 股股票时的最大利润, 则可得如下状态转移方程 :
     * 第i 天不持有股票的最大利润为: dp[i-1][1] + prices[i] (第 i 天卖出) 或 dp[i-1][0] (第 i 天不买入)
     * 第i 天持有股票的最大利润为: dp[i-1][1] (第 i 天不卖出) 或 dp[i-1] - prices[i] (第 i 天买入)
     * 最后一天卖出所有的股票后得到的结果就是最大结果.
     */
    @Answer
    public int maxProfit2(int[] prices) {
        final int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    /**
     * 上面 dp 解法的简化, 使用变量代替数组.
     */
    @Answer
    public int maxProfit3(int[] prices) {
        int p0 = 0, p1 = -prices[0];
        for (int price : prices) {
            p0 = Math.max(p0, p1 + price);
            p1 = Math.max(p1, p0 - price);
        }
        return p0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{7, 1, 5, 3, 6, 4}).expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3, 4, 5}).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{7, 6, 4, 3, 1}).expect(0);

}
