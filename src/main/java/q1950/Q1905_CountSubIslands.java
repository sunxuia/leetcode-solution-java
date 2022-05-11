package q1950;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1905. Count Sub Islands
 * https://leetcode.com/problems/count-sub-islands/
 *
 * You are given two m x n binary matrices grid1 and grid2 containing only 0's (representing water) and 1's
 * (representing land). An island is a group of 1's connected 4-directionally (horizontal or vertical). Any cells
 * outside of the grid are considered water cells.
 *
 * An island in grid2 is considered a sub-island if there is an island in grid1 that contains all the cells that make up
 * this island in grid2.
 *
 * Return the number of islands in grid2 that are considered sub-islands.
 *
 * Example 1:
 * (图Q1905_PIC1.png)
 * Input: grid1 = [[1,1,1,0,0],[0,1,1,1,1],[0,0,0,0,0],[1,0,0,0,0],[1,1,0,1,1]], grid2 =
 * [[1,1,1,0,0],[0,0,1,1,1],[0,1,0,0,0],[1,0,1,1,0],[0,1,0,1,0]]
 * Output: 3
 * Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
 * The 1s colored red in grid2 are those considered to be part of a sub-island. There are three sub-islands.
 *
 * Example 2:
 * (图Q1905_PIC2.png)
 * Input: grid1 = [[1,0,1,0,1],[1,1,1,1,1],[0,0,0,0,0],[1,1,1,1,1],[1,0,1,0,1]], grid2 =
 * [[0,0,0,0,0],[1,1,1,1,1],[0,1,0,1,0],[0,1,0,1,0],[1,0,0,0,1]]
 * Output: 2
 * Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
 * The 1s colored red in grid2 are those considered to be part of a sub-island. There are two sub-islands.
 *
 * Constraints:
 *
 * m == grid1.length == grid2.length
 * n == grid1[i].length == grid2[i].length
 * 1 <= m, n <= 500
 * grid1[i][j] and grid2[i][j] are either 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1905_CountSubIslands {

    @Answer
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        final int m = grid1.length;
        final int n = grid1[0].length;

        // grid2 染色
        int color = 2;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    setColor(grid2, i, j, color++);
                }
            }
        }

        // 检查grid1, 如果grid1[i][j]==0 则grid2 中的岛不是子岛
        boolean[] match = new boolean[color];
        Arrays.fill(match, true);
        int res = color - 2;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int c = grid2[i][j];
                if (c > 0 && match[c]
                        && grid1[i][j] == 0) {
                    res--;
                    match[c] = false;
                }
            }
        }
        return res;
    }

    private void setColor(int[][] grid, int i, int j, int color) {
        final int m = grid.length;
        final int n = grid[0].length;
        if (i == -1 || i == m || j == -1 || j == n
                || grid[i][j] != 1) {
            return;
        }
        grid[i][j] = color;
        setColor(grid, i + 1, j, color);
        setColor(grid, i, j + 1, color);
        setColor(grid, i - 1, j, color);
        setColor(grid, i, j - 1, color);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(
                    new int[][]{
                            {1, 1, 1, 0, 0},
                            {0, 1, 1, 1, 1},
                            {0, 0, 0, 0, 0},
                            {1, 0, 0, 0, 0},
                            {1, 1, 0, 1, 1}},
                    new int[][]{
                            {1, 1, 1, 0, 0},
                            {0, 0, 1, 1, 1},
                            {0, 1, 0, 0, 0},
                            {1, 0, 1, 1, 0},
                            {0, 1, 0, 1, 0}})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(
                    new int[][]{
                            {1, 0, 1, 0, 1},
                            {1, 1, 1, 1, 1},
                            {0, 0, 0, 0, 0},
                            {1, 1, 1, 1, 1},
                            {1, 0, 1, 0, 1}},
                    new int[][]{
                            {0, 0, 0, 0, 0},
                            {1, 1, 1, 1, 1},
                            {0, 1, 0, 1, 0},
                            {0, 1, 0, 1, 0},
                            {1, 0, 0, 0, 1}})
            .expect(2);

}
