package q750;

import org.junit.runner.RunWith;
import q350.Q309_BestTimeToBuyAndSellStockWithCooldown;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
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
 */
@RunWith(LeetCodeRunner.class)
public class Q714_BestTimeToBuyAndSellStockWithTransactionFee {

    // https://www.cnblogs.com/grandyang/p/7776979.html
    @Answer
    public int maxProfit(int[] prices, int fee) {
        int buy = -prices[0];
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            buy = Math.max(buy, sum - prices[i]);
            sum = Math.max(sum, buy + prices[i] - fee);
        }
        return sum;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{1, 3, 2, 8, 4, 9}, 2).expect(8);

}
