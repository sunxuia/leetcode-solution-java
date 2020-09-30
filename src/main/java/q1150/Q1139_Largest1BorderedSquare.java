package q1150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1139. Largest 1-Bordered Square
 * https://leetcode.com/problems/largest-1-bordered-square/
 *
 * Given a 2D grid of 0s and 1s, return the number of elements in the largest square subgrid that has all 1s on its
 * border, or 0 if such a subgrid doesn't exist in the grid.
 *
 * Example 1:
 *
 * Input: grid = [[1,1,1],[1,0,1],[1,1,1]]
 * Output: 9
 *
 * Example 2:
 *
 * Input: grid = [[1,1,0,0]]
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= grid.length <= 100
 * 1 <= grid[0].length <= 100
 * grid[i][j] is 0 or 1
 *
 * 题解: 这题的意思是从矩形grid 中找出一个正方形, 正方形的4 边的值都是1 (里面的值无所谓), 求正方形的面积.
 */
@RunWith(LeetCodeRunner.class)
public class Q1139_Largest1BorderedSquare {

    @Answer
    public int largest1BorderedSquare(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int[][] counts = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                counts[i + 1][j + 1] = grid[i][j]
                        + counts[i + 1][j] + counts[i][j + 1] - counts[i][j];
            }
        }

        int res = 0;
        for (int si = 0; si < m; si++) {
            for (int sj = 0; sj < n; sj++) {
                for (int len = 1, ei = si, ej = sj; ei < m && ej < n; len++, ei++, ej++) {
                    if (len == getArea(counts, si, sj, si, ej)
                            && len == getArea(counts, si, sj, ei, sj)
                            && len == getArea(counts, si, ej, ei, ej)
                            && len == getArea(counts, ei, sj, ei, ej)) {
                        res = Math.max(res, len * len);
                    }
                }
            }
        }
        return res;
    }

    private int getArea(int[][] counts, int si, int sj, int ei, int ej) {
        return counts[ei + 1][ej + 1] - counts[ei + 1][sj] - counts[si][ej + 1] + counts[si][sj];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
    }).expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 1, 0, 0}}).expect(1);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[][]{{0}}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {1, 1, 0},
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    }).expect(9);

}
