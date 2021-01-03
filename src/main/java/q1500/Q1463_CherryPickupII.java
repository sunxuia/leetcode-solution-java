package q1500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import q750.Q741_CherryPickup;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1463. Cherry Pickup II
 * https://leetcode.com/problems/cherry-pickup-ii/
 *
 * Given a rows x cols matrix grid representing a field of cherries. Each cell in grid represents the number of cherries
 * that you can collect.
 *
 * You have two robots that can collect cherries for you, Robot #1 is located at the top-left corner (0,0) , and Robot
 * #2 is located at the top-right corner (0, cols-1) of the grid.
 *
 * Return the maximum number of cherries collection using both robots  by following the rules below:
 *
 * From a cell (i,j), robots can move to cell (i+1, j-1) , (i+1, j) or (i+1, j+1).
 * When any robot is passing through a cell, It picks it up all cherries, and the cell becomes an empty cell (0).
 * When both robots stay on the same cell, only one of them takes the cherries.
 * Both robots cannot move outside of the grid at any moment.
 * Both robots should reach the bottom row in the grid.
 *
 * Example 1:
 * <img src="./Q1463_PIC1.png">
 * Input: grid = [[3,1,1],[2,5,1],[1,5,5],[2,1,1]]
 * Output: 24
 * Explanation: Path of robot #1 and #2 are described in color green and blue respectively.
 * Cherries taken by Robot #1, (3 + 2 + 5 + 2) = 12.
 * Cherries taken by Robot #2, (1 + 5 + 5 + 1) = 12.
 * Total of cherries: 12 + 12 = 24.
 *
 * Example 2:
 * <img src="./Q1463_PIC2.png">
 * Input: grid = [[1,0,0,0,0,0,1],[2,0,0,0,0,3,0],[2,0,9,0,0,0,0],[0,3,0,5,4,0,0],[1,0,2,3,0,0,6]]
 * Output: 28
 * Explanation: Path of robot #1 and #2 are described in color green and blue respectively.
 * Cherries taken by Robot #1, (1 + 9 + 5 + 2) = 17.
 * Cherries taken by Robot #2, (1 + 3 + 4 + 3) = 11.
 * Total of cherries: 17 + 11 = 28.
 *
 * Example 3:
 *
 * Input: grid = [[1,0,0,3],[0,0,0,3],[0,0,3,3],[9,0,3,3]]
 * Output: 22
 *
 * Example 4:
 *
 * Input: grid = [[1,1],[1,1]]
 * Output: 4
 *
 * Constraints:
 *
 * rows == grid.length
 * cols == grid[i].length
 * 2 <= rows, cols <= 70
 * 0 <= grid[i][j] <= 100
 *
 * 上一题 {@link Q741_CherryPickup}
 */
@RunWith(LeetCodeRunner.class)
public class Q1463_CherryPickupII {

    /**
     * 带缓存的dfs
     */
    @Answer
    public int cherryPickup(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        // 缓存, cache[level][left+1][right+1] 表示第level 层, 机器人分别在left 和right 的结果.
        //left <= right, -1 表示没有缓存, 0 用于标记边界值.
        int[][][] cache = new int[m + 1][n + 2][n + 2];
        for (int i = 0; i < m; i++) {
            for (int j = 1; j <= n; j++) {
                Arrays.fill(cache[i][j], 1, n + 1, -1);
            }
        }
        return dfs(cache, grid, 0, 0, n - 1);
    }

    private int dfs(int[][][] cache, int[][] grid, int level, int left, int right) {
        if (cache[level][left + 1][right + 1] != -1) {
            return cache[level][left + 1][right + 1];
        }
        // 下一层的最大值.
        int res = 0;
        for (int i = left - 1; i <= left + 1; i++) {
            for (int j = Math.max(i, right - 1); j <= right + 1; j++) {
                res = Math.max(res, dfs(cache, grid, level + 1, i, j));
            }
        }
        // 加上当前层的结果.
        res += grid[level][left];
        if (left != right) {
            res += grid[level][right];
        }
        cache[level][left + 1][right + 1] = res;
        return res;
    }

    /**
     * dp 的解法, 是上面解法的改写.
     */
    @Answer
    public int cherryPickup2(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        // dp[level][i+1][j+1] 表示第level 层, 机器人分别在i 和j (i <= j) 的结果.
        int[][][] dp = new int[m][n + 2][n + 2];
        dp[0][1][n] = grid[0][0] + grid[0][n - 1];
        for (int level = 1; level < m; level++) {
            // 限制i, j 的最大边界, 避免出现不可能位置的情况
            for (int i = 0; i < level + 1 && i < n; i++) {
                for (int j = Math.max(0, n - 1 - level); j < n; j++) {
                    int count = i == j ? grid[level][i] : grid[level][i] + grid[level][j];
                    for (int pi = i - 1; pi <= i + 1; pi++) {
                        for (int pj = Math.max(pi, j - 1); pj <= j + 1; pj++) {
                            dp[level][i + 1][j + 1] = Math.max(dp[level][i + 1][j + 1],
                                    count + dp[level - 1][pi + 1][pj + 1]);
                        }
                    }
                }
            }
        }

        int res = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                res = Math.max(res, dp[m - 1][i][j]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {3, 1, 1},
            {2, 5, 1},
            {1, 5, 5},
            {2, 1, 1}
    }).expect(24);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 0, 0, 0, 0, 0, 1},
            {2, 0, 0, 0, 0, 3, 0},
            {2, 0, 9, 0, 0, 0, 0},
            {0, 3, 0, 5, 4, 0, 0},
            {1, 0, 2, 3, 0, 0, 6}
    }).expect(28);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 0, 0, 3},
            {0, 0, 0, 3},
            {0, 0, 3, 3},
            {9, 0, 3, 3}
    }).expect(22);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {1, 1},
            {1, 1}
    }).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {0, 10, 1, 3, 5, 1, 8},
            {2, 7, 9, 1, 3, 8, 9},
            {8, 9, 5, 9, 0, 7, 9},
            {2, 5, 7, 10, 9, 3, 9},
            {0, 5, 3, 4, 9, 0, 1},
            {4, 8, 8, 8, 6, 3, 8},
            {1, 6, 10, 10, 10, 10, 3},
            {2, 9, 3, 2, 1, 3, 5},
            {9, 2, 6, 10, 9, 6, 5},
            {2, 2, 2, 9, 1, 7, 1}
    }).expect(149);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[][]{
            {0, 8, 7, 10, 9, 10, 0, 9, 6},
            {8, 7, 10, 8, 7, 4, 9, 6, 10},
            {8, 1, 1, 5, 1, 5, 5, 1, 2},
            {9, 4, 10, 8, 8, 1, 9, 5, 0},
            {4, 3, 6, 10, 9, 2, 4, 8, 10},
            {7, 3, 2, 8, 3, 3, 5, 9, 8},
            {1, 2, 6, 5, 6, 2, 0, 10, 0}
    }).expect(96);

}
