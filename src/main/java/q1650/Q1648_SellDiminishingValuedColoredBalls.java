package q1650;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1648. Sell Diminishing-Valued Colored Balls
 * https://leetcode.com/problems/sell-diminishing-valued-colored-balls/
 *
 * You have an inventory of different colored balls, and there is a customer that wants orders balls of any color.
 *
 * The customer weirdly values the colored balls. Each colored ball's value is the number of balls of that color you
 * currently have in your inventory. For example, if you own 6 yellow balls, the customer would pay 6 for the first
 * yellow ball. After the transaction, there are only 5 yellow balls left, so the next yellow ball is then valued at 5
 * (i.e., the value of the balls decreases as you sell more to the customer).
 *
 * You are given an integer array, inventory, where inventory[i] represents the number of balls of the ith color that
 * you initially own. You are also given an integer orders, which represents the total number of balls that the customer
 * wants. You can sell the balls in any order.
 *
 * Return the maximum total value that you can attain after selling orders colored balls. As the answer may be too
 * large, return it modulo 109 + 7.
 *
 * Example 1:
 * <img src="./Q1648_PIC.png">
 * Input: inventory = [2,5], orders = 4
 * Output: 14
 * Explanation: Sell the 1st color 1 time (2) and the 2nd color 3 times (5 + 4 + 3).
 * The maximum total value is 2 + 5 + 4 + 3 = 14.
 *
 * Example 2:
 *
 * Input: inventory = [3,5], orders = 6
 * Output: 19
 * Explanation: Sell the 1st color 2 times (3 + 2) and the 2nd color 4 times (5 + 4 + 3 + 2).
 * The maximum total value is 3 + 2 + 5 + 4 + 3 + 2 = 19.
 *
 * Example 3:
 *
 * Input: inventory = [2,8,4,10,6], orders = 20
 * Output: 110
 *
 * Example 4:
 *
 * Input: inventory = [1000000000], orders = 1000000000
 * Output: 21
 * Explanation: Sell the 1st color 1000000000 times for a total value of 500000000500000000. 500000000500000000 modulo
 * 109 + 7 = 21.
 *
 * Constraints:
 *
 * 1 <= inventory.length <= 10^5
 * 1 <= inventory[i] <= 10^9
 * 1 <= orders <= min(sum(inventory[i]), 10^9)
 */
@RunWith(LeetCodeRunner.class)
public class Q1648_SellDiminishingValuedColoredBalls {

    /**
     * 思路从 inventory 的高处找出 orders 个球
     */
    @Answer
    public int maxProfit(int[] inventory, int orders) {
        final int mod = 10_0000_0007;
        final int n = inventory.length;
        Arrays.sort(inventory);
        // rest 表示 rest 以上的球都会被买走
        // rest0 表示有 rest0 个 rest 数量的球会被买走
        long sum = 0, rest = 0, rest0 = 0;
        for (int i = n - 1, len = 1; sum < orders; i--, len++) {
            int prev = i == 0 ? 0 : inventory[i - 1];
            int diff = inventory[i] - prev;
            if (sum + diff * len >= orders) {
                rest = inventory[i] - (orders - sum) / len;
                rest0 = (orders - sum) % len;
            }
            sum += diff * len;
        }
        long res = 0;
        for (int i = n - 1; i >= 0 && inventory[i] >= rest; i--) {
            res += (inventory[i] + rest + 1) * (inventory[i] - rest) / 2;
        }
        res += rest * rest0;
        return (int) (res % mod);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{2, 5}, 4).expect(14);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, 5}, 6).expect(19);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{2, 8, 4, 10, 6}, 20).expect(110);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new int[]{1000000000}, 1000000000).expect(21);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{497978859, 167261111, 483575207, 591815159}, 836556809)
            .expect(373219333);

}
