package q150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 *
 * Say you have an array for which the ith element is the price of a given stock on day i.
 *
 * If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock),
 * design an algorithm to find the maximum profit.
 *
 * Note that you cannot sell a stock before you buy one.
 *
 * Example 1:
 *
 * Input: [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * Not 7-1 = 6, as selling price needs to be larger than buying price.
 *
 * Example 2:
 *
 * Input: [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transaction is done, i.e. max profit = 0.
 */
@RunWith(LeetCodeRunner.class)
public class Q121_BestTimeToBuyAndSellStock {

    // 题目限制只能1 次买卖
    @Answer
    public int maxProfit(int[] prices) {
        int min = 0, res = 0;
        for (int i = 1; i < prices.length; i++) {
            res = Math.max(res, prices[i] - prices[min]);
            if (prices[min] > prices[i]) {
                min = i;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{7, 1, 5, 3, 6, 4}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{7, 6, 4, 3, 1}).expect(0);

}
