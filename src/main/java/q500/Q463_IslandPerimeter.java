package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/island-perimeter/
 *
 * You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water.
 *
 * Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water, and
 * there is exactly one island (i.e., one or more connected land cells).
 *
 * The island doesn't have "lakes" (water inside that isn't connected to the water around the island). One cell is a
 * square with side length 1. The grid is rectangular, width and height don't exceed 100. Determine the perimeter of
 * the island.
 *
 *
 *
 * Example:
 *
 * Input:
 * [[0,1,0,0],
 * [1,1,1,0],
 * [0,1,0,0],
 * [1,1,0,0]]
 *
 * Output: 16
 *
 * Explanation: The perimeter is the 16 yellow stripes in the image below:
 *
 * (图 Q463_PIC.png)
 */
@RunWith(LeetCodeRunner.class)
public class Q463_IslandPerimeter {

    @Answer
    public int islandPerimeter(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                res += 4;
                if (i > 0 && grid[i - 1][j] == 1) {
                    res -= 2;
                }
                if (j > 0 && grid[i][j - 1] == 1) {
                    res -= 2;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{
            {0, 1, 0, 0},
            {1, 1, 1, 0},
            {0, 1, 0, 0},
            {1, 1, 0, 0}
    }).expect(16);

}
