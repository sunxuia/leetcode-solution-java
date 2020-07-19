package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 877. Stone Game
 * https://leetcode.com/problems/stone-game/
 *
 * Alex and Lee play a game with piles of stones.  There are an even number of piles arranged in a row, and each pile
 * has a positive integer number of stones piles[i].
 *
 * The objective of the game is to end with the most stones.  The total number of stones is odd, so there are no ties.
 *
 * Alex and Lee take turns, with Alex starting first.  Each turn, a player takes the entire pile of stones from either
 * the beginning or the end of the row.  This continues until there are no more piles left, at which point the person
 * with the most stones wins.
 *
 * Assuming Alex and Lee play optimally, return True if and only if Alex wins the game.
 *
 * Example 1:
 *
 * Input: [5,3,4,5]
 * Output: true
 * Explanation:
 * Alex starts first, and can only take the first 5 or the last 5.
 * Say he takes the first 5, so that the row becomes [3, 4, 5].
 * If Lee takes 3, then the board is [4, 5], and Alex takes 5 to win with 10 points.
 * If Lee takes the last 5, then the board is [3, 4], and Alex takes 4 to win with 9 points.
 * This demonstrated that taking the first 5 was a winning move for Alex, so we return true.
 *
 * Note:
 *
 * 2 <= piles.length <= 500
 * piles.length is even.
 * 1 <= piles[i] <= 500
 * sum(piles) is odd.
 *
 * 题解: (这题题目设计/测试用例有缺陷, 结果都是true)
 */
@RunWith(LeetCodeRunner.class)
public class Q877_StoneGame {

    // 简单的dfs 回溯法会超时
//    @Answer
    public boolean stoneGame_dfs(int[] piles) {
        return canWin(piles, 0, piles.length - 1, 0, 0);
    }

    private boolean canWin(int[] piles, int start, int end, int sum, int otherSum) {
        if (start > end) {
            return sum > otherSum;
        }
        return !canWin(piles, start + 1, end, otherSum, sum + piles[start])
                || !canWin(piles, start, end - 1, otherSum, sum + piles[end]);
    }

    // dp 的解法
    @Answer
    public boolean stoneGame(int[] piles) {
        final int n = piles.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + piles[i];
        }
        // dp[i][j] 表示区间范围在 [i, j] 时候先手的最大得分
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = Math.max(piles[i] + sums[j + 1] - sums[i + 1] - dp[i + 1][j],
                        piles[j] + sums[j] - sums[i] - dp[i][j - 1]);
            }
        }
        return dp[0][n - 1] * 2 > sums[n];
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{5, 3, 4, 5}).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{
            7, 7, 12, 16, 41, 48, 41, 48, 11, 9, 34, 2, 44, 30, 27, 12, 11, 39, 31, 8, 23, 11, 47, 25, 15, 23, 4, 17,
            11, 50, 16, 50, 38, 34, 48, 27, 16, 24, 22, 48, 50, 10, 26, 27, 9, 43, 13, 42, 46, 24
    }).expect(true);

}
