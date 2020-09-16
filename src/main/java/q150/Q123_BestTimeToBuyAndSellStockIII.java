package q150;

import org.junit.runner.RunWith;
import q200.Q188_BestTimeToBuyAndSellStockIV;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 123. Best Time to Buy and Sell Stock III
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit. You may complete at most two transactions.
 *
 * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy
 * again).
 *
 * Example 1:
 *
 * Input: [3,3,5,0,0,3,1,4]
 * Output: 6
 * Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 * Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
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
 * 相关题目:
 * 下一题 {@link Q188_BestTimeToBuyAndSellStockIV}
 *
 * 题解:
 * 相比上题可以无限制买卖这题只能买卖 2 次 (买卖只 1 次交易, 即一买一卖算 1 次交易)
 */
@RunWith(LeetCodeRunner.class)
public class Q123_BestTimeToBuyAndSellStockIII {

    @Answer
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        final int n = 2;
        // dp[i][j] 表示第i 次买入卖出(0 表示没有买入卖出操作) 在第j 天的最大获利状况.
        int[][] dp = new int[n + 1][prices.length];
        int res = 0;
        for (int i = 1; i <= n; i++) {
            // 表示当前买入后的剩余的利润 (之后可以卖出1次)
            int remain = -prices[0];
            for (int j = 1; j < prices.length; j++) {
                // 如果今日卖出的获利高于昨日, 则更新今日的利润.
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + remain);
                res = Math.max(res, dp[i][j]);
                // 如果上一轮买入/卖出后在此时买入的剩余利润高于之前买入, 则更新为此时买入.
                remain = Math.max(remain, dp[i - 1][j] - prices[j]);
            }
        }
        return res;
    }

    /**
     * 这个算法是网上的一个解法, 相比上面的解法能节省一点空间. (空间占用与 prices 无关)
     */
    @Answer
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        final int n = 2;
        int[] local = new int[n + 1];
        int[] global = new int[n + 1];
        for (int i = 0; i < prices.length - 1; i++) {
            int diff = prices[i + 1] - prices[i];
            for (int j = n; j >= 1; j--) {
                local[j] = Math.max(global[j - 1] + (diff > 0 ? diff : 0), local[j] + diff);
                global[j] = Math.max(local[j], global[j]);
            }
        }
        return global[n];
    }

    /**
     * 这题是 {@link Q188_BestTimeToBuyAndSellStockIV} 的 k = 2 的特例.
     */
    @Answer
    public int maxProfit3(int[] prices) {
        return new Q188_BestTimeToBuyAndSellStockIV().maxProfit(2, prices);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 3, 5, 0, 0, 3, 1, 4}).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3, 4, 5}).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{7, 6, 4, 3, 1}).expect(0);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[]{}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 2, 4, 2, 5, 7, 2, 4, 9, 0}).expect(13);

}
