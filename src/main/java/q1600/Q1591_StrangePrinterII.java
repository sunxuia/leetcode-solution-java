package q1600;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import q700.Q664_StrangePrinter;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1591. Strange Printer II
 * https://leetcode.com/problems/strange-printer-ii/
 *
 * There is a strange printer with the following two special requirements:
 *
 * On each turn, the printer will print a solid rectangular pattern of a single color on the grid. This will cover up
 * the existing colors in the rectangle.
 * Once the printer has used a color for the above operation, the same color cannot be used again.
 *
 * You are given a m x n matrix targetGrid, where targetGrid[row][col] is the color in the position (row, col) of the
 * grid.
 *
 * Return true if it is possible to print the matrix targetGrid, otherwise, return false.
 *
 * Example 1:
 * <img src="./Q1591_PIC1.png">
 * Input: targetGrid = [[1,1,1,1],[1,2,2,1],[1,2,2,1],[1,1,1,1]]
 * Output: true
 *
 * Example 2:
 * <img src="./Q1591_PIC2.png">
 * Input: targetGrid = [[1,1,1,1],[1,1,3,3],[1,1,3,4],[5,5,1,4]]
 * Output: true
 *
 * Example 3:
 *
 * Input: targetGrid = [[1,2,1],[2,1,2],[1,2,1]]
 * Output: false
 * Explanation: It is impossible to form targetGrid because it is not allowed to print the same color in different
 * turns.
 *
 * Example 4:
 *
 * Input: targetGrid = [[1,1,1],[3,1,3]]
 * Output: false
 *
 * Constraints:
 *
 * m == targetGrid.length
 * n == targetGrid[i].length
 * 1 <= m, n <= 60
 * 1 <= targetGrid[row][col] <= 60
 *
 * 上一题 {@link Q664_StrangePrinter}
 */
@RunWith(LeetCodeRunner.class)
public class Q1591_StrangePrinterII {

    /**
     * 根据 hint, 可以从后往前推导, 找到被最后涂色的色块.
     */
    @Answer
    public boolean isPrintable(int[][] targetGrid) {
        final int m = targetGrid.length;
        final int n = targetGrid[0].length;
        // 坐标方向: ↑ ↓ ← →
        Map<Integer, int[]> colors = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int[] pos = colors.get(targetGrid[i][j]);
                if (pos == null) {
                    pos = new int[]{i, i, j, j};
                    colors.put(targetGrid[i][j], pos);
                } else {
                    pos[1] = i;
                    pos[2] = Math.min(pos[2], j);
                    pos[3] = Math.max(pos[3], j);
                }
            }
        }
        for (int i = colors.size(); i > 0; i--) {
            int square = getSequre(targetGrid, colors);
            if (square == -1) {
                return false;
            }
            clearSquare(targetGrid, colors.remove(square));
        }
        return true;
    }

    private int getSequre(int[][] grid, Map<Integer, int[]> colors) {
        for (var pair : colors.entrySet()) {
            if (isSquare(grid, pair.getKey(), pair.getValue())) {
                return pair.getKey();
            }
        }
        return -1;
    }

    private boolean isSquare(int[][] grid, int color, int[] pos) {
        for (int i = pos[0]; i <= pos[1]; i++) {
            for (int j = pos[2]; j <= pos[3]; j++) {
                if (grid[i][j] != 0 && grid[i][j] != color) {
                    return false;
                }
            }
        }
        return true;
    }

    private void clearSquare(int[][] grid, int[] pos) {
        for (int i = pos[0]; i <= pos[1]; i++) {
            for (int j = pos[2]; j <= pos[3]; j++) {
                grid[i][j] = 0;
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 1, 1, 1},
            {1, 2, 2, 1},
            {1, 2, 2, 1},
            {1, 1, 1, 1}
    }).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 1, 1, 1},
            {1, 1, 3, 3},
            {1, 1, 3, 4},
            {5, 5, 1, 4}
    }).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 2, 1},
            {2, 1, 2},
            {1, 2, 1}
    }).expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {1, 1, 1},
            {3, 1, 3}
    }).expect(false);

}
