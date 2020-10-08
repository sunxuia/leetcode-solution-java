package q1200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1155. Number of Dice Rolls With Target Sum
 * https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
 *
 * You have d dice, and each die has f faces numbered 1, 2, ..., f.
 *
 * Return the number of possible ways (out of fd total ways) modulo 10^9 + 7 to roll the dice so the sum of the face up
 * numbers equals target.
 *
 * Example 1:
 *
 * Input: d = 1, f = 6, target = 3
 * Output: 1
 * Explanation:
 * You throw one die with 6 faces.  There is only one way to get a sum of 3.
 *
 * Example 2:
 *
 * Input: d = 2, f = 6, target = 7
 * Output: 6
 * Explanation:
 * You throw two dice, each with 6 faces.  There are 6 ways to get a sum of 7:
 * 1+6, 2+5, 3+4, 4+3, 5+2, 6+1.
 *
 * Example 3:
 *
 * Input: d = 2, f = 5, target = 10
 * Output: 1
 * Explanation:
 * You throw two dice, each with 5 faces.  There is only one way to get a sum of 10: 5+5.
 *
 * Example 4:
 *
 * Input: d = 1, f = 2, target = 3
 * Output: 0
 * Explanation:
 * You throw one die with 2 faces.  There is no way to get a sum of 3.
 *
 * Example 5:
 *
 * Input: d = 30, f = 30, target = 500
 * Output: 222616187
 * Explanation:
 * The answer must be returned modulo 10^9 + 7.
 *
 * Constraints:
 *
 * 1 <= d, f <= 30
 * 1 <= target <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1155_NumberOfDiceRollsWithTargetSum {

    @Answer
    public int numRollsToTarget(int d, int f, int target) {
        // dp[i][j] 表示i 个骰子(dice) 组成数字 j 的组合数.
        int[][] dp = new int[d + 1][target + 1];
        for (int i = 1; i <= f && i <= target; i++) {
            dp[1][i] = 1;
        }
        for (int i = 1; i < d; i++) {
            for (int j = 1; j <= target; j++) {
                for (int k = 1; k <= f && j + k <= target; k++) {
                    dp[i + 1][j + k] = (dp[i + 1][j + k] + dp[i][j]) % 10_0000_0007;
                }
            }
        }
        return dp[d][target];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 6, 3).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 6, 7).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(2, 5, 10).expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(1, 2, 3).expect(0);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith(30, 30, 500).expect(222616187);

}
