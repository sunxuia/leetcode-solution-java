package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/magic-squares-in-grid/
 *
 * A 3 x 3 magic square is a 3 x 3 grid filled with distinct numbers from 1 to 9 such that each row, column, and both
 * diagonals all have the same sum.
 *
 * Given an grid of integers, how many 3 x 3 "magic square" subgrids are there?  (Each subgrid is contiguous).
 *
 *
 *
 * Example 1:
 *
 * Input: [[4,3,8,4],
 * [9,5,1,9],
 * [2,7,6,2]]
 * Output: 1
 * Explanation:
 * The following subgrid is a 3 x 3 magic square:
 * 438
 * 951
 * 276
 *
 * while this one is not:
 * 384
 * 519
 * 762
 *
 * In total, there is only one magic square inside the given grid.
 *
 * Note:
 *
 * 1 <= grid.length <= 10
 * 1 <= grid[0].length <= 10
 * 0 <= grid[i][j] <= 15
 */
@RunWith(LeetCodeRunner.class)
public class Q840_MagicSquaresInGrid {

    @Answer
    public int numMagicSquaresInside(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        if (m < 3 || n < 3) {
            return 0;
        }
        int res = 0;
        for (int i = 0; i < m - 2; i++) {
            for (int j = 0; j < n - 2; j++) {
                if (isMagic(grid, i, j)) {
                    res++;
                }
            }
        }
        return res;
    }

    private boolean isMagic(int[][] grid, int top, int left) {
        int mask = 0;
        // 0-2: 行的和, 3-5: 列的和, 6-7: 对角线的和, 这些和的结果一定要是15
        int[] sums = new int[8];
        for (int i = top; i < top + 3; i++) {
            for (int j = left; j < left + 3; j++) {
                mask |= 1 << grid[i][j];
                sums[i - top] += grid[i][j];
                sums[3 + j - left] += grid[i][j];
            }
        }
        if (mask != 0b11_1111_1110) {
            return false;
        }
        sums[6] = grid[top][left] + grid[top + 1][left + 1] + grid[top + 2][left + 2];
        sums[7] = grid[top + 2][left] + grid[top + 1][left + 1] + grid[top][left + 2];
        for (int sum : sums) {
            if (sum != 15) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{
            {4, 3, 8, 4},
            {9, 5, 1, 9},
            {2, 7, 6, 2}
    }).expect(1);

}
