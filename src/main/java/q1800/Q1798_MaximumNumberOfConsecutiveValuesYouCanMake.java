package q1800;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1798. Maximum Number of Consecutive Values You Can Make
 * https://leetcode.com/problems/maximum-number-of-consecutive-values-you-can-make/
 *
 * You are given an integer array coins of length n which represents the n coins that you own. The value of the ith coin
 * is coins[i]. You can make some value x if you can choose some of your n coins such that their values sum up to x.
 *
 * Return the maximum number of consecutive integer values that you can make with your coins starting from and including
 * 0.
 *
 * Note that you may have multiple coins of the same value.
 *
 * Example 1:
 *
 * Input: coins = [1,3]
 * Output: 2
 * Explanation: You can make the following values:
 * - 0: take []
 * - 1: take [1]
 * You can make 2 consecutive integer values starting from 0.
 *
 * Example 2:
 *
 * Input: coins = [1,1,1,4]
 * Output: 8
 * Explanation: You can make the following values:
 * - 0: take []
 * - 1: take [1]
 * - 2: take [1,1]
 * - 3: take [1,1,1]
 * - 4: take [4]
 * - 5: take [4,1]
 * - 6: take [4,1,1]
 * - 7: take [4,1,1,1]
 * You can make 8 consecutive integer values starting from 0.
 *
 * Example 3:
 *
 * Input: nums = [1,4,10,3,1]
 * Output: 20
 *
 * Constraints:
 *
 * coins.length == n
 * 1 <= n <= 4 * 10^4
 * 1 <= coins[i] <= 4 * 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1798_MaximumNumberOfConsecutiveValuesYouCanMake {

    /**
     * 按照hint 1: If you can make the first x values and you have a value v, then you can make all the values ≤ v + x
     */
    @Answer
    public int getMaximumConsecutive(int[] coins) {
        Arrays.sort(coins);
        int x = 0;
        for (int coin : coins) {
            if (coin > x + 1) {
                break;
            }
            x += coin;
        }
        return x + 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 3}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 1, 4}).expect(8);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 4, 10, 3, 1}).expect(20);

}
