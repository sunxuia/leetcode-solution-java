package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1568. Minimum Number of Days to Disconnect Island
 * https://leetcode.com/problems/minimum-number-of-days-to-disconnect-island/
 *
 * Given a 2D grid consisting of 1s (land) and 0s (water).  An island is a maximal 4-directionally (horizontal or
 * vertical) connected group of 1s.
 *
 * The grid is said to be connected if we have exactly one island, otherwise is said disconnected.
 *
 * In one day, we are allowed to change any single land cell (1) into a water cell (0).
 *
 * Return the minimum number of days to disconnect the grid.
 *
 * Example 1:
 *
 * Input: grid = [[0,1,1,0],[0,1,1,0],[0,0,0,0]]
 * Output: 2
 * Explanation: We need at least 2 days to get a disconnected grid.
 * Change land grid[1][1] and grid[0][2] to water and get 2 disconnected island.
 *
 * Example 2:
 *
 * Input: grid = [[1,1]]
 * Output: 2
 * Explanation: Grid of full water is also disconnected ([[1,1]] -> [[0,0]]), 0 islands.
 *
 * Example 3:
 *
 * Input: grid = [[1,0,1,0]]
 * Output: 0
 *
 * Example 4:
 *
 * Input: grid = [[1,1,0,1,1],
 * [1,1,1,1,1],
 * [1,1,0,1,1],
 * [1,1,0,1,1]]
 * Output: 1
 *
 * Example 5:
 *
 * Input: grid = [[1,1,0,1,1],
 * [1,1,1,1,1],
 * [1,1,0,1,1],
 * [1,1,1,1,1]]
 * Output: 2
 *
 * Constraints:
 *
 * 1 <= grid.length, grid[i].length <= 30
 * grid[i][j] is 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1568_MinimumNumberOfDaysToDisconnectIsland {

    /**
     * 根据 hint, 分离出1 个岛屿最多只需要2 天(边上的岛屿两边切断即可).
     */
    @Answer
    public int minDays(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        if (isSeparate(grid)) {
            return 0;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    grid[i][j] = 0;
                    if (isSeparate(grid)) {
                        return 1;
                    }
                    grid[i][j] = 1;
                }
            }
        }
        return 2;
    }

    private boolean isSeparate(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        boolean hasIsland = false;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    if (hasIsland) {
                        return true;
                    } else {
                        hasIsland = true;
                        markVisit(grid, visited, i, j);
                    }
                }
            }
        }
        return !hasIsland;
    }

    private void markVisit(int[][] grid, boolean[][] visited, int i, int j) {
        final int m = grid.length, n = grid[0].length;
        if (i == -1 || i == m || j == -1 || j == n
                || visited[i][j] || grid[i][j] == 0) {
            return;
        }
        visited[i][j] = true;
        markVisit(grid, visited, i - 1, j);
        markVisit(grid, visited, i, j + 1);
        markVisit(grid, visited, i + 1, j);
        markVisit(grid, visited, i, j - 1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0}
    }).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 1}}).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{1, 0, 1, 0}}).expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1},
            {1, 1, 0, 1, 1}
    }).expect(1);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[][]{
            {1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1}
    }).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    }).expect(1);

}
