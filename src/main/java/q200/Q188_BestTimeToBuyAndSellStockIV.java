package q200;

import org.junit.runner.RunWith;
import q150.Q123_BestTimeToBuyAndSellStockIII;
import q350.Q309_BestTimeToBuyAndSellStockWithCooldown;
import util.asserthelper.TestDataFileHelper;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
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
 *  上一题 {@link Q123_BestTimeToBuyAndSellStockIII}, 这题的买入卖出次数作为参数输入.
 *  下一题 {@link Q309_BestTimeToBuyAndSellStockWithCooldown}
 */
@RunWith(LeetCodeRunner.class)
public class Q188_BestTimeToBuyAndSellStockIV {

    @Answer
    public int maxProfit(int k, int[] prices) {
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

    int solveMaxProfit(int[] prices) {
        int res = 0;
        for (int i = 1; i < prices.length; ++i) {
            if (prices[i] - prices[i - 1] > 0) {
                res += prices[i] - prices[i - 1];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, new int[]{2, 4, 1}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, new int[]{3, 2, 6, 5, 0, 3}).expect(7);

    @TestData
    public DataExpectation border = DataExpectation.createWith(1, new int[]{}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(2, new int[]{1, 2, 4, 2, 5, 7, 2, 4, 9, 0}).expect(13);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(1, new int[]{1, 2}).expect(1);

    // 1万个元素的数组
    @TestData
    public DataExpectation overMemory = DataExpectation
            .createWith(1000000000, TestDataFileHelper.readIntegerArray("Q188_LongTestData"))
            .expect(1648961);

}
