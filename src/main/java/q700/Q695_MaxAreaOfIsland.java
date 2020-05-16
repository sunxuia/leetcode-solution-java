package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/max-area-of-island/
 *
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected
 * 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
 *
 * Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)
 *
 * Example 1:
 *
 * > [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 * >  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * >  [0,1,1,0,1,0,0,0,0,0,0,0,0],
 * >  [0,1,0,0,1,1,0,0,1,0,1,0,0],
 * >  [0,1,0,0,1,1,0,0,1,1,1,0,0],
 * >  [0,0,0,0,0,0,0,0,0,0,1,0,0],
 * >  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * >  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 *
 * Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
 *
 * Example 2:
 *
 * [[0,0,0,0,0,0,0,0]]
 *
 * Given the above grid, return 0.
 *
 * Note: The length of each dimension in the given grid does not exceed 50.
 */
@RunWith(LeetCodeRunner.class)
public class Q695_MaxAreaOfIsland {

    @Answer
    public int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                res = Math.max(res, dfs(grid, i, j));
            }
        }
        return res;
    }

    private int dfs(int[][] grid, int i, int j) {
        final int m = grid.length, n = grid[0].length;
        if (i == -1 || i == m || j == -1 || j == n || grid[i][j] == 0) {
            return 0;
        }
        grid[i][j] = 0;
        int res = 1;
        res += dfs(grid, i + 1, j);
        res += dfs(grid, i, j + 1);
        res += dfs(grid, i - 1, j);
        res += dfs(grid, i, j - 1);
        return res;
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.create(new int[][]{
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
    }).expect(6);

    @TestData
    public DataExpectation exmaple2 = DataExpectation.create(new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0}
    }).expect(0);

}
