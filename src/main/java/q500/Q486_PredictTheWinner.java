package q500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/predict-the-winner/
 *
 * Given an array of scores that are non-negative integers. Player 1 picks one of the numbers from either end of the
 * array followed by the player 2 and then player 1 and so on. Each time a player picks a number, that number will
 * not be available for the next player. This continues until all the scores have been chosen. The player with the
 * maximum score wins.
 *
 * Given an array of scores, predict whether player 1 is the winner. You can assume each player plays to maximize his
 * score.
 *
 * Example 1:
 *
 * Input: [1, 5, 2]
 * Output: False
 * Explanation: Initially, player 1 can choose between 1 and 2.
 * If he chooses 2 (or 1), then player 2 can choose from 1 (or 2) and 5. If player 2 chooses 5, then player 1 will be
 * left with 1 (or 2).
 * So, final score of player 1 is 1 + 2 = 3, and player 2 is 5.
 * Hence, player 1 will never be the winner and you need to return False.
 *
 * Example 2:
 *
 * Input: [1, 5, 233, 7]
 * Output: True
 * Explanation: Player 1 first chooses 1. Then player 2 have to choose between 5 and 7. No matter which number player
 * 2 choose, player 1 can choose 233.
 * Finally, player 1 has more score (234) than player 2 (12), so you need to return True representing player1 can win.
 *
 * Note:
 *
 * 1 <= length of the array <= 20.
 * Any scores in the given array are non-negative integers and will not exceed 10,000,000.
 * If the scores of both players are equal, then player 1 is still the winner.
 */
@RunWith(LeetCodeRunner.class)
public class Q486_PredictTheWinner {

    @Answer
    public boolean PredictTheWinner(int[] nums) {
        final int length = nums.length;
        if (length <= 2) {
            return true;
        }
        int[] sums = new int[length + 1];
        // cache[i][j] 表示 [i, j] 区间内当前玩家可以取到的最大结果
        int[][] cache = new int[length][length];
        for (int i = 0; i < length; i++) {
            sums[i + 1] = sums[i] + nums[i];
            Arrays.fill(cache[i], -1);
            cache[i][i] = nums[i];
        }
        int max = maxScore(nums, 0, length - 1, sums, cache);
        return max >= Math.min(cache[1][length - 1], cache[0][length - 2]);
    }

    private int maxScore(int[] nums, int head, int tail, int[] sums, int[][] cache) {
        if (cache[head][tail] == -1) {
            cache[head][tail] = sums[tail + 1] - sums[head] - Math.min(
                    maxScore(nums, head + 1, tail, sums, cache),
                    maxScore(nums, head, tail - 1, sums, cache));
        }
        return cache[head][tail];
    }

    // LeetCode 上更为简洁的做法, 是对上面做法的改进
    @Answer
    public boolean PredictTheWinner2(int[] nums) {
        final int length = nums.length;
        int[][] dp = new int[length + 1][length];
        for (int i = length - 1; i >= 0; i--) {
            for (int j = i + 1; j < length; j++) {
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }
        return dp[0][nums.length - 1] >= 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 5, 2}).expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 5, 233, 7}).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0, 0, 7, 6, 5, 6, 1}).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}).expect(true);

}
