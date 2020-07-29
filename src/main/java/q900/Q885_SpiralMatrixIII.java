package q900;

import org.junit.runner.RunWith;
import q100.Q059_SpiralMatrixII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 885. Spiral Matrix III
 * https://leetcode.com/problems/spiral-matrix-iii/
 *
 * On a 2 dimensional grid with R rows and C columns, we start at (r0, c0) facing east.
 *
 * Here, the north-west corner of the grid is at the first row and column, and the south-east corner of the grid is at
 * the last row and column.
 *
 * Now, we walk in a clockwise spiral shape to visit every position in this grid.
 *
 * Whenever we would move outside the boundary of the grid, we continue our walk outside the grid (but may return to the
 * grid boundary later.)
 *
 * Eventually, we reach all R * C spaces of the grid.
 *
 * Return a list of coordinates representing the positions of the grid in the order they were visited.
 *
 * Example 1:
 *
 * Input: R = 1, C = 4, r0 = 0, c0 = 0
 * Output: [[0,0],[0,1],[0,2],[0,3]]
 * (图Q885_PIC1.png)
 *
 * Example 2:
 *
 * Input: R = 5, C = 6, r0 = 1, c0 = 4
 * Output: [[1,4],[1,5],[2,5],[2,4],[2,3],[1,3],[0,3],[0,4],[0,5],[3,5],[3,4],[3,3],[3,2],[2,2],[1,2],[0,2],[4,5],[4,
 * 4],[4,3],[4,2],[4,1],[3,1],[2,1],[1,1],[0,1],[4,0],[3,0],[2,0],[1,0],[0,0]]
 * (图Q885_PIC2.png)
 *
 * Note:
 *
 * 1 <= R <= 100
 * 1 <= C <= 100
 * 0 <= r0 < R
 * 0 <= c0 < C
 *
 * 上一题 {@link Q059_SpiralMatrixII}
 */
@RunWith(LeetCodeRunner.class)
public class Q885_SpiralMatrixIII {

    @Answer
    public int[][] spiralMatrixIII(int R, int C, int r0, int c0) {
        final int[] directions = new int[]{0, 1, 0, -1, 0};
        int[][] res = new int[R * C][];
        res[0] = new int[]{r0, c0};
        int size = 1, len = 0, d = 0;
        while (size < R * C) {
            len += d == 0 || d == 2 ? 1 : 0;
            for (int i = 0; i < len; i++) {
                r0 += directions[d];
                c0 += directions[d + 1];
                if (0 <= r0 && r0 < R && 0 <= c0 && c0 < C) {
                    res[size++] = new int[]{r0, c0};
                }
            }
            d = (d + 1) % 4;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 4, 0, 0)
            .expect(new int[][]{{0, 0}, {0, 1}, {0, 2}, {0, 3}});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(5, 6, 1, 4)
            .expect(new int[][]{{1, 4}, {1, 5}, {2, 5}, {2, 4}, {2, 3}, {1, 3}, {0, 3}, {0, 4}, {0, 5}, {3, 5}, {3, 4},
                    {3, 3}, {3, 2}, {2, 2}, {1, 2}, {0, 2}, {4, 5}, {4, 4}, {4, 3}, {4, 2}, {4, 1}, {3, 1}, {2, 1},
                    {1, 1}, {0, 1}, {4, 0}, {3, 0}, {2, 0}, {1, 0}, {0, 0}});

}
