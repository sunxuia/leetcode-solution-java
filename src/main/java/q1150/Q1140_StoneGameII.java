package q1150;

import org.junit.runner.RunWith;
import q1450.Q1406_StoneGameIII;
import q900.Q877_StoneGame;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1140. Stone Game II
 * https://leetcode.com/problems/stone-game-ii/
 *
 * Alex and Lee continue their games with piles of stones.  There are a number of piles arranged in a row, and each pile
 * has a positive integer number of stones piles[i].  The objective of the game is to end with the most stones.
 *
 * Alex and Lee take turns, with Alex starting first.  Initially, M = 1.
 *
 * On each player's turn, that player can take all the stones in the first X remaining piles, where 1 <= X <= 2M.  Then,
 * we set M = max(M, X).
 *
 * The game continues until all the stones have been taken.
 *
 * Assuming Alex and Lee play optimally, return the maximum number of stones Alex can get.
 *
 * Example 1:
 *
 * Input: piles = [2,7,9,4,4]
 * Output: 10
 * Explanation:  If Alex takes one pile at the beginning, Lee takes two piles, then Alex takes 2 piles again. Alex can
 * get 2 + 4 + 4 = 10 piles in total. If Alex takes two piles at the beginning, then Lee can take all three piles left.
 * In this case, Alex get 2 + 7 = 9 piles in total. So we return 10 since it's larger.
 *
 * Constraints:
 *
 * 1 <= piles.length <= 100
 * 1 <= piles[i] <= 10 ^ 4
 *
 * 上一题 {@link Q877_StoneGame}
 * 下一题 {@link Q1406_StoneGameIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q1140_StoneGameII {

    /**
     * 单纯的dfs 会超时.
     */
    @Answer
    public int stoneGameII(int[] piles) {
        final int n = piles.length;
        // sums[i] 表示 piles[i:n) 的和
        int[] sums = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            sums[i] = sums[i + 1] + piles[i];
        }
        return dfs(new int[n + 1][n + 1], piles, sums, 0, 1);
    }

    private int dfs(int[][] cache, int[] piles, int[] sums, int i, int m) {
        final int n = piles.length;
        if (n - i <= 2 * m) {
            // 剪枝: 当前玩家能拿走所有的石头, 参考 LeetCode 上最快的解法.
            return sums[i];
        }
        if (cache[i][m] != 0) {
            return cache[i][m];
        }
        int res = 0, limit = Math.min(i + 2 * m, n);
        for (int j = i; j < limit; j++) {
            int opposite = dfs(cache, piles, sums, j + 1, Math.max(m, j - i + 1));
            res = Math.max(res, sums[i] - opposite);
        }
        cache[i][m] = res;
        return res;
    }

    /**
     * dp 的解法, 是对上面解法的改写.
     */
    @Answer
    public int stoneGameII2(int[] piles) {
        final int n = piles.length;
        int[] sums = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            sums[i] = sums[i + 1] + piles[i];
        }

        // dp[i][m] 表示在piles[0, i) 已经被取走了的情况下, M 为m 情况下可以取得的最好结果.
        int[][] dp = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int m = 1; m < n; m++) {
                for (int x = 1; x <= 2 * m && i + x <= n; x++) {
                    // 本次取走 [i, i+x) 后, 下一个人可以取得的最好结果.
                    int prev = dp[i + x][Math.min(n, Math.max(x, m))];
                    dp[i][m] = Math.max(dp[i][m], sums[i] - prev);
                }
            }
        }
        return dp[0][1];
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{2, 7, 9, 4, 4}).expect(10);

    @TestData
    public DataExpectation overTime = DataExpectation.create(new int[]{
            3111, 4303, 2722, 2183, 6351, 5227, 8964, 7167, 9286, 6626, 2347, 1465, 5201, 7240, 5463, 8523, 8163, 9391,
            8616, 5063, 7837, 7050, 1246, 9579, 7744, 6932, 7704, 9841, 6163, 4829, 7324, 6006, 4689, 8781, 621})
            .expect(112766);

}
