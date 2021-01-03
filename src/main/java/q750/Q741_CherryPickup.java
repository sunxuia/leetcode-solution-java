package q750;

import java.util.Arrays;
import org.junit.runner.RunWith;
import q1500.Q1463_CherryPickupII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/cherry-pickup/
 *
 * In a N x N grid representing a field of cherries, each cell is one of three possible integers.
 *
 *
 *
 * 0 means the cell is empty, so you can pass through;
 * 1 means the cell contains a cherry, that you can pick up and pass through;
 * -1 means the cell contains a thorn that blocks your way.
 *
 *
 *
 * Your task is to collect maximum number of cherries possible by following the rules below:
 *
 *
 *
 * Starting at the position (0, 0) and reaching (N-1, N-1) by moving right or down through valid path cells
 * (cells with value 0 or 1);
 * After reaching (N-1, N-1), returning to (0, 0) by moving left or up through valid path cells;
 * When passing through a path cell containing a cherry, you pick it up and the cell becomes an empty cell (0);
 * If there is no valid path between (0, 0) and (N-1, N-1), then no cherries can be collected.
 *
 *
 *
 *
 *
 * Example 1:
 *
 * Input: grid =
 * [[0, 1, -1],
 * [1, 0, -1],
 * [1, 1,  1]]
 * Output: 5
 * Explanation:
 * The player started at (0, 0) and went down, down, right right to reach (2, 2).
 * 4 cherries were picked up during this single trip, and the matrix becomes [[0,1,-1],[0,0,-1],[0,0,0]].
 * Then, the player went left, up, up, left to return home, picking up one more cherry.
 * The total number of cherries picked up is 5, and this is the maximum possible.
 *
 *
 *
 * Note:
 *
 * grid is an N by N 2D array, with 1 <= N <= 50.
 * Each grid[i][j] is an integer in the set {-1, 0, 1}.
 * It is guaranteed that grid[0][0] and grid[N-1][N-1] are not -1.
 *
 * 下一题 {@link Q1463_CherryPickupII}
 */
@RunWith(LeetCodeRunner.class)
public class Q741_CherryPickup {

    /**
     * https://www.cnblogs.com/grandyang/p/8215787.html
     * 这题可以使用dp 来做, 使用dp[i][j] 表示从(0, 0) 到 (i, j) 然后再回去的路径中能够得到的最大结果.
     */
    @Answer
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp[0][0] = grid[0][0];

        for (int k = 1; k < 2 * n - 1; k++) {
            for (int i = n - 1; i >= 0; i--) {
                for (int p = n - 1; p >= 0; p--) {
                    final int j = k - i, q = k - p;
                    if (j < 0 || j >= n || q < 0 || q >= n || grid[i][j] < 0 || grid[p][q] < 0) {
                        dp[i][p] = -1;
                        continue;
                    }
                    if (i > 0) {
                        dp[i][p] = Math.max(dp[i][p], dp[i - 1][p]);
                    }
                    if (p > 0) {
                        dp[i][p] = Math.max(dp[i][p], dp[i][p - 1]);
                    }
                    if (i > 0 && p > 0) {
                        dp[i][p] = Math.max(dp[i][p], dp[i - 1][p - 1]);
                    }
                    if (dp[i][p] >= 0) {
                        dp[i][p] += grid[i][j] + (i != p ? grid[p][q] : 0);
                    }
                }
            }
        }
        return Math.max(dp[n - 1][n - 1], 0);
    }

    // LeetCode 中和上面方法类似的另一个版本
    @Answer
    public int cherryPickup2(int[][] grid) {
        this.grid = grid;
        n = grid.length;
        memo = new int[n][n][n];

        for (int[][] layer : memo) {
            for (int[] row : layer) {
                Arrays.fill(row, -1);
            }
        }
        return Math.max(0, dp(0, 0, 0));
    }

    private int[][][] memo;
    private int[][] grid;
    private int n;

    public int dp(int r1, int c1, int c2) {
        int r2 = r1 - c2 + c1;
        if (n == r1 | n == r2 || n == c1 || n == c2
                || grid[r1][c1] == -1 || grid[r2][c2] == -1) {
            return Integer.MIN_VALUE;
        } else if (r1 == n - 1 && c1 == n - 1) {
            return grid[r1][c1];
        } else if (memo[r1][c1][c2] != -1) {
            return memo[r1][c1][c2];
        }

        int res = grid[r1][c1];
        if (c1 != c2) {
            res += grid[r2][c2];
        }
        int next = Math.max(Math.max(dp(r1, c1 + 1, c2 + 1), dp(r1 + 1, c1, c2 + 1)),
                Math.max(dp(r1, c1 + 1, c2), dp(r1 + 1, c1, c2)));
        res += next;
        memo[r1][c1][c2] = res;
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{
            {0, 1, -1},
            {1, 0, -1},
            {1, 1, +1}
    }).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {1, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 1}
    }).expect(15);
}
