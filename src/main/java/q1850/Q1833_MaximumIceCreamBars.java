package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1833. Maximum Ice Cream Bars
 * https://leetcode.com/problems/maximum-ice-cream-bars/
 *
 * It is a sweltering summer day, and a boy wants to buy some ice cream bars.
 *
 * At the store, there are n ice cream bars. You are given an array costs of length n, where costs[i] is the price of
 * the ith ice cream bar in coins. The boy initially has coins coins to spend, and he wants to buy as many ice cream
 * bars as possible.
 *
 * Return the maximum number of ice cream bars the boy can buy with coins coins.
 *
 * Note: The boy can buy the ice cream bars in any order.
 *
 * Example 1:
 *
 * Input: costs = [1,3,2,4,1], coins = 7
 * Output: 4
 * Explanation: The boy can buy ice cream bars at indices 0,1,2,4 for a total price of 1 + 3 + 2 + 1 = 7.
 *
 * Example 2:
 *
 * Input: costs = [10,6,8,7,7,8], coins = 5
 * Output: 0
 * Explanation: The boy cannot afford any of the ice cream bars.
 *
 * Example 3:
 *
 * Input: costs = [1,6,3,1,2,5], coins = 20
 * Output: 6
 * Explanation: The boy can buy all the ice cream bars for a total price of 1 + 6 + 3 + 1 + 2 + 5 = 18.
 *
 * Constraints:
 *
 * costs.length == n
 * 1 <= n <= 10^5
 * 1 <= costs[i] <= 10^5
 * 1 <= coins <= 10^8
 */
@RunWith(LeetCodeRunner.class)
public class Q1833_MaximumIceCreamBars {

    @Answer
    public int maxIceCream(int[] costs, int coins) {
        Arrays.sort(costs);
        for (int i = 0; i < costs.length; i++) {
            if (costs[i] > coins) {
                return i;
            }
            coins -= costs[i];
        }
        return costs.length;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 3, 2, 4, 1}, 7).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{10, 6, 8, 7, 7, 8}, 5).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 6, 3, 1, 2, 5}, 20).expect(6);

}
