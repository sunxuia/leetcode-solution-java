package q350;

import org.junit.runner.RunWith;
import q200.Q188_BestTimeToBuyAndSellStockIV;
import q750.Q714_BestTimeToBuyAndSellStockWithTransactionFee;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
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
 */
@RunWith(LeetCodeRunner.class)
public class Q309_BestTimeToBuyAndSellStockWithCooldown {

    // https://soulmachine.gitbooks.io/algorithm-essentials/java/dp/best-time-to-buy-and-sell-stock-with-cooldown.html
    @Answer
    public int maxProfit(int[] prices) {
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
