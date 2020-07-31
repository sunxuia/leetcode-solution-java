package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 892. Surface Area of 3D Shapes
 * https://leetcode.com/problems/surface-area-of-3d-shapes/
 *
 * On a N * N grid, we place some 1 * 1 * 1 cubes.
 *
 * Each value v = grid[i][j] represents a tower of v cubes placed on top of grid cell (i, j).
 *
 * Return the total surface area of the resulting shapes.
 *
 * Example 1:
 *
 * Input: [[2]]
 * Output: 10
 *
 * Example 2:
 *
 * Input: [[1,2],[3,4]]
 * Output: 34
 *
 * Example 3:
 *
 * Input: [[1,0],[0,2]]
 * Output: 16
 *
 * Example 4:
 *
 * Input: [[1,1,1],[1,0,1],[1,1,1]]
 * Output: 32
 *
 * Example 5:
 *
 * Input: [[2,2,2],[2,1,2],[2,2,2]]
 * Output: 46
 *
 * Note:
 *
 * 1 <= N <= 50
 * 0 <= grid[i][j] <= 50
 */
@RunWith(LeetCodeRunner.class)
public class Q892_SurfaceAreaOf3dShapes {

    @Answer
    public int surfaceArea(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int height = grid[i][j];
                if (height > 0) {
                    res += height * 4 + 2;
                    int top = i == 0 ? 0 : grid[i - 1][j];
                    int left = j == 0 ? 0 : grid[i][j - 1];
                    res -= 2 * (Math.min(top, height) + Math.min(left, height));
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{2}}).expect(10);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 2}, {3, 4}}).expect(34);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{1, 0}, {0, 2}}).expect(16);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}}).expect(32);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[][]{{2, 2, 2}, {2, 1, 2}, {2, 2, 2}}).expect(46);

}
