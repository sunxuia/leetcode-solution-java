package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1034. Coloring A Border
 * https://leetcode.com/problems/coloring-a-border/
 *
 * Given a 2-dimensional grid of integers, each value in the grid represents the color of the grid square at that
 * location.
 *
 * Two squares belong to the same connected component if and only if they have the same color and are next to each other
 * in any of the 4 directions.
 *
 * The border of a connected component is all the squares in the connected component that are either 4-directionally
 * adjacent to a square not in the component, or on the boundary of the grid (the first or last row or column).
 *
 * Given a square at location (r0, c0) in the grid and a color, color the border of the connected component of that
 * square with the given color, and return the final grid.
 *
 * Example 1:
 *
 * Input: grid = [[1,1],[1,2]], r0 = 0, c0 = 0, color = 3
 * Output: [[3, 3], [3, 2]]
 *
 * Example 2:
 *
 * Input: grid = [[1,2,2],[2,3,2]], r0 = 0, c0 = 1, color = 3
 * Output: [[1, 3, 3], [2, 3, 3]]
 *
 * Example 3:
 *
 * Input: grid = [[1,1,1],[1,1,1],[1,1,1]], r0 = 1, c0 = 1, color = 2
 * Output: [[2, 2, 2], [2, 1, 2], [2, 2, 2]]
 *
 * Note:
 *
 * 1 <= grid.length <= 50
 * 1 <= grid[0].length <= 50
 * 1 <= grid[i][j] <= 1000
 * 0 <= r0 < grid.length
 * 0 <= c0 < grid[0].length
 * 1 <= color <= 1000
 *
 * 题解: 这题要求给[r0, c0] 所在的色块的边框着色, 边框里面的不需要.
 */
@RunWith(LeetCodeRunner.class)
public class Q1034_ColoringABorder {

    @Answer
    public int[][] colorBorder(int[][] grid, int r0, int c0, int color) {
        final int m = grid.length, n = grid[0].length;
        int oldColor = grid[r0][c0];
        markBorder(grid, r0, c0, oldColor);

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == -1) {
                    grid[r][c] = oldColor;
                } else if (grid[r][c] == -2) {
                    grid[r][c] = color;
                }
            }
        }
        return grid;
    }

    // 边界标记为-2, 非边界标记为-1
    private void markBorder(int[][] grid, int r, int c, int oldColor) {
        final int m = grid.length, n = grid[0].length;
        if (r == -1 || r == m || c == -1 || c == n || grid[r][c] != oldColor) {
            return;
        }
        boolean isBorder = r == 0 || grid[r - 1][c] > 0 && grid[r - 1][c] != oldColor
                || r == m - 1 || grid[r + 1][c] > 0 && grid[r + 1][c] != oldColor
                || c == 0 || grid[r][c - 1] > 0 && grid[r][c - 1] != oldColor
                || c == n - 1 || grid[r][c + 1] > 0 && grid[r][c + 1] != oldColor;
        grid[r][c] = -1;
        markBorder(grid, r - 1, c, oldColor);
        markBorder(grid, r + 1, c, oldColor);
        markBorder(grid, r, c - 1, oldColor);
        markBorder(grid, r, c + 1, oldColor);
        if (isBorder) {
            grid[r][c] = -2;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{
                    {1, 1},
                    {1, 2}
            }, 0, 0, 3)
            .expect(new int[][]{
                    {3, 3},
                    {3, 2}
            });

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{
                    {1, 2, 2},
                    {2, 3, 2}
            }, 0, 1, 3)
            .expect(new int[][]{
                    {1, 3, 3},
                    {2, 3, 3}
            });

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{
                    {1, 1, 1},
                    {1, 1, 1},
                    {1, 1, 1}
            }, 1, 1, 2)
            .expect(new int[][]{
                    {2, 2, 2},
                    {2, 1, 2},
                    {2, 2, 2}
            });

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{
                    {2, 1, 2, 2, 1, 1},
                    {1, 2, 2, 2, 1, 1},
                    {1, 1, 2, 2, 1, 1},
                    {2, 2, 2, 1, 2, 1},
                    {2, 2, 2, 1, 1, 2},
                    {1, 2, 2, 1, 1, 1}
            }, 3, 2, 1)
            .expect(new int[][]{
                    {2, 1, 1, 1, 1, 1},
                    {1, 1, 2, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 2, 1},
                    {1, 2, 1, 1, 1, 2},
                    {1, 1, 1, 1, 1, 1}
            });

}
