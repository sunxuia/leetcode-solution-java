package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1254. Number of Closed Islands
 * https://leetcode.com/problems/number-of-closed-islands/
 *
 * Given a 2D grid consists of 0s (land) and 1s (water).  An island is a maximal 4-directionally connected group of 0s
 * and a closed island is an island totally (all left, top, right, bottom) surrounded by 1s.
 *
 * Return the number of closed islands.
 *
 * Example 1:
 * <img src="./1254_PIC1.png">
 * Input: grid = [[1,1,1,1,1,1,1,0],[1,0,0,0,0,1,1,0],[1,0,1,0,1,1,1,0],[1,0,0,0,0,1,0,1],[1,1,1,1,1,1,1,0]]
 * Output: 2
 * Explanation:
 * Islands in gray are closed because they are completely surrounded by water (group of 1s).
 *
 * Example 2:
 * <img src="./1254_PIC2.png">
 * Input: grid = [[0,0,1,0,0],[0,1,0,1,0],[0,1,1,1,0]]
 * Output: 1
 *
 * Example 3:
 *
 * > Input: grid = [[1,1,1,1,1,1,1],
 * >                [1,0,0,0,0,0,1],
 * >                [1,0,1,1,1,0,1],
 * >                [1,0,1,0,1,0,1],
 * >                [1,0,1,1,1,0,1],
 * >                [1,0,0,0,0,0,1],
 * >                [1,1,1,1,1,1,1]]
 * Output: 2
 *
 * Constraints:
 *
 * 1 <= grid.length, grid[0].length <= 100
 * 0 <= grid[i][j] <=1
 */
@RunWith(LeetCodeRunner.class)
public class Q1254_NumberOfClosedIslands {

    @Answer
    public int closedIsland(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            dfs(grid, i, 0, -1);
            dfs(grid, i, n - 1, -1);
        }
        for (int j = 0; j < n; j++) {
            dfs(grid, 0, j, -1);
            dfs(grid, m - 1, j, -1);
        }
        int color = 2;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    dfs(grid, i, j, color++);
                }
            }
        }
        return color - 2;
    }

    private void dfs(int[][] grid, int i, int j, int color) {
        if (i == -1 || j == -1 || i == grid.length || j == grid[0].length
                || grid[i][j] != 0) {
            return;
        }
        grid[i][j] = color;
        dfs(grid, i - 1, j, color);
        dfs(grid, i, j + 1, color);
        dfs(grid, i + 1, j, color);
        dfs(grid, i, j - 1, color);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 0},
            {1, 0, 0, 0, 0, 1, 1, 0},
            {1, 0, 1, 0, 1, 1, 1, 0},
            {1, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0}
    }).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {0, 0, 1, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 1, 1, 1, 0}
    }).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1}
    }).expect(2);

}
