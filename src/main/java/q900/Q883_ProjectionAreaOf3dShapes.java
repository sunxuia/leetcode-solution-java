package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 883. Projection Area of 3D Shapes
 * https://leetcode.com/problems/projection-area-of-3d-shapes/
 *
 * On a N * N grid, we place some 1 * 1 * 1 cubes that are axis-aligned with the x, y, and z axes.
 *
 * Each value v = grid[i][j] represents a tower of v cubes placed on top of grid cell (i, j).
 *
 * Now we view the projection of these cubes onto the xy, yz, and zx planes.
 *
 * A projection is like a shadow, that maps our 3 dimensional figure to a 2 dimensional plane.
 *
 * Here, we are viewing the "shadow" when looking at the cubes from the top, the front, and the side.
 *
 * Return the total area of all three projections.
 *
 * Example 1:
 *
 * Input: [[2]]
 * Output: 5
 *
 * Example 2:
 *
 * Input: [[1,2],[3,4]]
 * Output: 17
 * Explanation:
 * Here are the three projections ("shadows") of the shape made with each axis-aligned plane.
 * (图 Q883_PIC.png)
 *
 * Example 3:
 *
 * Input: [[1,0],[0,2]]
 * Output: 8
 *
 * Example 4:
 *
 * Input: [[1,1,1],[1,0,1],[1,1,1]]
 * Output: 14
 *
 * Example 5:
 *
 * Input: [[2,2,2],[2,1,2],[2,2,2]]
 * Output: 21
 *
 * Note:
 *
 * 1 <= grid.length = grid[0].length <= 50
 * 0 <= grid[i][j] <= 50
 */
@RunWith(LeetCodeRunner.class)
public class Q883_ProjectionAreaOf3dShapes {

    @Answer
    public int projectionArea(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int res = 0;
        // z 轴透视
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res += grid[i][j] > 0 ? 1 : 0;
            }
        }
        // y 轴透视
        for (int i = 0; i < m; i++) {
            int max = 0;
            for (int j = 0; j < n; j++) {
                max = Math.max(max, grid[i][j]);
            }
            res += max;
        }
        // x 轴透视
        for (int j = 0; j < n; j++) {
            int max = 0;
            for (int i = 0; i < m; i++) {
                max = Math.max(max, grid[i][j]);
            }
            res += max;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{2}}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 2}, {3, 4}}).expect(17);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{1, 0}, {0, 2}}).expect(8);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}}).expect(14);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[][]{{2, 2, 2}, {2, 1, 2}, {2, 2, 2}}).expect(21);

}
