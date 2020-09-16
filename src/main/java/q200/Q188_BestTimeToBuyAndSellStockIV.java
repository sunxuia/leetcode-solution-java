package q200;

import java.util.Arrays;
import org.junit.runner.RunWith;
import q150.Q122_BestTimeToBuyAndSellStockII;
import q150.Q123_BestTimeToBuyAndSellStockIII;
import q350.Q309_BestTimeToBuyAndSellStockWithCooldown;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 188. Best Time to Buy and Sell Stock IV
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
 *
 * Say you have an array for which the i-th element is the price of a given stock on day i.
 *
 * Design an algorithm to find the maximum profit. You may complete at most k transactions.
 *
 * Note:
 * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 *
 * Example 1:
 *
 * Input: [2,4,1], k = 2
 * Output: 2
 * Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
 *
 * Example 2:
 *
 * Input: [3,2,6,5,0,3], k = 2
 * Output: 7
 * Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
 * Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 *
 * 相关题目:
 * 上一题 {@link Q123_BestTimeToBuyAndSellStockIII}
 * 下一题 {@link Q309_BestTimeToBuyAndSellStockWithCooldown}
 *
 * 题解:
 * 123 题的买卖次数固定为 2, 本题相比 123 题题目限制最多有 k 次买卖 (买卖只 1 次交易, 即一买一卖算 1 次交易)
 */
@RunWith(LeetCodeRunner.class)
public class Q188_BestTimeToBuyAndSellStockIV {


    /**
     * 参考极客时间视频, 比较容易理解的解法如下,
     * 在 {@link Q122_BestTimeToBuyAndSellStockII#maxProfit2(int[])} 的思路上加入k 次交易的限制.
     * 定义 dp[i][j][k] 表示第 i 天手上有 j 股股票, 且已经交易了 k 次时候的最大收益.
     * (买入则交易次数 +1, 卖出交易次数不变)
     * 则 dp[i][0][k] 的值为 dp[i-1][0][k] (无法卖出) 或 dp[i-1][1][k] + prices[i] (卖出)
     * dp[i][1][k] 的值为 dp[i-1][1][k] (无法买入) 或 dp[i-1][1][k-1] - prices[i] (买入)
     * 同时要注意去除边界条件, 最后的出来 dp[i][0][0 ~ K] 中的最大值就是结果.
     */
    @Answer
    public int maxProfit(int K, int[] prices) {
        final int n = prices.length;
        if (n < 2 || K == 0) {
            return 0;
        }
        if (K >= prices.length) {
            return solveMaxProfit(prices);
        }
        int[][][] dp = new int[n][2][K + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                Arrays.fill(dp[i][j], Integer.MIN_VALUE / 2);
            }
        }
        // (第0 次交易的边界条件)
        dp[0][0][0] = 0;
        dp[0][1][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            int[][] curr = dp[i], prev = dp[i - 1];
            // (交易0 次的边界条件)
            curr[0][0] = 0;
            for (int k = 1; k <= K; k++) {
                // 本次不卖出或卖出
                curr[0][k] = Math.max(prev[0][k], prev[1][k] + prices[i]);
                // 本次不买入或买入
                curr[1][k] = Math.max(prev[1][k], prev[0][k - 1] - prices[i]);
            }
        }
        return Arrays.stream(dp[n - 1][0]).max().getAsInt();
    }

    /**
     * 次数限制超过最大可能买卖次数的情况, 这用来避免k 极端大的情况.
     * 解答同 {@link Q122_BestTimeToBuyAndSellStockII#maxProfit(int[])}
     */
    private int solveMaxProfit(int[] prices) {
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            res += Math.max(0, prices[i] - prices[i - 1]);
        }
        return res;
    }

    /**
     * 参考网络解法解答如下:
     */
    @Answer
    public int maxProfit2(int k, int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        // 避免overTime 测试用例的内存溢出问题
        if (k >= prices.length) {
            return solveMaxProfit(prices);
        }
        int[] local = new int[k + 1];
        int[] global = new int[k + 1];
        for (int i = 0; i < prices.length - 1; i++) {
            int diff = prices[i + 1] - prices[i];
            for (int j = k; j >= 1; j--) {
                local[j] = Math.max(global[j - 1] + (diff > 0 ? diff : 0), local[j] + diff);
                global[j] = Math.max(local[j], global[j]);
            }
        }
        return global[k];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, new int[]{2, 4, 1}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, new int[]{3, 2, 6, 5, 0, 3}).expect(7);

    @TestData
    public DataExpectation border0 = DataExpectation.createWith(0, new int[]{1, 3}).expect(0);

    @TestData
    public DataExpectation border1 = DataExpectation.createWith(1, new int[]{}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(2, new int[]{1, 2, 4, 2, 5, 7, 2, 4, 9, 0}).expect(13);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(1, new int[]{1, 2}).expect(1);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(1, new int[]{2, 1}).expect(0);

    // 1万个元素的数组
    @TestData
    public DataExpectation overMemory = DataExpectation
            .createWith(1000000000, TestDataFileHelper.readIntegerArray("Q188_LongTestData"))
            .expect(1648961);

}
