package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1219. Path with Maximum Gold
 * https://leetcode.com/problems/path-with-maximum-gold/
 *
 * In a gold mine grid of size m * n, each cell in this mine has an integer representing the amount of gold in that
 * cell, 0 if it is empty.
 *
 * Return the maximum amount of gold you can collect under the conditions:
 *
 * Every time you are located in a cell you will collect all the gold in that cell.
 * From your position you can walk one step to the left, right, up or down.
 * You can't visit the same cell more than once.
 * Never visit a cell with 0 gold.
 * You can start and stop collecting gold from any position in the grid that has some gold.
 *
 * Example 1:
 *
 * Input: grid = [[0,6,0],[5,8,7],[0,9,0]]
 * Output: 24
 * Explanation:
 * [[0,6,0],
 * [5,8,7],
 * [0,9,0]]
 * Path to get the maximum gold, 9 -> 8 -> 7.
 *
 * Example 2:
 *
 * Input: grid = [[1,0,7],[2,0,6],[3,4,5],[0,3,0],[9,0,20]]
 * Output: 28
 * Explanation:
 * [[1,0,7],
 * [2,0,6],
 * [3,4,5],
 * [0,3,0],
 * [9,0,20]]
 * Path to get the maximum gold, 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7.
 *
 * Constraints:
 *
 * 1 <= grid.length, grid[i].length <= 15
 * 0 <= grid[i][j] <= 100
 * There are at most 25 cells containing gold.
 */
@RunWith(LeetCodeRunner.class)
public class Q1219_PathWithMaximumGold {

    @Answer
    public int getMaximumGold(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) {
                    res = Math.max(res, dfs(grid, visited, i, j));
                }
            }
        }
        return res;
    }

    private int dfs(int[][] grid, boolean[][] visited, int i, int j) {
        final int m = grid.length, n = grid[0].length;
        if (-1 == i || i == m || -1 == j || j == n
                || grid[i][j] == 0 || visited[i][j]) {
            return 0;
        }
        visited[i][j] = true;
        int max = dfs(grid, visited, i + 1, j);
        max = Math.max(max, dfs(grid, visited, i, j + 1));
        max = Math.max(max, dfs(grid, visited, i - 1, j));
        max = Math.max(max, dfs(grid, visited, i, j - 1));
        visited[i][j] = false;
        return max + grid[i][j];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{0, 6, 0}, {5, 8, 7}, {0, 9, 0}})
            .expect(24);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 0, 7}, {2, 0, 6}, {3, 4, 5}, {0, 3, 0}, {9, 0, 20}})
            .expect(28);

}
